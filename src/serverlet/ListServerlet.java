package serverlet;

import com.fasterxml.jackson.databind.JsonNode;
import onLineDAO.ListDAOImpl;
import onLineDAO.MerchantDAOImpl;
import onLineDAO.PlatPayMent;
import onLineDAO.UserImp;
import onlineModel.MerchantVO;
import onlineModel.PlatformVO;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class ListServerlet
 */
@Controller
public class ListServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int EXPIRY_TIME_A_DAY = 60 * 60 * 24;

	@Autowired
	private  UserImp userImp;

	@Autowired
	private ListDAOImpl pa;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PlatPayMent platPayMent;

	@Autowired
	private MerchantDAOImpl pl;



	@ModelAttribute
	public void getObject(Map<String,Object> map){
		System.out.println("我有进来modeAttribute");
		if(map.get("merchant") == null){
			map.put("merchant",new MerchantVO());	//商戶
		}
		if(map.get("platform") == null){
			map.put("platform",new PlatformVO());	//接口
		}
	}



	//方法都一定要宣告成public，form對應的modelAttribute才能找到
	@RequestMapping(value = "/merchantDetele/{deletePlatformId}/{deleteMerchantId}",method = RequestMethod.DELETE)
	public String merchantDetele(@PathVariable("deletePlatformId") String deletePlatformId
			,@PathVariable("deleteMerchantId") String deleteMerchantId,Map<String,Object> map)
			throws  Exception {
		System.out.println("进来删除商户的方法");
		int merchId = 0;
		String mString = "";
		try {
			if (StringUtils.isNotEmpty(deleteMerchantId)) {
				merchId = Integer.valueOf(deleteMerchantId);
				MerchantDAOImpl pa = new MerchantDAOImpl();
				mString += pa.deleteMerchent(merchId);
			} else {
				System.out.println("沒有傳入商戶ID merchId is null");
				mString = "沒有傳入商戶ID merchId is null";
			}
		} catch (Exception e) {
			mString += e.toString();
		} finally {

			System.out.println(map);
		}
		//使用RestFul风格的删除，不能直接返回视图，要先回到任一controller，再回视图
		//回到列出所有商户的controller，因为转交预设为Get所以要这样的写法，串被删除的商户的接口编号，再回到原本商户列表的画面
		return "redirect:/merchantList?id=" + deletePlatformId;

	}

	//一键懒人新增商户，从ajax来的
    @RequestMapping(value = "/insertMerchantLazy",method = {RequestMethod.POST,RequestMethod.GET})
	public void insertMerchantLazy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("====insertMerchantLazy===");
		List<Integer> list = new ArrayList();
		int plantNum = 0;
		if (StringUtils.isNotEmpty(request.getParameter("payment_platform_id")))
			plantNum = Integer.valueOf(request.getParameter("payment_platform_id"));
		else
			System.out.println("沒有傳入接口ID plantNum is null");

		// 第一步 先拿到接口有哪些配置需要設定

		Map<String, String> map = new HashMap<String, String>();
		map = platPayMent.getPlantList(plantNum);
		System.out.println("獲取到接口的參數訊息 == >" + map);

		// 第二步 拿到該接口有支持那些支付方式
		String sign = platPayMent.getSignType(plantNum);
		System.out.println("獲取到接口所支持的签名方式 == >" + sign);

		// 第三步 先拿到接口有哪些支付方式

		list = platPayMent.getPlantPayment(plantNum);
		System.out.println("獲取到接口所支持的支付方式 == >" + list);

		// 因為我的mpa 有設定條件不能放入其他的東西 所以將其list裝到 json物件中

		JSONObject json = new JSONObject(map);
		json.put("list", list);
		json.put("sign", sign);
		System.out.println(json);
		response.setContentType("application/json");
		response.getWriter().write(new String(json.toString().getBytes("UTF-8"), "UTF-8"));
	}

	//新增商户
	@RequestMapping(value = "/insertMerchant",method = RequestMethod.POST)
	public String insertMerchant(@Valid @ModelAttribute("merchant") MerchantVO merchantVO,BindingResult errors, Map<String,Object> map) {
		System.out.println("新增的商戶資料為:" + merchantVO);

		System.out.println("====insertMerchant===");
		String msg = "";
		List<Integer> list = new ArrayList();

		String payment_platform_id = merchantVO.getPayment_platform_id();
		String merchant_name = merchantVO.getMerchant_name();
		if (errors.hasErrors()) {
			System.out.println("資料驗證出錯");
			for(FieldError error : errors.getFieldErrors()){
				System.out.println(error.getField() + ":" +  error.getDefaultMessage());
			}

			map.put("isDisplay","true");
			return "index";
		}

		try {

			// MD5密鑰
			String Md5Key = null;
			if (StringUtils.isNotEmpty(merchantVO.getSignature_key()))
				Md5Key = merchantVO.getSignature_key().trim();
			// 平台號 為甚麼要取name 是因為 no 很容易跟接口編號搞混
//			String plantName = null;
//			if (StringUtils.isNotEmpty(merchantVO.getPlatform_no()))
//				plantName = merchantVO.getPlatform_no();
			// 商戶密碼
			String pswName = null;
			if (StringUtils.isNotEmpty(merchantVO.getMerchant_pwd()))
				pswName = merchantVO.getMerchant_pwd().trim();
			// RSA私鑰
			String RSAPublic = null;
			if (StringUtils.isNotEmpty(merchantVO.getRsa_merchant_private_key()))
				RSAPublic = merchantVO.getRsa_merchant_private_key().trim();
			// RSA公鑰
			String RSAPrivate = null;
			if (StringUtils.isNotEmpty(merchantVO.getRsa_merchant_public_key()))
				RSAPrivate = merchantVO.getRsa_merchant_public_key().trim();
	//		// 平台號
	//		String plantNo = null;
	//		if (StringUtils.isNotEmpty(request.getParameter("plantNo")))
	//			plantNo = request.getParameter("plantNo").trim();
			// 支付方式
			String[] payList = null;
			if (request.getParameterValues("payList").length != 0) {
				payList = request.getParameterValues("payList");
				for (String current : payList) {
					list.add(Integer.parseInt(current));
				}
			}

			String state = null;
			if (!StringUtils.isEmpty(RSAPublic) && StringUtils.isEmpty(RSAPrivate)) {
				state = "2";
			} else {
				state = "1";
			}


			String ip = request.getRemoteAddr();




			String merchant_no = merchantVO.getMerchant_no();
			String platform_no = merchantVO.getPlatform_no();
			System.out.println("====開始 insertMerchant===");
			System.out.println("====plantformId ===   " + payment_platform_id);
			System.out.println("====merchantName ===   " + merchant_name);
			System.out.println("====Md5Key ===   " + Md5Key);
			System.out.println("====merchantNo ===   " + merchantVO.getMerchant_no());
			System.out.println("====pswName ===   " + pswName);
			System.out.println("====RSAPrivate ===   " + RSAPrivate);
			System.out.println("====RSAPublic ===   " + RSAPublic);
			System.out.println("====state ===   " + state);
			System.out.println("====plantNo ===   " + merchantVO.getPlatform_no());
			System.out.println("====ip ===   " + ip);
			System.out.println("====list ===   " + list);
			msg +=  pl.insertMerchent(Integer.parseInt(payment_platform_id), merchant_name, Md5Key, merchant_no, pswName, RSAPrivate, RSAPublic,
					state, platform_no, list, ip);
			System.out.println("------------新增商戶完成---------");
			map.put("method", "merchantList");
			pa.PlantNoList("id", payment_platform_id);

		} catch (Exception e) {
			System.out.println(e);
			msg += e;
		} finally {
			map.put("msg", msg);
//			request.setAttribute("msg", msg);
			//request.getRequestDispatcher("/index.jsp").forward(request, response);
            return "index";
		}
	}

	//找所有商户的资讯，又设method = get 是为了让merchantDelete预设Get转交到这边时也能吃到这个方法
	@RequestMapping(value = "/merchantList",method = {RequestMethod.POST,RequestMethod.GET})
	public String merchantList(@RequestParam("id") String platformId,Map<String,Object> map) throws Exception {
		System.out.println("merchantList");
		String meString = "";// 回應訊息
		int id = 0;
		if (StringUtils.isNotEmpty(platformId)){
				id = Integer.valueOf(platformId);
		} else {
			System.out.println("id is null");
		}

		pa.merChantList(id);
		List<MerchantVO> merList = pa.merChantList(id);
		map.put("merList", merList);
		System.out.println("找到的商户列表为:" + merList);

		map.put("method", "merchantList");

		return "index";

	}

	//在DB新增order_page
	@RequestMapping(value = "/insertMypay",method = RequestMethod.POST)
	public String insertMypay(@ModelAttribute("platform") PlatformVO platform, Map<String,Object> map)
			throws SQLException, ServletException, IOException {
		System.out.println("新增的平台為:" + platform);

		int id = 0;
		String meString = "";// 回應訊息
		if (StringUtils.isEmpty(platform.getPlatform_id()))
			id = Integer.valueOf(platform.getPlatform_id());

		String name = new String(platform.getPlatform_name().getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("id==" + id + "name==" + platform.getPlatform_name());
		meString = pa.insertMypay(name, id);
		map.put("method","insertMypay");
		map.put("meString","meString");
		return "index";
	}

	// protected void auth(HttpServletRequest request, HttpServletResponse
	// response) throws Exception {
	// String client_id =
	// "735076368637-p15f2u9l48i5uam636idv4br91skqr19.apps.googleusercontent.com"
	// ;
	// String client_secret = "BZMqJI4PA40oaKKo6ZKGPzTZ" ;
	// String redirect_uri = "http://localhost:8080/MypayOnline1/auth.jsp" ;
	// String response_type = "code";
	// String code = GoogleApiV3.getCode(client_id, response_type,
	// redirect_uri);
	// System.out.println("code==="+code);
	// AccessToken token = new AccessToken();
	// String grant_type = "authorization_code";
	// token = GoogleApiV3.getToken(client_id, grant_type, code, redirect_uri,
	// client_secret);
	// String access_token = token.getAccess_token();
	// String refresh_token = token.getRefresh_token();
	// System.out.println("===access_token :"+access_token+"=== refresh_token
	// :"+refresh_token);
	//
	// }


	@RequestMapping(value = "/query",method = {RequestMethod.POST,RequestMethod.GET})
	public String query(@RequestParam Map<String,Object> reqMap , Map<String,Object> resMap) throws Exception {

        String platformId = "" ;
        String platformUrl = "" ;
        String platformName = "" ;
        if(reqMap.get("platformId") != null){
            platformId = String.valueOf(reqMap.get("platformId"));
        }
        if(reqMap.get("platformUrl") != null){
            platformUrl = String.valueOf(reqMap.get("platformUrl"));
        }
        if(reqMap.get("platformName") != null){
            platformName = String.valueOf(reqMap.get("platformName"));
        }

		String columName = "";
		String columValue = "";
		if (StringUtils.isNotBlank(platformId)) {
            columName = "id";
            columValue = platformId;
		} else if (StringUtils.isNotBlank(platformName)) {
            columName = "name";
            columValue = platformName;
		} else if (StringUtils.isNotBlank(platformUrl)) {
            columName = "url";
            columValue = platformUrl;
		}

		List<PlatformVO> list = pa.PlantNoList(columName, columValue);

        resMap.put("platformInfoList",list);
        resMap.put("method", "query");
        resMap.put("platformId", platformId);
        resMap.put("platformUrl", platformUrl);
        resMap.put("platformName", platformName);

        System.out.println(resMap);

		return "index";
	}

	public void modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String url = request.getParameter("url").trim();
		ListDAOImpl pa = new ListDAOImpl();
		pa.updateUrl(id, url);
		response.getWriter().write(new String("{'result':'靽格摰��'}".getBytes("utf-8"), "ISO-8859-1"));

	}

	@RequestMapping(value = "/loginCheckUser",method = RequestMethod.POST)
    public String loginCheckUser(@RequestParam("userName") String userName , @RequestParam("passWord") String passWord
			,Map<String,Object> map) throws Exception {
        System.out.println("userName:"+userName);
        System.out.println("passWord:"+passWord);

        JsonNode json = userImp.selectUserByUserId(userName);
        System.out.println("json:"+json);
        JSONObject resJson = new JSONObject();


        if(json.size() != 0){
            if(!json.get(0).get("PWD").asText().equals(passWord)){
				map.put("resCode","1001");
				map.put("resMsg","pass word in wrong.");
            }else{
				map.put("resCode","0000");
				map.put("resMsg","success");
            }
        }else{
			map.put("resCode", "1002");
			map.put("resMsg", "not found.");
        }

        System.out.println("错误讯息为:"+map);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type","application/json");

		Cookie cookie = new Cookie(userName,passWord);
		cookie.setMaxAge(EXPIRY_TIME_A_DAY*7); //存活时间七天
		map.put("cookie",cookie);

        return "index";
    }



}

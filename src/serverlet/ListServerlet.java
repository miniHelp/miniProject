package serverlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import onLineDAO.ListDAOImpl;
import onLineDAO.MerchantDAOImpl;
import onLineDAO.PlatPayment;
import onLineDAO.UserImp;
import onlineModel.LoginVO;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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
	private PlatPayment platPayMent;

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
	@RequestMapping(value = "/merchantDelete/{deletePlatformId}/{deleteMerchantId}",method = RequestMethod.DELETE)
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

		PlatformVO platformVO = platPayMent.getPlantList(plantNum);
		System.out.println("獲取到接口的參數訊息 == >" + platformVO);

		// 第二步 拿到該接口有支持那些支付方式
		Character sign = platPayMent.getSignType(plantNum);
		System.out.println("獲取到接口所支持的签名方式 == >" + sign);

		// 第三步 先拿到接口有哪些支付方式

		list = platPayMent.getPlantPayment(plantNum);
		System.out.println("獲取到接口所支持的支付方式 == >" + list);

		// 因為我的mpa 有設定條件不能放入其他的東西 所以將其list裝到 json物件中

		JSONObject json = new JSONObject(new Gson().toJson(platformVO));
		json.put("list", list);
		json.put("sign", sign);
		System.out.println(json);
		response.setContentType("application/json");
		response.getWriter().write(new String(json.toString().getBytes("UTF-8"), "UTF-8"));
	}

	//新增商户
	@RequestMapping(value = "/insertMerchant",method = RequestMethod.POST)
	public String insertMerchant(@Valid @ModelAttribute("merchant") MerchantVO merchantVO,BindingResult errors, Map<String,Object> map) {
		//	這裡的ModelAttribute一定要加，表單才會驗證
		System.out.println("新增的商戶資料為:" + merchantVO);

		System.out.println("====insertMerchant===");
		String msg = "";
		List<Integer> list = new ArrayList();

		Integer payment_platform_id = merchantVO.getPayment_platform_id();
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
			HttpSession session = request.getSession();
			LoginVO loginUser = (LoginVO) session.getAttribute("loggingUser");
			System.out.println("现在登入的使用者资讯是 = " + loginUser);
			String merchant_no = merchantVO.getMerchant_no();
			String platform_no = merchantVO.getPlatform_no();
			String username = loginUser.getLoginUser().getUser_name();
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
			System.out.println("====Username ===   " + username);

			msg +=  pl.insertMerchent(payment_platform_id, merchant_name, Md5Key, merchant_no, pswName, RSAPrivate, RSAPublic,
					state, platform_no, list, ip,username);
			System.out.println("------------新增商戶完成---------");
			map.put("method", "merchantList");
			pa.PlantNoList("id", String.valueOf(payment_platform_id));

		} catch (Exception e) {
			System.out.println(e);
			msg += e;
		} finally {
			map.put("msg", msg);
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
	public String insertMypay(@RequestParam("order_page_id") String order_page_id,
		  	@RequestParam("order_page_name") String order_page_name, Map<String,Object> map)
			throws SQLException, ServletException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		System.out.println("新增的接口orderpageid = " + order_page_id + ",接口名称为 = " + order_page_name);
		String meString = "";// 回應訊息
		int id = 0;
		if (StringUtils.isNotEmpty(order_page_id)){
			id = Integer.valueOf(order_page_id);
		}
		System.out.println("id==" + id + "name==" + order_page_name);
		meString = pa.insertMypay(order_page_name, id);
		System.out.println(meString);
		map.put("method","insertMypay");
		map.put("meString",meString);
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
	public String query(@RequestParam Map<String,String> reqMap , Map<String,Object> resMap) throws Exception {


        String platformId = reqMap.get("platformId");
        String platformName = reqMap.get("platformName");
		String platformUrl = reqMap.get("platformUrl");
		if(StringUtils.isBlank(platformId) && StringUtils.isBlank(platformName) && StringUtils.isBlank(platformUrl)){
			resMap.put("errorMsg", "必须输入其中一个查询参数");
			return "index";
		}

		String columName = "";
		String columValue = "";
		if (StringUtils.isNotBlank(platformId)) {
//            columName = "id";
			columName = "platform_id";
            columValue = platformId;
		} else if (StringUtils.isNotBlank(platformName)) {
//            columName = "name";
			columName = "platform_name";
            columValue = platformName;
		} else if (StringUtils.isNotBlank(platformUrl)) {
//            columName = "url";
			columName = "platform_url";
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

	@RequestMapping(value = "/modifyPlatform",method = RequestMethod.POST)
	public String modifyPlatform(@ModelAttribute("platform") PlatformVO platformVO) throws Exception {
		System.out.println("要修改的接口为: " + platformVO);
		pa.updateUrl(platformVO.getPlatform_id(),platformVO.getPlatform_url());
		return "index";

	}
}

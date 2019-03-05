package serverlet;

import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import onLineDAO.UserImp;
import onlineModel.MerchantVO;
import onlineModel.PlatformVO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.json.JSONObject;
import Util.method;
import onLineDAO.ListDAOImpl;
import onLineDAO.MerchantDAOImpl;
import onLineDAO.plantPayMent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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



	@ModelAttribute
	public void getObject(Map<String,Object> map){
		System.out.println("我有进来modeAttribute");
		System.out.println("modeAttribute的map现况:" + map);
		if(map.get("merchant") == null){
			map.put("merchant",new MerchantVO());	//商戶
		}
		if(map.get("platform") == null){
			map.put("platform",new PlatformVO());	//接口
		}
	}

//	@ModelAttribute
//	public String loginValidation(@RequestParam(value = "method") String doWhat){	//要做什么动作
//
//		String nextPageOrAction = "";
//		String params = doWhat;
//		System.out.println("doWhat:" + doWhat);
//		switch (params) {
//			case "query":
////					query(request, response);
//				break;
//			case "modify":
////					modify(request, response);
//				break;
//			case "insertMypay":
////					insertMypay(request, response);
//				break;
//			case "merchantList":
////					merchantList(request, response);
//				break;
//			case "insertMerchant":// 新增一筆商戶
////					insertMerchant(request, response);
//				break;
//			case "findPlant":// 找到平台資訊
////					findPlant(request, response);
//				break;
//			case "merchantDetele":// 幹掉商戶
////					merchantDetele(request, response);
//				break;
//			case "auth":
//				// auth(request, response);
//				break;
//			case "loginTest":
//				nextPageOrAction = "forward:/loginCheckUser";
////                break;
//			default:
//				break;
//		}
//		return nextPageOrAction;
//	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		try {
//			String params = StringUtils.isNotEmpty(request.getParameter("method")) ? request.getParameter("method")
//					: method.getString(request.getInputStream(), "utf-8", "method");
//			System.out.println("method:" + params);
//			switch (params) {
//			case "modify":
//				modify(request, response);
//				break;
//			case "insertMypay":
//			case "insertMerchant":// 新增一筆商戶
//				//insertMerchant(request, response);
//				break;
//			case "findPlant":// 找到平台資訊
//				findPlant(request, response);
//				break;
//			case "merchantDetele":// 幹掉商戶
//				merchantDetele(request, response);
//				break;
//			case "auth":
//				// auth(request, response);
//				break;
////			case "loginTest":
//                //loginCheckUser(request, response);
////                break;
//			default:
//				break;
//			}
//		} catch (Exception e) {
//			e.getMessage();
//		}
//
//	}

	//方法都一定要宣告成public，form對應的modelAttribute才能找到

	public void merchantDetele(HttpServletRequest request, HttpServletResponse response)
			throws  Exception {
		int merchId = 0;
		String mString = "";
		try {
			if (StringUtils.isNotEmpty(request.getParameter("id"))) {
				merchId = Integer.valueOf(request.getParameter("id"));
				MerchantDAOImpl pa = new MerchantDAOImpl();
				mString += pa.deleteMerchent(merchId);
			} else {
				System.out.println("沒有傳入商戶ID merchId is null");
				mString = "沒有傳入商戶ID merchId is null";
			}
		} catch (Exception e) {
			mString += e.toString();
		} finally {
			// 利用ajax呼叫
			JSONObject json = new JSONObject();
			json.put("mString", mString);
			System.out.println(json);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new String(json.toString().getBytes("UTF-8"), "UTF-8"));
		}

	}

	public void findPlant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("====findPlant===");
		List<Integer> list = new ArrayList();
		int plantNum = 0;
		if (StringUtils.isNotEmpty(request.getParameter("id")))
			plantNum = Integer.valueOf(request.getParameter("id"));
		else
			System.out.println("沒有傳入接口ID plantNum is null");

		// 第一步 先拿到接口有哪些配置需要設定

		plantPayMent pl = new plantPayMent();
		Map<String, String> map = new HashMap<String, String>();
		map = pl.getPlantList(plantNum);
		System.out.println("獲取到接口的參數訊息 == >" + map);

		// 第二步 拿到該接口有支持那些支付方式
		plantPayMent pl2 = new plantPayMent();
		String sign = pl2.getSignType(plantNum);
		System.out.println("獲取到接口所支持的支付方式 == >" + sign);

		// 第三步 先拿到接口有哪些支付方式

		plantPayMent pl3 = new plantPayMent();
		list = pl3.getPlantPayment(plantNum);
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
	public void insertMerchant(@ModelAttribute("merchant") MerchantVO merchant,
									@RequestParam("id") String merchantId,@RequestParam("merchentName") String merchantName,
								@RequestParam("merchentNo") String merchantNo,Map<String,Object> map) throws Exception {


		System.out.println("====insertMerchant===");
		String msg = "";
		List<Integer> list = new ArrayList();

		// 第一步 先判斷拿到的值都不是空值
		// 接口ID
		int plantId = 0;
		if (StringUtils.isNotEmpty(merchantId))
			plantId = Integer.valueOf(merchantId);
		else
			System.out.println("沒有傳入接口ID plantNum is null");

		// 商戶名稱
		if (StringUtils.isNotEmpty(merchantName))
			merchantName = new String(merchantName.getBytes("ISO-8859-1"), "UTF-8");
		else
			System.out.println("沒有傳入商戶名稱 merchentName is null");

		// 商戶號
		if (StringUtils.isEmpty(merchantNo))
			System.out.println("沒有傳入商戶號  merchentNo is null");

		// MD5密鑰
		String Md5Key = null;
		if (StringUtils.isNotEmpty(request.getParameter("Md5Key")))
			Md5Key = request.getParameter("Md5Key").trim();
		// 平台號 為甚麼要取name 是因為 no 很容易跟接口編號搞混
		String plantName = null;
		if (StringUtils.isNotEmpty(request.getParameter("plantName")))
			plantName = new String(request.getParameter("plantName").trim().getBytes("ISO-8859-1"), "UTF-8");
		// 商戶密碼
		String pswName = null;
		if (StringUtils.isNotEmpty(request.getParameter("pswName")))
			pswName = request.getParameter("pswName").trim();
		// RSA私鑰
		String RSAPublic = null;
		if (StringUtils.isNotEmpty(request.getParameter("RSAPublic")))
			RSAPublic = request.getParameter("RSAPublic").trim();
		// RSA公鑰
		String RSAPrivate = null;
		if (StringUtils.isNotEmpty(request.getParameter("RSAPrivate")))
			RSAPrivate = request.getParameter("RSAPrivate").trim();
		// 平台號
		String plantNo = null;
		if (StringUtils.isNotEmpty(request.getParameter("plantNo")))
			plantNo = request.getParameter("plantNo").trim();
		// 支付方式
		String[] payList = null;
		if (request.getParameterValues("payList").length != 0){
			payList = request.getParameterValues("payList");
		for(String current:payList){
			list.add(Integer.parseInt(current));
			}
		}
		// //
		String state = null;
		if (!StringUtils.isEmpty(RSAPublic) && StringUtils.isEmpty(RSAPrivate)) {
			state = "2";
		} else {
			state = "1";
		}

		String ip = request.getRemoteAddr();

		try {

			MerchantDAOImpl pl = new MerchantDAOImpl();
 			ListDAOImpl pa = new ListDAOImpl();
			System.out.println("====開始 insertMerchant===");
			System.out.println("====plantId ===   " + plantId);
			System.out.println("====merchantName ===   " + merchantName);
			System.out.println("====Md5Key ===   " + Md5Key);
			System.out.println("====merchantNo ===   " + merchantNo);
			System.out.println("====pswName ===   " + pswName);
			System.out.println("====RSAPrivate ===   " + RSAPrivate);
			System.out.println("====RSAPublic ===   " + RSAPublic);
			System.out.println("====state ===   " + state);
			System.out.println("====plantNo ===   " + plantNo);
			System.out.println("====ip ===   " + ip);
			System.out.println("====list ===   " + list);
			msg +=  pl.insertMerchent(plantId, merchantName, Md5Key, merchantNo, pswName, RSAPrivate, RSAPublic,
					state, plantNo, list, ip);
			System.out.println("------------新增商戶完成---------");
			request.setAttribute("method", "merchantList");
			pa.PlantNoList("id", String.valueOf(plantId));

		} catch (Exception e) {
			System.out.println(e);
			msg += e;
		} finally {
			request.setAttribute("msg", msg);
			//request.getRequestDispatcher("/index.jsp").forward(request, response);

		}

	}

	//找所有商户的资讯
	@RequestMapping(value = "/merchantList",method = RequestMethod.POST)
	public String merchantList(@RequestParam("id") String platformId,Map<String,Object> map) throws Exception {
		System.out.println("merchantList");
		String meString = "";// 回應訊息
		int id = 0;
		if (StringUtils.isNotEmpty(platformId))
			id = Integer.valueOf(platformId);
		else
			System.out.println("id is null");

		pa.merChantList(id);
		List<MerchantVO> merList = pa.merChantList(id);
		//List<PlatformVO> platlist = pa.PlantNoList("id", String.valueOf(id));
		map.put("merList", merList);
		System.out.println("找到的商户列表为:" + merList);
		System.out.println("找到的商户列表为:" + 12);
		//map.put("platformList", platlist);
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


	@RequestMapping(value = "/query",method = RequestMethod.POST)
	public String query(@RequestParam("id") String platformId,@RequestParam("url") String platformUrl
			,@RequestParam("name") String platformName,Map<String,Object> map) throws Exception {
		String colum = "";
		String str = "";
		if (StringUtils.isNotBlank(platformId)) {
			colum = "id";
			str = platformId;
		} else if (StringUtils.isNotBlank(platformName)) {
			colum = "name";
			str = new String(platformName.getBytes("iso-8859-1"), "utf-8");
		} else if (StringUtils.isNotBlank(platformUrl)) {
			colum = "url";
			str = platformUrl;
		}

		List<PlatformVO> list = pa.PlantNoList(colum, str);

		map.put("platformInfoList",list);

		System.out.println("查詢到的接口資料為:" + list);

		map.put("method", "query");
		map.put("name", str);

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
    public String loginCheckUser(@ModelAttribute("platform") PlatformVO platoform,@ModelAttribute("merchant") MerchantVO merchant,
										@RequestParam("userName") String userName , @RequestParam("passWord") String passWord
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
		map.put("platoform",platoform);
		map.put("merchant",merchant);

        return "index";
    }



}

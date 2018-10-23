package serverlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import Util.method;
import onLineDAO.ListDAOImpl;
import onLineDAO.MerchantDAOmpl;
import onLineDAO.plantPayMent;

/**
 * Servlet implementation class ListServerlet
 */
@WebServlet("/ListServerlet")
public class ListServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListServerlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String params = StringUtils.isNotEmpty(request.getParameter("method")) ? request.getParameter("method")
					: method.getString(request.getInputStream(), "utf-8", "method");
			System.out.println("method:" + params);
			switch (params) {
			case "query":
				query(request, response);
				break;
			case "modify":
				modify(request, response);
				break;
			case "insertMypay":
				insertMypay(request, response);
				break;
			case "merchantList":
				merchantList(request, response);
				break;
			case "insertMerchant":// 新增一筆商戶
				insertMerchant(request, response);
				break;
			case "findPlant":// 找到平台資訊
				findPlant(request, response);
				break;
			case "merchantDetele":// 幹掉商戶
				merchantDetele(request, response);
				break;
			case "auth":
				// auth(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	private void merchantDetele(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, UnsupportedEncodingException, IOException {
		int merchId = 0;
		String mString = "";
		try {
			if (StringUtils.isNotEmpty(request.getParameter("id"))) {
				merchId = Integer.valueOf(request.getParameter("id"));
				MerchantDAOmpl pa = new MerchantDAOmpl();
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

	private void findPlant(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
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

	private void insertMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("====insertMerchant===");
		String msg = "";
		List<Integer> list = new ArrayList();

		// 第一步 先判斷拿到的值都不是空值
		// 接口ID
		int plantId = 0;
		if (StringUtils.isNotEmpty(request.getParameter("id")))
			plantId = Integer.valueOf(request.getParameter("id"));
		else
			System.out.println("沒有傳入接口ID plantNum is null");
		// 商戶名稱
		String merchantName = null;
		if (StringUtils.isNotEmpty(request.getParameter("merchentName")))
			merchantName = new String(request.getParameter("merchentName").getBytes("ISO-8859-1"), "UTF-8");
		else
			System.out.println("沒有傳入商戶名稱 merchentName is null");
		// 商戶號
		String merchentNo = null;
		if (StringUtils.isNotEmpty(request.getParameter("merchentNo")))
			merchentNo = request.getParameter("merchentNo");
		else
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
		if (request.getParameterValues("payList").length != 0)
			payList = request.getParameterValues("payList");

		// //
		String state = null;
		if (!StringUtils.isEmpty(RSAPublic) && StringUtils.isEmpty(RSAPrivate)) {
			state = "2";
		} else {
			state = "1";
		}

		String ip = request.getRemoteAddr();

		try {

			MerchantDAOmpl pl = new MerchantDAOmpl();
			ListDAOImpl pa = new ListDAOImpl();
			System.out.println("====開始 insertMerchant===");
			System.out.println("====plantId ===   " + plantId);
			System.out.println("====merchantName ===   " + merchantName);
			System.out.println("====Md5Key ===   " + Md5Key);
			System.out.println("====merchentNo ===   " + merchentNo);
			System.out.println("====pswName ===   " + pswName);
			System.out.println("====RSAPrivate ===   " + RSAPrivate);
			System.out.println("====RSAPublic ===   " + RSAPublic);
			System.out.println("====state ===   " + state);
			System.out.println("====plantNo ===   " + plantNo);
			System.out.println("====ip ===   " + ip);
			msg +=  pl.insertMerchent(plantId, merchantName, Md5Key, merchentNo, pswName, RSAPrivate, RSAPublic,
					state, plantNo, list, ip);
			System.out.println("------------新增商戶完成---------");
			request.setAttribute("method", "merchantList");
			pa.PlantNoList("id", String.valueOf(plantId));

		} catch (Exception e) {
			System.out.println(e);
			msg += e;
		} finally {
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}

	}

	private void merchantList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("merchantList");
		int id = 0;
		String meString = "";// 回應訊息
		if (StringUtils.isNotEmpty(request.getParameter("id")))
			id = Integer.valueOf(request.getParameter("id"));
		else
			System.out.println("id is null");

		ListDAOImpl pa = new ListDAOImpl();
		List<Map<String, String>> merList = pa.merChantList(id);
		List<Map<String, String>> list = pa.PlantNoList("id", String.valueOf(id));
		request.setAttribute("merList", merList);
		request.setAttribute("list", list);
		request.setAttribute("method", "merchantList");
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void insertMypay(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		System.out.println("insertMypay");

		int id = 0;
		String meString = "";// 回應訊息
		if (StringUtils.isNotEmpty(request.getParameter("id")))
			id = Integer.valueOf(request.getParameter("id"));
		else
			System.out.println("id is null");

		String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("id==" + id + "name==" + name);
		ListDAOImpl pa = new ListDAOImpl();
		meString = pa.insertMypay(name, id);
		request.setAttribute("method", "insertMypay");
		request.setAttribute("meString", meString);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
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

	protected void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String colum = "";
		String str = "";
		ListDAOImpl pa = new ListDAOImpl();
		if (StringUtils.isNotBlank(id)) {
			colum = "id";
			str = id;
		} else if (StringUtils.isNotBlank(name)) {
			colum = "name";
			str = new String(name.getBytes("iso-8859-1"), "utf-8");
		} else if (StringUtils.isNotBlank(url)) {
			colum = "url";
			str = url;
		}
		pa.PlantNoList(colum, str);
		List<Map<String, String>> list = pa.PlantNoList(colum, str);
		request.setAttribute("list", list);
		request.setAttribute("method", "query");
		request.setAttribute("name", str);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String url = request.getParameter("url").trim();
		ListDAOImpl pa = new ListDAOImpl();
		pa.updateUrl(id, url);
		response.getWriter().write(new String("{'result':'靽格摰��'}".getBytes("utf-8"), "ISO-8859-1"));
		;
	}

}

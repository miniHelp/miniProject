package onLineDAO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.client.spreadsheet.WorksheetQuery;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

import onlineModel.AccessToken;

public class GoogleApiV3 {
	// 創建一個name
	private static final String APPLICATION_NAME = "spreadsheet-application-example";

	// //你所用的電子信箱
	/*
	 * 
		要為ACCOUNT_P12_ID設置的值
		註冊服務帳戶時，會發出客戶端ID和電子郵件地址，但程序會使用電子郵件地址。
	 */
	private static final String ACCOUNT_P12_ID = "api3-994@mapget-165603.iam.gserviceaccount.com";
	// //api key
	 private static final File P12FILE = new File("src/googleTest/mapGet-dc95f65efe8e.p12");

	private static final List<String> SCOPES = Arrays.asList("https://docs.google.com/feeds",
			"https://spreadsheets.google.com/feeds");

//	// 這區用的是V4的創建憑證
//	// 全域
//	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//	// http transport
//	private static HttpTransport HTTP_TRANSPORT;
	// 暫存的憑據
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home", ".credentials/sheets.googleapis.com-java-googleApi"));

	private static FileDataStoreFactory DATA_STORE_FACTORY;

	// v3 會直接針對該使用的 所擁有的試算表下去查詢
	private static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

	private static final URL SPREADSHEET_FEED_URL;

	static {
		try {
//			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
			SPREADSHEET_FEED_URL = new URL(SPREADSHEET_URL); //v3
		} catch ( IOException e) {
			throw new RuntimeException(e);
		}
	}

	// post过去 拿到token
	public static String getToken() throws Exception {

		System.out.println("==================getTokenIn================");
		String postUrl = "https://accounts.google.com/o/oauth2/v2/auth";
		String scope = "https://www.googleapis.com/auth/spreadsheets";
		String client_id = "735076368637-4mhg1tbhf9s18ubo8lu8vf08jdtmnflg.apps.googleusercontent.com";
		String access_type = "offline";
		String response_type = "code";
		String redirect_uri = "http://localhost:8080/MypayOnline1/auth.jsp";
		StringBuffer respons = new StringBuffer();
		String getString = "";
		HttpsURLConnection con = null;

		try{
			
		getString = postUrl + "?scope=" + scope + "&client_id=" + client_id + "&access_type=" + access_type
				+ "&response_type=" + response_type + "&redirect_uri=" + redirect_uri;
//	
        
        System.out.println(getString);
       
		}  catch (Exception e) {
            e.printStackTrace();
	
        } finally {
            if (con != null) {
                con.disconnect();
            }
            con =null;
        }
		return getString;

	}
//	
//	/**
//	 * 获取code (先獲得驗證)
//	 * @return return code
//	 * @throws Exception
//	 */
//	public static String getCode(String client_id, String response_type, String redirect_uri) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        // 将用户名和密码放入header中
//        String plainClientCredentials = "user:user";
//        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
//        String url = "http://localhost:8080/MypayOnline1/authorize?client_id=" + client_id + "&"
//                + "response_type=" + response_type + "&" + "redirect_uri=" + redirect_uri;
//
//        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).setConnectionRequestTimeout(5000)
//                .build();
//        HttpGet httpGet = new HttpGet(url);
//        httpGet.setHeader("Authorization", "Basic " + base64ClientCredentials);
//        httpGet.setConfig(config);
//        String result = "";
//        try {
//
//            HttpResponse response = httpClient.execute(httpGet);
//            // 从从定向地址中取得code
//            if (response.getStatusLine().getStatusCode() == 302) {
//                Header header = response.getFirstHeader("Location");
//                String location = header.getValue();
//                String code = location.substring(location.indexOf("=") + 1, location.length());
//                return code;
//            }
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "error";
//    }
//	
//	/**
//	 * 拿到code 拿到永久驗證碼 getCode第二步
//	 * @return AccessToken()
//	 * @throws Exception
//	 */
//	public static AccessToken getToken(String client_id, String grant_type, String code, String redirect_uri,
//            String client_secret) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String url = "http://127.0.0.1:8080/authoriza/oauth/token?client_id=" + client_id + "&grant_type="
//                + grant_type + "&code=" + code + "&redirect_uri=" + redirect_uri + "&client_secret=" + client_secret;
//        HttpPost httpPost = new HttpPost(url);
//        HttpResponse response = null;
//        try {
//            response = httpClient.execute(httpPost);
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String result = "";
//        if (response.getStatusLine().getStatusCode() == 200) {
//            try {
//                result = EntityUtils.toString(response.getEntity(), "UTF-8");
//                // 解析token的json数据
//                JSONObject jsonObject = JSONObject.parseObject(result);
//                AccessToken accessToken = new AccessToken();
//                 
//                accessToken.setAccess_token(jsonObject.getString("access_token"));
//                accessToken.setToken_type(jsonObject.getString("token_type"));
//                accessToken.setRefresh_token(jsonObject.getString("refresh_token"));
//                accessToken.setExpires_in(jsonObject.getString("expires_in"));
//                accessToken.setScope(jsonObject.getString("scope"));
//                return accessToken;
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return new AccessToken();
//    }
	
	
	public static String tokenPost() throws Exception {

		System.out.println("==================tokenPostIn================");
		String client_id = "735076368637-4mhg1tbhf9s18ubo8lu8vf08jdtmnflg.apps.googleusercontent.com";
		String client_secret = "AgI7tdZ8e6c9dTPRIuKFC5FX";
		String redirect_uri = "http://localhost:8080/MypayOnline1/auth.jsp";
		String url = "https://www.googleapis.com/oauth2/v4/token";
		StringBuffer respons = null;
		String code = "4/SQDxv9d0BHPGywoOxyglpM0yMS3aZJjhHLcMCeeDpNq-JDpMjQSjsYh5vWwOnHGIX31jN6duc5mw1PnpKVEhNAQ";
		HttpsURLConnection con = null;

		try{
			
		String  getString = url +"?code="+ code  + "&client_id=" + client_id + "&client_secret=" + client_secret
				+ "&redirect_uri=" + redirect_uri + "&grant_type=authorization_code";
		URL getUrl = new URL(getString);
		con = (HttpsURLConnection) getUrl.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 4.2.1; Nexus 7 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19");
		
		//发送post请求
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(getString);
		wr.flush();
		wr.close();
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        System.out.println("start??");
        while ((inputLine = in.readLine()) != null) {
            respons.append(inputLine);
        }
        
        in.close();
        
        System.out.println(respons.toString());
       
		}  catch (Exception e) {
            e.printStackTrace();
	
        } finally {
            if (con != null) {
                con.disconnect();
            }
            con =null;
        }
		return respons.toString();
		
		
//
//		URL connect = new URL(postUrl);
//		HttpsURLConnection conn = (HttpsURLConnection) connect.openConnection();
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		conn.setRequestProperty("charset", "utf-8");

	}

	// 獲取憑證
	 private static Credential authorize() throws Exception {
	 System.out.println("authorize in");
	
	 HttpTransport httpTransport =
	 GoogleNetHttpTransport.newTrustedTransport();
	 JsonFactory jsonFactory = new JacksonFactory();
	
	 GoogleCredential credential = new GoogleCredential.Builder()
	 .setTransport(httpTransport)
	 .setJsonFactory(jsonFactory)
	 .setServiceAccountId(ACCOUNT_P12_ID)
	 .setServiceAccountPrivateKeyFromP12File(P12FILE)
	 .setServiceAccountScopes(SCOPES)
	 .build();
	
	 boolean ret = credential.refreshToken();
	 // debug dump
	 System.out.println("refreshToken:" + ret);
	
	 // debug dump
	 if (credential != null) {
	 System.out.println("AccessToken:" + credential.getAccessToken());
	 }
	
	 System.out.println("authorize out");
	
	 return credential;
	 }

//	// 開始創造 被授權的項目 V4
//	/**
//	 * @throws Exception *
//	 */
//	public static Credential authorize() throws Exception {
//		// 載入用戶憑證
//		InputStream inputStream = GoogleApi.class.getResourceAsStream("/googleTest/client_secret.json");
//		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));
//
//		// 創建流程 並觸發使用者的授權需求
//		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
//				clientSecrets, (Collection<String>) SCOPES).setDataStoreFactory(DATA_STORE_FACTORY)
//						.setAccessType("offline").build();
//		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//		System.out.println("credential憑據 存於)" + DATA_STORE_DIR.getAbsolutePath());
//		return credential;
//
//	}

	// 建立並且返回一個使用者的 試算表api 客戶端服務 (V3版本)
	private static SpreadsheetService getService() throws Exception {
		System.out.println("====get Service====");

		SpreadsheetService service = new SpreadsheetService(APPLICATION_NAME);
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		Credential credential = authorize();
		service.setOAuth2Credentials(credential);

		System.out.println("=======service " + service.toString() + "======");
		System.out.println("=======Scheme " + service.getSchema().toString() + "======");
		System.out.println("=======Protocol " + service.getProtocolVersion().getVersionString() + "======");
		System.out.println("=======ServiceVersion " + service.getServiceVersion() + "======");
		System.out.println("=======Service out======");
		return service;
	}

	
	/*
	 **電子表格列表
	 * 檢索您可以訪問的電子表格列表
	 */
	private static List<SpreadsheetEntry> findAllSpreadsheets(SpreadsheetService service)
			throws IOException, ServiceException {
		System.out.println("==findAllSpreadsheets start====");
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);

		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		// debug dump
		for (SpreadsheetEntry spreadsheet : spreadsheets) {
			System.out.println("title: " + spreadsheet.getTitle().getPlainText());
		}

		System.out.println("findAllSpreadsheets out");
		return spreadsheets;
	}

	// 按電子表格名稱搜索
	// 指定電子表格名稱和搜索
	private static SpreadsheetEntry findSpreadsheetByName(SpreadsheetService service, String spreadsheetName)
			throws Exception {
		System.out.println("findSpreadsheetByName in");
		SpreadsheetQuery sheetQuery = new SpreadsheetQuery(SPREADSHEET_FEED_URL);
		sheetQuery.setTitleQuery(spreadsheetName);
		SpreadsheetFeed feed = service.query(sheetQuery, SpreadsheetFeed.class);
		SpreadsheetEntry ssEntry = null;
		if (feed.getEntries().size() > 0) {
			ssEntry = feed.getEntries().get(0);
			
		}
		System.out.println("findSpreadsheetByName out");
		return ssEntry;
	}

	// 按工作表名稱搜索
	private static WorksheetEntry findWorksheetByName(SpreadsheetService service, SpreadsheetEntry ssEntry,
			String sheetName) throws Exception {
		System.out.println("findWorksheetByName in");
		WorksheetQuery worksheetQuery = new WorksheetQuery(ssEntry.getWorksheetFeedUrl());
		worksheetQuery.setTitleQuery(sheetName);
		WorksheetFeed feed = service.query(worksheetQuery, WorksheetFeed.class);
		WorksheetEntry wsEntry = null;
		if (feed.getEntries().size() > 0) {
			wsEntry = feed.getEntries().get(0);
		}
		System.out.println("findWorksheetByName out");
		return wsEntry;
	}

	// 添加工作表
	private static WorksheetEntry addWorksheet(SpreadsheetService service, SpreadsheetEntry ssEntry, String sheetName,
			int colNum, int rowNum) throws Exception {
		System.out.println("addWorksheet in");
		WorksheetEntry wsEntry = new WorksheetEntry();
		wsEntry.setTitle(new PlainTextConstruct(sheetName));
		wsEntry.setColCount(colNum);
		wsEntry.setRowCount(rowNum);
		URL worksheetFeedURL = ssEntry.getWorksheetFeedUrl();
		System.out.println("addWorksheet out");
		return service.insert(worksheetFeedURL, wsEntry);
	}

	/**
	 * 添加工作表標題行 使用參數查詢指定標題行（第一行），並將標題名稱寫入該行的單元格
	 */
	private static void insertHeadRow(SpreadsheetService service, WorksheetEntry wsEntry, List<String> header,
			String query) throws Exception {
		System.out.println("insertHeadRow in");

		URL cellFeedUrl = new URI(wsEntry.getCellFeedUrl().toString() + query).toURL();
		CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

		for (int i = 0; i < header.size(); i++) {
			cellFeed.insert(new CellEntry(1, i + 1, header.get(i)));
		}

		System.out.println("insertHeadRow out");
	}

	// 在工作表中添加一行
	private static void insertDataRow(SpreadsheetService service, WorksheetEntry wsEntry, Map<String, Object> values)
			throws Exception {
		System.out.println("insertDataRow in");

		ListEntry dataRow = new ListEntry();

		values.forEach((title, value) -> {
			dataRow.getCustomElements().setValueLocal(title, value.toString());
		});

		URL listFeedUrl = wsEntry.getListFeedUrl();
		service.insert(listFeedUrl, dataRow);

		System.out.println("insertDataRow out");
	}

	// 更新工作表行
	private static void updateDataRow(SpreadsheetService service, WorksheetEntry wsEntry, int rowNum,
			Map<String, Object> values) throws Exception {
		System.out.println("updateDataRow in");

		URL listFeedUrl = wsEntry.getListFeedUrl();
		ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

		ListEntry row = listFeed.getEntries().get(rowNum);

		values.forEach((title, value) -> {
			row.getCustomElements().setValueLocal(title, value.toString());
		});

		row.update();

		System.out.println("updateDataRow out");
	}

	// 範圍特定的查詢
	private static String makeQuery(int minrow, int maxrow, int mincol, int maxcol) {
		String base = "?min-row=MINROW&max-row=MAXROW&min-col=MINCOL&max-col=MAXCOL";
		return base.replaceAll("MINROW", String.valueOf(minrow)).replaceAll("MAXROW", String.valueOf(maxrow))
				.replaceAll("MINCOL", String.valueOf(mincol)).replaceAll("MAXCOL", String.valueOf(maxcol));
	}

	private static void deleteWorksheet(WorksheetEntry wsEntry) throws Exception {
		System.out.println("deleteWorksheet in");
		wsEntry.delete();
		System.out.println("deleteWorksheet out");
	}

	public static void main(String[] args) throws Exception {
		System.out.println("main start");
//		System.out.println(tokenPost());

//		getToken();
		SpreadsheetService service = getService();

		findAllSpreadsheets(service);

		 String ssName = "Test";
		
		 SpreadsheetEntry ssEntry = findSpreadsheetByName(service, ssName);
//		 System.out.println("================ getEtag==========="+ssEntry.getEtag());
//		 System.out.println("================ getId==========="+ssEntry.getId());
//		 System.out.println("================ getKey==========="+ssEntry.getKey());
//		
//		 System.out.println("================ toString()==========="+ssEntry.toString());
//		
//		 
//		 System.out.println("================ 獲取想要的工作表 ===========");
//		 WorksheetEntry worksheetEntry = findWorksheetByName(service, ssEntry, ssName);
//		 ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());
//		   		    ListFeed listFeed = service.query(listQuery,ListFeed.class);
//		    ListEntry listEntry = listFeed.getEntries().get(0);
//	        CustomElementCollection elements = listEntry.getCustomElements();
//	        System.out.println("HEADER1：" + elements.getValue("1.支付宝扫码"));
//	        System.out.println("HEADER2：" + elements.getValue("header2"));
//	        System.out.println("HEADER3：" + elements.getValue("eader3"));
//		 
		 String ssName2 = "Test3";
		 
		 
		 
		 System.out.println("================ 獲取想要的工作表 2===========");
		 WorksheetEntry newWorksheet = addWorksheet(service, ssEntry, ssName2, 50, 100);
			 // insert
			 Map<String, Object> insertValues1 = new HashMap<>();
			 insertValues1.put("test1", "2015-09-01");
			 insertValues1.put("test2", 1200);
			 insertValues1.put("test3", 1300);
			 insertValues1.put("test4", 1400);
			 insertValues1.put("test5", 1500);
			 insertValues1.put("test6", 1600);
			
			 insertDataRow(service, newWorksheet, insertValues1);

			 WorksheetEntry worksheetEntry2 = findWorksheetByName(service, ssEntry, ssName2);
			 ListQuery listQuery2 = new ListQuery(worksheetEntry2.getListFeedUrl());
			    listQuery2.setSpreadsheetQuery("header1 = \" HEADER1-1 \""); // OK 
			    listQuery2.setSpreadsheetQuery("\"header1-9 \"= \"HEADER1-1 \""); // OK 
			    ListFeed listFeed2 = service.query(listQuery2,ListFeed .class);
			    ListEntry listEntry2 = listFeed2.getEntries().get(0);
		        CustomElementCollection elements2 = listEntry2.getCustomElements();
		        System.out.println("HEADER1：" + elements2.getValue("HEADER1"));
		        System.out.println("HEADER2：" + elements2.getValue("header2"));
		        System.out.println("HEADER3：" + elements2.getValue("eader3"));
		// String wsName = "Test";

		// WorksheetEntry wsEntry = findWorksheetByName(service, ssEntry,
		// wsName);
		// if (wsEntry != null) {
		// deleteWorksheet(wsEntry);
		// }
		//

		// 工作表的標題名稱
		// List<String> header = new ArrayList<>();
		// header.add("test1");
		// header.add("test2");
		// header.add("test3");
		// header.add("test4");
		// header.add("test5");
		// header.add("test6");
		//
		// insertHeadRow(service, newWorksheet, header, makeQuery(1, 1, 1, 5));

		// // debug dump
		// specCell(service, newWorksheet, makeQuery(1,1,1,5));

	
		//
		//
		// // insert
		// Map<String, Object> insertValues2 = new HashMap<>();
		// insertValues2.put("test1", "2015-09-02");
		// insertValues2.put("test2", 2200);
		// insertValues2.put("test3", 2300);
		// insertValues2.put("test4", 2400);
		// insertValues2.put("test5", 2500);
		// insertValues2.put("test6", 2600);
		//
		// insertDataRow(service, newWorksheet, insertValues2);
		//
		//
		// // update
		// Map<String, Object> updateValues = new HashMap<>();
		// updateValues.put("test1", "2015-09-01");
		// updateValues.put("test2", 1202);
		// updateValues.put("test3", 1303);
		// updateValues.put("test4", 1404);
		// updateValues.put("test5", 1505);
		// updateValues.put("test6", 1606);
		//
		// updateDataRow(service, newWorksheet, 0, updateValues);

		System.out.println("main end");
	}


}

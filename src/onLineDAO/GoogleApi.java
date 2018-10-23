package onLineDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.BatchGetByDataFilter;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesByDataFilterRequest;
import com.google.api.services.sheets.v4.model.DataFilter;
import com.google.api.services.sheets.v4.model.SearchDeveloperMetadataRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

public class GoogleApi {
	// application name
	private static final String APPLICATION_NAME = "googleapi";

	// �Ȧs���̾�
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home", ".credentials/sheets.googleapis.com-java-googleApi"));

	private static FileDataStoreFactory DATA_STORE_FACTORY;

	// ����
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	// http transport
	private static HttpTransport HTTP_TRANSPORT;

	// ��google api�һݭn�������ܼƽd��
	// �p�G���N�ʨ�o�� �ЧR�����Ȧs���̾� DATA_STORE_DIR
	private static final List SCOPES = (List) Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	// �@�ҰʴN�|���檺�Ʊ�
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);

		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	// �}�l�гy �Q���v������
	/**
	 * @throws Exception *
	 */
	public static Credential authorize() throws Exception {
		// ���J�Τ����
		InputStream inputStream = GoogleApi.class.getResourceAsStream("/googleTest/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

		// �Ыجy�{ ��Ĳ�o�ϥΪ̪����v�ݨD
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, (Collection<String>) SCOPES).setDataStoreFactory(DATA_STORE_FACTORY)
						.setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("credential�̾� �s��)" + DATA_STORE_DIR.getAbsolutePath());
		return credential;

	}

	// �إߨåB��^�@�ӨϥΪ̪� �պ��api �Ȥ�ݪA�� V4����
	public static Sheets getSheetsService() throws Exception {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
	}
	
	
	// �إߨåB��^�@�ӨϥΪ̪� �պ��api �Ȥ�ݪA�� (V3����)
	private static SpreadsheetService getService() throws Exception{
		System.out.println("====get Service====");
		
		SpreadsheetService service = new SpreadsheetService(APPLICATION_NAME);
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		Credential credential = authorize();
		service.setOAuth2Credentials(credential);
		
		System.out.println("=======Scheme "+service.getSchema().toString()+"======");
		System.out.println("=======Protocol "+service.getProtocolVersion().getVersionString()+"======");
		System.out.println("=======ServiceVersion "+service.getServiceVersion()+"======");
		System.out.println("=======Service out======");
		return service;
	}
	
//

	public static void main(String[] args) throws Exception {
		// �إߤ@�ӨϥΪ�
		Sheets service = getSheetsService();

		// ��J�A���պ�� ��誺�N�X
		/**
		 * https://docs.google.com/spreadsheets/d/1A1OCr0Ca-CSzw9C_pQyCfv7tj_VeUBgtQUb235UhwVY/edit#gid=0
		 * 1A1OCr0Ca-CSzw9C_pQyCfv7tj_VeUBgtQUb235UhwVY/edit#gid=0<<<
		 */

		String spreadSheetsId = "1A1OCr0Ca-CSzw9C_pQyCfv7tj_VeUBgtQUb235UhwVY";
		String range = "A2:E";
		ValueRange response = service.spreadsheets().values().get(spreadSheetsId, range).execute();
		List<List<Object>> values = response.getValues();
		if (values.isEmpty() || values.size() == 0) {
			System.out.println("�S���d�����");
		} else {
			System.out.println("�ڮ���F��F");
			for (List row : values) {
				System.out.println(row);
			}
		}
//		BatchGetByDataFilter search = service.spreadsheets().values().batchGetByDataFilter(spreadSheetsId, new BatchGetValuesByDataFilterRequest().setMajorDimension("ROWS"));
//		search.execute().getValueRanges().forEach((s) -> {System.err.println(s);});		
		// java.util.List < DataFilter > dateFilter = new
		// BatchGetValuesByDataFilterRequest().setMajorDimension("COLUMNS").getDataFilters();
		//
		// dateFilter.forEach((s) ->{if(s !=null )System.out.println(s);});
//		
	}

}

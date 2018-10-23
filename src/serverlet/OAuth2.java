package serverlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 * Servlet implementation class OAuth2
 */
@WebServlet("/OAuth2")
public class OAuth2 extends AbstractAuthorizationCodeServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	public static AuthorizationCodeFlow flow;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OAuth2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * doGet複寫
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		Credential credential = this.getCredential();
		if (credential != null) {
			if (credential.getExpiresInSeconds() < 0)
				// if the accessToken expired, refresh the accessToken using
				// refreshToken
				if (credential.refreshToken()) {
					System.out.println("refresh the access token's expired date="
							+ new Date(credential.getExpirationTimeMilliseconds()));
				}
			req.getSession().setAttribute("credential", this.getCredential());
			StringBuffer sb = new StringBuffer();
			sb.append("http://").append(req.getLocalAddr() + ":" + req.getLocalPort()).append(req.getContextPath())
					.append("/auth.jsp");
			try {
				response.sendRedirect(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(req.getLocalAddr() + ":" + req.getLocalPort()).append(req.getContextPath())
				.append("/oauth2callback");
		String redirectUri = sb.toString();
		return redirectUri;
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		// TODO Auto-generated method stub

		return "root";
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		// TODO Auto-generated method stub

		String clientID = "735076368637-coun2mf15sn9t23vkubnk8ehdr0l12l9.apps.googleusercontent.com";
		String password = "AgI7tdZ8e6c9dTPRIuKFC5FX";
		List scopes = new ArrayList();
		scopes.add("https://www.googleapis.com/auth/spreadsheets");
		String CREDENTIALS_DIRECTORY = ".oauth-credentials";

		File file = new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY);

		FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(file);
		DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore("spreadsheets");

		flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
				clientID, password, scopes).setDataStoreFactory(fileDataStoreFactory).
				setAccessType("offline").
				setApprovalPrompt("force").
				build();

		return flow;

	}

	@Override
	protected void onAuthorization(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp, AuthorizationCodeRequestUrl authorizationUrl)
			throws javax.servlet.ServletException, IOException {

		Credential credential = flow.loadCredential("root");
		if (credential != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("http://").append(req.getLocalAddr() + ":" + req.getLocalPort()).append(req.getContextPath())
					.append("/auth.jsp");
			req.getSession().setAttribute("credential", credential);
		} else {
			super.onAuthorization(req, resp, authorizationUrl);
		}
	}

}

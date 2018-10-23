package serverlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;

/**
 * Servlet implementation class AuthorizationCodeCallbackServlet
 */
@WebServlet("/AuthorizationCodeCallbackServlet")
public class AuthorizationCodeCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {
	private static final long serialVersionUID = 1L;
	private static String firstPage = "" ;
	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
		// TODO Auto-generated method stub
		return OAuth2.flow;
	}
	
	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	   throws ServletException, IOException {
	  StringBuilder sb = new StringBuilder();
	   
	  sb.append("http://").append(req.getLocalAddr() + ":" + req.getLocalPort()).append(req.getContextPath())
	    .append("/auth.jsp");
	  req.getSession().setAttribute("credential", credential);
	  resp.sendRedirect(sb.toString());
	  System.out.println("onSuccess ==="+sb.toString());
	 }
	 
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorizationCodeCallbackServlet() {
		super();
		// TODO Auto-generated constructor stub
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
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append("127.0.0.1:" + req.getLocalPort()).append(req.getContextPath())
				.append("/oauth2callback");
		String redirectUri = sb.toString();
		return redirectUri;
	}

	@Override
	protected String getUserId(HttpServletRequest arg0) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  return "root";
	}
	
	@Override
	protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	   throws ServletException, IOException {
	  // handle error
	 
	  String error = errorResponse.getError();
	  System.out.println(error);
	 }
	 

}

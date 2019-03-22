package serverlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginFilter implements Filter {

    private static final long serialVersionUID = 1L;

    private FilterConfig config;

    public void init(FilterConfig config) {
        this.config = config;
    }

    public void destroy() {
        config = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 【取得 session】
        HttpSession session = req.getSession();
        // 【從 session 判斷此員工是否登入過】
        Object memVO = session.getAttribute("loggingUser");
        if (memVO == null) {
            res.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }else {
            chain.doFilter(req,res);	//這行不能少
        }
    }

}

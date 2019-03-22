package serverlet;

import onLineDAO.UserImp;
import onlineModel.LoginVO;
import onlineModel.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@SessionAttributes("loggingUser")   //正在登入的使用者，存入Session的當前登入的使用者物件
@RequestMapping("/user")
public class UserServlet {

    private static final int A_HOUR_SECOND = 60 * 60 * 3600;   //一小时的秒数

    @Autowired
    private UserImp ul;

    @RequestMapping(value = "/login")
    public String login(){

        return "login";
    }

    @RequestMapping(value = "/logOut")
    public RedirectView logOut(){   //重導回view
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.removeAttribute("loggingUser");
        return new RedirectView("login");
    }

    @RequestMapping(value = "/loginCheckUser",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView loginCheckUser(@RequestParam("userName") String userName , @RequestParam("passWord") String passWord) throws Exception {

        ModelAndView mav = new ModelAndView();
		String toWhere = "";
        LoginVO loginVO = ul.loginCheck(userName,passWord);
        if(loginVO.isLoginSuccess()){
            HttpServletRequest  request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            HttpSession session = request.getSession(true);
            mav.addObject("loggingUser",loginVO);   //把正在登入的使用者资讯存进session
            session.setMaxInactiveInterval(A_HOUR_SECOND);  //让session存活一小时
            //登入成功的时候设定Cookie

            String [] rememberCookie = request.getParameterValues("ck_rmbUser");    //取checkbox
            boolean isRemember = (rememberCookie != null);
            System.out.println("要记住帐密吗 = " + isRemember);
            if(isRemember){  //如果要记住cookie
                Cookie loginId = new Cookie("userName",userName);
                Cookie loginPwd = new Cookie("passWord",passWord);
                Cookie remeberLoginInfo = new Cookie("remember","remember");
                loginId.setMaxAge(A_HOUR_SECOND * 24 * 7);  //7天的cookie时效
                loginPwd.setMaxAge(A_HOUR_SECOND * 24 * 7);  //7天的cookie时效
                response.addCookie(loginId);
                response.addCookie(loginPwd);
                response.addCookie(remeberLoginInfo);
            }else{                      //如果不要记住cookie
                Cookie [] cookies = request.getCookies();
                for(Cookie cookie : cookies){
                    if("userName".equals(cookie.getName()) || "passWord".equals(cookie.getName()) || "remember".equals(cookie.getName())){
                        cookie.setMaxAge(0);    //删除cookie要把age设为0
                        response.addCookie(cookie); //还要重新加入到response
                    }
                }
            }
            toWhere = "index";
        }else{
			toWhere = "login";
            mav.addObject("errorMsg",loginVO.getLoginMessage());
        }
        mav.setViewName(toWhere);
        return mav;

    }
}

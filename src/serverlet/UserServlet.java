package serverlet;

import onLineDAO.UserImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserServlet {

    @Autowired
    private UserImp ul;

    @RequestMapping(value = "/login")
    public String login(){

        return "login";
    }

    @RequestMapping(value = "/loginCheckUser",method = RequestMethod.POST)
    public String loginCheckUser(@RequestParam("userName") String userName , @RequestParam("passWord") String passWord
            , Map<String,Object> map) throws Exception {

        System.out.println("userName:"+userName);
        System.out.println("passWord:"+passWord);
		String toWhere = "";
        boolean isLoginSuccess = ul.loginCheck(userName,passWord);
        if(isLoginSuccess){
			toWhere = "index";
		}else{
			toWhere = "login";
		}
        System.out.println("登入是否成功 = " + isLoginSuccess);
        return toWhere;


//        JsonNode json = userImp.selectUserByUserId(userName);
//        System.out.println("json:"+json);
//        JSONObject resJson = new JSONObject();
//
//
//        if(json.size() != 0){
//            if(!json.get(0).get("PWD").asText().equals(passWord)){
//				map.put("resCode","1001");
//				map.put("resMsg","pass word in wrong.");
//            }else{
//				map.put("resCode","0000");
//				map.put("resMsg","success");
//            }
//        }else{
//			map.put("resCode", "1002");
//			map.put("resMsg", "not found.");
//        }
//
//        System.out.println("错误讯息为:"+map);
//
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.add("Content-Type","application/json");
//
//		Cookie cookie = new Cookie(userName,passWord);
//		cookie.setMaxAge(EXPIRY_TIME_A_DAY*7); //存活时间七天
//		map.put("cookie",cookie);

    }
}

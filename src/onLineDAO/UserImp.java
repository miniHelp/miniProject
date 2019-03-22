package onLineDAO;

import Util.DBQueryRunner;
import Util.GetConnection;
import Util.HibernateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import onlineModel.LoginVO;
import onlineModel.UserVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.tomcat.util.security.MD5Encoder;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Caster on 2018/11/5.
 */
@Component
public class UserImp implements UserDAO{

    @Override
    public LoginVO loginCheck(String login_id,String login_password)throws SQLException {
        boolean isLoginSuccess = false;
        LoginVO loginVO = isLoingIdExist(login_id);
        if(!loginVO.isLoginIdExist()) {  //如果账号不存在
            loginVO.setLoginSuccess(isLoginSuccess);
            loginVO.setLoginMessage("没有此账号");
        }else{      //如果账号存在，则继续验证密码是否正确
            String p1 = loginVO.getLoginUser().getLogin_pwd();
            String p2 = DigestUtils.md5Hex(login_password.getBytes());
            try {
                if(p1.equals(p2)){
                    isLoginSuccess = true;
                    loginVO.setLoginMessage("登入成功");
                }else{
                    loginVO.setLoginMessage("密码错误");
                }
                loginVO.setLoginSuccess(isLoginSuccess);
            }catch(Exception e){
                System.out.println(e);
                throw e;
            }
        }
        return loginVO;

    }

    private LoginVO isLoingIdExist(String login_id) throws SQLException {   //检查账号是否存在
        Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();
        UserVO userVO = null;
        boolean isLoingIdExist = false;
        try {
            session.beginTransaction();
            Query<UserVO> query = session.createQuery("from UserVO where login_id = :login_id",UserVO.class);
            query.setParameter("login_id",login_id);
            userVO = (UserVO)query.getResultList().get(0);
            if(userVO != null){
                isLoingIdExist = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            session.getTransaction().rollback();
        }
        LoginVO loginVO = new LoginVO();
        loginVO.setLoginIdExist(isLoingIdExist);
        loginVO.setLoginUser(userVO);

        return loginVO;
    }

}

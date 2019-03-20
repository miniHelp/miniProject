package onLineDAO;

import Util.DBQueryRunner;
import Util.GetConnection;
import Util.HibernateUtil;
import com.fasterxml.jackson.databind.JsonNode;
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

/**
 * Created by Caster on 2018/11/5.
 */
@Component
public class UserImp implements UserDAO{
    @Override
    public UserVO selectUserByUserId(String login_id) throws SQLException {
        Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();
        UserVO userVO = null;
        try {
            session.beginTransaction();
            Query<UserVO> query = session.createQuery("from UserVO where login_id = :login_id",UserVO.class);
            query.setParameter("login_id",login_id);
            userVO = (UserVO)query.getResultList().get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            session.getTransaction().rollback();
        }
        return userVO;
    }


    @Override
    public boolean loginCheck(String login_id,String login_password)throws SQLException {
        UserVO userVO = selectUserByUserId(login_id);
        boolean isLoginSuccess = false;
        String p1 = userVO.getLogin_pwd();
        String p2 = DigestUtils.md5Hex(login_password.getBytes());
        System.out.println("DB捞出来该User的密码为:" + p1);
        System.out.println("使用者自己打的密码为:" + p2);
        try {
            if(p1.equals(p2)){
                isLoginSuccess = true;
            }else{
                isLoginSuccess = false;
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
        return isLoginSuccess;
    }
}

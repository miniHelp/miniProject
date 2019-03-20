package onLineDAO;

import com.fasterxml.jackson.databind.JsonNode;
import onlineModel.LoginVO;
import onlineModel.UserVO;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Caster on 2018/11/5.
 */
public interface UserDAO {
    public LoginVO loginCheck(String username,String userpassword) throws SQLException; //检验密码是否输入正确
//    public LoginVO isLoingIdExist(String username) throws SQLException; //检查该账号是否存在
}

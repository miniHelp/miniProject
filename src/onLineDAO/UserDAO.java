package onLineDAO;

import com.fasterxml.jackson.databind.JsonNode;
import onlineModel.UserVO;

import java.sql.SQLException;

/**
 * Created by Caster on 2018/11/5.
 */
public interface UserDAO {
    public UserVO selectUserByUserId(String userId) throws SQLException;
    public boolean loginCheck(String username,String userpassword) throws SQLException;
}

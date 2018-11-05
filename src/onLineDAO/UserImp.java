package onLineDAO;

import Util.DBQueryRunner;
import Util.GetConnection;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Caster on 2018/11/5.
 */
public class UserImp implements UserDAO{
    @Override
    public JsonNode selectUserByUserId(String userName) throws SQLException {
        GetConnection get = new GetConnection();
        Connection conn = null;
        try {
            conn = get.getConnection();
            String sql = "select * from PY_USER where name = ? ";
            return DBQueryRunner.getJsonArray(conn,sql,userName);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conn.close();
        }
        return null;
    }
}

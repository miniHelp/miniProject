package onLineDAO;

import com.fasterxml.jackson.databind.JsonNode;
import java.sql.SQLException;

/**
 * Created by Caster on 2018/11/5.
 */
public interface UserDAO {
    public JsonNode selectUserByUserId(String userId) throws SQLException;
}

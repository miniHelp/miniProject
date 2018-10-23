package onLineDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface PlatformDAO {
	public List<Integer> getPlantPayment (int plantNum) throws SQLException;
	public Map<String, String>  getPlantList (int plantNum) throws SQLException;
	public String getSignType(int plantNo) throws SQLException;
	
	
	
}

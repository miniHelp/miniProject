package onLineDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import onlineModel.PlatformVO;
import org.json.JSONObject;

public interface PlatformDAO {
	public List<Integer> getPlantPayment (int plantNum) throws SQLException;
	public PlatformVO getPlatformInfo (int plantNum) throws SQLException;
	public Character getSignType(int plantNo) throws SQLException;
	
	
	
}

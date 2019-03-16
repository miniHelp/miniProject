package onLineDAO;

import onlineModel.MerchantVO;
import onlineModel.PlatformVO;

import java.sql.SQLException;
import java.util.List;


public interface ListDAO {
	public List<PlatformVO> PlantNoList (String colum , String str) throws Exception;
	public List<MerchantVO> merChantList (int colum) throws SQLException;
	public boolean isPlanNo(String oldUrl , String newUrl);
	public void updateUrl (String id,String url) throws Exception;
	public String insertMypay (String name , int id) throws SQLException;

	 
}
package onLineDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface ListDOA {
	public List<Map<String, String>> PlantNoList (String colum , String str) throws Exception;
	public List<Map<String, String>> merChantList (int colum) throws SQLException;
	public boolean isPlanNo(String oldUrl , String newUrl);
	public void updateUrl (String id,String url) throws Exception;
	public String insertMypay (String name , int id) throws SQLException;

	 
}
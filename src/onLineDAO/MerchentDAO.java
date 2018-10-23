package onLineDAO;

import java.sql.SQLException;
import java.util.List;

public interface MerchentDAO {
	//新增商户的部份

		public String insertMerchent (int plant, String merchantName, String MD5, String merchentNo, String password,
				String RSAPrivate, String RSAPublic, String type, String plantNo ,List<Integer>list ,String ip)throws Exception;
		public String insertMerPayment (int id , String type , List<Integer>  payment ) throws SQLException;
		public String insertMerLog( String userIp, int merchId ,String name) throws SQLException;
		public String deleteMerchent (int merchId) throws  SQLException;

}

package onLineDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Util.GetConnection;

public class plantPayMent implements PlatformDAO {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		plantPayMent pl = new plantPayMent();
		String msString = pl.getSignType(1093);
		System.out.println(msString);
	}

	@Override
	public List<Integer> getPlantPayment(int plantNum) throws SQLException {
		String sql = "select PAYMENT_METHOD_ID from PY_PAYMENT_PLATFORM_METHOD where PAYMENT_PLATFORM_ID = ?  order by PAYMENT_METHOD_ID asc";
		System.out.println(sql);
		GetConnection get = new GetConnection();
		Connection conn = get.getConnection();
		List<Integer> list = new ArrayList<>();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try {
			// 建立字串
			pstmt.setInt(1, plantNum);

			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				list.add(result.getInt(1));
			}

			System.out.println(list);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			pstmt.close();
			conn.close();
		}

		return list;
	}

	@Override
	public Map<String, String> getPlantList(int plantNum) throws SQLException {
		String sql = "select MERCHANT_NO_TIPS , MERCHANT_PWD_NAME ,MERCHANT_PWD_TIPS ,PLATFORM_NO_NAME,PLATFORM_NO_TIPS,RSA_MERCHANT_PRIVATE_KEY_TIPS,RSA_SERVER_PUBLIC_KEY_TIPS from PY_PAYMENT_PLATFORM where ID = ?";
		System.out.println(sql);
		GetConnection get = new GetConnection();
		Connection conn = get.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try {
			// 建立字串
			pstmt.setInt(1, plantNum);

			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				
				map.put("merchant_no_tips", result.getString(1));
				map.put("merchant_pwd_name", result.getString(2));
				map.put("merchant_pwd_tips", result.getString(3));
				map.put("platform_no_name", result.getString(4));
				map.put("platform_no_tips", result.getString(5));
				map.put("rsa_merchant_private_key_tips", result.getString(6));
				map.put("rsa_server_public_key_tips", result.getString(7));
			}

			System.out.println(map);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			pstmt.close();
			conn.close();
		}

		return map;
	}

	@Override
	public String getSignType(int plantNo) throws SQLException {
		String sql = "select PAYMENT_SIGNATURE_TYPE from PY_PAYMENT_PLATFORM_SIGN where PAYMENT_PLATFORM_ID = ?";
		System.out.println(sql);
		GetConnection get = new GetConnection();
		Connection conn = get.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		String msString = null ;
		try {
			// 建立字串
			pstmt.setInt(1, plantNo);

			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				msString = result.getString(1);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			pstmt.close();
			conn.close();
		}
		return msString;

}
}

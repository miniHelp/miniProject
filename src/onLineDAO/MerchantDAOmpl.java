package onLineDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import Util.GetConnection;

public class MerchantDAOmpl implements MerchentDAO {

	public static void main(String[] args) throws SQLException {
		MerchantDAOmpl pa = new MerchantDAOmpl();
		System.out.println(pa.deleteMerchent(18884));

	}

	// 商戶的序號
	private int getSeq(Connection connection) throws SQLException {
		System.out.println("into == getSeq");
		int myId = 0;
		String sql = "select seq_merchant_id.nextval from dual ";
		PreparedStatement pst = connection.prepareStatement(sql);
		synchronized (this) {
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				myId = rs.getInt(1);
		}
		return myId;
	}

	// 商戶支付方式的序號
	private int getPaymentSeq(Connection conn) throws SQLException {

		System.out.println("into == getLogSeq");
		int myId = 0;
		String sql = "select seq_merchant_payment_id.nextval from dual";
		PreparedStatement pst = conn.prepareStatement(sql);
		synchronized (this) {
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				myId = rs.getInt(1);
		}
		return myId;

	}

	// Log黨的序號
	private int getLogSeq(Connection connection) throws SQLException {
		System.out.println("into == getLogSeq");
		int myId = 0;
		String sql = "select SEQ_MERCHANT_LOG.nextval from dual";
		PreparedStatement pst = connection.prepareStatement(sql);
		synchronized (this) {
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				myId = rs.getInt(1);
		}
		return myId;
	}

	@Override
	public String insertMerchent(int plant, String merchantName, String MD5, String merchentNo, String password,
			String RSAPrivate, String RSAPublic, String type ,String  plantNo ,List<Integer>list ,String ip) throws Exception {
		String sql = "INSERT INTO PY_MERCHANT (ID, ORDER_PAGE_ID, PAYMENT_PLATFORM_ID, MERCHANT_NO,"
				+ " NAME, MAX_STOP_AMOUNT, CREATE_DATE, UPDATE_DATE, SUBMIT_URL,GROUP_AREA ,SIGNATURE_TYPE,"
				+ " SIGNATURE_KEY, RSA_MERCHANT_PRIVATE_KEY,RSA_SERVER_PUBLIC_KEY," + "PLATFORM_NO,MERCHANT_PWD"
				+ " ,IS_RECHARGE_RANDOM_DECIMAL , STATUS)"

				+ "VALUES (?, ?, ?, ?, ?,      '9999',  sysdate , sysdate,"
				+ "'http://211.75.237.90','1',?, ?, ?, ? ,?,?,'N','1')";

		System.out.println(sql);
		System.out.println(merchantName + "  ===  " + merchantName);
		String meString = "";
		GetConnection get = new GetConnection();
		Connection conn = get.getMypayConnection();
		conn.setAutoCommit(false);  
		int id = getSeq(conn);

		PreparedStatement pstmt = conn.prepareStatement(sql);
		try {
			pstmt.setInt(1, id);// 自增主鍵
			pstmt.setInt(2, plant);// 平台的ID 測試機上跟層級是一樣的
			pstmt.setInt(3, plant);
			pstmt.setString(4, merchentNo);// 商戶號
			pstmt.setString(5, merchantName + "自動產生測試號");
			pstmt.setString(6, type);
			pstmt.setString(7, StringUtils.isNotEmpty(MD5) ? MD5 : "");// md5密
			pstmt.setString(8, StringUtils.isNotEmpty(RSAPrivate) ? RSAPrivate : "");
			pstmt.setString(9, StringUtils.isNotEmpty(RSAPublic) ? RSAPublic : "");
			pstmt.setString(10, StringUtils.isNotEmpty(plantNo) ? plantNo : "");
			pstmt.setString(11, StringUtils.isNotEmpty(password) ? password : "");
			pstmt.executeUpdate();
			conn.commit();    
			System.out.println(pstmt.toString());
			meString += meString + "," + insertMerPayment(plant, type, list);
			System.out.println("------------新增商戶支付方式完成------------");
			meString += meString + "," + insertMerLog(ip, id, merchantName);
			System.out.println("------------新增商戶log完成------------");
			
			
			return meString = "商戶新增成功";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return meString = e.getMessage();
		} finally {
			pstmt.close();
			conn.close();

		}
	}

	/**
	 * 加入log
	 * 
	 * @param userIp
	 * @param 商戶ID
	 * @param json
	 * @throws Exception
	 */
	
	@Override
	public String insertMerLog(String userIp, int merchId, String name ) throws SQLException {
		String sql = "INSERT INTO PY_MERCHANT_LOG										"
				+ "  (ID,                                                           "
				+ "   KIND,                                                         "
				+ "   MSG,                                                          "
				+ "   CREATE_DATE,                                                  "
				+ "   USER_ID,                                                      "
				+ "   USER_LOGIN_ID,                                                "
				+ "   USER_NAME,                                                    "
				+ "   USER_IP                                                      "
				+ "   )                                                 "
				+ "VALUES                                                           "
				+ "  (?,                                                         "
				+ "   ?,                                                       "
				+ "   ?,                                                        "
				+ "   sysdate,                                                "
				+ "   ?,                                                    "
				+ "   ?,                                              "
				+ "   ?,                                                  "
				+ "   ?                                                    "
				+ "   )                                              ";

		System.out.println(sql);
		String meString = "";
		GetConnection get = new GetConnection();
		Connection conn = get.getMypayConnection();
		int num = getLogSeq(conn);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try {
			pstmt.setInt(1, num);// 自增主鍵
			pstmt.setString(2, "2");// 2是新增
			pstmt.setString(3,  "商户编号："+ merchId +" "+name + " 启用商户");
			pstmt.setInt(4, 1061);// 懶惰按鈕
			pstmt.setString(5, "懶惰按鈕");
			pstmt.setString(6, "懶惰按鈕");
			pstmt.setString(7, userIp);

			pstmt.executeUpdate();
			System.out.println(pstmt.toString());
			return meString = "商戶Log新增成功";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return meString = e.getMessage();
		} finally {
			pstmt.close();
			conn.close();

		}

	}

	/**
	 * 加入支付方式
	 * 
	 * @param 對應的商戶編號ID
	 * @param 支付類型
	 * @param 支付方式list
	 * @throws Exception
	 */
	
	@Override
	public String insertMerPayment(int id, String type, List<Integer> payment) throws SQLException {
		String sql = "INSERT INTO PY_MERCHANT_PAYMENT										"
				+ "  (ID,                                                           "
				+ "   MERCHANT_ID,                                                         "
				+ "   PAYMENT_METHOD_ID,                                                          "
				+ "   TYPE,                                                  "
				+ "   CREATE_DATE,                                                      "
				+ "   UPDATE_DATE                                                "
				+ "   )                                                 "
				+ "VALUES                                                           "
				+ "  (?,                                                         "
				+ "   ?,                                                       "
				+ "   ?,                                                        "
				+ "   ?,                                                "
				+ "   sysdate,                                                    " + "   sysdate"
				+ "   )                                              ";

		String meString = "";
		GetConnection get = new GetConnection();
		Connection conn = get.getMypayConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try {
			for (int y = 1; y < 3; y++) {
				for (int i = 0; i < payment.size(); i++) {
					System.out.println(sql);
					int num = getPaymentSeq(conn);
					pstmt.setInt(1, num);// 自增主鍵
					pstmt.setInt(2, id);// 對應的商戶編號
					pstmt.setInt(3, payment.get(i));
					pstmt.setString(4, String.valueOf(y));// 一個是支付方式 一個是商戶確認鑰用的
															// 所以兩個都滾完
					pstmt.executeUpdate();
					System.out.println(pstmt.toString());
					System.out.println("商戶 " + i + " 新增成功");
				}

			}
			return meString = "新增商戶支付方式  完成";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return meString = e.getMessage();
		} finally {
			pstmt.close();
			conn.close();

		}
	}

	/**
	 * 刪除商戶
	 * 
	 * @param 對應的商戶編號ID
	 * @throws SQLException
	 */
	
	@Override
	public String deleteMerchent(int merchId) throws SQLException {
		String sql = "DELETE from PY_MERCHANT where id = ?";
		String meString = "";
		GetConnection get = new GetConnection();
		Connection conn = get.getMypayConnection();
		conn.setAutoCommit(false);  
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try {
			
					System.out.println(sql);
					int num = getPaymentSeq(conn);
					pstmt.setInt(1, merchId);// 自增主鍵
					pstmt.executeUpdate();
					conn.commit();
					System.out.println(pstmt.toString());
					System.out.println("商戶 " + merchId + " 刪除成功");
					

			
			return meString = "商戶 " + merchId + " 刪除成功";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return meString = e.getMessage();
		} finally {
			pstmt.close();
			conn.close();

		}
		
	}

	

}

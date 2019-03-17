package onLineDAO;

import Util.GetConnection;
import Util.HibernateUtil;
import onlineModel.MerchantLogVO;
import onlineModel.MerchantPaymentVO;
import onlineModel.MerchantVO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Component
public class MerchantDAOImpl implements MerchentDAO {

	public static void main(String[] args) throws SQLException {
		MerchantDAOImpl pa = new MerchantDAOImpl();
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
			String RSAPrivate, String RSAPublic, String type ,String  plantNo ,List<Integer>list ,String ip) throws Exception{
		Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();

		try {
			MerchantVO merchantVO = new MerchantVO();
			session.beginTransaction();

			merchantVO.setOrder_page_id(plant);
			merchantVO.setPayment_platform_id(plant);
			merchantVO.setMerchant_no(merchentNo);
			merchantVO.setMerchant_name(merchantName + "自動產生測試號");
			merchantVO.setMax_stop_amount(999999);
			merchantVO.setAccumulate_amount(0);
			merchantVO.setAccumulate_record(0);
			merchantVO.setCreate_date(new java.sql.Date(new Date().getTime()));
			merchantVO.setUpdate_date(new java.sql.Date(new Date().getTime()));
			merchantVO.setSubmit_url("http://211.75.237.90");
			merchantVO.setGroup_area("1");
			merchantVO.setMerchant_status("1");
			merchantVO.setSignature_type(type);
			merchantVO.setSignature_key(StringUtils.isNotEmpty(MD5) ? MD5 : "");
			merchantVO.setRsa_merchant_private_key(StringUtils.isNotEmpty(RSAPrivate) ? RSAPrivate : "");
			merchantVO.setRsa_server_public_key(StringUtils.isNotEmpty(RSAPublic) ? RSAPublic : "");
			merchantVO.setPlatform_no(StringUtils.isNotEmpty(plantNo) ? plantNo : "");
			merchantVO.setMerchant_pwd(StringUtils.isNotEmpty(password) ? password : "");
			session.saveOrUpdate(merchantVO);

            Query<Object> query = session.createQuery("select max(merchantId) from MerchantVO", Object.class);
            List<Object> listQuery = query.getResultList();
            session.getTransaction().commit();

            insertMerPayment((int)listQuery.get(0), type, list);
            System.out.println("------------新增商戶支付方式完成------------");
            insertMerLog(ip, (int)listQuery.get(0), merchantName);
            System.out.println("------------新增商戶log完成------------");

        } catch (Exception ex) {

            session.getTransaction().rollback();
            System.out.println(ex.getMessage());
            throw ex;
        }

		return "新增商戶完成";
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
	public String insertMerLog(String userIp, int merchId, String name) throws SQLException {
		Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			MerchantLogVO merchantLogVO = new MerchantLogVO();
			merchantLogVO.setKind("2");
			merchantLogVO.setMsg("商户编号："+ merchId +" "+name + " 启用商户");
			merchantLogVO.setCreate_date(new java.sql.Date(new Date().getTime()));
			merchantLogVO.setUser_id(1061);
			merchantLogVO.setUser_login_id("懶惰按鈕");
			merchantLogVO.setUser_name("懶惰按鈕");
			merchantLogVO.setUser_ip(userIp);

			session.saveOrUpdate(merchantLogVO);
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}finally {
			return "商戶Log新增成功";
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
		String meString = "";

			Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();
        try {
            MerchantPaymentVO merchantPaymentVO = null;
            session.beginTransaction();
            for (int y = 1; y < 3; y++) {
                    for (int i = 0; i < payment.size(); i++) {
                        merchantPaymentVO = new MerchantPaymentVO();
                        merchantPaymentVO.setMerchant_id(id);
                        merchantPaymentVO.setPayment_method_id(payment.get(i));
                        merchantPaymentVO.setType(String.valueOf(y));
                        merchantPaymentVO.setCreate_date(new java.sql.Date(new Date().getTime()));
                        merchantPaymentVO.setUpdate_date(new java.sql.Date(new Date().getTime()));
                        session.saveOrUpdate(merchantPaymentVO);
                    }
                }
                session.getTransaction().commit();
				meString = "新增商戶支付方式  完成";
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				meString = ex.getMessage();
				throw ex;
			}finally {
				return meString;
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

package onLineDAO;

import Util.GetConnection;
import Util.HibernateUtil;
import onlineModel.MerchantVO;
import onlineModel.PlatformVO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class ListDAOImpl implements ListDAO {


	@Override
	public String insertMypay(String name, int id) throws SQLException {
		String sql = "insert into PY_ORDER_PAGE (RECHARGE_CENTER_TYPE_NAME ,LOGO_CONSULTING_NAME ,LOGO_CONSULTING_IS_SHOW ,LOGO_BG_GRADIENT_COLOR_DOWN ,LOGO_BG_GRADIENT_COLOR_UP ,CREATE_DATE ,UPDATE_DATE ,CLOSE_MSG ,STATUS ,DOMAIN_NAME,TITLE,ID,NAME)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(sql);
		System.out.println(name + "===" + id);
		String meString = "";
		GetConnection get = new GetConnection();
		Connection conn = get.getMypayConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		try {
			pstmt.setString(1, "充值中心A");// 第一個?要插入的值
			pstmt.setString(2, "商务咨询");
			pstmt.setString(3, "Y");
			pstmt.setString(4, "#ededed");
			pstmt.setString(5, "#ffffff");
			pstmt.setString(6, "08-8月 -18");
			pstmt.setString(7, "08-8月 -18");
			pstmt.setString(8, "网站已经关闭");
			pstmt.setString(9, "1");
			pstmt.setString(10, "http://192.168.0.21/order");
			pstmt.setString(11, name + "充值中心");
			pstmt.setInt(12, id);
			pstmt.setString(13, name + "充值中心");
			pstmt.executeUpdate();
			System.out.println(pstmt.toString());
			return meString = "新增成功";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return meString = e.getMessage();
		} finally {
			pstmt.close();
			conn.close();

		}

	}

	@Override
	public List<PlatformVO> PlantNoList(String column, String str) throws Exception {

		List<PlatformVO> list = null;
		Session session = HibernateUtil.getMypayCenterSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<PlatformVO> query =
					session.createQuery("from PlatformVO where "+ column + " like :column order by platform_id asc", PlatformVO.class);
			if(column.equals("platform_id")){
				query.setParameter("column",Integer.parseInt(str));
			}else{
				query.setParameter("column","%" + str + "%");
			}
            list  = query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public boolean isPlanNo(String oldUrl, String newUrl) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String arg[]) throws Exception {
		ListDAOImpl pa = new ListDAOImpl();
//		System.out.println(re);
	}

	@Override
	public void updateUrl(int id, String url) throws Exception {

		Session session = HibernateUtil.getMypayCenterSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			PlatformVO platformVO = session.get(PlatformVO.class,id);
			platformVO.setPlatform_url(url);
			session.saveOrUpdate(platformVO);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public List<MerchantVO> merChantList(int column) throws SQLException {

		List<MerchantVO> list = null;
		Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();
		System.out.println("要查詢接編號:" + column + "底下所有商戶!!");
		try {
			session.beginTransaction();
			Query<MerchantVO> query =
					session.createQuery("from MerchantVO where payment_platform_id=:payment_platform_id order by merchantId asc", MerchantVO.class);
			query.setParameter("payment_platform_id",column);
			list  = query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}


	/**
	 *
	 */




}

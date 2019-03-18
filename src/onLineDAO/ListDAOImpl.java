package onLineDAO;

import Util.HibernateUtil;
import onlineModel.MerchantVO;
import onlineModel.OrderPageVO;
import onlineModel.PlatformVO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ListDAOImpl implements ListDAO {


	@Override
	public String insertMypay(String name, int id) throws SQLException {
		String meString = "";
		Session session = HibernateUtil.getMypayCenterSessionFactory().getCurrentSession();
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM月  -yy");
		try {
			OrderPageVO orderPageVO = new OrderPageVO();
			orderPageVO.setRecharge_center_type_name("充值中心A");
			orderPageVO.setLogo_consulting_name("商务咨询");
			orderPageVO.setLogo_consulting_is_show("Y");
			orderPageVO.setLogo_bg_gradient_color_down("#ededed");
			orderPageVO.setLogo_bg_gradient_color_up("#ffffff");
			orderPageVO.setCreate_date(java.sql.Date.valueOf(sd.format(new Date().getTime())));
			orderPageVO.setUpdate_date(java.sql.Date.valueOf(sd.format(new Date().getTime())));
			orderPageVO.setClose_msg("网站已经关闭");
			orderPageVO.setOrder_page_status("1");
			orderPageVO.setDomain_name("http://192.168.0.21/order");
			orderPageVO.setTitle(name + "充值中心");
			orderPageVO.setOrder_page_id(id);
			orderPageVO.setOrder_page_name(name + "充值中心");
			session.getTransaction().commit();
			meString = "新增成功";

		}catch (RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		}

		return meString;
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

}

package onLineDAO;

import Util.GetConnection;
import Util.HibernateUtil;
import onlineModel.PlatformVO;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class PlatformImp implements PlatformDAO {

	@Override
	public List<Integer> getPlantPayment(int plantNum) throws SQLException {
		Session session = HibernateUtil.getMypayCenterSessionFactory().getCurrentSession();
		List<Integer> listInt = new ArrayList<>();

		try {
			// 建立字串
			session.beginTransaction();
		    Query<Object> query = session.createQuery("select payment_method_id from PaymentPlatformMethodVO" +
					" where payment_platform_id=:payment_platform_id",Object.class);
			query.setParameter("payment_platform_id",plantNum);

			List<Object> list = query.getResultList();
			Iterator<Object> itr = list.iterator();
			while(itr.hasNext()){
				int num = (int)itr.next();
				listInt.add(num);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println(e.getMessage());
		}

		return listInt;
	}

	@Override
	public PlatformVO getPlatformInfo(int plantNum) throws SQLException {

		Session session = HibernateUtil.getMypayCenterSessionFactory().getCurrentSession();
		PlatformVO platformVO = null;

		try {
			// 建立字串
			session.beginTransaction();
			platformVO = (PlatformVO)session.get(PlatformVO.class,plantNum);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println(e.getMessage());
		}
		return platformVO;
	}

	@Override
	public Character getSignType(int plantNo) throws SQLException {
		Session session = HibernateUtil.getMypaySessionFactory().getCurrentSession();
		List<Object> list = null;
		try {
			session.beginTransaction();
			@SuppressWarnings("unchecked")
			NativeQuery<Object> query = session.createNativeQuery("select PAYMENT_SIGNATURE_TYPE from PY_PAYMENT_PLATFORM_SIGN" +
					" where PAYMENT_PLATFORM_ID =:payment_platform_id");
			query.setParameter("payment_platform_id",plantNo);
			list = query.getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println(e.getMessage());
		}

		return (Character)list.get(0);
	}
}

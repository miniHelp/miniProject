package onLineDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import onlineModel.MerchantVO;
import onlineModel.PlatformVO;
import org.apache.commons.lang3.StringUtils;

import Util.GetConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public List<PlatformVO> PlantNoList(String colum, String str) throws Exception {
		String sql = "select id,name,url from py_payment_platform where " + colum + " like ? order by id asc";
		System.out.println(sql);
		GetConnection get = new GetConnection();
		Connection conn = get.getConnection();
		List<PlatformVO> list = new ArrayList<PlatformVO>();
		PreparedStatement  pstmt = null;

		try {
			// 建立字串
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + str + "%");
			
			ResultSet result = pstmt.executeQuery();
			PlatformVO platformVO = null;
			while (result.next()) {
				platformVO = new PlatformVO();
				platformVO.setId(result.getString(1));
				platformVO.setName(result.getString(2));
				platformVO.setUrl(result.getString(3));
				list.add(platformVO);
			}

			System.out.println(list);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			conn.close();
			pstmt.close();
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
	public void updateUrl(String id, String url) throws Exception {
		GetConnection get = new GetConnection();
		Connection conn = null;
		int count = 0;
		try {
			conn = get.getConnection();
			String sql = "update py_payment_platform  set url = ? where id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, url);
			ps.setString(2, id);
			count = ps.executeUpdate();
			System.out.println("完成" + count + "筆更新");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			conn.close();
		}

	}

	@Override
	public List<MerchantVO> merChantList(int colum) throws SQLException {
		System.out.println("into == merChantList");
		String sql = "select id ,PAYMENT_PLATFORM_ID , NAME , MERCHANT_NO ,PLATFORM_NO , MERCHANT_PWD ,SIGNATURE_KEY ,SIGNATURE_TYPE ,RSA_MERCHANT_PRIVATE_KEY ,RSA_MERCHANT_PUBLIC_KEY ,RSA_SERVER_PUBLIC_KEY from PY_MERCHANT where PAYMENT_PLATFORM_ID = ?  order by id asc";
		System.out.println(sql);
		GetConnection get = new GetConnection();
		Connection conn = get.getMypayConnection();
		List<MerchantVO> list = new ArrayList<MerchantVO>();

		try {
			// 建立字串
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, colum);

			ResultSet result = pstmt.executeQuery();
			MerchantVO merchantVO = null;
			while (result.next()) {
				merchantVO = new MerchantVO();
				merchantVO.setMerchantId(result.getString(1));
				merchantVO.setPayment_platform_id(result.getString(2));
				merchantVO.setName(result.getString(3));
				merchantVO.setMerchant_no(result.getString(4));
				merchantVO.setPlatform_no(result.getString(5));
				merchantVO.setMerchant_pwd(result.getString(6));
				merchantVO.setSignature_key(result.getString(7));
				merchantVO.setSignature_type(result.getString(8));
				merchantVO.setRsa_merchant_private_key(result.getString(9));
				merchantVO.setRsa_merchant_public_key(result.getString(10));
				merchantVO.setRsa_server_public_key(result.getString(11));
				list.add(merchantVO);
			}

			System.out.println(list);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} finally {
			conn.close();
		}
		return list;
	}
	
	
	/**
	 * 
	 */
	
	


}

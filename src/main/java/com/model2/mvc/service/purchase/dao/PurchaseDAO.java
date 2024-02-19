package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {
		// blank
	}
	
	// getPurchase : 구매정보 상세 조회
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		System.out.println("[PurchaseDAO.getPurchase] start");
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM transaction WHERE tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		PurchaseVO purchaseVO = null;
		
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			
			ProductVO productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("prod_no"));
			purchaseVO.setPurchaseProd(productVO);
			
			UserDAO userDAO = new UserDAO();
			UserVO userVO = userDAO.findUser(rs.getString("buyer_id"));
			purchaseVO.setBuyer(userVO);
			
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
		}
		
		
		System.out.println("[PurchaseDAO.getPurchase] end");
		
		con.close();
		
		return purchaseVO;
	}
	
	// getPurchaseList : 구매 목록 조회
	public Map<String, Object> getPurchaseList(SearchVO searchVO, String userId) throws SQLException {
		System.out.println("[PurchaseDAO.getPurchaseList] start");
		
		Connection con = DBUtil.getConnection();
				
		String sql = "SELECT * FROM transaction WHERE buyer_id = ? ORDER BY tran_no";
		
		// 커서의 양방향 진행을 위한 parameter : ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
		PreparedStatement pstmt = con.prepareStatement(sql, 
				ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setString(1, userId);
		
		ResultSet rs = pstmt.executeQuery();
		
		// 열의 개수를 가져와서 Map에 put 하기
		rs.last();
		int total = rs.getRow();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO vo = new PurchaseVO();
				UserVO userVO = new UserVO();
				ProductVO productVO = new ProductVO();
				
				userVO.setUserId(rs.getString("BUYER_ID"));
				productVO.setProdNo(rs.getInt("PROD_NO"));
				
				vo.setBuyer(userVO);
				vo.setPurchaseProd(productVO);
				vo.setTranNo(rs.getInt("tran_no"));
				vo.setPaymentOption(rs.getString("payment_option"));
				vo.setReceiverName(rs.getString("receiver_name"));
				vo.setReceiverPhone(rs.getString("receiver_phone"));
				vo.setDivyAddr(rs.getString("demailaddr"));
				vo.setDivyRequest(rs.getString("dlvy_request"));
				vo.setTranCode(rs.getString("tran_status_code"));
				vo.setOrderDate(rs.getDate("order_data"));
				vo.setDivyDate(rs.getString("dlvy_date"));
				list.add(vo);
				
				if (!rs.next())
					break;
			}
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
		
		System.out.println("[PurchaseDAO.getPurchaseList] end");
			
		return map;
	}
	
	// getSaleList : 판매 목록 조회
	public Map<Integer, Object> getSaleList() throws SQLException {
		System.out.println("[PurchaseDAO.getSaleList] start");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT t.tran_status_code, p.prod_no, t.tran_no FROM transaction t, product p WHERE t.prod_no = p.prod_no";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		
		while(rs.next()) {
			PurchaseVO purchaseVO = new PurchaseVO();
			int prodNo = rs.getInt("prod_no");
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			map.put(prodNo, purchaseVO);
		}
		System.out.println("map.size() : " + map.size());
		
		System.out.println("[PurchaseDAO.getSaleList] end");
		
		return  map;
	}
	
	
	// addPurchase : 구매
	public void addPurchase(PurchaseVO purchaseVO) throws SQLException {
		System.out.println("[PurchaseDAO.insertPurchase] : start");
		System.out.println("purchaseVO : " + purchaseVO);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		// System.out.println("purchaseVO.getPurchaseProd().getProdNo() " + purchaseVO.getPurchaseProd().getProdNo());
		pstmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		pstmt.setString(2, purchaseVO.getBuyer().getUserId());
		pstmt.setString(3, purchaseVO.getPaymentOption());
		pstmt.setString(4, purchaseVO.getReceiverName());
		pstmt.setString(5, purchaseVO.getReceiverPhone());
		pstmt.setString(6, purchaseVO.getDivyAddr());
		pstmt.setString(7, purchaseVO.getDivyRequest());
		pstmt.setString(8, purchaseVO.getTranCode());
		pstmt.setString(9, purchaseVO.getDivyDate());
		
		pstmt.executeUpdate();
		
		Statement st = con.createStatement();
		st.execute("commit");
		
		con.close();
		
		System.out.println("[PurchaseDAO.insertPurchase] : end");
	}
	
	
	// updatePurchase : 구매 정보 수정
	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) throws Exception {
		System.out.println("[PurchaseDAO.updatePurchase] start");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction set payment_option = ?, receiver_name = ?, receiver_phone = ?, demailaddr = ?, dlvy_request = ?, dlvy_date = ? WHERE tran_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, purchaseVO.getPaymentOption());
		pstmt.setString(2, purchaseVO.getReceiverName());
		pstmt.setString(3, purchaseVO.getReceiverPhone());
		pstmt.setString(4, purchaseVO.getDivyAddr());
		pstmt.setString(5, purchaseVO.getDivyRequest());
		pstmt.setString(6, purchaseVO.getDivyDate());
		pstmt.setInt(7, purchaseVO.getTranNo());
		
		pstmt.executeUpdate();
		
		Statement st = con.createStatement();
		st.execute("commit");
		
		con.close();
		
		System.out.println("[PurchaseDAO.updatePurchase] end");
		
		
		return purchaseVO;
	}
	
	// updateTranCode : 구매 상태 코드 수정
	public void updateTranCode(PurchaseVO purchaseVO) throws SQLException {
		System.out.println("[PurchaseDAO.updateTranCode] start");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction set tran_status_code = ? WHERE tran_no = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, TranStatusCodeUtil.getNextCode(purchaseVO.getTranCode()));
		pstmt.setInt(2, purchaseVO.getTranNo());
		
		pstmt.executeUpdate();
		
		Statement st = con.createStatement();
		st.execute("commit");
		
		con.close();
		
		System.out.println("[PurchaseDAO.updateTranCode] end");
	}
}

package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	// findPurchase : 구매정보 상세 조회
	
	// getPurchaseList : 구매 목록 조회
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO) throws SQLException {
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM TRANSACTION ORDER BY TRAN_NO";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
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
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setDivyAddr(rs.getString("DLVY_ADDR"));
				vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setOrderDate(rs.getDate("ORDER_DATE"));
				vo.setDivyDate(rs.getString("DLVY_DATE"));
				
				list.add(vo);
				
				if (!rs.next())
					break;
			}
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	// getSaleList : 판매 목록 조회
	
	// addPurchase : 구매
	public void addPurchase(PurchaseVO purchaseVO) throws SQLException {
		Connection con = DBUtil.getConnection();
		
		// Debugging
		System.out.println("[Start] PurchaseDAO.insertPurchase : PurchaseVO" + purchaseVO);
		
		String sql = "INSERT INTO transaction values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
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
		
		con.close();
	}
	
	
	// updatePurchase : 구매 정보 수정
	
	// updateTranCode : 구매 상태 코드 수정
}

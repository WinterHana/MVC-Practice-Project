package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseDAO {
	// findPurchase : 구매정보 상세 조회
	
	// getPurchaseList : 구매 목록 조회
	
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

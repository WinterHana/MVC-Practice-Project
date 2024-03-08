package com.model2.mvc.service.purchase.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.purchase.PurchaseDAO;

@Repository("purchaseDAOImpl")
public class PurchaseDAOImpl implements PurchaseDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public PurchaseDAOImpl() {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
	}
	
	public PurchaseDAOImpl(SqlSession sqlSession) {
		System.out.println("[" + getClass().getName() + " SqlSeesion set Constructor] Call");
		this.sqlSession = sqlSession;
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("[" + getClass().getName() + ".setSqlSession] Call");
		this.sqlSession = sqlSession;
	}
	
	@Override
	public PurchaseVO getPurchase(int tranNo) {
		System.out.println("[" + getClass().getName() + ".getPurchase] Call");
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}

	@Override
	public List<PurchaseVO> getPurchaseList(Map<String,Object> map) {
		System.out.println("[" + getClass().getName() + ".getPurchaseList] Call");
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	@Override
	public int getPurchaseCount(Map<String,Object> map) {
		System.out.println("[" + getClass().getName() + ".getPurchaseList] Call");
		return sqlSession.selectOne("PurchaseMapper.getPurchaseCount", map);
	}

	@Override
	public List<Map<String, Object>> getSaleList() {
		System.out.println("[" + getClass().getName() + ".getSaleList] Call");
		return sqlSession.selectList("PurchaseMapper.getSaleList", "p.prod_no");
	}

	@Override
	public int addPurchase(PurchaseVO purchaseVO) {
		System.out.println("[" + getClass().getName() + ".addPurchase] Call");
		return sqlSession.insert("PurchaseMapper.addPurchase", purchaseVO);
	}

	@Override
	public int updatePurchase(PurchaseVO purchaseVO) {
		System.out.println("[" + getClass().getName() + ".updatePurchase] Call");
		return sqlSession.update("PurchaseMapper.updatePurchase", purchaseVO);
	}

	@Override
	public int updateTranCode(Map<String,Object> map) {
		System.out.println("[" + getClass().getName() + ".updateTranCode] Call");
		return sqlSession.update("PurchaseMapper.updateTranCode", map);
	}

	@Override
	public int deletePurchase(int tranNo) {
		System.out.println("[" + getClass().getName() + ".deletePurchase] Call");
		return sqlSession.update("PurchaseMapper.deletePurchase", tranNo);
	}
	
//	// getPurchase : 구매정보 상세 조회
//	public PurchaseVO getPurchase(int tranNo) throws Exception {
//		System.out.println("[PurchaseDAO.getPurchase] start");
//		
//		Connection con = DBUtil.getConnection();
//		String sql = "SELECT * FROM transaction WHERE tran_no = ?";
//		
//		PreparedStatement stmt = con.prepareStatement(sql);
//		stmt.setInt(1, tranNo);
//		
//		ResultSet rs = stmt.executeQuery();
//		
//		PurchaseVO purchaseVO = null;
//		
//		while (rs.next()) {
//			purchaseVO = new PurchaseVO();
//			
//			ProductVO productVO = new ProductVO();
//			productVO.setProdNo(rs.getInt("prod_no"));
//			purchaseVO.setPurchaseProd(productVO);
//			
//			UserDAOImpl userDAO = new UserDAOImpl();
//			UserVO userVO = userDAO.getUser(rs.getString("buyer_id"));
//			purchaseVO.setBuyer(userVO);
//			
//			purchaseVO.setTranNo(rs.getInt("tran_no"));
//			purchaseVO.setPaymentOption(rs.getString("payment_option"));
//			purchaseVO.setReceiverName(rs.getString("receiver_name"));
//			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
//			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
//			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
//			purchaseVO.setOrderDate(rs.getDate("order_data"));
//			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
//		}
//		
//		
//		System.out.println("[PurchaseDAO.getPurchase] end");
//		
//		con.close();
//		
//		return purchaseVO;
//	}
//	
//	// getPurchaseList : 구매 목록 조회
//	public Map<String, Object> getPurchaseList(SearchVO searchVO, String userId) throws Exception {
//		System.out.println("[PurchaseDAO.getPurchaseList] start");
//		
//		Connection con = DBUtil.getConnection();
//				
//		String sql = "SELECT * FROM transaction WHERE buyer_id = '" + userId + "' ORDER BY tran_no";
//		
//		System.out.println("Inner table SQL : " + sql);
//		
//		int totalCount = this.getTotalCount(sql);
//		
//		System.out.println("totalCount : " + totalCount);
//		/*
//		// 커서의 양방향 진행을 위한 parameter : ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
//		PreparedStatement pstmt = con.prepareStatement(sql, 
//				ResultSet.TYPE_SCROLL_INSENSITIVE, 
//				ResultSet.CONCUR_UPDATABLE);
//		*/
//		
//		sql = makeCurrentPageSql(sql, searchVO);
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		// pstmt.setString(1, userId);
//		ResultSet rs = pstmt.executeQuery();
//		
//		HashMap<String,Object> map = new HashMap<String,Object>();
//		map.put("totalCount", totalCount);
//		
//		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
//		
//		while(rs.next()) {
//			PurchaseVO vo = new PurchaseVO();
//			UserVO userVO = new UserVO();
//			ProductVO productVO = new ProductVO();
//			
//			userVO.setUserId(rs.getString("BUYER_ID"));
//			productVO.setProdNo(rs.getInt("PROD_NO"));
//			
//			vo.setBuyer(userVO);
//			vo.setPurchaseProd(productVO);
//			vo.setTranNo(rs.getInt("tran_no"));
//			vo.setPaymentOption(rs.getString("payment_option"));
//			vo.setReceiverName(rs.getString("receiver_name"));
//			vo.setReceiverPhone(rs.getString("receiver_phone"));
//			vo.setDivyAddr(rs.getString("demailaddr"));
//			vo.setDivyRequest(rs.getString("dlvy_request"));
//			vo.setTranCode(rs.getString("tran_status_code"));
//			vo.setOrderDate(rs.getDate("order_data"));
//			vo.setDivyDate(rs.getString("dlvy_date"));
//			list.add(vo);
//		}
//		
//		System.out.println("list size : " + list.size());
//		map.put("list", list);
//
//		rs.close();
//		pstmt.close();
//		con.close();
//		
//		System.out.println("[PurchaseDAO.getPurchaseList] end");
//			
//		return map;
//	}
//	
//	private int getTotalCount(String sql) throws Exception {
//		Connection con = DBUtil.getConnection();
//		
//		sql = "SELECT COUNT(*) FROM (" + sql + ") countTable";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		ResultSet rs = pstmt.executeQuery();
//		
//		int totalCount = 0;
//		if(rs.next()) {
//			totalCount = rs.getInt(1);
//		}
//		
//		pstmt.close();
//		con.close();
//		rs.close();
//		
//		return totalCount;
//	}
//	
//	private String makeCurrentPageSql(String sql, SearchVO searchVO) {
//		sql = 	"SELECT * "+ 
//				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
//								" 	FROM (	"+ sql +" ) inner_table "+
//								"	WHERE ROWNUM <="+searchVO.getPage()*searchVO.getPageSize()+" ) " +
//				"WHERE row_seq BETWEEN "+((searchVO.getPage()-1)*searchVO.getPageSize()+1) +" AND "+ searchVO.getPage() * searchVO.getPageSize();
//	
//		System.out.println("UserDAO :: make SQL :: "+ sql);	
//	
//		return sql;
//	}
//	
//	// getSaleList : 판매 목록 조회
//	public Map<Integer, Object> getSaleList() throws SQLException {
//		System.out.println("[PurchaseDAO.getSaleList] start");
//		
//		Connection con = DBUtil.getConnection();
//		
//		String sql = "SELECT t.tran_status_code, p.prod_no, t.tran_no FROM transaction t, product p WHERE t.prod_no = p.prod_no";
//		PreparedStatement stmt = con.prepareStatement(sql);
//		ResultSet rs = stmt.executeQuery();
//		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
//		
//		while(rs.next()) {
//			PurchaseVO purchaseVO = new PurchaseVO();
//			int prodNo = rs.getInt("prod_no");
//			purchaseVO.setTranNo(rs.getInt("tran_no"));
//			purchaseVO.setTranCode(rs.getString("tran_status_code"));
//			map.put(prodNo, purchaseVO);
//		} 
//		System.out.println("map.size() : " + map.size());
//		
//		System.out.println("[PurchaseDAO.getSaleList] end");
//		
//		return  map;
//	}
//	
//	
//	// addPurchase : 구매
//	public void addPurchase(PurchaseVO purchaseVO) throws SQLException {
//		System.out.println("[PurchaseDAO.insertPurchase] : start");
//		System.out.println("purchaseVO : " + purchaseVO);
//		
//		Connection con = DBUtil.getConnection();
//		
//		String sql = "INSERT INTO transaction values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		// System.out.println("purchaseVO.getPurchaseProd().getProdNo() " + purchaseVO.getPurchaseProd().getProdNo());
//		pstmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
//		pstmt.setString(2, purchaseVO.getBuyer().getUserId());
//		pstmt.setString(3, purchaseVO.getPaymentOption());
//		pstmt.setString(4, purchaseVO.getReceiverName());
//		pstmt.setString(5, purchaseVO.getReceiverPhone());
//		pstmt.setString(6, purchaseVO.getDivyAddr());
//		pstmt.setString(7, purchaseVO.getDivyRequest());
//		pstmt.setString(8, purchaseVO.getTranCode().trim());
//		pstmt.setString(9, purchaseVO.getDivyDate());
//		
//		pstmt.executeUpdate();
//		
//		Statement st = con.createStatement();
//		st.execute("commit");
//		
//		con.close();
//		
//		System.out.println("[PurchaseDAO.insertPurchase] : end");
//	}
//	
//	
//	// updatePurchase : 구매 정보 수정
//	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) throws Exception {
//		System.out.println("[PurchaseDAO.updatePurchase] start");
//		
//		Connection con = DBUtil.getConnection();
//		
//		String sql = "UPDATE transaction set payment_option = ?, receiver_name = ?, receiver_phone = ?, demailaddr = ?, dlvy_request = ?, dlvy_date = ? WHERE tran_no = ?";
//		
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		pstmt.setString(1, purchaseVO.getPaymentOption());
//		pstmt.setString(2, purchaseVO.getReceiverName());
//		pstmt.setString(3, purchaseVO.getReceiverPhone());
//		pstmt.setString(4, purchaseVO.getDivyAddr());
//		pstmt.setString(5, purchaseVO.getDivyRequest());
//		pstmt.setString(6, purchaseVO.getDivyDate());
//		pstmt.setInt(7, purchaseVO.getTranNo());
//		
//		pstmt.executeUpdate();
//		
//		Statement st = con.createStatement();
//		st.execute("commit");
//		
//		con.close();
//		
//		System.out.println("[PurchaseDAO.updatePurchase] end");
//		
//		
//		return purchaseVO;
//	}
//	
//	// updateTranCode : 구매 상태 코드 수정
//	public void updateTranCode(PurchaseVO purchaseVO) throws SQLException {
//		System.out.println("[PurchaseDAO.updateTranCode] start");
//		
//		Connection con = DBUtil.getConnection();
//		
//		String sql = "UPDATE transaction set tran_status_code = ? WHERE tran_no = ?";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		
//		pstmt.setString(1, TranStatusCodeUtil.getNextCode(purchaseVO.getTranCode()));
//		pstmt.setInt(2, purchaseVO.getTranNo());
//		
//		pstmt.executeUpdate();
//		
//		Statement st = con.createStatement();
//		st.execute("commit");
//		
//		con.close();
//		
//		System.out.println("[PurchaseDAO.updateTranCode] end");
//	}
}

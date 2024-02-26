package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.domain.ProductVO;
import com.model2.mvc.service.user.domain.UserVO;

public class ProductDAO {
	
	public ProductDAO() {
		// blank
	}
	
	// findProduct
	public ProductVO findProduct(int productId) throws Exception {
		Connection con = DBUtil.getConnection();
		
		System.out.println("produdctId : " + productId);
		String sql = "SELECT * FROM product where prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, productId);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductVO prodVO = null;
		
		while (rs.next()) {
			prodVO = new ProductVO();
			prodVO.setProdNo(rs.getInt("PROD_NO"));
			prodVO.setProdName(rs.getString("PROD_NAME"));
			prodVO.setProdDetail(rs.getString("PROD_DETAIL"));
			prodVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			prodVO.setPrice(rs.getInt("PRICE"));
			prodVO.setFileName(rs.getString("IMAGE_FILE"));
			prodVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		System.out.println("ProductDAO.findProduct productVO : " + prodVO);
		con.close();
		
		return prodVO;
	}
	
	// getProductList
	public Map<String, Object> getProductList(SearchVO searchVO) throws Exception {
		System.out.println("[ProductDAO.getProductList] start");
		
		Connection con = DBUtil.getConnection();
		
		// ★ 제품 검색에 대한 처리
		String sql = "SELECT * from PRODUCT ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("prodNo") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PROD_NO LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("prodName") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PROD_NAME LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("price") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PRICE= '" + searchVO.getSearchKeyword() + "'";
			}
		}
		sql += " order by PROD_NO";
		
		System.out.println("Inner table SQL : " + sql);
		
		int totalCount = this.getTotalCount(sql);
		
		System.out.println("totalCount : " + totalCount);
		
		sql = makeCurrentPageSql(sql, searchVO);
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", totalCount);
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		while(rs.next()) {
			ProductVO vo = new ProductVO();
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setFileName(rs.getString("IMAGE_FILE"));
			vo.setRegDate(rs.getDate("REG_DATE"));
			
			list.add(vo);
		}
		
		System.out.println("list size : " + list.size());
		map.put("list", list);

		rs.close();
		pstmt.close();
		con.close();
		
		System.out.println("[ProductDAO.getProductList] end");
		
		return map;
	}
	
	private int getTotalCount(String sql) throws Exception {
		Connection con = DBUtil.getConnection();
		
		sql = "SELECT COUNT(*) FROM (" + sql + ") countTable";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int totalCount = 0;
		if(rs.next()) {
			totalCount = rs.getInt(1);
		}
		
		pstmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	private String makeCurrentPageSql(String sql, SearchVO searchVO) {
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+ sql +" ) inner_table "+
								"	WHERE ROWNUM <="+searchVO.getPage()*searchVO.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((searchVO.getPage()-1)*searchVO.getPageSize()+1) +" AND "+ searchVO.getPage() * searchVO.getPageSize();
	
		System.out.println("UserDAO :: make SQL :: "+ sql);	
	
		return sql;
	}
	
	// insertProduct
	public void insertProduct(ProductVO productVO) throws Exception {
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product values (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		// 문자열 크기에 맞게 파싱
		stmt.setString(3, productVO.getManuDate().replace("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		stmt.executeUpdate();
		
		Statement st = con.createStatement();
		st.execute("commit");
		
		con.close();
	}
	
	// updateProduct
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product set PROD_NAME = ?, PROD_DETAIL = ?, MANUFACTURE_DAY = ?, PRICE = ?, IMAGE_FILE = ? WHERE PROD_NO = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		stmt.executeUpdate();
		
		Statement st = con.createStatement();
		st.execute("commit");
		
		con.close();
	}
}

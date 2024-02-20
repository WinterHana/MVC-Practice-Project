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
		Connection con = DBUtil.getConnection();
		
		// ★ 제품 검색에 대한 처리
		String sql = "SELECT * from PRODUCT ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("prodNo")) {
				sql += " where PROD_NO='" + searchVO.getSearchKeyword() + "'";
			} else if (searchVO.getSearchCondition().equals("prodName")) {
				sql += " where PROD_NAME='" + searchVO.getSearchKeyword() + "'";
			} else if (searchVO.getSearchCondition().equals("price")) {
				sql += " where PRICE='" + searchVO.getSearchKeyword() + "'";
			}
		}
		sql += " order by PROD_NO";
		
		PreparedStatement stmt = 
				con.prepareStatement(sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		// 열의 개수를 가져와서 Map에 put 하기
		rs.last();
		int total = rs.getRow();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				
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

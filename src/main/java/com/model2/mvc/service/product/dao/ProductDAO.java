package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

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
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		Connection con = DBUtil.getConnection();
		
		// ★ 여기 알고리즘 파악할 필요가 있다.
		String sql = "SELECT * from PRODUCT ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME='" + searchVO.getSearchKeyword()
						+ "'";
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
	
	// updateProduct
}

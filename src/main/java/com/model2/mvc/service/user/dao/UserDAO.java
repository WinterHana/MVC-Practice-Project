package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.user.domain.UserVO;


public class UserDAO {
	
	public UserDAO(){
	}

	public void insertUser(UserVO userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into USERS values (?,?,?,'user',?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserId());
		stmt.setString(2, userVO.getUserName());
		stmt.setString(3, userVO.getPassword());
		stmt.setString(4, userVO.getSsn());
		stmt.setString(5, userVO.getPhone());
		stmt.setString(6, userVO.getAddr());
		stmt.setString(7, userVO.getEmail());
		stmt.executeUpdate();
		
		con.close();
	}

	public UserVO findUser(String userId) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from USERS where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userId);
		
		// test
		System.out.println("UserDAO.findUser : userId = " + userId);
		
		ResultSet rs = stmt.executeQuery();

		UserVO userVO = null;
		while (rs.next()) {
			userVO = new UserVO();
			userVO.setUserId(rs.getString("USER_ID"));
			userVO.setUserName(rs.getString("USER_NAME"));
			userVO.setPassword(rs.getString("PASSWORD"));
			userVO.setRole(rs.getString("ROLE"));
			userVO.setSsn(rs.getString("SSN"));
			userVO.setPhone(rs.getString("CELL_PHONE"));
			userVO.setAddr(rs.getString("ADDR"));
			userVO.setEmail(rs.getString("EMAIL"));
			userVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();
		
		// test
		System.out.println("UserDAO.findUser : userVO Infomation = " + userVO);
		
		return userVO;
	}

	public Map<String,Object> getUserList(SearchVO searchVO) throws Exception {
		System.out.println("[UserDAO.getUserList] start");
		
		Connection con = DBUtil.getConnection();
		
		// Inner table sql
		String sql = "select * from USERS ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("") ) {
				sql += " where USER_ID = '" + searchVO.getSearchKeyword() + "'";
			} else if (searchVO.getSearchCondition().equals("1") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where USER_NAME = '" + searchVO.getSearchKeyword()  + "'";
			}
		}
		sql += " order by USER_ID";
		
		System.out.println("Inner table SQL : " + sql);
		
		int totalCount = this.getTotalCount(sql);
		
		System.out.println("totalCount : " + totalCount);
		
		sql = makeCurrentPageSql(sql, searchVO);
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", totalCount);

		ArrayList<UserVO> list = new ArrayList<UserVO>();
		while(rs.next()) {
			UserVO userVO = new UserVO();
			userVO.setUserId(rs.getString("USER_ID"));
			userVO.setUserName(rs.getString("USER_NAME"));
			userVO.setPassword(rs.getString("PASSWORD"));
			userVO.setRole(rs.getString("ROLE"));
			userVO.setSsn(rs.getString("SSN"));
			userVO.setPhone(rs.getString("CELL_PHONE"));
			userVO.setAddr(rs.getString("ADDR"));
			userVO.setEmail(rs.getString("EMAIL"));
			userVO.setRegDate(rs.getDate("REG_DATE"));
			
			list.add(userVO);
		}
		
		System.out.println("list size : " + list.size());
		map.put("list", list);

		rs.close();
		pstmt.close();
		con.close();
			
		System.out.println("[UserDAO.getUserList] end");
		
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
	
	public void updateUser(UserVO userVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update USERS set USER_NAME=?,CELL_PHONE=?,ADDR=?,EMAIL=? where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserName());
		stmt.setString(2, userVO.getPhone());
		stmt.setString(3, userVO.getAddr());
		stmt.setString(4, userVO.getEmail());
		stmt.setString(5, userVO.getUserId());
		stmt.executeUpdate();
		
		con.close();
	}
}
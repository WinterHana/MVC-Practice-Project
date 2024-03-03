package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.user.UserDAO;
import com.model2.mvc.service.user.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;

	public UserServiceImpl() {
		System.out.println("[" + getClass().getName() + " Default constructor] Call");
	}
	
	public UserServiceImpl(UserDAO userDAO) {
		System.out.println("[" + getClass().getName() + " UserDAO set constructor] Call");
		this.userDAO = userDAO;
	}
	
	public void setUserDAO(UserDAO userDAO) {
		System.out.println("[" + getClass().getName() + ".setUserDAO] Call");
		this.userDAO = userDAO;
	}
	
	public int addUser(UserVO userVO) {
		int result = 0;
		try {
			result = userDAO.addUser(userVO);
			
		} catch(Exception e) {
			System.out.println("[" + getClass().getName() + ".addUser] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	public UserVO loginUser(UserVO userVO) {
		UserVO result = null;
		
		try {
			result =userDAO.getUser(userVO.getUserId());
			
			if(! result.getPassword().equals(userVO.getPassword())) {
				throw new Exception("Password Error Exception");
			}
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".loginUser] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	public UserVO getUser(String userId) {
		UserVO result = null;
		
		try {
			result =userDAO.getUser(userId);
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".getUser] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	public Map<String,Object> getUserList(SearchVO searchVO) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		List<UserVO> list = null;
		int totalCount = 0;
		
		// 번호나 정수에 대해서 한글 입력에 대한 유효성 처리
		if(searchVO != null) {
			String searchCondition = searchVO.getSearchCondition();
			String serachKeyword = searchVO.getSearchKeyword();
			
			if(searchCondition != null && serachKeyword != null) {
				serachKeyword = CommonUtil.null2str(serachKeyword);
				System.out.println("searchKeyword : " + serachKeyword);
				
				searchVO.setSearchKeyword(serachKeyword);
			}
		}
		
		try {
			list =  userDAO.getUserList(searchVO);
			totalCount = userDAO.getUserCount(searchVO);
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".getUserList] Exception");
			e.printStackTrace();
		}

		hashMap.put("totalCount", totalCount);
		hashMap.put("list", list);
		
		return hashMap;
	}

	public int updateUser(UserVO userVO) {
		int result = 0;
		
		try {
			result = userDAO.updateUser(userVO);
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".updateUser] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean checkDuplication(String userId) {
		boolean result=true;
		
		try {
			UserVO userVO=userDAO.getUser(userId);
			
			if(userVO != null) {
				result=false;
			}
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".checkDuplication] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deleteUser(String userId) {
		int result = 0;
		
		try {
			result = userDAO.deleteUser(userId);
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".deleteUser] Exception");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
}
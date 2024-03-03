package com.model2.mvc.service.user.test;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.UserVO;

import utils.SqlSessionFactoryBean;

public class UserMapperTest {
	public static void main(String[] args) {
		// 0-1. Take SqlSession instance by SqlSessionFactoryBean
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		// 0-2. Testing User Domain, Search Domain
		UserVO userVO = new UserVO();
		userVO.setUserId("TestUser");
		userVO.setUserName("test");
		userVO.setPassword("1234");
		userVO.setEmail("abc@naver.com");
		userVO.setPhone("010-1234-5678");
		userVO.setAddr("서울");
		
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(1);
		searchVO.setPageSize(5);
		
		System.out.println("==========<1. addUser Test>==========");
		System.out.println(sqlSession.insert("UserMapper.addUser", userVO));
		
		System.out.println("==========<2. getUserList Test>==========");
		SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper.getUserList", searchVO));
		
		System.out.println("==========<3. updateUser Test>==========");
		userVO.setUserName("changeTest");
		userVO.setEmail("def@daum.net");
		System.out.println(sqlSession.update("UserMapper.updateUser", userVO));
		
		System.out.println("==========<4. getUser Test>==========");
		System.out.println(sqlSession.selectOne("UserMapper.getUser", userVO.getUserId()).toString());
		
		System.out.println("==========<5. removeUser Test>==========");
		System.out.println(sqlSession.selectList("UserMapper.removeUser", userVO.getUserId()));
		
		System.out.println("==========<6. getUserCount Test>==========");
		System.out.println(sqlSession.selectOne("UserMapper.getUserCount", searchVO ).toString());
		
		System.out.println("==========<7. getUserList Test : searchCondition == 'userId'>==========");
		searchVO.setSearchCondition("userId");
		searchVO.setSearchKeyword("user");
		
		SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper.getUserList", searchVO));
		System.out.println("==========<8. getUserList Test : searchCondition == 'userName'>==========");
		searchVO.setSearchCondition("userName");
		searchVO.setSearchKeyword("SCOTT");
		
		SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper.getUserList", searchVO));
		
		System.out.println("==========<9. SqlSession Close>==========");
		sqlSession.close();
	}
}

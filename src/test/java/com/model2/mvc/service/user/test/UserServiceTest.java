package com.model2.mvc.service.user.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })

@ContextConfiguration(locations = {
		"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml"
})
public class UserServiceTest {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Test
	public void getUserAndAddUserTest() {
		UserVO userVO = new UserVO();
		userVO.setUserId("testUserId");
		userVO.setUserName("testUserName");
		userVO.setPassword("testPasswd");
		userVO.setSsn("1111112222222");
		userVO.setPhone("111-2222-3333");
		userVO.setAddr("서울 홍대입구역");
		userVO.setEmail("test@test.com");
	
		int result = userService.addUser(userVO);

		UserVO userResult = userService.getUser("testUserId");
		
		Assert.assertEquals(1, result);
		Assert.assertEquals("testUserId", userVO.getUserId());
		Assert.assertEquals("testUserName", userVO.getUserName());
		Assert.assertEquals("testPasswd", userVO.getPassword());
		Assert.assertEquals("1111112222222", userVO.getSsn());
		Assert.assertEquals("111-2222-3333", userVO.getPhone());
		Assert.assertEquals("서울 홍대입구역", userVO.getAddr());
		Assert.assertEquals("test@test.com", userVO.getEmail());
	}
	
	@Test
	public void getUserListAllTest() {
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(1);
		searchVO.setPageSize(5);
	 	Map<String,Object> map = userService.getUserList(searchVO);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(5, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println("[Test] totalCount : " + totalCount);
	}
	
	@Test
	public void getUserListByUserIdTest() {
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(1);
		searchVO.setPageSize(5);
		searchVO.setSearchCondition("userId");
		searchVO.setSearchKeyword("user");
	 	Map<String,Object> map = userService.getUserList(searchVO);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(5, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println("[Test] totalCount : " + totalCount);
	}
	
	@Test
	public void getUserListByUserNameTest() {
		SearchVO search = new SearchVO();
	 	search.setPage(1);
	 	search.setPageSize(5);
	 	search.setSearchCondition("userName");
	 	search.setSearchKeyword("SCOTT");
	 	Map<String,Object> map = userService.getUserList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(5, list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println("[Test] totalCount : " + totalCount);
	}
	
	@Test
	public void updateUserTest() {
		UserVO userVO = new UserVO();
		userVO.setUserId("testUserId");
		userVO.setUserName("testUserName01");
		userVO.setPassword("testPasswd01");
		userVO.setSsn("2222233333");
		userVO.setPhone("222-222-11111");
		userVO.setAddr("경기도 판교");
		userVO.setEmail("test01@test.com");
		
		int result = userService.updateUser(userVO);
		
		Assert.assertEquals(1, result);
	}
	
	@Test
	public void deleteUserTest() {
		String userId = "testUserId";
		
		int result = userService.deleteUser(userId);
		
		Assert.assertEquals(1, result);
	}
}
package com.model2.mvc.view.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.view.common.CommonController;
/**
 * https://velog.io/@twomin/JSON%EC%9D%98-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EC%82%AC%EC%9A%A9%EB%B2%95#point-1--json%EC%9C%BC%EB%A1%9C-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B3%80%ED%99%98%EC%9D%84-%EC%9D%B5%ED%98%94%EB%8B%A4%EB%A9%B4-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%A0%84%EC%86%A1%ED%95%A0%EA%B9%8C
 */
@RestController
@RequestMapping("/rest/user/*")
public class UserRestController extends CommonController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	// test
	@RequestMapping(value = "login")
	public Map<String, Object> login(HttpSession session, @ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.loginUser()] start");
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("path","redirect:/index.jsp");
		session.setAttribute("user",  userService.loginUser(user));
		
		System.out.println("[UserController.loginUser()] end");
		return map;
	}
	
	// test
	@RequestMapping(value = "logout")
	public Map<String, Object> logout(HttpSession session) {
		System.out.println("[UserController.logout()] start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		session.invalidate();
		map.put("path", "redirect:/index.jsp");
		
		System.out.println("[UserController.logout()] end");
		
		return map;
	}
	
	// test
	@RequestMapping(value = "checkDuplication")
	public Map<String, Object> checkDuplication(@RequestBody UserVO user) {
		System.out.println("[UserController.checkDuplicationUser()] start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		boolean result = userService.checkDuplication(user.getUserId());
		
		map.put("path", "forward:/user/checkDuplication.jsp");
		map.put("result", result);
		map.put("userId", user.getUserId());
		
		System.out.println("[UserController.checkDuplicationUser()] end");
		
		return map;
	}			
		
	// Test
	@RequestMapping(value = "getUser/{userId}")
	public Map<String, Object> getUser(@PathVariable("userId") String userId) {
		
		System.out.println("[UserController.getUser()] start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
//		ModelAndView modelAndView = new ModelAndView("forward:/user/readUser.jsp");
//		UserVO user = userService.getUser(userId);
//		modelAndView.addObject("user", user);
		
		UserVO user = userService.getUser(userId);
		map.put("path", "forward:/user/readUser.jsp");
		map.put("user", user);
		
		System.out.println("[UserController.getUser()] end");
		
		return map;
	}
	
	@RequestMapping(value = "addUser")
	public ModelAndView addUser(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.addUser()] start");
		
		userService.addUser(user);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/loginView.jsp");
		
		System.out.println("[UserController.addUser()] end");
		
		return modelAndView;
	}
	
	// Test
	@RequestMapping(value = "listUser/{page}")
	public Map<String, Object> listUser(
			@ModelAttribute("search") SearchVO search,
			// @ModelAttribute("user") UserVO user,
			@PathVariable("page") int page) {
		System.out.println("[UserController.listUser()] start");
		
		// 1. Page setting Default page = 1;
		search.setPage(page);
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		search.setPageUnit(PAGE_UNIT); 
		search.setPageSize(PAGE_SIZE);
		Map<String, Object> map = userService.getUserList(search);
		 
		Page resultPage	= new Page (
				search.getPage(), 
				((Integer)map.get("totalCount")).intValue(), 
				PAGE_UNIT,
				PAGE_SIZE
		);

//		ModelAndView modelAndView = new ModelAndView("forward:/user/listUser.jsp");
//		modelAndView.addObject("list", map.get("list"));
//		modelAndView.addObject("resultPage", resultPage);
//		modelAndView.addObject("search", search);
//		modelAndView.addObject("getList", "fncGetUserList");
		
		Map<String, Object> result = new HashMap<String, Object>();
		map.put("path", "forward:/user/listUser.jsp");
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("search", search);
		map.put("getList", "fncGetUserList");
		
		
		System.out.println("[UserController.listUser()] end");
		
		return map;
	}
	
	@RequestMapping(value = "updateUserView/{userId}")
	public ModelAndView updateUserView(@PathVariable("userId") String userId) {
		System.out.println("[UserController.updateUserView()] start");
		
		UserVO resultUser = userService.getUser(userId);
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/updateUser.jsp");
		modelAndView.addObject("user", resultUser);
		
		System.out.println("[UserController.updateUserView()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "updateUser")
	public ModelAndView updateUser(HttpSession session, @ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.updateUser()] start");
		
		userService.updateUser(user);
		
		String sessionId=((UserVO)session.getAttribute("user")).getUserId();
		
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/getUser/" + user.getUserId());
		
		System.out.println("[UserController.updateUser()] end");
		
		return modelAndView;
	}
}

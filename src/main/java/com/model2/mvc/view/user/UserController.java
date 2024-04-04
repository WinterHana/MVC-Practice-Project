package com.model2.mvc.view.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.view.common.CommonController;

@Controller
@RequestMapping("/user/*")
public class UserController extends CommonController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@PostMapping(value = "login")
	public ModelAndView login(HttpSession session, @ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.loginUser()] start");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		session.setAttribute("user",  userService.loginUser(user));
		
		System.out.println("[UserController.loginUser()] end");
		return modelAndView;
	}
	
	@GetMapping(value = "loginView")
	public ModelAndView loginView() {
		System.out.println("[UserController.loginUser()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/login.jsp");
		
		System.out.println("[UserController.loginUser()] end");
		return modelAndView;
	}
	
	@GetMapping(value = "logout")
	public ModelAndView logout(HttpSession session) {
		System.out.println("[UserController.logout()] start");
		
		session.invalidate();
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/login.jsp");
		
		System.out.println("[UserController.logout()] end");
		
		return modelAndView;
	}
	
	@PostMapping(value = "checkDuplication")
	public ModelAndView checkDuplication(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.checkDuplicationUser()] start");
		
		boolean result = userService.checkDuplication(user.getUserId());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("result", result);
		modelAndView.addObject("userId", user.getUserId());
		
		System.out.println("[UserController.checkDuplicationUser()] end");
		return modelAndView;
	}			
	
	@PostMapping(value = "getUser/{userId}")
	public ModelAndView getUser(@PathVariable("userId") String userId) {
		
		System.out.println("[UserController.getUser()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/getUser.jsp");
		modelAndView.addObject("user", userService.getUser(userId));
		System.out.println("[UserController.getUser()] end");
		
		return modelAndView;
	}	
	
	// Error 방지 GetMapping
	@GetMapping(value = "getUser/{userId}")
	public ModelAndView getUserMethodGet(@PathVariable("userId") String userId) {
		
		System.out.println("[UserController.getUser()] start");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		
		System.out.println("[UserController.getUser()] end");
		
		return modelAndView;
	}	
	
	@PostMapping(value = "addUser")
	public ModelAndView addUser(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.addUser()] start");
		
		userService.addUser(user);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/loginView.jsp");
		
		System.out.println("[UserController.addUser()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "listUser/{page}")
	public ModelAndView listUser(
			@ModelAttribute("search") SearchVO search,
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

		ModelAndView modelAndView = new ModelAndView("forward:/user/listUser.jsp");
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.addObject("getList", "fncGetUserList");
		
		System.out.println("[UserController.listUser()] end");
		
		return modelAndView;
	}
	
	@PostMapping(value = "updateUserView/{userId}")
	public ModelAndView updateUserView(@PathVariable("userId") String userId) {
		System.out.println("[UserController.updateUserView()] start");
		
		UserVO resultUser = userService.getUser(userId);
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/updateUser.jsp");
		modelAndView.addObject("user", resultUser);
		
		System.out.println("[UserController.updateUserView()] end");
		
		return modelAndView;
	}
	
	@PostMapping(value = "updateUser")
	public ModelAndView updateUser(HttpSession session, @ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.updateUser()] start");
		
		userService.updateUser(user);
		
		String sessionId=((UserVO)session.getAttribute("user")).getUserId();
		
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/getUser/" + user.getUserId());
		
		System.out.println("[UserController.updateUser()] end");
		
		return modelAndView;
	}
	
	@PostMapping(value = "deleteUser")
	public ModelAndView deleteUser(
			HttpSession session,
			@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.deleteUser()] start");

		String url = null;
		String userRole=((UserVO)session.getAttribute("user")).getRole();
		if(userRole.equals("admin")) {
			url = "redirect:/user/listUser/1";
		} else {
			url = "redirect:/user/loginView";
		}
		ModelAndView modelAndView = new ModelAndView(url);
		userService.deleteUser(user.getUserId());
		
		System.out.println("[UserController.deleteUser()] end");
		
		return modelAndView;
	}
}

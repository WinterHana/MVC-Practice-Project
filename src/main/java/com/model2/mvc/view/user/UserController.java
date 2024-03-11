package com.model2.mvc.view.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class UserController extends CommonController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;
	
	@RequestMapping(value = "/login.do")
	public ModelAndView loginUser(HttpSession session, @ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.loginUser()] start");
		
		ModelAndView modelAndView = new ModelAndView("redirect:/index.jsp");
		session.setAttribute("user",  userService.loginUser(user));
		
		System.out.println("[UserController.loginUser()] end");
		return modelAndView;
	}
	
	@RequestMapping(value = "/logout.do")
	public ModelAndView logoutUser(HttpSession session) {
		System.out.println("[UserController.logout()] start");
		
		session.invalidate();
		
		ModelAndView modelAndView = new ModelAndView("redirect:/index.jsp");
		
		System.out.println("[UserController.logout()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/checkDuplication.do")
	public ModelAndView checkDuplicationUser(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.checkDuplicationUser()] start");
		
		boolean result = userService.checkDuplication(user.getUserId());
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/checkDuplication.jsp");
		modelAndView.addObject("result", result);
		modelAndView.addObject("userId", user.getUserId());
		
		System.out.println("[UserController.checkDuplicationUser()] end");
		return modelAndView;
	}			
			
	@RequestMapping(value = "/getUser.do")
	public ModelAndView getUser(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.getUser()] start");
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/readUser.jsp");
		modelAndView.addObject("user", userService.getUser(user.getUserId()));
				
		System.out.println("[UserController.getUser()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/addUser.do")
	public ModelAndView addUser(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.addUser()] start");
		
		userService.addUser(user);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/loginView.jsp");
		
		System.out.println("[UserController.addUser()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/listUser.do")
	public ModelAndView listUser(
			@ModelAttribute("search") SearchVO search,
			@ModelAttribute("user") UserVO user,
			HttpServletRequest request) {
		System.out.println("[UserController.listUser()] start");
		
		// 1. Page setting
		String currentPage = request.getParameter("currentPage");
		
		int page = 0;
		if(currentPage != null && !currentPage.equals("undefined")) {
			page = Integer.parseInt(currentPage);
		}
		
		// Default page = 1;
		search.setPage(page);
		if(search.getPage() == 0) {
			search.setPage(1);
		}
		
		search.setPageUnit(PAGE_UNIT); 
		search.setPageSize(PAGE_SIZE);
		Map<String, Object> map = userService.getUserList(search);
		
		Page resultPage	= new Page(
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
	
	@RequestMapping(value = "/updateUserView.do")
	public ModelAndView updateUserView(@ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.updateUserView()] start");
		
		UserVO resultUser = userService.getUser(user.getUserId());
		
		ModelAndView modelAndView = new ModelAndView("forward:/user/updateUser.jsp");
		modelAndView.addObject("user", resultUser);
		
		System.out.println("[UserController.updateUserView()] end");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateUser.do")
	public ModelAndView updateUser(HttpSession session, @ModelAttribute("user") UserVO user) {
		System.out.println("[UserController.updateUser()] start");
		
		userService.updateUser(user);
		
		String sessionId=((UserVO)session.getAttribute("user")).getUserId();
		
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/getUser.do?userId="+ user.getUserId());
		
		System.out.println("[UserController.updateUser()] end");
		
		return modelAndView;
	}
}

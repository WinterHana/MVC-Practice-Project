package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class LoginAction extends Action {
    
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		UserVO userVO=new UserVO();
		userVO.setUserId(request.getParameter("userId"));
		userVO.setPassword(request.getParameter("password"));
		
		// UserService service=new UserServiceImpl();
		UserVO dbVO=userService.loginUser(userVO);
		
		HttpSession session=request.getSession();
		session.setAttribute("user", dbVO);
		
		return "redirect:/index.jsp";
	}
}
package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@RequestMapping(value="/user")
@Controller
public class UserController {
	@Autowired
	UserService uService;
	
	@RequestMapping(value="/joinForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String joinForm() {
		//System.out.println("UserController > joinForm");
		return "user/joinForm";
	}
	
	@RequestMapping(value="/join", method= {RequestMethod.GET, RequestMethod.POST})
	public String join(@ModelAttribute UserVo uVo) {
		//System.out.println("UserController > join");
		//System.out.println(uVo);
		
		int count = uService.join(uVo);
		//System.out.println(count + "건 가입 성공");
		
		return "user/joinSuccess";
	}
	
	@RequestMapping(value="/loginForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String loginForm() {
		//System.out.println("UserController > loginForm");
		return "user/loginForm";
	}
	
	@RequestMapping(value="/login", method= {RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo uVo, HttpSession session) {
		//System.out.println("UserController > login");
		//System.out.println(uVo);
		
		UserVo authUser = uService.login(uVo);
		
		if(authUser != null) {
			session.setAttribute("authUser", authUser);
			return "redirect:/";
		} else {
			return "redirect:/user/loginForm?login=fail";
		}
	}
	
	@RequestMapping(value="logout", method= {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		//System.out.println("UserController > logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
}

package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	@RequestMapping(value="/", method= {RequestMethod.POST, RequestMethod.GET})
	public String main(HttpSession session) {
		session.removeAttribute("bMap");
		
		return "main/index";
	}
}

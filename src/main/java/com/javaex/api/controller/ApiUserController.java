package com.javaex.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;

@RequestMapping(value="/api/user")
@Controller
public class ApiUserController {
	@Autowired
	UserService uService;
	
	//중복체크
	@ResponseBody
	@RequestMapping(value="/checkId", method= {RequestMethod.POST, RequestMethod.GET})
	public boolean checkId(@RequestBody String checkId) {
		//System.out.println("ApiUserController > checkId");
		//System.out.println(checkId);
		
		boolean check = uService.checkId(checkId);
		//System.out.println(check);
		
		return check;
	}
}

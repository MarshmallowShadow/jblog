package com.javaex.api.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/api/post")
@Controller
public class ApiPostController {
	@RequestMapping(value="/getPostInfo", method= {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> getPostInfo(int cateNo) {
		
	}
}

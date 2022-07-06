package com.javaex.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.BlogService;

@Controller
public class BlogController {
	@Autowired
	BlogService bService;
	
	@RequestMapping(value="/{id}", method= {RequestMethod.GET, RequestMethod.POST})
	public String getBlog(@PathVariable String id, Model model) {
		//System.out.println("BlogController > getBlog");
		//System.out.println(id);
		
		Map<String, String> bMap = bService.getBlog(id);
		model.addAttribute("bMap", bMap);
		
		return "blog/blog-main";
	}
}

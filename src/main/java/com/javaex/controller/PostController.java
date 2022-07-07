package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.PostService;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Controller
public class PostController {
	@Autowired
	PostService pService;
	
	@RequestMapping(value="/{id}/admin/writeForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String writeForm(@PathVariable String id, HttpSession session) {
		System.out.println("PostController > writeForm");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String checkId = authUser.getId();
		if(!id.equals(checkId)) {
			return "error/403";
		}
		
		return "blog/admin/blog-admin-write";
	}
	
	@RequestMapping(value="/{id}/admin/write", method= {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute PostVo pVo) {
		System.out.println("PostController > write");
		System.out.println(pVo);
		
		int count = pService.write(pVo);
		
		return "redirect:/{id}/admin/writeForm";
	}
	
	
}

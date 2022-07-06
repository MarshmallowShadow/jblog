package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.vo.UserVo;

@Controller
public class CategoryController {
	
	//카테고리 페이지로 이동
	@RequestMapping(value="/{id}/admin/category", method= {RequestMethod.POST, RequestMethod.GET})
	public String getCategory(@PathVariable String id, HttpSession session, Model model) {
		System.out.println("CategoryController > category");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String checkId = authUser.getId();
		if(!id.equals(checkId)) {
			return "error/403";
		}
		
		return "blog/admin/blog-admin-cate";
	}
}

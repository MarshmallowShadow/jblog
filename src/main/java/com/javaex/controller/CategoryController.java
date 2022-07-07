package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.CategoryService;
import com.javaex.vo.UserVo;

@Controller
public class CategoryController {
	@Autowired
	CategoryService cService;
	
	//카테고리 페이지로 이동
	@RequestMapping(value="/{id}/admin/category", method= {RequestMethod.POST, RequestMethod.GET})
	public String getCategory(@PathVariable String id, HttpSession session, Model model) {
		//System.out.println("CategoryController > getCategory");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String checkId = authUser.getId();
		Map<String, String> bMap = cService.getHeader(id, checkId);
		if(bMap == null) {
			return "error/403";
		}
		model.addAttribute("bMap", bMap);
		
		return "blog/admin/blog-admin-cate";
	}
}

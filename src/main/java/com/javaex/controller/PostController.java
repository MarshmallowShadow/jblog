package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.BlogService;
import com.javaex.service.PostService;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Controller
public class PostController {
	@Autowired
	PostService pService;
	
	@Autowired
	BlogService bService;
	
	@RequestMapping(value="/{id}/admin/writeForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String writeForm(@PathVariable String id, Model model, HttpSession session) {
		System.out.println("PostController > writeForm");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String checkId = authUser.getId();
		Map<String, Object> postMap = pService.writeForm(id, checkId);
		
		if(postMap == null) {
			return "error/403";
		}
		
		model.addAttribute("bMap", postMap.get("bMap"));
		model.addAttribute("cList", postMap.get("cList"));
		
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

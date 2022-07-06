package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.BlogService;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

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
	
	@RequestMapping(value="/{id}/admin/basic", method= {RequestMethod.GET, RequestMethod.POST})
	public String getBasic(@PathVariable String id, Model model, HttpSession session) {
		//System.out.println("BlogController > basic");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String checkId = authUser.getId();
		if(!id.equals(checkId)) {
			return "redirect:/" + id;
		}
		
		Map<String, String> bMap = bService.getBlog(id);
		model.addAttribute("bMap", bMap);
		
		return "blog/admin/blog-admin-basic";
	}
	
	@RequestMapping(value="/{id}/admin/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@PathVariable String id, @RequestParam("blogTitle") String blogTitle,
			@RequestParam("file") MultipartFile file) {
		//System.out.println("BlogController > modify");
		
		BlogVo bVo = new BlogVo();
		bVo.setId(id);
		bVo.setBlogTitle(blogTitle);
		
		int count = bService.modify(bVo, file);
		
		return "redirect:/" + id;
	}
}

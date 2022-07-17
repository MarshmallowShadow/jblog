package com.javaex.controller;

import java.util.List;
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
import com.javaex.vo.CategoryVo;
import com.javaex.vo.UserVo;

@Controller
public class BlogController {
	@Autowired
	private BlogService bService;
	
	@RequestMapping(value="/{id}", method= {RequestMethod.GET, RequestMethod.POST})
	public String getBlog(@PathVariable String id, Model model,
			@RequestParam(value="cateNo", required=false, defaultValue="0") int cateNo,
			@RequestParam(value="postNo", required=false, defaultValue="0") int postNo,
			@RequestParam(value="pageNo", required=false, defaultValue="1") int pageNo) {
		//System.out.println("BlogController > getBlog");
		//System.out.println(id);
		
		Map<String, Object> blogMap = bService.getBlog(id, cateNo, postNo, pageNo);
		model.addAttribute("bMap", blogMap.get("bMap"));
		model.addAttribute("cList", blogMap.get("cList"));
		model.addAttribute("pList", blogMap.get("pList"));
		model.addAttribute("pMap", blogMap.get("pMap"));
		model.addAttribute("pageMap", blogMap.get("pageMap"));
		
		return "blog/blog-main";
	}
	
	@RequestMapping(value="/{id}/admin/basic", method= {RequestMethod.GET, RequestMethod.POST})
	public String getBasic(@PathVariable String id, HttpSession session, Model model) {
		//System.out.println("BlogController > basic");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String checkId = authUser.getId();
		Map<String, String> bMap = bService.getBasic(id, checkId);
		if(bMap == null) {
			return "error/403";
		}
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
		
		return "redirect:/" + id + "/admin/basic";
	}
}

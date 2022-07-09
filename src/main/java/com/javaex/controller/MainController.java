package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService mService;
	
	@RequestMapping(value="/", method= {RequestMethod.POST, RequestMethod.GET})
	public String main(HttpSession session) {
		return "main/index";
	}
	
	@RequestMapping(value="/search", method= {RequestMethod.GET, RequestMethod.POST})
	public String search(Model model,
			@RequestParam(value="pageNo", required=false, defaultValue="1") int pageNo,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword,
			@RequestParam(value="kwdOpt", required=false, defaultValue="optTitle") String kwdOpt) {
		//System.out.println("MainController > search");
		
		Map<String, Object> dMap = mService.searchList(keyword, kwdOpt, pageNo);
		model.addAttribute("bList", dMap.get("bList"));
		model.addAttribute("pageMap", dMap.get("pageMap"));
		
		return "main/index";
	}
}

package com.javaex.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.CategoryService;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.UserVo;

@RequestMapping(value="/api/category")
@Controller
public class ApiCategoryController {
	@Autowired
	CategoryService cService;
	
	@ResponseBody
	@RequestMapping(value="/getList", method= {RequestMethod.GET, RequestMethod.POST})
	public List<Map<String, Object>> getList(@RequestBody String id){
		//System.out.println("ApiCategoryController > getList");
		
		List<Map<String, Object>> cList = cService.getList(id);
		return cList;
	}
	
	@ResponseBody
	@RequestMapping(value="/addCategory", method= {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> addCategory(@RequestBody CategoryVo cVo){
		//System.out.println("ApiCategoryController > addCategory");
		
		Map<String, Object> cMap = cService.addCategory(cVo);
		return cMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteCategory", method= {RequestMethod.GET, RequestMethod.POST})
	public int deleteCategory(@RequestBody int cateNo) {
		//System.out.println("CategoryController > deleteCategory");
		
		int count = cService.deleteCategory(cateNo);
		return count;
	}
	
	@ResponseBody
	@RequestMapping(value="/getNameList", method= {RequestMethod.GET, RequestMethod.POST})
	public List<CategoryVo> getNameList() {
		//System.out.println("ApiCategoryController > getNameList");
		
		List<CategoryVo> cVo = cService.getNameList();
		return cVo;
	}
}

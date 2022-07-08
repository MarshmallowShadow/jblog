package com.javaex.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.CommentService;
import com.javaex.vo.CommentVo;

@RequestMapping(value="/api/comments")
@Controller
public class ApiCommentController {
	@Autowired
	CommentService cService;
	
	@ResponseBody
	@RequestMapping(value="/getList", method={RequestMethod.GET, RequestMethod.POST})
	public List<Map<String, Object>> getList(@RequestBody int postNo) {
		//System.out.println("ApiCommentController > getList");
		
		List<Map<String, Object>> comList = cService.getList(postNo);
		return comList;
	}
	
	@ResponseBody
	@RequestMapping(value="/addComment", method={RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> addComment(@RequestBody CommentVo comVo) {
		//System.out.println("ApiCommentController > addComment");
		//System.out.println(comVo);
		
		Map<String, Object> comMap = cService.addComment(comVo);
		return comMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public int delete(@RequestBody int cmtNo) {
		//System.out.println("ApiCommentController > delete");
		
		int count = cService.delete(cmtNo);
		return count;
	}
}

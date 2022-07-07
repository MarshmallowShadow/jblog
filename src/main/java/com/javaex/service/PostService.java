package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CategoryDao;
import com.javaex.dao.PostDao;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.PostVo;

@Service
public class PostService {
	@Autowired
	PostDao pDao;
	@Autowired
	CategoryDao cDao;
	@Autowired
	BlogDao bDao;
	
	//카테고리 영역 가져오기
	public Map<String, Object> writeForm(String id, String checkId) {
		//System.out.println("PostService > WriteForm");
		
		if(!id.equals(checkId)) {
			return null;
		}
		
		Map<String, Object> postMap = new HashMap<>();
		Map<String, String> bMap = bDao.getHeader(id);
		postMap.put("bMap", bMap);
		List<CategoryVo> cList = cDao.getNameList(id);
		postMap.put("cList", cList);
		
		return postMap;
	}
	
	//글 추가
	public int write(PostVo pVo) {
		//System.out.println("PostService > addPost");
		
		pVo.setPostContent(pVo.getPostContent().replace("\n", "<br>"));
		
		int count = pDao.write(pVo);
		return count;
	}
}

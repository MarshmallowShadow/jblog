package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CategoryDao;
import com.javaex.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao cDao;
	@Autowired
	private BlogDao bDao;
	
	//카테고리 페이지에 헤더 정보 가져오기
	public Map<String, String> getHeader(String id, String checkId) {
		//System.out.println("CategoryService > getHeader");
		
		if(!id.equals(checkId)) {
			return null;
		}
		Map<String, String> bMap = bDao.getHeader(id);
		
		return bMap;
	}
	
	//카테고리 리스트 가져오기
	public List<Map<String, Object>> getList(String id) {
		//System.out.println("CategoryService > getList");
		
		List<Map<String, Object>> cList = cDao.getList(id);
		return cList;
	}
	
	//카테고리 추가
	public Map<String, Object> addCategory(CategoryVo cVo) {
		//System.out.println("CategoryService > addCategory");
		
		int count = cDao.addCategory(cVo);
		
		Map<String, Object> cMap = null;
		int cateNo = cVo.getCateNo();
		cMap = cDao.getCategory(cateNo);
		
		return cMap;
	}
	
	//카테고리 삭제
	public int deleteCategory(int cateNo) {
		//System.out.println("CategoryService > delteCategory");
		
		int count = cDao.delteCategory(cateNo);
		return count;
	}
}

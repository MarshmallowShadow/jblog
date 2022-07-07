package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.CategoryVo;

@Repository
public class CategoryDao {
	@Autowired
	private SqlSession sqlSession;
	
	//카테고리 리스트 가져오기
	public List<Map<String, Object>> getList(String id) {
		//System.out.println("CategoryDao > getList");
		
		List<Map<String, Object>> cList = sqlSession.selectList("category.getList", id);
		//System.out.println(cList);
		
		return cList;
	}
	
	//카테고리 추가
	public int addCategory(CategoryVo cVo) {
		//System.out.println("CategoryDao > addCategory");
		
		int count = sqlSession.insert("category.addCategory", cVo);
		//System.out.println(cVo);
		
		return count;
	}
	//카테고리 추가 후 정보 가져오기
	public Map<String, Object> getCategory(int cateNo) {
		//System.out.println("CategoryDao > getCategory");
		
		Map<String, Object> cMap = sqlSession.selectOne("category.getCategory", cateNo);
		//System.out.println(cMap);
		
		return cMap;
	}
	
	//카테고리 삭제
	public int delteCategory(int cateNo) {
		//System.out.println("CategoryDao > deleteCategory");
		
		int count = sqlSession.delete("category.deleteCategory", cateNo);
		return count;
	}
	
	//글 작성용 카테고리 가져오기
	public List<CategoryVo> getNameList(String id) {
		System.out.println("CategoryDao > getNameList");
		
		List<CategoryVo> cVo = sqlSession.selectList("category.getNameList", id);
		System.out.println(cVo);
		
		return cVo;
	}
}

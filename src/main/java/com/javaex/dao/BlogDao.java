package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;

@Repository
public class BlogDao {
	@Autowired
	private SqlSession sqlSession;
	
	//회원가입 시 블로그도 만들기
	public int join(BlogVo bVo) {
		//System.out.println("BlogDao > join");
		
		int count = sqlSession.insert("blog.join", bVo);
		return count;
	}
	
	//개인 블로그 정보 가져오기
	public Map<String, String> getBlog(String id) {
		//System.out.println("BlogDao > getBlog");
		
		Map<String, String> bMap = sqlSession.selectOne("blog.getBlog", id);
		//System.out.println(bMap);
		
		return bMap;
	}
	
	//블로그 헤더 정보 가져오기
	public Map<String, String> getHeader(String id) {
		//System.out.println("BlogDao > getBlog");
		
		Map<String, String> bMap = sqlSession.selectOne("blog.getHeader", id);
		//System.out.println(bMap);
		
		return bMap;
	}
	
	//블로그 수정
	public int modify(BlogVo bVo) {
		//System.out.println("BlogDao > modify");
		
		int count = sqlSession.update("blog.modify", bVo);
		return count;
	}
	
	//블로그 로고 정보 가져오기
	public String getLogoFile(String id) {
		//System.out.println("BlogDao > getLogoFile");
		
		String logoFile = sqlSession.selectOne("getLogoFile", id);
		//System.out.println(logoFile);
		
		return logoFile;
	}
	
	//블로그 검색 리스트 가져오기
	public List<Map<String, String>> searchList(Map<String, Object> sMap) {
		//System.out.println("BlogDao > searchList");
		
		List<Map<String, String>> bList = sqlSession.selectList("blog.searchPageList", sMap);
		//System.out.println(bList);
		
		return bList;
	}
	
	//블로그 검색 결과 총 횟수
	public int getSearchCount(Map<String, Object> sMap) {
		System.out.println("BlogController > Map");
		
		int count = sqlSession.selectOne("blog.getSearchCount", sMap);
		return count;
	}
	
}

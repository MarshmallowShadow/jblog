package com.javaex.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;

@Repository
public class BlogDao {
	@Autowired
	SqlSession sqlSession;
	
	public int join(BlogVo bVo) {
		//System.out.println("BlogDao > join");
		int count = sqlSession.insert("blog.join", bVo);
		return count;
	}
	
	public Map<String, String> getBlog(String id) {
		//System.out.println("BlogDao > getBlog");
		
		Map<String, String> bMap = sqlSession.selectOne("blog.getBlog", id);
		//System.out.println(bMap);
		
		return bMap;
	}
}

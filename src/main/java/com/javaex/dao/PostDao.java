package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PostVo;

@Repository
public class PostDao {
	@Autowired
	private SqlSession sqlSession;
	
	//글 추가
	public int write(PostVo pVo) {
		System.out.println("PostDao > addPost");
		
		int count = sqlSession.insert("post.write", pVo);
		return count;
	}
	
	
}

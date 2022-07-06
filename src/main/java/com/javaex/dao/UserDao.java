package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	SqlSession sqlSession;
	
	//아이디 중복확인
	public String checkId(String checkId) {
		//System.out.println("UserDao > checkId");
		//System.out.println(checkId);
		
		String id = sqlSession.selectOne("users.checkId", checkId);
		//System.out.println(id);
		
		return id;
	}
	
	//회원가입
	public int join(UserVo uVo) {
		//System.out.println("UserDao > join");
		int count = sqlSession.insert("users.join", uVo);
		return count;
	}
	
	//로그인
	public UserVo login(UserVo uVo) {
		//System.out.println("UserDao > login");
		
		UserVo authUser = sqlSession.selectOne("users.login", uVo);
		//System.out.println(authUser);
		
		return authUser;
	}
}

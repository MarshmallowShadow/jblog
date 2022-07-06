package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao uDao;
	
	@Autowired
	private BlogDao bDao;
	
	//중복체크
	public boolean checkId(String checkId) {
		//System.out.println("UserService > checkId");
		//System.out.println(checkId);
		
		String id = uDao.checkId(checkId);
		
		if(id == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//회원가입
	public int join(UserVo uVo) {
		//System.out.println("UserService > join");
		int count = uDao.join(uVo);
		
		int bCount = 0;
		if(count > 0) {
			BlogVo bVo = new BlogVo();
			bVo.setId(uVo.getId());
			bVo.setBlogTitle(uVo.getUserName() + "의 블로그");
			bVo.setLogoFile("/assets/images/spring-logo.jpg");
			bCount = bDao.join(bVo);
		}
		
		return bCount;
	}
	
	//로그인
	public UserVo login(UserVo uVo) {
		//System.out.println("UserService > login");
		UserVo authUser = uDao.login(uVo);
		return authUser;
	}
}

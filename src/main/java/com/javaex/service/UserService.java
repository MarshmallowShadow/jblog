package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	@Autowired
	UserDao uDao;
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
		return count;
	}
	
	//로그인
	public UserVo login(UserVo uVo) {
		//System.out.println("UserService > login");
		UserVo authUser = uDao.login(uVo);
		return authUser;
	}
}

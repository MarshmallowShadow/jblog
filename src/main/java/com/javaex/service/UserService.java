package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CategoryDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao uDao;
	
	@Autowired
	private BlogDao bDao;
	
	@Autowired
	private CategoryDao cDao;
	
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
		
		if(count > 0) {
			BlogVo bVo = new BlogVo();
			bVo.setId(uVo.getId());
			bVo.setBlogTitle(uVo.getUserName() + "의 블로그");
			bVo.setLogoFile("s");
			count += bDao.join(bVo);
			
			CategoryVo cVo = new CategoryVo();
			cVo.setCateName("기타");
			cVo.setDescription("기타 카테고리");
			cVo.setId(uVo.getId());
			count += cDao.addCategory(cVo);
		}
		
		return count;
	}
	
	//로그인
	public UserVo login(UserVo uVo) {
		//System.out.println("UserService > login");
		UserVo authUser = uDao.login(uVo);
		return authUser;
	}
}

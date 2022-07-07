package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.PostDao;
import com.javaex.vo.PostVo;

@Service
public class PostService {
	@Autowired
	PostDao pDao;
	
	//글 추가
	public int write(PostVo pVo) {
		System.out.println("PostService > addPost");
		
		int count = pDao.write(pVo);
		return count;
	}
	
	
}

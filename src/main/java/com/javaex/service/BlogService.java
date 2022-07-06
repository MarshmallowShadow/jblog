package com.javaex.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;

@Service
public class BlogService {
	@Autowired BlogDao bDao;
	
	public Map<String, String> getBlog(String id) {
		//System.out.println("BlogService > getBlog");
		
		Map<String, String> bVo = bDao.getBlog(id);
		return bVo;
	}
}

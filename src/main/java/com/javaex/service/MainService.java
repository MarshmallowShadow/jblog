package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;

@Service
public class MainService {
	@Autowired
	BlogDao bDao;
	
	public Map<String, Object> searchList(String keyword, String kwdOpt, int pageNo) {
		//System.out.println("MainService > searchList");
		
		Map<String, Object> dMap = new HashMap<>();
		
		Map<String, Object> sMap = new HashMap<>();
		if(keyword == null) {
			keyword = "";
		}
		//System.out.println(keyword);
		
		//키워드
		if(kwdOpt.equals("optName")) {
			sMap.put("optName", "%" + keyword + "%");
			sMap.put("optTitle", "");
		} else {
			sMap.put("optTitle", "%" + keyword + "%");
			sMap.put("optName", "");
		}
		//페이지
		int start = (pageNo - 1) * 10 + 1;
		int end = pageNo * 10;
		sMap.put("start", start);
		sMap.put("end", end);
		System.out.println(sMap);
		//블로그 리스트
		List<Map<String, String>> bList = bDao.searchList(sMap);
		if(bList.size() == 0) {
			bList = null;
		}
		//System.out.println(bList);
		dMap.put("bList", bList);
		
		//페이지 리스트
		Map<String, Object> pageMap = new HashMap<>();
		int currPage = pageNo;
		int maxPage = (int)Math.ceil(bDao.getSearchCount(sMap) / (double)10);
		int startPage = (int)Math.floor(currPage / (double)5) * 5 + 1;
		int endPage = (int)Math.ceil(currPage / (double)5) * 5;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		boolean prev = true;
		boolean next = true;
		if(startPage <= 1) {
			prev = false;
		}
		if(endPage == maxPage) {
			next = false;
		}
		pageMap.put("currPage", currPage);
		pageMap.put("startPage", startPage);
		pageMap.put("endPage", endPage);
		pageMap.put("prev", prev);
		pageMap.put("next", next);
		dMap.put("pageMap", pageMap);
		
		return dMap;
	}
}

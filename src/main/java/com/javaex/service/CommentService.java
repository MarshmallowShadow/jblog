package com.javaex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.CommentDao;
import com.javaex.vo.CommentVo;

@Service
public class CommentService {
	@Autowired
	private CommentDao cDao;
	
	//포스트의 코멘트 리스트 가져오기
	public List<Map<String, Object>> getList(int postNo) {
		//System.out.println("CommentService > getList");
		
		List<Map<String, Object>> comList = cDao.getList(postNo);
		return comList;
	}
	
	//코멘트 DB에 추가 후 jsp에 실시간 추가용 Map보내기
	public Map<String, Object> addComment(CommentVo comVo){
		//System.out.println("CommentService > addComment");
		
		int count = cDao.addComment(comVo);
		if(count < 0) {
			return null;
		}
		Map<String, Object> comMap = cDao.getComment(comVo.getCmtNo());
		
		return comMap;
	}
	
	//코멘트 삭제
	public int delete(int cmtNo) {
		System.out.println("CommentService > delete");
		
		int count = cDao.delete(cmtNo);
		return count;
	}
}

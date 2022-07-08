package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.CommentVo;

@Repository
public class CommentDao {
	@Autowired
	SqlSession sqlSession;
	
	//코멘트 리스트 가져오기
	public List<Map<String, Object>> getList(int postNo){
		//System.out.println("CommentDao > getList");
		
		List<Map<String, Object>> comList = sqlSession.selectList("comments.getList", postNo);
		//System.out.println(comList);
		
		return comList;
	}
	
	//코멘트 추가
	public int addComment(CommentVo cVo) {
		//System.out.println("CommentDao > getList");
		
		int count = sqlSession.insert("comments.addComment", cVo);
		return count;
	}
	//ajax로 바로 추가할 코멘트 가져오기
	public Map<String, Object> getComment(int cmtNo){
		//System.out.println("CommentDao > getComment");
		
		Map<String, Object> comMap = sqlSession.selectOne("comments.getComment", cmtNo);
		//System.out.println(comMap);
		
		return comMap;
	}
	
	//코멘트 삭제
	public int delete (int cmtNo) {
		System.out.println("CommentDao > delete");
		
		int count = sqlSession.delete("comments.delete", cmtNo);
		return count;
	}
}

package com.javaex.dao;

import java.util.List;
import java.util.Map;

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
		//System.out.println("PostDao > addPost");
		
		int count = sqlSession.insert("post.write", pVo);
		return count;
	}

	//글 리스트 가져오기
	public List<PostVo> getList(Map<String, Object> dMap) {
		//System.out.println("PostDao > getList");
		
		List<PostVo> pList = sqlSession.selectList("post.getList", dMap);
		//System.out.println(pList);
		
		return pList;
	}
	
	//글 가져오기
	public Map<String, String> getPost(Map<String, Object> dMap) {
		//System.out.println("PostDao > getPost");
		
		Map<String, String> pMap = sqlSession.selectOne("post.getPost", dMap);
		//System.out.println(pMap);
		
		return pMap;
	}
}

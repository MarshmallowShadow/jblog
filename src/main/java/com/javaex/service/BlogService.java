package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CategoryDao;
import com.javaex.dao.PostDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogDao bDao;
	
	@Autowired
	private PostDao pDao;
	
	@Autowired
	private CategoryDao cDao;
	
	//블로그 정보 가져오기
	public Map<String, Object> getBlog(String id, int cateNo, int postNo) {
		//System.out.println("BlogService > getBlog");
		
		Map<String, Object> blogMap = new HashMap<>();
		//블로그 정보
		Map<String, String> bMap = bDao.getBlog(id);
		blogMap.put("bMap", bMap);
		//카테고리 영역
		List<CategoryVo> cList = cDao.getNameList(id);
		blogMap.put("cList", cList);
		//개시글 영역
		List<PostVo> pList = null;
		if(cList != null && cateNo == 0 && postNo == 0) {
			cateNo = cList.get(0).getCateNo();
		}
		pList = pDao.getList(cateNo);
		blogMap.put("pList", pList);
		//view용 개시글
		Map<String, String> pMap = null;
		if(pList != null && postNo == 0) {
			postNo = pList.get(0).getPostNo();
		}
		pMap = pDao.getPost(postNo);
		blogMap.put("pMap", pMap);
		
		return blogMap;
	}
	
	//블로그 수정 페이지 정보 가져오기
	public Map<String, String> getBasic(String id, String checkId) {
		//System.out.println("BlogService > getHeader");
		
		if(!id.equals(checkId)) {
			return null;
		}
		
		Map<String, String> bVo = bDao.getBlog(id);
		return bVo;
	}
	
	//블로그 정보 수정
	public int modify(BlogVo bVo, MultipartFile file) {
		//System.out.println("BlogService > modify");
		
		String logoFile;
		long fileSize = file.getSize();
		//System.out.println(fileSize);
		
		if(fileSize > 0) {
			String orgName = file.getOriginalFilename();
			String exName = orgName.substring(orgName.lastIndexOf("."));
			long rand1 = System.currentTimeMillis();
			String rand2 = UUID.randomUUID().toString();
			
			String saveName = rand1 + rand2 + exName;
			String filePath = "C:\\javastudy\\upload\\" + saveName;
			
			try {
				byte[] fileData = file.getBytes();
				
				OutputStream os = new FileOutputStream(filePath);
				BufferedOutputStream bos = new BufferedOutputStream(os);
				bos.write(fileData);
				bos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			logoFile = "upload/" + saveName;
		} else {
			logoFile = bDao.getLogoFile(bVo.getId());
		}
		
		//System.out.println(logoFile);
		
		bVo.setLogoFile(logoFile);
		int count = bDao.modify(bVo);
		
		return count;
	}
}

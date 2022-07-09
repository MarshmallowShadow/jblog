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
	public Map<String, Object> getBlog(String id, int cateNo, int postNo, int pageNo) {
		//System.out.println("BlogService > getBlog");
		
		//컨트롤러로 한번에 보낼 정보
		Map<String, Object> blogMap = new HashMap<>();
		
		//다오로 보낼 정보
		Map<String, Object> dMap = new HashMap<>();
		dMap.put("id", id);
		
		//블로그 정보
		Map<String, String> bMap = bDao.getBlog(id);
		blogMap.put("bMap", bMap);
		
		//카테고리 영역
		List<CategoryVo> cList = cDao.getNameList(id);
		blogMap.put("cList", cList);
		
		//개시글 영역
		List<PostVo> pList = null;
		if(cList != null && cateNo == 0 && postNo == 0) {
			if(cList.size() > 0) {
				cateNo = cList.get(0).getCateNo();
			}
		}
		dMap.put("cateNo", cateNo);
		if(cateNo != 0) {
			//페이지 순번
			int start = (pageNo-1) * 10 + 1;
			int end = pageNo * 10;
			
			pList = pDao.getList(dMap, start, end);
		}
		blogMap.put("pList", pList);
		
		//view용 개시글
		Map<String, String> pMap = null;
		if(pList != null && postNo == 0) {
			if(pList.size() > 0) {
				postNo = pList.get(0).getPostNo();
			}
		}
		if(postNo != 0) {
			dMap.put("postNo", postNo);
			pMap = pDao.getPost(dMap);
		}
		blogMap.put("pMap", pMap);
		
		//페이지 순번 구하기
		Map<String, Object> pageMap = null;
		if(postNo != 0) {
			pageMap = new HashMap<>();
			int currPage = pageNo;
			int maxPage = (int)Math.ceil(pDao.getCount(cateNo)/(double)10);
			//System.out.println(maxPage);
			int startPage = ((int)Math.floor(pageNo/(double)5) * 5) + 1;
			//System.out.println(startPage);
			int endPage = (int)Math.ceil(pageNo/(double)5) * 5;
			if(endPage > maxPage) {
				endPage = maxPage;
			}
			//System.out.println(endPage);
			boolean prev = true;
			if(startPage == 1) {
				prev = false;
			}
			boolean next = true;
			if(endPage >= maxPage) {
				next = false;
			}
			pageMap.put("startPage", startPage);
			pageMap.put("currPage", currPage);
			pageMap.put("endPage", endPage);
			pageMap.put("prev", prev);
			pageMap.put("next", next);
			
			blogMap.put("pageMap", pageMap);
		}
		
		
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

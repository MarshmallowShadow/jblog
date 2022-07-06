package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogDao bDao;
	
	//블로그 정보 가져오기
	public Map<String, String> getBlog(String id) {
		//System.out.println("BlogService > getBlog");
		
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

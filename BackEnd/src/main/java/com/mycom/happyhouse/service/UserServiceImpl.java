package com.mycom.happyhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dao.UserDao;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserParamDto;
import com.mycom.happyhouse.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	

	static final String uploadFolder = "profile";
	private static final String uploadPath = "C:" + File.separator + "SSAFY_SpringBoot"
            + File.separator + "Happy_House_5_Spring" 
            + File.separator + "src" 
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "static"
			+ File.separator + "upload";
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@Override
	public UserResultDto userRegister(UserDto userDto) {
		System.out.println("회원가입 서비스 실행");
		UserResultDto userResultDto = new UserResultDto();
		if( userDao.userRegister(userDto) == 1 ) {
			userResultDto.setResult(SUCCESS);
		}else {
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}

	@Override
	public UserDto findpw(String userEmail) {
		System.out.println("비밀번호 찾기 서비스 실행");
		
		UserDto userResultDto = new UserDto();
		
		userResultDto = userDao.findpw(userEmail);
		if( userResultDto != null ) {
			return userResultDto;
		}else {
			return null;
		}
	
	}
	
	
	@Override
	public int modify(UserParamDto userParamDto) {
		System.out.println("UserServiceImpl");
		if(userDao.modify(userParamDto) == 1) {
			return 1;
		}else {
			return -1;
		}
	}

	
	@Override
	public int signout(String userEmail) {
		System.out.println();
		
		if(userDao.signout(userEmail) == 1) {
			return 1;
		}else {
			return -1;
		}
	}

	@Override
	public int updateprofile(UserDto userDto, MultipartHttpServletRequest request) {
		int result;
		
		if ( !userDto.getUserProfileImageUrl().equals("profile/005_noprofile.PNG")) {
			// 서버에 있는 파일 삭제
			String url = userDto.getUserProfileImageUrl().substring(8);
			System.out.println("수정된 url " + url);
			File file = new File(uploadPath + File.separator + uploadFolder, url);
            if(file.exists()) {
                file.delete();
            }
		}
		
		try {
			
	        List<MultipartFile> fileList = request.getFiles("file");
	        
	        File uploadDir = new File(uploadPath + File.separator + uploadFolder);
	        if (!uploadDir.exists()) uploadDir.mkdir(); // 만약 경로가 없다면 자동으로 만들어 준다.

	        for (MultipartFile part : fileList) {
	        	String fileName = part.getOriginalFilename(); // 실제로 첨부했을때 file 이름
	            
	            //Random File Id
	            UUID uuid = UUID.randomUUID(); // id가 중복되지 않도록 해준다. 
	            
	            //file extension
	            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName() // 확장자
	        
	            String savingFileName = uuid + "." + extension; // 파일 이름과 확장자를 붙인다.
	        
	            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName); // 최종 경로
	            
	            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            part.transferTo(destFile); // part를 destFile쪽으로 넘긴다.	
	        
	            String FileUrl = uploadFolder + "/" + savingFileName;
	            System.out.println("바뀐 URL :"+FileUrl);
	        	userDto.setUserProfileImageUrl(FileUrl);
	            System.out.println(userDto.toString());
	        
	        }
	        
	        userDao.updateprofile(userDto);

	        
	        result = 1;
		} catch(Exception e) {
	        e.printStackTrace();
	        result = -1;
	        
	    }
		return result;
	}
	
}

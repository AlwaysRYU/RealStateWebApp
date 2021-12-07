package com.mycom.happyhouse.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserParamDto;
import com.mycom.happyhouse.dto.UserResultDto;

public interface UserService {
	public UserResultDto userRegister(UserDto userDto);
	public UserDto findpw(String userEmail);
	
	public int signout(String userEmail);
	
	public int modify(UserParamDto userParamDto);
	
	public int updateprofile(UserDto userDto, MultipartHttpServletRequest request);
	
	
}

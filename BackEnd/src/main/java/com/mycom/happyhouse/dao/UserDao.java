package com.mycom.happyhouse.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserParamDto;

@Mapper
public interface UserDao {
	// 회원가입
	public int userRegister(UserDto userDto);
	public int userInfo(UserDto userDto);
	public UserDto findpw(String email);
	
	// 회원정보수정
	public int modify(UserParamDto userParamDto);
	// 회원탈퇴
	public int signout(String userEmail);
	// 이미지포함 회원정보수정
	public int updateprofile(UserDto userDto);
	
	
}

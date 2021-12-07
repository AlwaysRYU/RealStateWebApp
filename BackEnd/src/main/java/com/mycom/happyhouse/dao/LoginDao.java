package com.mycom.happyhouse.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.UserDto;

@Mapper
public interface LoginDao {
	public UserDto login(String userEmail);
}

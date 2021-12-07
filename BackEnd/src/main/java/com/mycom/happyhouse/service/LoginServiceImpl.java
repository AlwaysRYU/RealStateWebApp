package com.mycom.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.LoginDao;
import com.mycom.happyhouse.dto.UserDto;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	
	@Override
	public UserDto login(UserDto dto) {
		
		UserDto userDto = loginDao.login(dto.getUserEmail());
		System.out.println(userDto.toString());
		if( userDto != null && userDto.getUserPassword().equals(dto.getUserPassword())) {
			
			return userDto;
		}else {
			return null;
		}
	}
}
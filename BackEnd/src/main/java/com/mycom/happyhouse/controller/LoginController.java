package com.mycom.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.service.LoginService;

@CrossOrigin(
		// 어디로부터 들어오는 걸 허용할 것인지?
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
				RequestMethod.HEAD, RequestMethod.OPTIONS   } 
		)


	@RestController
	public class LoginController {
	
		@Autowired
		LoginService service;
		
		@PostMapping(value="/login")
		public ResponseEntity<UserDto> login(@RequestBody UserDto dto, HttpSession session){
			
			System.out.println("로그인 요청이 왔다!! " + dto.toString() );
			
			UserDto userDto = service.login(dto);
			if( userDto != null ) {
				session.setAttribute("userDto", userDto);
				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
			}
			return new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
		}
		
		
	}



package com.mycom.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserParamDto;
import com.mycom.happyhouse.dto.UserResultDto;
import com.mycom.happyhouse.service.UserService;

@CrossOrigin(
		// 어디로부터 들어오는 걸 허용할 것인지?
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
				RequestMethod.HEAD, RequestMethod.OPTIONS   } 
		)

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@PostMapping(value="/register")
	public ResponseEntity<UserResultDto> register(@RequestBody UserDto dto, HttpSession session){
		System.out.println("회원가입 컨트롤러 실행");
		UserResultDto userResultDto = userService.userRegister(dto);
		System.out.println(dto.toString());
		
		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/mypage")
	public ResponseEntity<UserResultDto> userInfo(UserDto dto, HttpSession session){
		System.out.println("마이페이지 컨트롤러 실행");
		UserResultDto userResultDto = userService.userRegister(dto);
		
		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/findpw/{userEmail}")
	private ResponseEntity<UserDto> findpw(@PathVariable String userEmail, HttpSession session) throws Exception{
		
		UserDto boardResultDto = userService.findpw(userEmail);
	
	    if( boardResultDto != null ) {
	        return new ResponseEntity<UserDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<UserDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	    
	}
	
	@PostMapping(value="/modify")
    private ResponseEntity<UserResultDto> modi(@RequestBody UserParamDto userParamDto, 
    		HttpSession session) throws Exception{

        System.out.println(userParamDto.getUserName());
        System.out.println(userParamDto.getUserEmail());
        System.out.println(userParamDto.getUserPassword());
        int boardResultDto = userService.modify(userParamDto);
        UserResultDto rs = new UserResultDto();
        rs.setResult(boardResultDto);
        System.out.println("수정 컨트롤러!!");
        
        if( boardResultDto == 1 ) {
        	System.out.println("성공");
            return new ResponseEntity<UserResultDto>(rs, HttpStatus.OK);
        }else {
        	System.out.println("실패");
            return new ResponseEntity<UserResultDto>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }         
        
    }
	
	
	@DeleteMapping(value="/deleteuser/{userEmail}")
	private ResponseEntity<UserResultDto> signout(@PathVariable String userEmail, HttpSession session) throws Exception{
		System.out.println("회원탈퇴 요청이 옴!!");
		int result = userService.signout(userEmail);
		UserResultDto rs = new UserResultDto();
        rs.setResult(result);
	    if( result == SUCCESS ) {
	        return new ResponseEntity<UserResultDto>(rs, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<UserResultDto>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	    
	}
	
	// 이미지 포함
	@PostMapping(value="/updateprofile")
    private ResponseEntity<UserResultDto> updateprofile(
    		UserDto userDto, 
    		MultipartHttpServletRequest request) throws Exception{
		System.out.println("이미지 파일 포함 수정 컨트롤러");
		System.out.println("들어온 유저정보 " + userDto);
		int userResult = userService.updateprofile(userDto, request);
		UserResultDto rs = new UserResultDto();
        rs.setResult(userResult);
        
        if( userResult == 1 ) {
        	System.out.println("성공");
            return new ResponseEntity<UserResultDto>(rs, HttpStatus.OK);
        }else {
        	System.out.println("실패");
            return new ResponseEntity<UserResultDto>(rs, HttpStatus.INTERNAL_SERVER_ERROR);
        }         
        
    }
	
	
	
	
}

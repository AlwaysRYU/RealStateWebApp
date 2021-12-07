package com.mycom.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.dto.CodeDto;
import com.mycom.happyhouse.service.CodeService;

@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",// axios가 서로 다른 session을 보내는데 이를 제어해준다.
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
				RequestMethod.DELETE,RequestMethod.HEAD,RequestMethod.OPTIONS
		})

@RestController
public class CodeController {

	@Autowired
	CodeService service;
		
	@GetMapping(value="/codes")
	public ResponseEntity<List<CodeDto>> codeList(String groupCode){
		List<CodeDto> list = service.codeList(groupCode);
		if( list != null ) {
			return new ResponseEntity<List<CodeDto>>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<CodeDto>>(list, HttpStatus.NOT_FOUND);
		}
	}
}

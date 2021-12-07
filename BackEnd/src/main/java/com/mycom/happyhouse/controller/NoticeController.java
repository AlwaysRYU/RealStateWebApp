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

import com.mycom.happyhouse.dto.NoticeDto;
import com.mycom.happyhouse.dto.NoticeParamDto;
import com.mycom.happyhouse.dto.NoticeResultDto;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.service.NoticeService;


@CrossOrigin(
		// 어디로부터 들어오는 걸 허용할 것인지?
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
				RequestMethod.HEAD, RequestMethod.OPTIONS   } 
		)
@RestController
public class NoticeController {
	
	@Autowired
	NoticeService service;
	
	private static final int SUCCESS = 1;

	
	// 리스트를 생성한다.
	// 먼저 검색어가 없다면 boardList 를 이용한 쿼리문 실행 , 검색어가 잇다면 boardListSearchWord를 리용한 쿼리문 실행
	// 이후 저장된 값들을 return
    @GetMapping(value="/notice")
    private ResponseEntity<NoticeResultDto> boardList(NoticeParamDto boardParamDto){

//        String s = null;
//        s.length();

    	NoticeResultDto boardResultDto;

	    if( boardParamDto.getSearchWord().isEmpty() ) {
	    	boardResultDto = service.boardList(boardParamDto);
	    }else {
	    	boardResultDto = service.boardListSearchWord(boardParamDto); // 검색어가 있다면
	    }
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

    // 상세페이지
    // 먼저 boardId를 set, userSeq를 통해 현재 사용자의 userSeq를 set
    // 이후 해당 값들을 가진 창을 생성
	@GetMapping(value="/notice/{boardId}") // 키 값도 받아온다.
	private ResponseEntity<NoticeResultDto> boardDetail(@PathVariable int boardId, HttpSession session){
	
	    NoticeParamDto boardParamDto = new NoticeParamDto();
	    boardParamDto.setBoardId(boardId);
	    boardParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq()); // 사용자임을 알려줘야 sameUser를 처리 할 수 있게 된다.
	    
	    NoticeResultDto boardResultDto = service.boardDetail(boardParamDto);
	
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	// 등록
	// 먼저 로그인 필터를 통해 session 지정
	// userDto에 session값 저장
	// 현재 session에 저장된 사용자의 userSeq를 통해 booardDto의 userSeq 지정
	// 지정된 user에 값을 입력
	@PostMapping(value="/notice") // 등록
	private ResponseEntity<NoticeResultDto> boardInsert(
	        NoticeDto boardDto, 
	        MultipartHttpServletRequest request) {
	    
	    // LoginFilter 먼저 적용 필요!!
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    System.out.println(userDto.getUserSeq() + " 시퀀스 확인용");
	    boardDto.setUserSeq(userDto.getUserSeq());
	            
	    NoticeResultDto boardResultDto = service.boardInsert(boardDto, request);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// PUT + multipart/form-data (X)
	// In RESTful,
	// PUT & DELETE methods are defined to be idempotent
	// 만약 글만 수정한다면?? PUT mapping OK  여러 번 수정해도 back-end 결과가 같음
	// 첨부파일이 포함되면 idempotent 하지 않음.
	
	@PostMapping(value="/notice/{boardId}") 
	private ResponseEntity<NoticeResultDto> boardUpdate(
	        NoticeDto boardDto, 
	        MultipartHttpServletRequest request){
	
	    HttpSession session = request.getSession();
	    // NoticeDto userDto = (NoticeDto) session.getAttribute("userDto");
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    boardDto.setUserSeq(userDto.getUserSeq());
	    
	    NoticeResultDto boardResultDto = service.boardUpdate(boardDto, request);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }        
	}
	
	// 삭제
	// boardId를 받아와 해당 아이디에 boarDelete 쿼리문 실행후 값을 저장
	@DeleteMapping(value="/notice/{boardId}") 
	private ResponseEntity<NoticeResultDto> boardDelete(@PathVariable(value="boardId") int boardId){
		NoticeResultDto boardResultDto = service.boardDelete(boardId);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
}

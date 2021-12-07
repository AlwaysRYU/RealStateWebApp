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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardParamDto;
import com.mycom.happyhouse.dto.BoardResultDto;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.service.BoardService;

@CrossOrigin(
		// 어디로부터 들어오는 걸 허용할 것인지?
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
				RequestMethod.HEAD, RequestMethod.OPTIONS   } 
		)

@RestController
public class BoardController {
	
	@Autowired
	BoardService service;
	
	private static final int SUCCESS = 1;

	
	// 리스트를 생성한다.
	// 먼저 검색어가 없다면 boardList 를 이용한 쿼리문 실행 , 검색어가 잇다면 boardListSearchWord를 리용한 쿼리문 실행
	// 이후 저장된 값들을 return
    @GetMapping(value="/boards")
    private ResponseEntity<BoardResultDto> boardList(BoardParamDto boardParamDto){

//        String s = null;
//        s.length();

        BoardResultDto boardResultDto;
        System.out.println(boardParamDto.toString());

	    if( boardParamDto.getSearchWord().isEmpty() ) {
	    	System.out.println("검색어가 없음!!" + boardParamDto.getSearchWord() );
	        boardResultDto = service.boardList(boardParamDto);
	    }else {
	    	System.out.println("검색어가 있음" + boardParamDto.getSearchWord() );
	        boardResultDto = service.boardListSearchWord(boardParamDto); // 검색어가 있다면
	    }
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

    // 상세페이지
    // 먼저 boardId를 set, userSeq를 통해 현재 사용자의 userSeq를 set
    // 이후 해당 값들을 가진 창을 생성
	@GetMapping(value="/boards/{boardId}") // 키 값도 받아온다.
	private ResponseEntity<BoardResultDto> boardDetail(@PathVariable int boardId, HttpSession session) throws Exception{
		System.out.println();
	    BoardParamDto boardParamDto = new BoardParamDto();
	    boardParamDto.setBoardId(boardId);
	    boardParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq()); // 사용자임을 알려줘야 sameUser를 처리 할 수 있게 된다.
	    
	    BoardResultDto boardResultDto = service.boardDetail(boardParamDto);
	
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	// 등록
	// 먼저 로그인 필터를 통해 session 지정
	// userDto에 session값 저장
	// 현재 session에 저장된 사용자의 userSeq를 통해 booardDto의 userSeq 지정
	// 지정된 user에 값을 입력
	@PostMapping(value="/boards") // 등록
	private ResponseEntity<BoardResultDto> boardInsert(
	        BoardDto boardDto, 
	        MultipartHttpServletRequest request) {
		
	    // LoginFilter 먼저 적용 필요!!
	    HttpSession session = request.getSession();
	    System.out.println("글작성하기" + session );
	    
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    boardDto.setUserSeq(userDto.getUserSeq());

		
	    BoardResultDto boardResultDto = service.boardInsert(boardDto, request);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// PUT + multipart/form-data (X)
	// In RESTful,
	// PUT & DELETE methods are defined to be idempotent
	// 만약 글만 수정한다면?? PUT mapping OK  여러 번 수정해도 back-end 결과가 같음
	// 첨부파일이 포함되면 idempotent 하지 않음.
	
	@PostMapping(value="/boards/{boardId}") 
	private ResponseEntity<BoardResultDto> boardUpdate(
	        BoardDto boardDto, 
	        MultipartHttpServletRequest request){
	
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    boardDto.setUserSeq(userDto.getUserSeq());
	    
	    BoardResultDto boardResultDto = service.boardUpdate(boardDto, request);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }        
	}
	
	// 삭제
	// boardId를 받아와 해당 아이디에 boarDelete 쿼리문 실행후 값을 저장
	@DeleteMapping(value="/boards/{boardId}") 
	private ResponseEntity<BoardResultDto> boardDelete(@PathVariable(value="boardId") int boardId){
	    BoardResultDto boardResultDto = service.boardDelete(boardId);
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// 인기글
	@GetMapping(value="/popularboards") // 키 값도 받아온다.
	private ResponseEntity<BoardResultDto> PopularBoards(HttpSession session) throws Exception{
			System.out.println("인기글 뽑기");
			BoardResultDto boardResultDto = service.PopularBoards();
		    if( boardResultDto != null ) {
		        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
		    }else {
		        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		    }         
		}
	// 가장 최근 공지사항 4개 뽑는 컨트롤러
	@GetMapping(value="/noticeboards")
	private ResponseEntity<BoardResultDto> NoticeBoards(HttpSession session) throws Exception{
	    System.out.println("공지 4개 뽑기");
	    BoardResultDto boardResultDto = service.NoticeBoards();
	    if( boardResultDto != null ) {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BoardResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
}

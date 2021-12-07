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

import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardResultDto;
import com.mycom.happyhouse.dto.BuildingDto;
import com.mycom.happyhouse.dto.BuildingParamDto;
import com.mycom.happyhouse.dto.BuildingResultDto;
import com.mycom.happyhouse.dto.LikeParamDto;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserResultDto;
import com.mycom.happyhouse.service.BuildingService;

@CrossOrigin(
		// 어디로부터 들어오는 걸 허용할 것인지?
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
				RequestMethod.HEAD, RequestMethod.OPTIONS   } 
		)

@RestController
public class BuildingController {
	
	@Autowired
	BuildingService service;
	
	private static final int SUCCESS = 1;

	
	// 리스트를 생성한다.
	// 먼저 검색어가 없다면 boardList 를 이용한 쿼리문 실행 , 검색어가 잇다면 boardListSearchWord를 리용한 쿼리문 실행
	// 이후 저장된 값들을 return
//    @GetMapping(value="/building")
//    private ResponseEntity<BuildingResultDto> List(BuildingParamDto BuildingParamDto){
//    	System.out.println("빌딩리스트 불러오기 컨트롤러호출됨 : " + BuildingParamDto.getOffset() );
//    	
////        String s = null;
////        s.length();
//
//    	BuildingResultDto boardResultDto;
//
//	    if( BuildingParamDto.getSearchWord().isEmpty() ) {
//	    	System.out.println("검색어가 없다.");
//	        boardResultDto = service.boardList(BuildingParamDto);
//	    }else {
//	    	System.out.println("검색어가 있다.");
//	        boardResultDto = service.boardListSearchWord(BuildingParamDto); // 검색어가 있다면
//	    }
//	    
//	    if( boardResultDto.getResult() == SUCCESS ) {
//	    	System.out.println("리스트불러오기 성공");
//	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.OK);
//	    }else {
//	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
    
	// 검색 2
    @GetMapping(value="/building")
    private ResponseEntity<BuildingResultDto> List(BuildingParamDto BuildingParamDto){
    	System.out.println("빌딩리스트 불러오기 컨트롤러호출됨 : ");
    	System.out.println(BuildingParamDto.toString());
    	
//        String s = null;
//        s.length();

    	BuildingResultDto boardResultDto;
    
	    if( BuildingParamDto.getSearchWord().isEmpty() && BuildingParamDto.getSearchWordName().isEmpty()) {
	    	System.out.println("검색어가 없다.");
	        boardResultDto = service.boardList(BuildingParamDto);
	    }else {
	    	System.out.println("검색어가 있다.");
	        boardResultDto = service.boardListSearchWord(BuildingParamDto); // 검색어가 있다면
	    }
	    
	    if( boardResultDto.getResult() == SUCCESS ) {
	    	System.out.println("리스트불러오기 성공");
	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    

    // 상세페이지
    // 먼저 boardId를 set, userSeq를 통해 현재 사용자의 userSeq를 set
    // 이후 해당 값들을 가진 창을 생성
	@GetMapping(value="/building/{boardId}") // 키 값도 받아온다.
	private ResponseEntity<BuildingResultDto> boardDetail(@PathVariable int boardId){
		
	    BuildingParamDto boardParamDto = new BuildingParamDto();
	    boardParamDto.setBoardId(boardId);
	   
	    BuildingResultDto boardResultDto = service.boardDetail(boardParamDto);
	
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	@GetMapping(value="/buildingdetail") // 키 값도 받아온다.
	private ResponseEntity<BuildingResultDto> BuildingDetail2(BuildingDto Dto){
		 System.out.println("빌딩 디테일 불러오기 컨트롤러호출됨 : " +  Dto.toString());
		 
	    //BuildingParamDto Dto = new BuildingParamDto();
	    BuildingResultDto boardResultDto = service.boardDetail2(Dto);
	
	    if( boardResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingResultDto>(boardResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	
	// 좋아요한 목록 불러오기
	@GetMapping(value="/likelists") // 키 값도 받아온다.
	private ResponseEntity<BuildingResultDto> likeBuildinglist(BuildingParamDto BuildingParamDto){
		System.out.println("좋아요 목록 불러오기 ");
		System.out.println(BuildingParamDto.toString());
	    BuildingResultDto ResultDto = service.likeBuildinglist(BuildingParamDto);
	    System.out.println("좋아요 결과 " + ResultDto.toString() );
	
	    if( ResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BuildingResultDto>(ResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingResultDto>(ResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// 좋아요 디테일
	@GetMapping(value="/likebuildingdetail/{no}") // 키 값도 받아온다.
	private ResponseEntity<BuildingDto> likebuildingdetail(@PathVariable int no, HttpSession session) throws Exception{
		System.out.println("좋아요 디테일" + no);
		BuildingDto result = service.likebuildingdetail(no);
	
	    if( result != null ) {
	    	System.out.println(result.toString());
	        return new ResponseEntity<BuildingDto>(result, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingDto>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// 좋아요 목록에 있는지 확인하는 함수
	@GetMapping(value="/likey") // 키 값도 받아온다.
	private ResponseEntity<Integer> likeycheck(LikeParamDto LikeParamDto, HttpSession session) throws Exception{
		System.out.println("좋아요 목록에 있는지 확인하기" + LikeParamDto.toString());
		
		int result = 0;
		result = service.likeycheck(LikeParamDto.getLikeName(), LikeParamDto.getUserSeq());
		
	    if( result != 0 ) {
	        return new ResponseEntity<Integer>(result, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	@PostMapping(value="/likey") // 등록
	private ResponseEntity<BuildingResultDto> likeyInsert(
			LikeParamDto Dto, 
	        MultipartHttpServletRequest request) {
		System.out.println(Dto.toString());
		
	    System.out.println("유저에 좋아요 추가  ");
		
	    // LoginFilter 먼저 적용 필요!!
	    HttpSession session = request.getSession();
	     
	    BuildingResultDto ResultDto = new BuildingResultDto();
	    int rs = service.likeyInsert(Dto);
	    ResultDto.setResult(rs);
	    
	    if( ResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<BuildingResultDto>(ResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingResultDto>(ResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	
	@DeleteMapping(value="/likey")
	private ResponseEntity<BuildingResultDto> likeyDelete(LikeParamDto LikeParamDto) throws Exception{
		System.out.println("좋아요 목록에서 제거!");
		// LoginFilter 먼저 적용 필요!!
	    BuildingResultDto ResultDto = new BuildingResultDto();
	    int rs = service.likeyDelete(LikeParamDto);
	    ResultDto.setResult(rs);
	    
	    if( rs == SUCCESS ) {
	        return new ResponseEntity<BuildingResultDto>(ResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingResultDto>(ResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	    
	}
	
	// 사용자 좋아요 목록 중에서 랜덤뽑기
	@GetMapping(value="/randomapts") // 키 값도 받아온다.
	private ResponseEntity<BuildingDto> randomapts(HttpSession session) throws Exception{
		System.out.println("란듐뽑기");
		BuildingDto result = service.randomapts();
	
	    if( result != null ) {
	    	System.out.println(result.toString());
	        return new ResponseEntity<BuildingDto>(result, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<BuildingDto>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
}

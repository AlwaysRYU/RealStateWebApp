package com.mycom.happyhouse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 페이지 이동
@Controller
public class HomeController {

	@GetMapping(value="/")
	private String index() {
		return "index.html";
	}
	
	@GetMapping(value="/login")
	private String login() {
		return "login.html";
	}
	
	@GetMapping(value="/register")
	private String register() {
		return "register.html";
	}
	
	@GetMapping(value="/board")
	private String board() {
		return "board/boardMain.html";
	}
	
	@GetMapping(value="/noticelist")
	private String notice() {
		System.out.println("홈컨트롤러 -- 공지사항으로간다. ");
		return "board/notice.html";
	}
	
	@GetMapping(value="/index_AfterLogin")
	private String afterlogin() {
		System.out.println("홈컨트롤러 -- 로그인후 페이지로 간다");
		return "index_AfterLogin.html";
	}
	
	@GetMapping(value="/Building_Search")
	private String building() {
		System.out.println("홈컨트롤러 -- 아파트 매매 검색창으로간다. ");
		return "board/Building_Search.html";
	}
	
	@GetMapping(value="/Findpw")
	private String Findpw() {
		System.out.println("홈컨트롤러 -- 비밀번호 찾기 ");
		return "Find_Password.html";
	}
	
	@GetMapping(value="/modify")
	private String modify() {
		return "board/modify.html";
	}
}

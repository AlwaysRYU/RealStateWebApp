package com.mycom.happyhouse.dto;

import java.util.List;
// 서버쪽에서 데이터를 내려줄떄 사용
// 목록, 상세, 등등 에서 데이터를 보여줄때 BoardResultDto를 이용한다.
public class BoardResultDto {
	private int result;
	private BoardDto dto;
	private List<BoardDto> list;
	private int count;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public BoardDto getDto() {
		return dto;
	}
	public void setDto(BoardDto dto) {
		this.dto = dto;
	}

	public List<BoardDto> getList() {
		return list;
	}
	public void setList(List<BoardDto> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}

package com.mycom.happyhouse.dto;

public class BuildingParamDto {
	
	//목록을 보여줄때 사용
	//페이지 에서 보여줄 글 수 , 글검색
	private int limit;
	private int offset;
	private String searchWord;

	// 유저 좋아요 목록
	private int userSeq;
	
	// 이름으로 검색
	private String searchWordName;
	public String getSearchWordName() {
		return searchWordName;
	}
	public void setSearchWordName(String searchWordName) {
		this.searchWordName = searchWordName;
	}
	
	private int boardId;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	
	@Override
	public String toString() {
		return "BuildingParamDto [limit=" + limit + ", offset=" + offset + ", searchWord=" + searchWord
				+ ", searchWordName=" + searchWordName + ", boardId=" + boardId + ", userSeq=" + userSeq + "]";
	}
	
	
	
	
}

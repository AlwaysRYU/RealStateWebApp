package com.mycom.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardFileDto;
import com.mycom.happyhouse.dto.BoardParamDto;

@Mapper
public interface BoardDao {
	public BoardDto boardDetail(BoardParamDto boardParamDto);
	public List<BoardFileDto> boardDetailFileList(int boardId);

	// map - Dto
	public int boardUserReadCount(BoardParamDto boardParamDto); 

	// map - @param
	public int boardUserReadInsert(
	        @Param("boardId") int boardId, 
	        @Param("userSeq") int userSeq ); 

	public int boardReadCountUpdate(int boardId);


	public int boardDelete(int boardId);    
	public int boardFileDelete(int boardId);
	public List<String> boardFileUrlDeleteList(int boardId);
	public int boardReadCountDelete(int boardId);

	public int boardInsert(BoardDto dto);
	public int boardFileInsert(BoardFileDto dto);

	public List<BoardDto> boardList(BoardParamDto boardParamDto);
	// 전체 개수
	public int boardListTotalCount();

	public List<BoardDto> boardListSearchWord(BoardParamDto boardParamDto);
	public int boardListSearchWordTotalCount(BoardParamDto boardParamDto);

	// 최근 공지사항 리스트 
	public List<BoardDto> boardLatestNotice();
	
	public int boardUpdate(BoardDto dto);
	
	// 인기글
	public BoardDto popularboard();
	// 공지사항
	public BoardDto latestnotice();
}

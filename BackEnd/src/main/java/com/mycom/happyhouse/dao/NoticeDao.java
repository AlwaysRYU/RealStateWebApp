package com.mycom.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardParamDto;
import com.mycom.happyhouse.dto.NoticeDto;
import com.mycom.happyhouse.dto.NoticeFileDto;
import com.mycom.happyhouse.dto.NoticeParamDto;

@Mapper
public interface NoticeDao {
	public NoticeDto boardDetail(NoticeParamDto boardParamDto);
	public List<NoticeFileDto> boardDetailFileList(int boardId);

	// map - Dto
	public int boardUserReadCount(NoticeParamDto boardParamDto); 

	// map - @param
	public int boardUserReadInsert(
	        @Param("boardId") int boardId, 
	        @Param("userSeq") int userSeq ); 

	public int boardReadCountUpdate(int boardId);


	public int boardDelete(int boardId);    
	public int boardFileDelete(int boardId);
	public List<String> boardFileUrlDeleteList(int boardId);
	public int boardReadCountDelete(int boardId);

	public int boardInsert(NoticeDto dto);
	public int boardFileInsert(NoticeFileDto dto);

	public List<NoticeDto> boardList(NoticeParamDto boardParamDto);
	public int boardListTotalCount();

	public List<NoticeDto> boardListSearchWord(NoticeParamDto boardParamDto);
	public int boardListSearchWordTotalCount(NoticeParamDto boardParamDto);


	public int boardUpdate(NoticeDto dto);
}

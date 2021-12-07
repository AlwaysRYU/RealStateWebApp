package com.mycom.happyhouse.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.NoticeDto;
import com.mycom.happyhouse.dto.NoticeParamDto;
import com.mycom.happyhouse.dto.NoticeResultDto;

public interface NoticeService {
	public NoticeResultDto boardDetail(NoticeParamDto boardParamDto);

	public NoticeResultDto boardDelete(int boardId);

	public NoticeResultDto boardUpdate(NoticeDto dto, MultipartHttpServletRequest request);

	public NoticeResultDto boardInsert(NoticeDto dto, MultipartHttpServletRequest request);

	public NoticeResultDto boardList(NoticeParamDto boardParamDto);

	public NoticeResultDto boardListSearchWord(NoticeParamDto boardParamDto);
}

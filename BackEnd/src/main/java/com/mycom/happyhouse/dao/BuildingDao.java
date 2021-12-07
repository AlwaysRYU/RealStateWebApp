package com.mycom.happyhouse.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.happyhouse.dto.BuildingDto;
import com.mycom.happyhouse.dto.BuildingParamDto;
import com.mycom.happyhouse.dto.LikeParamDto;

@Mapper
public interface BuildingDao {
	// 목록 불러오기 
	public List<BuildingDto> boardList(BuildingParamDto boardParamDto);
	// 검색하기
	public List<BuildingDto> boardListSearchWord(BuildingParamDto boardParamDto);
	
	// 디테일
	//public BoardDto boardDetail(BoardParamDto boardParamDto);
	public List<BuildingDto> boardDetail(BuildingParamDto boardParamDto);
	// 디테일2
	public List<BuildingDto> boardDetail2(BuildingDto Dto);
	
	// 좋아요 리스트
	public List<BuildingDto> likeBuildinglist(BuildingParamDto BuildingParamDto);
	public int likeBuildinglistTotalCount(BuildingParamDto BuildingParamDto);
	// 좋아요 디테일
	public BuildingDto likebuildingdetail(int no);
	// 좋아요 중복 체크
	public int likeycheck(@Param("no")int no, @Param("userSeq")int userSeq);
	// 좋아요 추가
	public int likeyInsert(LikeParamDto dto);
	// 좋아요 제거
	public int likeyDelete(LikeParamDto dto);
	// 좋아요 중 랜덤 뽑기
	public BuildingDto randomapts();
	
	// 총 개수
	public int BoardListTotalCount();
    public int boardListSearchWordTotalCount(BuildingParamDto boardParamDto);

    // 
}

package com.mycom.happyhouse.service;


import com.mycom.happyhouse.dto.BoardResultDto;
import com.mycom.happyhouse.dto.BuildingDto;
import com.mycom.happyhouse.dto.BuildingParamDto;
import com.mycom.happyhouse.dto.BuildingResultDto;
import com.mycom.happyhouse.dto.LikeParamDto;


public interface BuildingService {
	// 목록 불러오기 
	//public List<BuildingDto> boardList(BuildingParamDto boardParamDto);
	public BuildingResultDto boardList(BuildingParamDto boardParamDto);

	// 검색하기
	//public List<BuildingDto> boardListSearchWord(BuildingParamDto boardParamDto, String searchWord);
	public BuildingResultDto boardListSearchWord(BuildingParamDto boardParamDto);
	
	// 디테일
	//public BuildingDto boardDetail(int boardId);
	public BuildingResultDto boardDetail(BuildingParamDto boardParamDto);
	// 디테일2
	public BuildingResultDto boardDetail2(BuildingDto Dto);
	
	
	public BuildingResultDto likeBuildinglist(BuildingParamDto BuildingParamDto);
	
	// 빌딩 디테일 확인
	public BuildingDto likebuildingdetail(int no);
	
	// 좋아요 리스트 중복확인
	public int likeycheck(int no, int userSeq);
	// 좋아요 추가
	public int likeyInsert(LikeParamDto dto);
	// 좋아요 제거
	public int likeyDelete(LikeParamDto dto);
	// 좋아요 중 랜덤 뽑기
	public BuildingDto randomapts();
	
	// 조회수
	//public int boardListTotalCnt();
    //public int boardListSearchWordTotalCount(BuildingParamDto boardParamDto);

    // 
}

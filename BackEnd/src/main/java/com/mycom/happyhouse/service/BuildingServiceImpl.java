package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.BuildingDao;
import com.mycom.happyhouse.dto.BuildingDto;
import com.mycom.happyhouse.dto.BuildingParamDto;
import com.mycom.happyhouse.dto.BuildingResultDto;
import com.mycom.happyhouse.dto.LikeParamDto;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	BuildingDao dao;

	private static final int SUCCESS = 1;
	private static final int FAIL = -1;

	@Override
	public BuildingResultDto boardList(BuildingParamDto boardParamDto) {
		BuildingResultDto boardResultDto = new BuildingResultDto();

		try {
			List<BuildingDto> list = dao.boardList(boardParamDto);
			int count = dao.BoardListTotalCount(); // service에서 모두 계산해서 넘겨온다.
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL); // 오류 발생시
		}
		return boardResultDto;
	}

	@Override
	public BuildingResultDto boardListSearchWord(BuildingParamDto BuildingParamDto) {
		BuildingResultDto boardResultDto = new BuildingResultDto();

		try {
			
			List<BuildingDto> list = dao.boardListSearchWord(BuildingParamDto);
			int count = dao.boardListSearchWordTotalCount(BuildingParamDto); // service에서 모두 계산해서 넘겨온다.
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL); // 오류 발생시
		}
		return boardResultDto;
	}

	@Override
	public BuildingResultDto boardDetail(BuildingParamDto boardParamDto) {
		BuildingResultDto boardResultDto = new BuildingResultDto();

		try {
			
			List<BuildingDto> list = dao.boardDetail(boardParamDto); // 게시글을 읽어온다.
			
			boardResultDto.setDto(list.get(0));

			boardResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL); // 오류 발생시
		}
		return boardResultDto;
	}
	
	// 상세보기 2
	@Override
	public BuildingResultDto boardDetail2(BuildingDto Dto) {
		BuildingResultDto boardResultDto = new BuildingResultDto();

		try {
			List<BuildingDto> list = dao.boardDetail2(Dto);
			int count = dao.BoardListTotalCount(); // service에서 모두 계산해서 넘겨온다.
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL); // 오류 발생시
		}
		return boardResultDto;
	}

	@Override
	public BuildingResultDto likeBuildinglist(BuildingParamDto BuildingParamDto) {
		
		BuildingResultDto boardResultDto = new BuildingResultDto();
		try {
			List<BuildingDto> list = dao.likeBuildinglist(BuildingParamDto);
			int count = dao.likeBuildinglistTotalCount(BuildingParamDto); // service에서 모두 계산해서 넘겨온다.
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL); // 오류 발생시
		}
		return boardResultDto;
	}

	@Override
	public BuildingDto likebuildingdetail(int no) {
		BuildingDto Result = new BuildingDto();
		
		try {
			Result = dao.likebuildingdetail(no);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return Result;
	}

	@Override
	public int likeycheck(int no, int userSeq) {
		int Result;
		System.out.println(no + " ,, " + userSeq);
		try {
			Result = dao.likeycheck(no, userSeq);
			if ( Result == 0 ) {
				Result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return Result;
	}

	@Override
	public int likeyInsert(LikeParamDto dto) {
		int Result = 1;
//		System.out.println(dto.toString());
		try {
			dao.likeyInsert(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return Result;
	}

	@Override
	public int likeyDelete(LikeParamDto dto) {
		// TODO Auto-generated method stub

		int Result = 1;
		try {
			dao.likeyDelete(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return Result;
	}

	@Override
	public BuildingDto randomapts() {
		BuildingDto Result = new BuildingDto();
		try {
			Result = dao.randomapts();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return Result;
	}
	
	

}

package com.mycom.happyhouse.dto;

import java.util.List;
// 서버쪽에서 데이터를 내려줄떄 사용
// 목록, 상세, 등등 에서 데이터를 보여줄때 BoardResultDto를 이용한다.
public class BuildingResultDto {
	private int result;
	private BuildingDto dto;
	private List<BuildingDto> list;
	private int count;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public BuildingDto getDto() {
		return dto;
	}
	public void setDto(BuildingDto dto) {
		this.dto = dto;
	}

	public List<BuildingDto> getList() {
		return list;
	}
	public void setList(List<BuildingDto> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "BuildingResultDto [result=" + result + ", dto=" + dto + ", list=" + list + ", count=" + count + "]";
	}
	

}

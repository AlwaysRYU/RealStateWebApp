package com.mycom.happyhouse.dto;


public class BuildingDto {
	private int no;
	private String dong;
	private String AptName;
	private String dealAmount;
	private String buildYear;
	private String dealYear;
	private String floor;
	private String area;
	private String gugunName;
	
	private double lat = 0;
	private double lng = 0;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {	
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getAptName() {
		return AptName;
	}
	public void setAptName(String aptName) {
		AptName = aptName;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	public String getDealYear() {
		return dealYear;
	}
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	@Override
	public String toString() {
		return "BuildingDto [no=" + no + ", dong=" + dong + ", AptName=" + AptName + ", dealAmount=" + dealAmount
				+ ", buildYear=" + buildYear + ", dealYear=" + dealYear + ", floor=" + floor + ", area=" + area
				+ ", gugunName=" + gugunName + "]";
	}
	
	public BuildingDto() {}
	public BuildingDto(int no, String dong, String aptName, String dealAmount, String buildYear, String dealYear,
			String floor, String area, String gugunName) {
		
		this.no = no;
		this.dong = dong;
		AptName = aptName;
		this.dealAmount = dealAmount;
		this.buildYear = buildYear;
		this.dealYear = dealYear;
		this.floor = floor;
		this.area = area;
		this.gugunName = gugunName;
	}
	
	
	
	
	

}

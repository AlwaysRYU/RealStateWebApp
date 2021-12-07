package com.mycom.happyhouse.dto;

public class LikeParamDto {
	int userSeq;
	int likeName;
	
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public int getLikeName() {
		return likeName;
	}
	public void setLikeName(int likeName) {
		this.likeName = likeName;
	}
	public LikeParamDto() {}
	public LikeParamDto(int userSeq, int likeName) {
		super();
		this.userSeq = userSeq;
		this.likeName = likeName;
	}
	@Override
	public String toString() {
		return "LikeParamDto [userSeq=" + userSeq + ", likeName=" + likeName + "]";
	}
	

}

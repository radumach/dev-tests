package com.scoreServer.server.bean;

public class UserScore {
	
	private Integer userId; 
	private int score;
	
	public UserScore(Integer userId, Integer score) {
		this.userId = userId;
		this.score = score;
	}
	
	public String toString() {
		return userId.toString() + "=" + score;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}

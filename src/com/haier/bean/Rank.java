package com.haier.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;

public class Rank {
	private String userName;
	private String totalScore;
	private String id;
	private int ranking;
	public static Rank parse(String jsonString) throws AppException {
		Rank file = null;
		try {
			file = new Gson().fromJson(jsonString, Rank.class);
		} catch (JsonSyntaxException e) {
			throw AppException.json(e);
		} 
		return file;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
}

package com.haier.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;


public class UserInfo  {
    
    private String userName;       
    private int id;       
    private String totalScore;
    private String sessionId;
    private String expertFlag;
    private String name;
    
    public static UserInfo parse(String jsonString) throws AppException {
    	UserInfo file = null;
    	try {
    		file = new Gson().fromJson(jsonString, UserInfo.class);
    	} catch (JsonSyntaxException e) {
    		throw AppException.json(e);
    	} 
    	return file;
    }
    
	public String getExpertFlag() {
		return expertFlag;
	}
	public void setExpertFlag(String string) {
		this.expertFlag = string;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int string) {
		this.id = string;
	}
	@Override
	public String  toString() 
	{
		return "UserInfo--->"+"id:"+id+"userName:"+userName+" totalScore:"+totalScore+" sessionId:"+sessionId+" name:"+name;
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

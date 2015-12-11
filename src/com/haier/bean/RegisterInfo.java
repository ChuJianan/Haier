package com.haier.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;

public class RegisterInfo {

	private String name;
	private String value;
	
	public static RegisterInfo parse(String jsonString) throws AppException {
		RegisterInfo file = null;
		try {
			file = new Gson().fromJson(jsonString, RegisterInfo.class);
		} catch (JsonSyntaxException e) {
			throw AppException.json(e);
		} 
		return file;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

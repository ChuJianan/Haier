package com.haier.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;

public class Equipments {
	private String id;
	private String name ;
	private String path;
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public static Equipments parse(String jsonString) throws AppException {
		Equipments file = null;
		try {
			file = new Gson().fromJson(jsonString, Equipments.class);
		} catch (JsonSyntaxException e) {
			throw AppException.json(e);
		} 
		return file;
	}
}

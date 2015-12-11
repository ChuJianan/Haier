package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

public class Glorys {
	public final static String INEWS = "files";
	private List<Glory> fileList = new ArrayList<Glory>();

	public Glorys(){}
	public Glorys(List<Glory> files) {
		this.fileList = files;
	}
	
	public List<Glory> getFileList() {
		return fileList;
	}

	public void setFileList(List<Glory> fileList) {
		this.fileList = fileList;
	}
	
	public static Glorys parse(String jsonString) throws AppException {
		Glorys files = new Glorys();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(Glory.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}

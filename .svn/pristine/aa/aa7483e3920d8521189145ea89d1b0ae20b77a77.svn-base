package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

public class Askalls {

	public final static String INEWS = "files";
	private List<AskallInfos> fileList = new ArrayList<AskallInfos>();

	public Askalls(){}
	public Askalls(List<AskallInfos> files) {
		this.fileList = files;
	}
	
	public List<AskallInfos> getFileList() {
		return fileList;
	}

	public void setFileList(List<AskallInfos> fileList) {
		this.fileList = fileList;
	}
	
	public static Askalls parse(String jsonString) throws AppException {
		Askalls files = new Askalls();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(AskallInfos.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}

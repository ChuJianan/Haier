package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

public class Reminds {

	public final static String INEWS = "files";
	private List<RemindInfos> fileList = new ArrayList<RemindInfos>();

	public Reminds(){}
	public Reminds(List<RemindInfos> files) {
		this.fileList = files;
	}
	
	public List<RemindInfos> getFileList() {
		return fileList;
	}

	public void setFileList(List<RemindInfos> fileList) {
		this.fileList = fileList;
	}
	
	public static Reminds parse(String jsonString) throws AppException {
		Reminds files = new Reminds();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(RemindInfos.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}

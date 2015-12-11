package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

import android.util.AndroidException;

public class Friends {
	public final static String INEWS = "files";
	private List<Friend> fileList = new ArrayList<Friend>();

	public Friends(){}
	public Friends(List<Friend> files) {
		this.fileList = files;
	}
	
	public List<Friend> getFileList() {
		return fileList;
	}

	public void setFileList(List<Friend> fileList) {
		this.fileList = fileList;
	}
	
	public static Friends parse(String jsonString) throws AppException {
		Friends files = new Friends();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(Friend.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}



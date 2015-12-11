package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

public class Registers {

	public final static String INEWS = "files";
	private List<RegisterInfo> fileList = new ArrayList<RegisterInfo>();

	public Registers(){}
	public Registers(List<RegisterInfo> files) {
		this.fileList = files;
	}
	
	public List<RegisterInfo> getFileList() {
		return fileList;
	}

	public void setFileList(List<RegisterInfo> fileList) {
		this.fileList = fileList;
	}
	
	public static Registers parse(String jsonString) throws AppException {
		Registers files = new Registers();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(RegisterInfo.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}

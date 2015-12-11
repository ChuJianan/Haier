package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

public class Ranks {
	public final static String INEWS = "files";
	private List<Rank> fileList = new ArrayList<Rank>();

	public Ranks(){}
	public Ranks(List<Rank> files) {
		this.fileList = files;
	}
	
	public List<Rank> getFileList() {
		return fileList;
	}

	public void setFileList(List<Rank> fileList) {
		this.fileList = fileList;
	}
	
	public static Ranks parse(String jsonString) throws AppException {
		Ranks files = new Ranks();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(Rank.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}



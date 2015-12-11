package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.provider.MediaStore.Files;

import com.haier.app.AppException;

public class LNews {
	public final static String INEWS = "files";
	private List<INews> fileList = new ArrayList<INews>();

	public LNews(){}
	public LNews(List<INews> files) {
		this.fileList = files;
	}
	
	public List<INews> getFileList() {
		return fileList;
	}

	public void setFileList(List<INews> fileList) {
		this.fileList = fileList;
	}
	
	public static LNews parse(String jsonString) throws AppException {
		LNews files = new LNews();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(INews.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}

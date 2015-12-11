package com.haier.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.haier.app.AppException;

public class Equip {
	public final static String INEWS = "files";
	private List<Equipments> fileList = new ArrayList<Equipments>();

	public Equip(){}
	public Equip(List<Equipments> files) {
		this.fileList = files;
	}
	
	public List<Equipments> getFileList() {
		return fileList;
	}

	public void setFileList(List<Equipments> fileList) {
		this.fileList = fileList;
	}
	
	public static Equip parse(String jsonString) throws AppException {
		Equip files = new Equip();
		try {
			JSONArray jsonFiles = new JSONArray(jsonString);
			for (int i = 0; i < jsonFiles.length(); i++) {
				files.getFileList().add(Equipments.parse(jsonFiles.getString(i)));
			}
		} catch (JSONException e) {
			throw AppException.json(e);
		}
		return files;
	}
}

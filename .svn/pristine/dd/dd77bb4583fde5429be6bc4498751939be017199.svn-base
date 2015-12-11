package com.haier.bean;

import com.haier.R;
import com.haier.utils.MediaFile;

/**
 * 资料信息
 * @author SYZ
 */
public class KnowledgeInfo implements ListItem {
	private int id;			// ID
	private String name;		// 资料显示名字
	private String theme;		// 资料主题
	private String path;		// 资料访问地址
	private String keyWords;	// 关键字
	private String uploadDate;	// 上传时间
	private String note;		// 说明信息
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	@Override
	public String getTitle() {
		return this.name;
	}
	@Override
	public String getIntro() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getImageResourse() {
		if (MediaFile.isVideoFileType(path)) {
			return R.drawable.ic_search_video;
		} else {
			return R.drawable.ic_search_other;
		}
	}
}

package com.haier.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;

/**
 * 互动
 * @author dan
 *
 */
public class AskallInfos implements Serializable {
	private int id;
	private String title;
	private String contents;
	private String fabuDate;
	private UserInfo userInfo;
	private List<ReplyInfo> askAllReplyInfos = Collections.emptyList();
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public static AskallInfos parse(String jsonString) throws AppException {
		AskallInfos file = null;
		try {
			file = new Gson().fromJson(jsonString, AskallInfos.class);
		} catch (JsonSyntaxException e) {
			throw AppException.json(e);
		} 
		return file;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getFabuDate() {
		return fabuDate;
	}
	public void setFabuDate(String fabuDate) {
		this.fabuDate = fabuDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ReplyInfo> getAskAllReplyInfos() {
		return askAllReplyInfos;
	}

	public void setAskAllReplyInfos(List<ReplyInfo> askAllReplyInfos) {
		this.askAllReplyInfos = askAllReplyInfos;
	}
	
}

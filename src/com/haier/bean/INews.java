package com.haier.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;

public class INews {
private String title;
private String newsContents;
private String publishDate;
private String type;
private String totalpage;


public static INews parse(String jsonString) throws AppException {
	INews file = null;
	try {
		file = new Gson().fromJson(jsonString, INews.class);
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
public String getNewsContents() {
	return newsContents;
}
public void setNewsContents(String newsContents) {
	this.newsContents = newsContents;
}
public String getPublishDate() {
	return publishDate;
}
public void setPublishDate(String publishDate) {
	this.publishDate = publishDate;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getTotalpage() {
	return totalpage;
}
public void setTotalpage(String totalpage) {
	this.totalpage = totalpage;
}
}

package com.haier.bean;

import java.io.Serializable;

import com.google.gson.JsonSyntaxException;
import com.haier.app.AppException;
import com.haier.utils.JsonUtils;

/**
 * Ӧ�ó������ʵ����
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public class Update implements Serializable{
    
    public final static String UTF8 = "UTF-8";
    
    private int versionCode;
    private String versionName;
    private String downloadUrl;
    private String updateLog;
    
    public int getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
    public String getVersionName() {
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public String getUpdateLog() {
        return updateLog;
    }
    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }
    
    public static Update parse(String jsonString) throws AppException {
        Update update = null;
        try {           
            update = JsonUtils.fromJson(jsonString, Update.class);
        } catch (JsonSyntaxException e) {
            throw AppException.json(e);
        }
        return update;       
    }
}

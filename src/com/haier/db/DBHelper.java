package com.haier.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
		private final static String DB_NAME = "meetreader.db";
		private final static int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		String userInfo = "CREATE TABLE userInfo( id INTEGER PRIMARY KEY AUTOINCREMENT ,"+
        		"userName varchar(20),totalScore varchar(4) ,sessionId varchar(10),expertFlag varchar(10))";
		String knowledges  = "CREATE TABLE knowledges(id varchar(10) primary key,"+
        		"parentId varchar(20) ,name varchar(4))";
		String schedules  = "CREATE TABLE schedules(knowledgeId varchar(20) primary key,"+
        		"status varchar(10))";
		String equipments  = "CREATE TABLE equipments(id varchar(10) primary key , "+
        		"name varchar(20),path varchar(20))";
		String accessFlag  = "CREATE TABLE accessFlag(flag varchar(10) primary key , "+
        		"describe varchar(20))";
		String gameList  = "CREATE TABLE gameList(id varchar(10) primary key , "+
        		"path varchar(20),sessionId varchar(10))";
		String rules  = "CREATE TABLE rules(seconds varchar(10) primary key , "+
        		"score varchar(20))";
		String questions  = "CREATE TABLE questions(id varchar(10) primary key , "+
        		"title varchar(20),answer varchar(20),optionList  varchar(20))";
		String optionList  = "CREATE TABLE optionList(id varchar(10) primary key , "+
        		"orderIndex  varchar(20),qcontext varchar(20))";
		String remindInfos  = "CREATE TABLE remindInfos(id INTEGER PRIMARY KEY AUTOINCREMENT , "+
        		"remindInfos  varchar(20))";
		String remindnews  = "CREATE TABLE remindnews(id INTEGER PRIMARY KEY AUTOINCREMENT , "+
        		"remindInfos  varchar(20))";
		String remindzj  = "CREATE TABLE remindzj(id INTEGER PRIMARY KEY AUTOINCREMENT , "+
        		"remindInfos  varchar(20))";
		String remindfz  = "CREATE TABLE remindfz(id INTEGER PRIMARY KEY AUTOINCREMENT , "+
        		"remindInfos  varchar(20))";
		String remindme  = "CREATE TABLE remindme(id INTEGER PRIMARY KEY AUTOINCREMENT , "+
        		"remindInfos  varchar(20))";
		db.execSQL(userInfo);
		Log.d("my", "create table userInfo:"+userInfo);
		db.execSQL(knowledges);
		Log.d("my", "create table knowledges:"+knowledges);
		db.execSQL(schedules);
		Log.d("my", "create table schedules:"+schedules);
		db.execSQL(equipments);
		Log.d("my", "create table equipments:"+equipments);
		db.execSQL(accessFlag);
		Log.d("my", "create table accessFlag:"+accessFlag);
		db.execSQL(gameList);
		Log.d("my", "create table gameList:"+gameList);
		db.execSQL(rules);
		Log.d("my", "create table rules:"+rules);
		db.execSQL(questions);
		Log.d("my", "create table questions:"+questions);
		db.execSQL(optionList);
		Log.d("my", "create table optionList:"+optionList);
		db.execSQL(remindInfos);
		Log.d("my", "create table optionList:"+remindInfos);
		db.execSQL(remindnews);
		Log.d("my", "create table optionList:"+remindnews);
		db.execSQL(remindzj);
		Log.d("my", "create table optionList:"+remindzj);
		db.execSQL(remindfz);
		Log.d("my", "create table optionList:"+remindfz);
		db.execSQL(remindme);
		Log.d("my", "create table optionList:"+remindme);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
	}

}

package com.haier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.Rules;

public class RulesDB {
	// 表名
	public final static String TABLE_NAME = "rules";
	public final static String RULES = "rules";
	public final static String SCORE = "score";
	private DBHelper helper;
	public RulesDB(Context context) {
		helper = new DBHelper(context);
	}
	//查看操作
	public String[] select() {
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] a=new String[2];
		String seconds = null;
		String score =null;
		Cursor cursor = db.rawQuery("select * from rules", null);
		while (cursor.moveToNext()) {
			seconds = cursor.getString(0);//获取第yi列的值
			score=cursor.getString(1);
			a[0]=seconds;
			a[1]=score;
		}
		return a;
	}
	//增加操作
	public void addRules(Rules entity)
	{
			SQLiteDatabase db = helper.getWritableDatabase();
			/* ContentValues */
			ContentValues cv = new ContentValues();
			cv.put(SCORE, entity.getScore());
			cv.put(RULES, entity.getSeconds());
			long row = db.insert(TABLE_NAME, null, cv);
			
	}
	
}

package com.haier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.AccessFlag;

public class AccessFlagDB {
	public final static String TABLE_NAME = "accessFlag";
	public final static String FLAG = "flag";
	public final static String DESCRIBE = "describe";
	private DBHelper helper;
	public AccessFlagDB(Context context) {
		helper = new DBHelper(context);
	}
	//查看操作
	public Cursor select(int flag) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String selection = FLAG + " = ?";
		String[] selectionArgs = { String.valueOf(flag) };
		Cursor cursor = db
				.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		return cursor;
	}
	public String select1(Object obj){
		SQLiteDatabase db = helper.getReadableDatabase();
		String  b="";
		String  destribe="";
		Cursor cursor = db.rawQuery("select * from accessFlag", null);
		while (cursor.moveToNext()){
			b=cursor.getString(0);
			if(b.equals(obj)){
				destribe=cursor.getString(1);//获取第yi列的值
			}
		}
		return destribe;
	}
	//添加操作
	public void addAccessFlag(AccessFlag entity)
	{
			SQLiteDatabase db = helper.getWritableDatabase();
			/* ContentValues */
			ContentValues cv = new ContentValues();
			cv.put(FLAG, entity.getFlag());
			cv.put(DESCRIBE, entity.getDescribe());
			long row = db.insert(TABLE_NAME, null, cv);
	}
	//删除操作
	public void delete(int flag) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String where = FLAG + " = ?";
		String[] whereValue = { Integer.toString(flag) };
		db.delete(TABLE_NAME, where, whereValue);
	}
	

}

package com.haier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.RemindInfos;

public class RemindzjDB {

	public final static String TABLE_NAME = "remindzj";
	public final static String ID = "id";
	public final static String STATUS = "remindInfos";
	private DBHelper helper;
	public RemindzjDB(Context context) {
		helper = new DBHelper(context);
	}
	public String select() {
		SQLiteDatabase db = helper.getReadableDatabase();
		String remindInfos ="";
		/*String selection = ID + " = ?";
		String[] selectionArgs = { String.valueOf(id) };
		Cursor cursor = db
				.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);*/
		Cursor cursor = db.rawQuery("select * from remindzj", null);
		while (cursor.moveToNext()) {
			remindInfos+= cursor.getString(1);//��ȡ�����е�ֵ
		}
		return remindInfos;
	}
	
	public void addRemind(String entity)
	{
			SQLiteDatabase db = helper.getWritableDatabase();
			/* ContentValues */
			ContentValues cv = new ContentValues();
			cv.put(STATUS, entity);
			long row = db.insert(TABLE_NAME, null, cv);
	}
	public void delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
	}
	
}

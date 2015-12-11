package com.haier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.Schedules;
/**
 * 学习进度
 * @author Administrator
 *
 */
public class ScheduleDB {
	public final static String TABLE_NAME = "schedules";
	public final static String STATUS = "status";
	public final static String KNOWLEDGEID = "knowledgeId";
	private DBHelper helper;
	public ScheduleDB(Context context) {
		helper = new DBHelper(context);
	}
	//�鿴����
	public Cursor select(int knowledgeId) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String selection = KNOWLEDGEID + " = ?";
		String[] selectionArgs = { String.valueOf(knowledgeId) };
		Cursor cursor = db
				.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		return cursor;
	}
	//���Ӳ���
	public void addSchedule(Schedules entity)
	{
			SQLiteDatabase db = helper.getWritableDatabase();
			/* ContentValues */
			ContentValues cv = new ContentValues();
			cv.put(STATUS, entity.getStatus());
			cv.put(KNOWLEDGEID, entity.getKnowledgeId());
			long row = db.insert(TABLE_NAME, null, cv);
	}
	//ɾ�����
	public void delete(int knowledgeId) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String where = KNOWLEDGEID + " = ?";
		String[] whereValue = { Integer.toString(knowledgeId) };
		db.delete(TABLE_NAME, where, whereValue);
	}

}

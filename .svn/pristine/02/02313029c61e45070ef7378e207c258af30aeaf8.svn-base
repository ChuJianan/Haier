package com.haier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.UserInfo;

public class UserDB {

	// ����
			public final static String TABLE_NAME = "userInfo";
			public final static String ID = "id";
			public final static String TITLE = "totalScore";
			public final static String USERNAME = "userName";
			public final static String SID_STRING="sessionId";
			
			private DBHelper helper;
			
			public UserDB(Context context) {
				helper = new DBHelper(context);
			}
			//�鿴����
			public String select() {
				SQLiteDatabase db = helper.getReadableDatabase();
				String sessionId = null;
				/*String selection = ID + " = ?";
				String[] selectionArgs = { String.valueOf(id) };
				Cursor cursor = db
						.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);*/
				Cursor cursor = db.rawQuery("select * from userInfo", null);
				while (cursor.moveToNext()) {
				    sessionId = cursor.getString(2);//��ȡ�����е�ֵ
				}
				return sessionId;
			}
			public String[] select1() {
				SQLiteDatabase db = helper.getReadableDatabase();
				String[] a=new String[3];
				Cursor cursor = db.rawQuery("select * from userInfo", null);
				while (cursor.moveToNext()){
					a[0]=cursor.getString(0);
					a[1]=cursor.getString(1);
					a[2]=cursor.getString(2);
				}
				return a;
			}
			public String[] select2() {
				SQLiteDatabase db = helper.getReadableDatabase();
				String[] a=new String[5];
				Cursor cursor = db.rawQuery("select * from userInfo", null);
				while (cursor.moveToNext()){
					a[0]=cursor.getString(0);
					a[1]=cursor.getString(1);
					a[2]=cursor.getString(2);
					a[3]=cursor.getString(3);
					a[4]=cursor.getString(4);
				}
				return a;
			}
			//���Ӳ���
			public void addUser(UserInfo entity)
			{
					SQLiteDatabase db = helper.getWritableDatabase();
					/* ContentValues */
					ContentValues cv = new ContentValues();
					cv.put(ID, entity.getId());
					cv.put(USERNAME, entity.getUserName());
					cv.put(TITLE, entity.getTotalScore() );
					cv.put(SID_STRING, entity.getSessionId() );
					long row = db.insert(TABLE_NAME, null, cv);
			}
			// ɾ�����
			public void delete(int id) {
				SQLiteDatabase db = helper.getWritableDatabase();
				String where = ID + " = ?";
				String[] whereValue = { Integer.toString(id) };
				db.delete(TABLE_NAME, where, whereValue);
			}
}

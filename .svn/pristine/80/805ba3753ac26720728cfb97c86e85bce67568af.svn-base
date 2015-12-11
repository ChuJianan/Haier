package com.haier.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.Equipments;

public class EquipmentDB {
	// 表名
			public final static String TABLE_NAME = "gameList";
			public final static String ID = "id";
			public final static String NAME = "name";
			public final static String PATH = "path";
			private DBHelper helper;
			
			public EquipmentDB(Context context) {
				helper = new DBHelper(context);
			}
			//查看操作
			public Cursor select(int id) {
				SQLiteDatabase db = helper.getReadableDatabase();
				String selection = ID + " = ?";
				String[] selectionArgs = { String.valueOf(id) };
				Cursor cursor = db
						.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
				return cursor;
			}
			//添加操作
			public void addEquipment(Equipments entity)
			{
					SQLiteDatabase db = helper.getWritableDatabase();
					/* ContentValues */
					ContentValues cv = new ContentValues();
					cv.put(ID, entity.getId());
					cv.put(NAME, entity.getName());
					cv.put(PATH, entity.getPath());
					long row = db.insert(TABLE_NAME, null, cv);
					
			}
			//删除操作
			public void delete(int id) {
				SQLiteDatabase db = helper.getWritableDatabase();
				String where = ID + " = ?";
				String[] whereValue = { Integer.toString(id) };
				db.delete(TABLE_NAME, where, whereValue);
			}

}

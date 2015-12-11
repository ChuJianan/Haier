package com.haier.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.GameList;

public class GameListDB {
	  // 表名
			public final static String TABLE_NAME = "gameList";
			public final static String ID = "id";
			public final static String PATH = "path";
			public final static String SESSIONID="sessionId";
			private DBHelper helper;
			public GameListDB(Context context) {
				helper = new DBHelper(context);
			}
			//查看操作
			public List<GameList> select() {
				SQLiteDatabase db = helper.getReadableDatabase();
				List<GameList> list=new ArrayList<GameList>();
				Cursor cursor = db
						.query(TABLE_NAME, null, null, null, null, null, "id desc");
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					GameList gameList=new GameList();
					gameList.setId(cursor.getString(0));
					gameList.setPath(cursor.getString(1));
					gameList.setSessionId(cursor.getString(2));
					list.add(gameList);
					cursor.moveToNext();
				}
				db.close();
				return list;
			}
			//增加操作
			public void addGameList(GameList entity)
			{
					SQLiteDatabase db = helper.getWritableDatabase();
					/* ContentValues */
					ContentValues cv = new ContentValues();
					cv.put(ID, entity.getId());
					cv.put(PATH, entity.getPath());
					cv.put(SESSIONID, entity.getSessionId());
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

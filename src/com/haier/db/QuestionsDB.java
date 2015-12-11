package com.haier.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haier.bean.Questions;

public class QuestionsDB {
	public final static String TABLE_NAME = "questions";
	public final static String ID = "id";
	public final static String TITLE = "title";
	public final static String ANSWER="answer";
	public final static String OPTIONLIST="optionlist";
	private DBHelper helper;
	public QuestionsDB(Context context) {
		helper = new DBHelper(context);
	}
	//�鿴����
	public List<Questions> select() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Questions> list=new ArrayList<Questions>();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, "id desc");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Questions questions=new Questions();
			questions.setId(cursor.getInt(0));
			questions.setTitle(cursor.getString(1));
			questions.setAnswer(cursor.getInt(2));
			questions.setOptionList(cursor.getString(3));
			list.add(questions);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}
	
	//��Ӳ���
	public void addQuestions(Questions entity)
	{
			SQLiteDatabase db = helper.getWritableDatabase();
			/* ContentValues */
			ContentValues cv = new ContentValues();
			cv.put(ID, entity.getId());
			cv.put(TITLE, entity.getTitle());
			cv.put(ANSWER, entity.getAnswer());
			cv.put(OPTIONLIST, entity.getOptionList());
			long row = db.insert(TABLE_NAME, null, cv);
			
	}
	//ɾ�����
	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String where = ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}
}

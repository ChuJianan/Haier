package com.yunrui.util;

import yunrui.game.been.Question;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class QuestionContentProviderUtil {

	
	public static void testFind(Context context) throws Throwable{  
        ContentResolver contentResolver = context.getContentResolver();  
        Uri selectUri = Uri.parse("content://com.haier.QuestionContentProvider/questions");  
        Cursor cursor = contentResolver.query(selectUri, null, null, null, "id desc");  
        while(cursor.moveToNext()){ 
        	Question question=new Question();
        	question.setId( cursor.getInt(cursor.getColumnIndex("id")));  
        	question.setAnswer(cursor.getInt(cursor.getColumnIndex("answer")));
           question.setTitle(cursor.getString(cursor.getColumnIndex("title"))) ;
           question.setOplists(JsonUtil.fromjson(cursor.getString(cursor.getColumnIndex("optionList"))));  
          JsonUtil.qList.add(question);
        }  
    }  
}

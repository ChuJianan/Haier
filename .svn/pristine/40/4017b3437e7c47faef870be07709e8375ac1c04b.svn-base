package com.haier.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class QuestionContentProvider extends ContentProvider {

	private DBHelper dbOpenHelper;  
    //常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码  
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);  
    private static final int USERS = 1;  
    private static final int USER = 2;  
    static{  
        //如果match()方法匹配content://com.yaku.ContentProvider.userprovider/user路径，返回匹配码为1  
        MATCHER.addURI("com.haier.QuestionContentProvider", "questions", USERS);  
        //如果match()方法匹配content://com.yaku.ContentProvider.userprovider/user/123路径，返回匹配码为2  
        MATCHER.addURI("com.haier.QuestionContentProvider", "questions/#", USER);//#号为通配符  
    }     
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		 switch (MATCHER.match(uri)) {  
	        case USERS:           
	            return "vnd.android.cursor.dir/user";  
	              
	        case USER:            
	            return "vnd.android.cursor.item/user";  
	              
	        default:  
	            throw new IllegalArgumentException("Unkwon Uri:"+ uri.toString());  
	        }  
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		 this.dbOpenHelper = new DBHelper(this.getContext());  
	        return false;  
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        switch (MATCHER.match(uri)) {  
        case USERS:  
            return db.query("questions", projection, selection, selectionArgs, null, null, sortOrder);  
        case USER:  
            long id = ContentUris.parseId(uri);  
            String where = "id = "+ id;  
            if(selection!=null && !"".equals(selection)){  
                where = selection + " and " + where;  
            }  
            return db.query("questions", projection, where, selectionArgs, null, null, sortOrder);  
        default:  
            throw new IllegalArgumentException("Unkwon Uri:"+ uri.toString());  
        }  
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	
}

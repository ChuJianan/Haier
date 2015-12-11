package com.haier.ContentProvider;

import com.haier.db.DBHelper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class EquipmentContentProvider extends ContentProvider {

	private DBHelper dbHelper;  
    //常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码  
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);  
    private static final int USERS = 1;  
    private static final int USER = 2;  
    static{  
        //如果match()方法匹配content://com.yaku.ContentProvider.userprovider/user路径，返回匹配码为1  
        MATCHER.addURI("com.haier.ContentProvider.EquipmentContentProvider", "equipments", USERS);  
        //如果match()方法匹配content://com.yaku.ContentProvider.userprovider/user/123路径，返回匹配码为2  
        MATCHER.addURI("com.haier.ContentProvider.EquipmentContentProvider", "equipments/#", USER);//#号为通配符  
    }     
    
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper=new DBHelper(getContext()); 
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentResolver resolver=this.getContext().getContentResolver();
        //插入数据，返回行号ID
        long rowid=db.insert("equipments", "id", values);
        //如果插入成功，返回Uri
        if(rowid>0)
        {
            Uri stuUri=ContentUris.withAppendedId(uri, rowid);
            resolver.notifyChange(stuUri, null);//数据发送变化时候，发出通知给注册了相应uri的
            return stuUri;
        }
        return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}

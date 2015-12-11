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
    //����UriMatcher.NO_MATCH��ʾ��ƥ���κ�·���ķ�����  
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);  
    private static final int USERS = 1;  
    private static final int USER = 2;  
    static{  
        //���match()����ƥ��content://com.yaku.ContentProvider.userprovider/user·��������ƥ����Ϊ1  
        MATCHER.addURI("com.haier.ContentProvider.EquipmentContentProvider", "equipments", USERS);  
        //���match()����ƥ��content://com.yaku.ContentProvider.userprovider/user/123·��������ƥ����Ϊ2  
        MATCHER.addURI("com.haier.ContentProvider.EquipmentContentProvider", "equipments/#", USER);//#��Ϊͨ���  
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
        //�������ݣ������к�ID
        long rowid=db.insert("equipments", "id", values);
        //�������ɹ�������Uri
        if(rowid>0)
        {
            Uri stuUri=ContentUris.withAppendedId(uri, rowid);
            resolver.notifyChange(stuUri, null);//���ݷ��ͱ仯ʱ�򣬷���֪ͨ��ע������Ӧuri��
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

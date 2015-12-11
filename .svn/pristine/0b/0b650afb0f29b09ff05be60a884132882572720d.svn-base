package com.haier.ContentProvider;

import com.haier.db.DBHelper;
import com.haier.db.UserDB;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class UserInfoContentProvider extends ContentProvider{

	private DBHelper dbHelper;  
    //��UriMatcher.NO_MATCH��ʾ��ƥ���κ�·���ķ�����  
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);  
    private static final int USERS = 1;  
    private static final int USER = 2;  
    static{  
        //���match()����ƥ��content://com.yaku.ContentProvider.userprovider/user·��������ƥ����Ϊ1  
        MATCHER.addURI("com.haier.ContentProvider.UserInfoContentProvider", "userInfo", USERS);  
        //���match()����ƥ��content://com.yaku.ContentProvider.userprovider/user/123·��������ƥ����Ϊ2  
        MATCHER.addURI("com.haier.ContentProvider.UserInfoContentProvider", "userInfo/#", USER);//#��Ϊͨ���  
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
		SQLiteDatabase db=dbHelper.getWritableDatabase();//����һ��ɶu���ݿ�
		ContentResolver resolver=this.getContext().getContentResolver();
        int count;
        switch(MATCHER.match(uri))
        {
        case USERS:
            count=db.update("userInfo", values, selection, selectionArgs);
            break;
        case USER:
            String stuId=uri.getPathSegments().get(1);//���id
            count=db.update("userInfo", values,UserDB.ID+"="+stuId+(!TextUtils.isEmpty(selection) ?
                    " and ("+selection+')':""), selectionArgs);
            break;
        default:
            //����4��Uri����������Ҫ������
            throw new IllegalArgumentException("this is Unknown Uri��"+uri);    
        }
        resolver.notifyChange(uri, null);
        return count;
	}

}

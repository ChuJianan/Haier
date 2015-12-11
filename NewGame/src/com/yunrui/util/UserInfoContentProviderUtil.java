package com.yunrui.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.haier.db.UserDB;

public class UserInfoContentProviderUtil {

	public static void update(String id,Context context)
    {
		 Uri uri1=Uri.parse("content://com.haier.ContentProvider.UserInfoContentProvider/userInfo");
		 int id1=Integer.valueOf(id).intValue();
		 Uri uri=ContentUris.withAppendedId(uri1, id1);
        ContentResolver resolver=context.getContentResolver();
        ContentValues values=new ContentValues();
        values.put(UserDB.TITLE, JsonUtil.userInfo.getTotalScore());
        resolver.update(uri, values, null, null);
    }
}

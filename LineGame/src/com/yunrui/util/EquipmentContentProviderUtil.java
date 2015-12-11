package com.yunrui.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.haier.db.EquipmentDB;

public class EquipmentContentProviderUtil {

	//鎻掑叆鏁版嵁
    public static void insert(Context context)
    {
    	
        Uri uri=Uri.parse("content://com.haier.ContentProvider.EquipmentContentProvider/equipments");
        //鑾峰緱ContentResolver瀵硅薄
        ContentResolver resolver=context.getContentResolver();
        ContentValues values=new ContentValues();
        //瑁呭娣诲姞
        
        	  values.put(EquipmentDB.ID, JsonUtil.equipments.getId());
              values.put(EquipmentDB.NAME, JsonUtil.equipments.getName());
              values.put(EquipmentDB.PATH, JsonUtil.equipments.getPath());
      
        //灏嗕俊鎭彃鍏�        resolver.insert(uri, values);
       
    }
}

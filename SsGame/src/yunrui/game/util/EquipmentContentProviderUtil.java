package yunrui.game.util;

import com.haier.db.EquipmentDB;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class EquipmentContentProviderUtil {

	//插入数据
    public static void insert(Context context)
    {
    	
        Uri uri=Uri.parse("content://com.haier.ContentProvider.EquipmentContentProvider/equipments");
        //获得ContentResolver对象
        ContentResolver resolver=context.getContentResolver();
        ContentValues values=new ContentValues();
        //装备添加
        
        	  values.put(EquipmentDB.ID, JsonUtil.equipments.getId());
              values.put(EquipmentDB.NAME, JsonUtil.equipments.getName());
              values.put(EquipmentDB.PATH, JsonUtil.equipments.getPath());
      
        //将信息插入
        resolver.insert(uri, values);
       
    }
}

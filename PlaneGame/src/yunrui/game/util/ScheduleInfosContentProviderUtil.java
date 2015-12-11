package yunrui.game.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.haier.bean.Schedules;
import com.haier.db.ScheduleDB;

public class ScheduleInfosContentProviderUtil {

	
	//插入数据
    public static void insert(Context context)
    {
    	List<Schedules> schedules=new ArrayList<Schedules>();
    	schedules=JsonUtil.scheduleslList;
        Uri uri=Uri.parse("content://com.haier.ContentProvider.ScheduleInfosContentProvider/schedules");
        //获得ContentResolver对象
        ContentResolver resolver=context.getContentResolver();
        ContentValues values=new ContentValues();
        //装备添加
        for (int i = 0; i < schedules.size(); i++) {
        	  values.put(ScheduleDB.STATUS, schedules.get(i).getStatus());
              values.put(ScheduleDB.KNOWLEDGEID, schedules.get(i).getKnowledgeId());
		}
      
        //将信息插入
        resolver.insert(uri, values);
       
    }
}

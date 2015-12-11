package com.haier.utils;

import android.content.Context;
import android.widget.Toast;

import com.haier.db.AccessFlagDB;

public class FlagUtils {

	public static boolean flag(Context context,String flag){
		AccessFlagDB accessFlagDB=new AccessFlagDB(context);
		if(flag.equals("6")){
	        /*	putSharedPreferences(password);*/
	        	}else if(flag.equals("5")){
	        	UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
	        	}else if(flag.equals("4")){
	        		 UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
	            }else if(flag.equals("3")){
	            	 UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
	            }else if(flag.equals("2")){
	            	 UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
	        	}else if(flag.equals("1")){
	        		UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
	        		//Dialog(accessFlagDB.select1(flag));
	        	}else if(flag.equals("7")){
	        		UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
	        	}else if (flag.equals("8")) {
					UIHelper.ToastMessage(context, accessFlagDB.select1(flag));
				} else if (flag.equals("9")) {
					
				}
					
				
				
	        	else {
					Toast.makeText(context, "错误", Toast.LENGTH_LONG).show();
				}
		return true;
	}
}

package com.yunrui.util;

public class TestUtil {
	public static int  answer;
	public static int  zs=0;
	public static String kid;  
	public static String ssid;  
	public static String name;
	public static String userid;
	public static String title="以下正确的说法是:";
	public static String item[];
	public static boolean music1;
	public static boolean music2;
	public static boolean dotest(int wich){
		if (answer==wich) {
			return true;
		}else {
			return false;
		}
	}
}

package com.yunrui.util;

import java.util.ArrayList;
import java.util.List;

import com.example.linegame.Point;

public class TestUtil {
	public static int  answer;
	public static int  zs=0;
	public static String kid;  
	public static String ssid;  
	public static String name;
	public static String userid;
	public static String realname;
	/**
	 * �ؼ�����
	 */
	public static List<Point> keyPoints = new ArrayList<Point>();
	public static String title="������ȷ��˵����:";
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

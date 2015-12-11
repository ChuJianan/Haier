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
	 * 关键点阵
	 */
	public static List<Point> keyPoints = new ArrayList<Point>();
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

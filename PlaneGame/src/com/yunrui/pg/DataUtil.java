package com.yunrui.pg;


public class DataUtil {

	public static int i;//时间
	private static boolean run;//是否停止
	private static int j;//剩余块数
	private static int mf;//分数
	private static boolean isClick;//是否点击
	
	public static int getI() {
		return i;
	}

	public static void setI(int i) {
		DataUtil.i = i;
	}
	public static boolean isRun() {
		return run;
	}

	public static void setRun(boolean run) {
		DataUtil.run = run;
	}

	public static int getJ() {
		return j;
	}

	public static void setJ(int j) {
		DataUtil.j = j;
	}

	public static int getMf() {
		return mf;
	}

	public static void setMf(int mf) {
		DataUtil.mf = mf;
	}

	public static boolean isClick() {
		return isClick;
	}

	public static void setClick(boolean isClick) {
		DataUtil.isClick = isClick;
	}
}

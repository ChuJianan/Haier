package com.yunrui.pg;



import yunrui.game.been.Getquestion;


public class Test {
	public static int  answer;
	public static String title="以下正确的说法是:";
	public static String item[];
	public static String name;
	public static String userid;
	public static String ssesionid;
	public static String realname;
	//��ָ�����ľ���
	private static float  juli;
	
	public static String url="http://42.96.134.201:8080/gamelearning/clientGame/getResults.action?";
	public static String kId;
	public static boolean music1=false;
	public static boolean music2=false;
	public static boolean dotest(int wich){
		Getquestion.questuserList.add((wich+1));
		if (answer==wich+1) {
			Getquestion.questTFList.add("1");
			return true;
		}else {
			Getquestion.questTFList.add("0");
			return false;
		}
	}
	public static float getJuli() {
		return juli;
	}
	public static void setJuli(float juli) {
		Test.juli = juli;
	}
}
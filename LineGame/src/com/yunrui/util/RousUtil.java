package com.yunrui.util;



public class RousUtil {
	public static int i;//时间
	public static int mf=0;//分数
	public static int imf=0;//分数
	public static int j;//几环
	public static boolean roustest(int a){
	  if (TestUtil.dotest(a)) {
  		if (i>=0&&i<=5) {
				 i=0;
				 j=0;
				 mf+=30;
				 imf=30;
				 return true;
			}
			if (i>5&&i<=10) {
				 i=0;
				j=1;
				mf+=25;
				imf=25;
				return true;
			}
			if (i>10&&i<=15) {
				 i=0;
				j=2;
				mf+=20;
				imf=20;
				 return true;
			}
			if (i>15&&i<=20) {
				i=0;
				j=3;
				mf+=15;
				imf=15;
				 return true;
			}
			if (i>20&&i<=25) {
				 i=0;
				j=-3;
				mf+=10;
				imf=10;
				 return true;
			}
			if (i>25&&i<=30) {
				 i=0;
				j=-3;
				mf+=5;
				imf=5;
				 return true;
			}
			} else {
				i=0;
				j=6;
				mf+=0;
				imf=0;
				 return false;
			}
  	 return false;
  	   }
}

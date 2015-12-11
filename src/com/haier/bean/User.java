package com.haier.bean;

public class User {
	 private static   String  userName;
	 private static   String  userscore;
	 private static   String  name;
	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String userName) {
		User.userName = userName;
	}
	public static String getUserscore() {
		return userscore;
	}
	public static void setUserscore(String userscore) {
		User.userscore = userscore;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		User.name = name;
	}

}

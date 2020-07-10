package com.zly.collectapp.utils;


/**
 * 用户的静态变量
 */
public class UserUtils {
    private static int userId;
    private static String userName;
    private static boolean isRemember;


    public static void setUserId(int id){
        userId=id;
    }

    public static void setUserName(String name){
        userName=name;
    }
    public static void setIsRemember(boolean remember){
        isRemember=remember;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean isIsRemember() {
        return isRemember;
    }
}

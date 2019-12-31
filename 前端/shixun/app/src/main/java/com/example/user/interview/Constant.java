package com.example.user.interview;

public class Constant {

    //全局ip
    public static String ip = "10.7.81.127";
    //登录注册地址r
    public static String URL_LOCAL = "http://"+ip+":8080/ShiguoServerSystem/User/";
    public static String URL_ICON = "http://"+ip+":8080/ShiguoServerSystem/avatarimg/";
    //个人中心，题库地址
    public static final String BASE_IP = "http://"+ip+":8080/";
    public static final String BASE_URL= "http://"+ip+":8080/ShiguoServerSystem/";
    public static final String URL_PAPER = "ShiguoServerSystem/Paper/SearchByType";
    public static final String URL_ALL_PROBLEM = "ShiguoServerSystem/Problem/SearchByPaperId";
    public static final String URL_HANDBOOK = "ShiguoServerSystem/HandBook/SearchAll";
    public static final String URL_PROBLEM ="ShiguoServerSystem/Problem/SearchByType";
    public static final String URL_SERCH_PROBLEM ="ShiguoServerSystem/Problem/SearchByName";
    public static final String URL_ADD_VERTIFICATE = "ShiguoServerSystem/User/Certification";
    public static final String URL_SERCH_MY_RESUME = "ShiguoServerSystem/Resume/SearchByUserId";
    public static final String URL_CHANGE_MY_RESUME = "ShiguoServerSystem/Resume/Edit";
    public static final String URL_ADD_MY_RESUME = "ShiguoServerSystem/Resume/Add";
    public static final String URL_DELETE_MY_RESUME = "ShiguoServerSystem/Resume/Delete";
    public static final String URL_SERCH_RECEIVED_RESUME = "ShiguoServerSystem/ResumeApplication/SearchByUserId";
    public static final String URL_Edit_PERSONAL = "ShiguoServerSystem/User/Edit";
    public static final String URL_UPLOAD_PIC = "ShiguoServerSystem/User/UploadAvatar";
    public static final String URL_MY_POSITON= "ShiguoServerSystem/Position/SearchAllByUser";
    public static final String URL_MY_JOB_SEARCH ="ShiguoServerSystem/JobSearch/SearchAllByUser";
    //消息地址
    public static String URL_MESSAGE_LOCAL = "http://"+ip+":8080/ShiguoServerSystem/Conversations/";
    public static String URL_CHAT_LOCAL = "http://"+ip+":8080/ShiguoServerSystem/ChatRecord/";
}

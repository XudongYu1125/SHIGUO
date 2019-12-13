package com.shiguo.personal.user.dao;

import java.util.List;

import com.shiguo.entity.User;
import com.shiguo.util.DBUtil;

public class UserDaoImpl {
	
	public List<User> findAllUser() {
		return DBUtil.findAll(User.class,"select * from user");
	}
	
	public User findUserByPhonenum(String phonenum){
		List<User> list = DBUtil.find(User.class, "select * from user where phonenum = ?", new Object[] {phonenum});
		if(list==null) {
			return null;
		}
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	public String findUserImgByNickname(String nickname) {
		List list = DBUtil.findOne("select imageurl from user where nickname = ?", new Object[] {nickname});
		if(list==null) {
			System.out.println("1");
			return "";
		}else if(list.size()==0){
			System.out.println("2");
			return "";
		}else if(list.get(0)==null) {
			System.out.println("3");
			return "";
		}else {
			System.out.println("4");
			return list.get(0).toString();
		}
			
	}
	public User findUserByQQ(String QQ){
		if(DBUtil.find(User.class, "select * from user where QQ = ?", new Object[] {QQ})==null) {
			return null;
		}
		if(DBUtil.find(User.class, "select * from user where QQ = ?", new Object[] {QQ}).size()==0){
			return null;
		}
		return (User)(DBUtil.find(User.class, "select * from user where QQ = ?", new Object[] {QQ})).get(0);
	}
	
	public User findUserByNickname(String nickname){
		List<User> list = DBUtil.find(User.class, "select * from user where nickname = ?", new Object[] {nickname});
		if(list==null) {
			return null;
		}
		if(list.size()==0) {
			return null;
		}
		return list.get(0);
	}
	
	public int insertUser(User user) {
		return DBUtil.executeUpdate("insert into user (sex,QQ,password,phonenum,nickname,imageurl,companyid) values (?,?,?,?,?,?,?)", new Object[] { user.getSex(),user.getQQ(),user.getPassword(),user.getPhonenum(),user.getNickname(),user.getNickname()+"_"+user.getUserid()+".png",user.getCompanyid()});
	}

	public User findUserById(int id) {
		return (User)DBUtil.findById(User.class,"select * from user where userid=?",id);
	}
	
	public int updateUser(User user) {
		return DBUtil.executeUpdate("update user set sex=?, QQ=?, password=?,phonenum=?,nickname=?,imageurl=?,companyid=? where userid=?", new Object[] {user.getSex(),user.getQQ(),user.getPassword(),user.getPhonenum(),user.getNickname(),user.getImageurl(),user.getCompanyid(),user.getUserid()});
	}
	
	public int deleteUserById(int id) {
		return DBUtil.executeUpdate("delete from user where userid=?", new Object[] {id});
	}
}

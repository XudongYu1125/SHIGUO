package com.shiguo.personal.user.service;

import java.util.List;

import com.shiguo.entity.User;
import com.shiguo.personal.user.dao.UserDaoImpl;



public class UserServiceImpl {
	public List<User> UserList() {
		return new UserDaoImpl().findAllUser();
	}
	
	public User searchUserById(int id) {
		return new UserDaoImpl().findUserById(id);
	}
	
	public String searchImgByNickname(String nickname) {
		return new UserDaoImpl().findUserImgByNickname(nickname);
	}
	public User searchUserByNickname(String nickname) {
		return new UserDaoImpl().findUserByNickname(nickname);
	}
	public User searchUserByQQ(String QQ) {
		return new UserDaoImpl().findUserByQQ(QQ);
	}
	public User searchUserByPhonenum(String phonenum) {
		return new UserDaoImpl().findUserByPhonenum(phonenum);
	}

	public boolean addUser(User user) {
		int count = new UserDaoImpl().insertUser(user);
		return count > 0;
	}


	public boolean editUser(User user) {
		int count = new UserDaoImpl().updateUser(user);
		return count > 0;
	}

	public boolean dropUser(int id) {
		int count = new UserDaoImpl().deleteUserById(id);
		return count > 0;
	}
}

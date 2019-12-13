package com.shiguo.entity;

public class UserStatus {
	private User user;
	private String status;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "UserStatus [user=" + user + ", status=" + status + "]";
	}
}

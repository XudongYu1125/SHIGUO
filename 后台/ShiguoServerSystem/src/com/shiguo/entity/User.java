package com.shiguo.entity;

public class User {
	
	private int userid;
	private String sex;
	private String QQ;
	private String password;
	private String phonenum;
	private String nickname;
	private String imageurl;
	private int companyid;
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", sex=" + sex + ", QQ=" + QQ + ", password=" + password + ", phonenum="
				+ phonenum + ", nickname=" + nickname + ", imageurl=" + imageurl + ", companyid=" + companyid + "]";
	}
	
	
}

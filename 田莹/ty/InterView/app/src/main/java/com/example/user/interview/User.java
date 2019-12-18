package com.example.user.interview;

public class User {
	
	private int userid;
	private String password;
	private String phonenum;
	private String nickname;
	private String imageurl;
	private int companyid;
	private String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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
		return "User{" +
				"userid=" + userid +
				", password='" + password + '\'' +
				", phonenum='" + phonenum + '\'' +
				", nickname='" + nickname + '\'' +
				", imageurl='" + imageurl + '\'' +
				", companyid=" + companyid +
				", sex='" + sex + '\'' +
				'}';
	}
}

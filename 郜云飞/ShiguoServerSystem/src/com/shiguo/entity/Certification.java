package com.shiguo.entity;

public class Certification {
	private int userid;
	private String name;
	private String verification;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVerification() {
		return verification;
	}
	public void setVerification(String verification) {
		this.verification = verification;
	}
	@Override
	public String toString() {
		return "Certification [userid=" + userid + ", name=" + name + ", verification=" + verification + "]";
	}
	
}

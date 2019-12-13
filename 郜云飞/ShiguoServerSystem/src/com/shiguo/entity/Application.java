package com.shiguo.entity;

public class Application {
	private int applicationid;
	private int userid;
	private int resumeid;
	public int getApplicationid() {
		return applicationid;
	}
	public void setApplicationid(int applicationid) {
		this.applicationid = applicationid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getResumeid() {
		return resumeid;
	}
	public void setResumeid(int resumeid) {
		this.resumeid = resumeid;
	}
	@Override
	public String toString() {
		return "Application [applicationid=" + applicationid + ", userid=" + userid + ", resumeid=" + resumeid + "]";
	}

}

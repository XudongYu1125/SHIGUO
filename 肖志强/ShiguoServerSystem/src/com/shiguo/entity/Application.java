package com.shiguo.entity;

public class Application {
	private int applicationid;
	private int receiverid;
	private int senderid;
	private int resumeid;
	private String date;
	public int getApplicationid() {
		return applicationid;
	}
	public void setApplicationid(int applicationid) {
		this.applicationid = applicationid;
	}
	public int getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(int receiverid) {
		this.receiverid = receiverid;
	}
	public int getSenderid() {
		return senderid;
	}
	public void setSenderid(int senderid) {
		this.senderid = senderid;
	}
	public int getResumeid() {
		return resumeid;
	}
	public void setResumeid(int resumeid) {
		this.resumeid = resumeid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Application [applicationid=" + applicationid + ", receiverid=" + receiverid + ", senderid=" + senderid
				+ ", resumeid=" + resumeid + ", date=" + date + "]";
	}

}

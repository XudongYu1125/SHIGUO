package com.shiguo.entity;

public class JobSearch {
	
	private int jobsearchid;
	private int resumeid;
	private int salary;
	private String place;
	private String position;
	private int userid;
	private String date;
	
	public int getJobsearchid() {
		return jobsearchid;
	}
	public void setJobsearchid(int jobsearchid) {
		this.jobsearchid = jobsearchid;
	}
	public int getResumeid() {
		return resumeid;
	}
	public void setResumeid(int resumeid) {
		this.resumeid = resumeid;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "JobSearch [jobsearchid=" + jobsearchid + ", resumeid=" + resumeid + ", salary=" + salary + ", place="
				+ place + ", position=" + position + ", userid=" + userid + ", date=" + date + "]";
	}
}

package com.shiguo.entity;

public class Paper {
	
	private int paperid;
	private String name;
	private String imageurl;
	private int type;
	private int level;
	private String date;
	private int companyid;
	
	public int getPaperid() {
		return paperid;
	}
	public void setPaperid(int paperid) {
		this.paperid = paperid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	@Override
	public String toString() {
		return "Paper [paperid=" + paperid + ", name=" + name + ", imageurl=" + imageurl + ", type=" + type + ", level="
				+ level + ", date=" + date + ", companyid=" + companyid + "]";
	}
	
}

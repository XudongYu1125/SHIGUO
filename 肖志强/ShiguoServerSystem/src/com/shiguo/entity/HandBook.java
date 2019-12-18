package com.shiguo.entity;

public class HandBook {
	
	private int handbookid;
	private String name;
	private String content;
	
	public int getHandbookid() {
		return handbookid;
	}
	public void setHandbookid(int handbookid) {
		this.handbookid = handbookid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "HandBook [handbookid=" + handbookid + ", name=" + name + ", content=" + content + "]";
	}
	
}

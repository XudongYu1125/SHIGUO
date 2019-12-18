package com.example.user.interview;

public class HandBook {
	
	private int handbookid;
	private String content;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHandbookid() {
		return handbookid;
	}
	public void setHandbookid(int handbookid) {
		this.handbookid = handbookid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "HandBook{" +
				"handbookid=" + handbookid +
				", content='" + content + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}

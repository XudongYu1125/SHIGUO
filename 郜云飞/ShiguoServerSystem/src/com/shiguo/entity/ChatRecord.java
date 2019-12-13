package com.shiguo.entity;

public class ChatRecord {
	
	private int chatrecordid;
	private int senderid;
	private int receiverid;
	
	private int UUID;
	
	private String content;
	private String date;
	
	public int getChatrecordid() {
		return chatrecordid;
	}
	public void setChatrecordid(int chatrecordid) {
		this.chatrecordid = chatrecordid;
	}
	public int getSenderid() {
		return senderid;
	}
	public void setSenderid(int senderid) {
		this.senderid = senderid;
	}
	public int getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(int receiverid) {
		this.receiverid = receiverid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "ChatRecord [chatrecordid=" + chatrecordid + ", senderid=" + senderid + ", receiverid=" + receiverid
				+ ", UUID=" + UUID + ", content=" + content + ", date=" + date + "]";
	}
	public int getUUID() {
		return UUID;
	}
	public void setUUID(int uUID) {
		UUID = uUID;
	}	
}

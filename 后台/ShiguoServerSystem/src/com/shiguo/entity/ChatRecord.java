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

	public int getUUID() {
		return UUID;
	}
	public void setUUID(int uUID) {
		UUID = uUID;
	}	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + UUID;
		result = prime * result + chatrecordid;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + receiverid;
		result = prime * result + senderid;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatRecord other = (ChatRecord) obj;
		if (UUID != other.UUID)
			return false;
		if (chatrecordid != other.chatrecordid)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (receiverid != other.receiverid)
			return false;
		if (senderid != other.senderid)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ChatRecord [chatrecordid=" + chatrecordid + ", senderid=" + senderid + ", receiverid=" + receiverid
				+ ", UUID=" + UUID + ", content=" + content + ", date=" + date + "]";
	}
}

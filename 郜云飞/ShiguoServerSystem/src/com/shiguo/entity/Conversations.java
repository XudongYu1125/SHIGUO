package com.shiguo.entity;

public class Conversations {

	private int UUID;
	private String OwnersStatus;
	private String update;
	
	public int getUUID() {
		return UUID;
	}
	public void setUUID(int uUID) {
		UUID = uUID;
	}
	public String getOwnersStatus() {
		return OwnersStatus;
	}
	public void setOwnersStatus(String ownersStatus) {
		OwnersStatus = ownersStatus;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	
	@Override
	public String toString() {
		return "Conversations [UUID=" + UUID + ", OwnersStatus=" + OwnersStatus + ", update=" + update + "]";
	}
}

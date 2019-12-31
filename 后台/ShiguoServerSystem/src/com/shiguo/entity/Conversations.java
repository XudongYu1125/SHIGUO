package com.shiguo.entity;

public class Conversations {

	private int UUID;
	
	private String ownersId;
	private String ownersStatus;
	
	private String content;
	private String date;
	
	
	
	
	
	public int getUUID() {
		return UUID;
	}
	public void setUUID(int uUID) {
		UUID = uUID;
	}
	public String getOwnersStatus() {
		return ownersStatus;
	}
	public void setOwnersStatus(String ownersStatus) {
		this.ownersStatus = ownersStatus;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String update) {
		this.date = update;
	}
	public String getOwnersId() {
		return ownersId;
	}
	public void setOwnersId(String ownersId) {
		this.ownersId = ownersId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Conversations [UUID=" + UUID + ", ownersId=" + ownersId + ", ownersStatus=" + ownersStatus
				+ ", content=" + content + ", update=" + date + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownersId == null) ? 0 : ownersId.hashCode());
		result = prime * result + ((ownersStatus == null) ? 0 : ownersStatus.hashCode());
		result = prime * result + UUID;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Conversations other = (Conversations) obj;
		if (ownersId == null) {
			if (other.ownersId != null)
				return false;
		} else if (!ownersId.equals(other.ownersId))
			return false;
		if (ownersStatus == null) {
			if (other.ownersStatus != null)
				return false;
		} else if (!ownersStatus.equals(other.ownersStatus))
			return false;
		if (UUID != other.UUID)
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
		return true;
	}

}

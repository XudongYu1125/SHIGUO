package com.example.user.interview;

public class Conversations {

    private int UUID;
    private String ownersId;
    private String ownersStatus;
    private String content;
    private String date;

    @Override
    public String toString() {
        return "Conversations{" +
                "UUID=" + UUID +
                ", ownersId='" + ownersId + '\'' +
                ", ownersStatus='" + ownersStatus + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

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
}

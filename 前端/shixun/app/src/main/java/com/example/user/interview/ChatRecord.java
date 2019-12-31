package com.example.user.interview;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ChatRecord implements Serializable {

    private int chatrecordid;

    private int UUID;

    private int senderid;
    private int receiverid;

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

    public void setUUID(int UUID) {
        this.UUID = UUID;
    }


}

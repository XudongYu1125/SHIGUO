package com.example.user.interview;

public class VertificateMessage {
    private int userid;
    private String verification;
    private String name;

    public VertificateMessage(int userid, String verification, String name) {
        this.userid = userid;
        this.verification = verification;
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

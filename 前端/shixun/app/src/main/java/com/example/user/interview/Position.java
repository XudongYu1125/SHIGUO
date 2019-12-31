package com.example.user.interview;

import java.io.Serializable;

public class Position{

    private int positionid;
    private String request;
    private String salary;
    private String place;
    private String date;
    private int userid;
    private String name;


    public int getPositionid() {
        return positionid;
    }
    public void setPositionid(int positionid) {
        this.positionid = positionid;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
    public String getSalary() {
        return salary;
    }
    public void setSalary( String salary) {
        this.salary = salary;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionid=" + positionid +
                ", request='" + request + '\'' +
                ", salary='" + salary + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", userid=" + userid +
                ", name='" + name + '\'' +
                '}';
    }
}

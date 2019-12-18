package com.example.user.interview;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Position implements Parcelable {

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
                ", date='" + simpleDateFormat.format (time) + '\'' +
                ", userid=" + userid +
                ", name='" + name + '\'' +
                '}';
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date time = new Date (System.currentTimeMillis ());

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel( Parcel parcel , int flags ) {
        parcel.writeString (name);
        parcel.writeString (salary);
        parcel.writeString (request);
        parcel.writeString (simpleDateFormat.format (time));
    }
    public static final Creator<Position> CREATOR = new Creator <Position> ( ) {
        @Override
        public Position createFromParcel( Parcel source ) {
            Position position = new Position ();
            position.setName (source.readString ());
            position.setSalary (source.readString ());
            position.setRequest (source.readString ());
            position.setDate (source.readString ());
            return position;
        }

        @Override
        public Position[] newArray( int size ) {
            return new Position[size];
        }
    };
}

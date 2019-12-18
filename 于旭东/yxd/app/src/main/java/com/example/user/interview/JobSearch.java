package com.example.user.interview;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobSearch implements Parcelable {

    /**职位id*/
    private int positiontid;
    /**发布者id*/
    private int userid;
    /**职务名称*/
    private String position;
    /**要求*/
    private String request;
    /**薪水*/
    private String salary;
    /**工作地点*/
    private String place;
    /**发布时间*/
    private String date;

    public String getPosition() {
        return position;
    }

    public void setPosition( String position ) {
        this.position = position;
    }

    public int getPositiontid() {
        return positiontid;
    }

    public void setPositiontid( int positiontid ) {
        this.positiontid = positiontid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid( int userid ) {
        this.userid = userid;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest( String request ) {
        this.request = request;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary( String salary ) {
        this.salary = salary;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace( String place ) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    Date time = new Date (System.currentTimeMillis ());

    @Override
    public String toString() {
        return "JobSearch{" +
                "positiontid=" + positiontid +
                ", userid=" + userid +
                ", position='" + position + '\'' +
                ", request='" + request + '\'' +
                ", salary='" + salary + '\'' +
                ", place='" + place + '\'' +
                ", date='" + simpleDateFormat.format (time) + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel parcel , int flags ) {
        parcel.writeInt (userid);
        parcel.writeString (position);
        parcel.writeString (place);
        parcel.writeString (salary);
        parcel.writeString (request);
        parcel.writeString (simpleDateFormat.format (time));
    }

    public static final Creator<JobSearch> CREATOR = new Creator<JobSearch> (){
        @Override
        public JobSearch createFromParcel( Parcel source ) {
            JobSearch jobSearch = new JobSearch ();
            jobSearch.setUserid (source.readInt ());
            jobSearch.setPosition (source.readString ());
            jobSearch.setPlace (source.readString ());
            jobSearch.setSalary (source.readString ());
            jobSearch.setRequest (source.readString ());
            jobSearch.setDate (source.readString ());
            return jobSearch;
        }

        @Override
        public JobSearch[] newArray( int size ) {
            return new JobSearch[size];
        }
    };
}

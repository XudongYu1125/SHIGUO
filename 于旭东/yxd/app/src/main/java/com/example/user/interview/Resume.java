package com.example.user.interview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author june
 */
public class Resume implements Parcelable {

    private String resumename;



    /**
     * 简历id
     */
    private int resumeid;
    /**
     * 所属用户id
     */
    private int userid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     *毕业院校
     */
    private String university;
    /**
     *工作经历
     */
    private String experience;
    /**
     *个人能力
     */
    private String ability;

    public int getResumeid() {
        return resumeid;
    }

    public void setResumeid( int resumeid ) {
        this.resumeid = resumeid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid( int userid ) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday( String birthday ) {
        this.birthday = birthday;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity( String university ) {
        this.university = university;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience( String experience ) {
        this.experience = experience;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility( String ability ) {
        this.ability = ability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getResumename() {
        return resumename;
    }

    public void setResumename(String resumename) {
        this.resumename = resumename;
    }

    @Override
    public void writeToParcel( Parcel dest , int flags ) {

        dest.writeInt (userid);
        dest.writeString (name);
        dest.writeString (birthday);
        dest.writeString (university);
        dest.writeString (experience);
        dest.writeString (ability);
    }

    public static final Creator <Resume> CREATOR = new Creator <Resume> ( ) {
        @Override
        public Resume createFromParcel( Parcel source ) {
            Resume resume = new Resume ();
            resume.setUserid (source.readInt ());
            resume.setName (source.readString ());
            resume.setBirthday (source.readString ());
            resume.setUniversity (source.readString ());
            resume.setExperience (source.readString ());
            resume.setAbility (source.readString ());
            return resume;
        }

        @Override
        public Resume[] newArray( int size ) {
            return new Resume[size];
        }
    };
}

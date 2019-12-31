package com.example.user.interview;

public class JobSearch{

    private int jobsearchid;

    private int resumeid;
    private String salary;
    private String place;
    private String position;
    private String experience;
    private int userid;
    private String date;
    public int getJobsearchid() {
        return jobsearchid;
    }
    public void setJobsearchid(int jobsearchid) {
        this.jobsearchid = jobsearchid;
    }
    public int getResumeid() {
        return resumeid;
    }
    public void setResumeid(int resumeid) {
        this.resumeid = resumeid;
    }
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((experience == null) ? 0 : experience.hashCode());
        result = prime * result + jobsearchid;
        result = prime * result + ((place == null) ? 0 : place.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + resumeid;
        result = prime * result + ((salary == null) ? 0 : salary.hashCode());
        result = prime * result + userid;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JobSearch other = (JobSearch) obj;
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }
        if (experience == null) {
            if (other.experience != null) {
                return false;
            }
        } else if (!experience.equals(other.experience)) {
            return false;
        }
        if (jobsearchid != other.jobsearchid) {
            return false;
        }
        if (place == null) {
            if (other.place != null) {
                return false;
            }
        } else if (!place.equals(other.place)) {
            return false;
        }
        if (position == null) {
            if (other.position != null) {
                return false;
            }
        } else if (!position.equals(other.position)) {
            return false;
        }
        if (resumeid != other.resumeid) {
            return false;
        }
        if (salary == null) {
            if (other.salary != null) {
                return false;
            }
        } else if (!salary.equals(other.salary)) {
            return false;
        }
        if (userid != other.userid) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "JobSearch [jobsearchid=" + jobsearchid + ", resumeid=" + resumeid + ", salary=" + salary + ", place="
                + place + ", position=" + position + ", experience=" + experience + ", userid=" + userid + ", date="
                + date + "]";
    }

}

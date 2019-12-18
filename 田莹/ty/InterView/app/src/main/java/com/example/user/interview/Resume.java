package com.example.user.interview;

public class Resume {
	private  String resumename;
	private int resumeid;
	private int userid;
	private String name;
	private String birthday;
	private String university;
	private String experience;
	private String ability;
	
	public int getResumeid() {
		return resumeid;
	}

	public String getResumename() {
		return resumename;
	}

	public void setResumename(String resumename) {
		this.resumename = resumename;
	}

	public void setResumeid(int resumeid) {
		this.resumeid = resumeid;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getAbility() {
		return ability;
	}
	public void setAbility(String ability) {
		this.ability = ability;
	}

	@Override
	public String toString() {
		return "Resume{" +
				"resumename='" + resumename + '\'' +
				", resumeid=" + resumeid +
				", userid=" + userid +
				", name='" + name + '\'' +
				", birthday='" + birthday + '\'' +
				", university='" + university + '\'' +
				", experience='" + experience + '\'' +
				", ability='" + ability + '\'' +
				'}';
	}
}

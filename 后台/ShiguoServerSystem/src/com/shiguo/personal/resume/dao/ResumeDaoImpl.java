package com.shiguo.personal.resume.dao;

import java.util.List;

import com.shiguo.entity.Application;
import com.shiguo.entity.Resume;
import com.shiguo.util.DBUtil;



public class ResumeDaoImpl {
	public List<Resume> findAllResume() {
		return DBUtil.findAll(Resume.class,"select * from resume");
	}
	
	public List<Integer> findResumeIdListByUserId(int userid){
		return DBUtil.findOne("select resumeid from application where receiverid=?",new Object[] {userid});
	}
	
	public List<Resume> findResumeByUserId(int userid) {
		return DBUtil.find(Resume.class,"select * from resume where userid=?",new Object[] {userid});
	}
	
	public int insertResume(Resume resume) {
		return DBUtil.executeUpdate("insert into resume (resumename,userid,name,birthday,university,experience,ability) values (?,?,?,?,?,?,?)", new Object[] {resume.getResumename(),resume.getUserid(),resume.getName(),resume.getBirthday(),resume.getUniversity(),resume.getExperience(),resume.getAbility()});
	}
	
	public int insertResumeApplication(Application application) {
		return DBUtil.executeUpdate("insert into application (senderid,receiverid,resumeid,date) values (?,?,?,?)", new Object[] {application.getSenderid(),application.getReceiverid(),application.getResumeid(),application.getDate()});
	}

	public Resume findResumeById(int id) {
		return (Resume)DBUtil.findById(Resume.class,"select * from resume where resumeid=?",id);
	}
	public int updateResume(Resume resume) {
		return DBUtil.executeUpdate("update resume set resumename=?, userid=?,name=?,birthday=?,university=?,experience=?,ability=? where resumeid=?", new Object[] {resume.getResumename(),resume.getUserid(),resume.getName(),resume.getBirthday(),resume.getUniversity(),resume.getExperience(),resume.getAbility(),resume.getResumeid()});
	}
	
	public int deleteResumeById(int id) {
		return DBUtil.executeUpdate("delete from resume where resumeid=?", new Object[] {id});
	}
}

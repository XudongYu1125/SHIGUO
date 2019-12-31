package com.shiguo.personal.resume.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.shiguo.entity.Application;
import com.shiguo.entity.Resume;
import com.shiguo.personal.resume.dao.ResumeDaoImpl;


public class ResumeServiceImpl {
	public List<Resume> resumeList() {
		return new ResumeDaoImpl().findAllResume();
	}
	
	public boolean addResumeApplication(Application application) {
		int count = new ResumeDaoImpl().insertResumeApplication(application);
		return count > 0;
	}
	
	public List<Resume> searchResumeListByUserId(int userid) {
		return new ResumeDaoImpl().findResumeByUserId(userid);
	}
	
	public List<Resume> searchApplicationListByUserId(int userid){
		List<Resume> resumeList = new ArrayList<>();
		List<Integer> list = searchResumeIdListByUserId(userid);
		for (int id:list) {
			new ResumeServiceImpl().searchResumeById(id);
			resumeList.add(new ResumeServiceImpl().searchResumeById(id));
		}
		return resumeList;
	
	}
	public List<Integer> searchResumeIdListByUserId(int userid){
		return new ResumeDaoImpl().findResumeIdListByUserId(userid);
	}
	public Resume searchResumeById(int id) {
		return new ResumeDaoImpl().findResumeById(id);
	}

	public boolean addResume(Resume resume) {
		int count = new ResumeDaoImpl().insertResume(resume);
		return count > 0;
	}


	public boolean editResume(Resume resume) {
		int count = new ResumeDaoImpl().updateResume(resume);
		return count > 0;
	}

	public boolean dropResume(int id) {
		int count = new ResumeDaoImpl().deleteResumeById(id);
		return count > 0;
	}
}

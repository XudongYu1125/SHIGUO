package com.shiguo.recruitment.jobsearch.dao;

import java.util.List;

import com.shiguo.entity.JobSearch;
import com.shiguo.util.DBUtil;

public class JobSearchDaoImpl {
	public List<JobSearch> findAllJobSearch() {
		return DBUtil.findAll(JobSearch.class,"select * from jobsearch");
	}
	
	public int insertJobSearch(JobSearch jobsearch) {
		return DBUtil.executeUpdate("insert into resume (resumeid,salary,place,position,userid,date) values (?,?,?,?,?,?)", new Object[] {jobsearch.getResumeid(),jobsearch.getSalary(),jobsearch.getPlace(),jobsearch.getPosition(),jobsearch.getUserid(),jobsearch.getDate()});
	}

	public JobSearch findJobSearchById(int id) {
		return (JobSearch)DBUtil.findById(JobSearch.class,"select * from jobsearch where jobsearchid=?",id);
	}
	public int updateJobSearch(JobSearch jobsearch) {
		return DBUtil.executeUpdate("update jobsearch set resumeid=?,salary=?,place=?,position=?,userid=?,date=? where jobsearchid=?", new Object[] {jobsearch.getResumeid(),jobsearch.getSalary(),jobsearch.getPlace(),jobsearch.getPosition(),jobsearch.getUserid(),jobsearch.getDate(),jobsearch.getJobsearchid()});
	}
	
	public int deleteJobSearchById(int id) {
		return DBUtil.executeUpdate("delete from jobsearch where jobsearchid=?", new Object[] {id});
	}
}

package com.shiguo.recruitment.jobsearch.service;

import java.util.List;

import com.shiguo.entity.JobSearch;
import com.shiguo.recruitment.jobsearch.dao.JobSearchDaoImpl;

public class JobSearchServiceImpl {
	public List<JobSearch> jobsearchList() {
		return new JobSearchDaoImpl().findAllJobSearch();
	}
	
	public JobSearch searchJobSearchById(int id) {
		return new JobSearchDaoImpl().findJobSearchById(id);
	}

	public boolean addJobSearch(JobSearch jobsearch) {
		int count = new JobSearchDaoImpl().insertJobSearch(jobsearch);
		return count > 0;
	}


	public boolean editJobSearch(JobSearch jobsearch) {
		int count = new JobSearchDaoImpl().updateJobSearch(jobsearch);
		return count > 0;
	}

	public boolean dropJobSearch(int id) {
		int count = new JobSearchDaoImpl().deleteJobSearchById(id);
		return count > 0;
	}
}

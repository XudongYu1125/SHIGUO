package com.shiguo.recruitment.jobsearch.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shiguo.entity.ChatRecord;
import com.shiguo.entity.JobSearch;
import com.shiguo.message.chatrecord.dao.ChatRecordDaoImpl;
import com.shiguo.recruitment.jobsearch.dao.JobSearchDaoImpl;

public class JobSearchServiceImpl {
	
	public List<JobSearch> jobsearchList() {
		return new JobSearchDaoImpl().findAllJobSearch();
	}
	
	
	
	public JobSearch searchJobSearchById(int id) {
		return new JobSearchDaoImpl().findJobSearchById(id);
	}
	
	public List<JobSearch> searchJobSearchByUser(int userid) {
		return new JobSearchDaoImpl().findJobSearchByUser(userid);
	}
	
	public List<JobSearch> searchJobSearchByCondition(String condition, String lastTime) {
		return new JobSearchDaoImpl().findJobSearchByCondition(condition, lastTime);
	}
	
	

	public String addJobSearch(JobSearch jobsearch) {
		
		List<JobSearch> oldList = jobsearchList();

		if(new JobSearchDaoImpl().insertJobSearch(jobsearch) > 0) {
			
			List<JobSearch> newList = jobsearchList();
			
			newList.removeAll(oldList);
			
			int id = newList.get(0).getJobsearchid();
			
			return String.valueOf(id);
			
		}
		
		return null;
		
	}
	
	

	public boolean editJobSearch(JobSearch jobsearch) {
		int count = new JobSearchDaoImpl().updateJobSearch(jobsearch);
		return count > 0;
	}

	
	
	public boolean dropJobSearch(int id) {
		int count = new JobSearchDaoImpl().deleteJobSearchById(id);
		return count > 0;
	}
	
	
	
	public List<JobSearch> reflashJobSearch(String lastTime) {
		
		List<JobSearch> result = jobsearchList();
		
		if(result == null) {return null;}
				
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date clienttime = df.parse(lastTime);
				
			for(int i = 0;i < result.size();) {
				
				Date update = df.parse(result.get(i).getDate());
				
				if(clienttime.getTime() >= update.getTime()) {
					result.remove(i);
				}else {
					i ++;
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.size() == 0 ? null : result;
	}
	
	
	
}

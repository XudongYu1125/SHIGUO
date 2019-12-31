package com.shiguo.recruitment.jobsearch.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shiguo.entity.JobSearch;
import com.shiguo.util.DBUtil;

public class JobSearchDaoImpl {
	
	public List<JobSearch> findAllJobSearch() {
		return DBUtil.findAll(JobSearch.class,"select * from jobsearch");
	}
	
	public int insertJobSearch(JobSearch jobsearch) {
		//System.out.println(jobsearch.toString());
		return DBUtil.executeUpdate("insert into jobsearch (resumeid,salary,place,position,userid,date,experience) values (?,?,?,?,?,?,?)", new Object[] {jobsearch.getResumeid(),jobsearch.getSalary(),jobsearch.getPlace(),jobsearch.getPosition(),jobsearch.getUserid(),jobsearch.getDate(), jobsearch.getExperience()});
	}

	public JobSearch findJobSearchById(int jobsearchid) {
		return (JobSearch)DBUtil.findById(JobSearch.class,"select * from jobsearch where jobsearchid=?",jobsearchid);
	}
	
	public List<JobSearch> findJobSearchByUser(int userid){
		Object obj = new DBUtil().find(JobSearch.class, "select * from jobsearch where userid=?", new Object[]{userid});
		return obj != null ? (List<JobSearch>)obj : null;
	}
	
	public List<JobSearch> findJobSearchByCondition(String condition, String lastTime) {
		
		Object obj = new DBUtil().find(JobSearch.class, "select * from jobsearch where position=? or place=?", new Object[]{condition});
		
		List<JobSearch> result = null;
		
		if(obj == null) {return null;}
		
		try {
			
			result = (List<JobSearch>) obj;
			
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
	
	
	public int updateJobSearch(JobSearch jobsearch) {
		return DBUtil.executeUpdate("update jobsearch set resumeid=?,salary=?,place=?,position=?,userid=?,date=?,experience=? where jobsearchid=?", new Object[] {jobsearch.getResumeid(),jobsearch.getSalary(),jobsearch.getPlace(),jobsearch.getPosition(),jobsearch.getUserid(),jobsearch.getDate(),jobsearch.getExperience(),jobsearch.getJobsearchid()});
	}
	
	public int deleteJobSearchById(int id) {
		return DBUtil.executeUpdate("delete from jobsearch where jobsearchid=?", new Object[] {id});
	}
}

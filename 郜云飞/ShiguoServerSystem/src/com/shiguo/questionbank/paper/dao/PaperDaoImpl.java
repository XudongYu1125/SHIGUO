package com.shiguo.questionbank.paper.dao;

import java.util.List;

import com.shiguo.entity.Paper;
import com.shiguo.util.DBUtil;

public class PaperDaoImpl {
	public List<Paper> findAllPaper() {
		return DBUtil.findAll(Paper.class,"select * from paper");
	}
	
	public int insertPaper(Paper paper) {
		return DBUtil.executeUpdate("insert into paper (name,imageurl,type,level,date,companyid) values (?,?,?,?,?,?)", new Object[] {paper.getName(),paper.getImageurl(),paper.getType(),paper.getLevel(),paper.getDate(),paper.getCompanyid()});
	}

	public Paper findPaperById(int id) {
		return (Paper)DBUtil.findById(Paper.class,"select * from paper where paperid=?",id);
	}
	
	public List<Paper> findPaperByType(int type) {
		return DBUtil.find(Paper.class,"select * from paper where type=?",new Object[] {type});
	}
	
	public int updatePaper(Paper paper) {
		return DBUtil.executeUpdate("update paper set name=?,imageurl=?,type=?,level=?,date=?,companyid=? where paperid=?", new Object[] {paper.getName(),paper.getImageurl(),paper.getType(),paper.getLevel(),paper.getDate(),paper.getCompanyid(),paper.getPaperid()});
	}
	
	public int deletePaperById(int id) {
		return DBUtil.executeUpdate("delete from paper where paperid=?", new Object[] {id});
	}
}

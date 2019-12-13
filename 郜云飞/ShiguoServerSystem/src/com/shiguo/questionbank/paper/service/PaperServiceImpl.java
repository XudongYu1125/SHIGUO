package com.shiguo.questionbank.paper.service;

import java.util.List;

import com.shiguo.entity.Paper;
import com.shiguo.questionbank.paper.dao.PaperDaoImpl;



public class PaperServiceImpl {
	public List<Paper> paperList() {
		return new PaperDaoImpl().findAllPaper();
	}
	
	public Paper searchPaperById(int id) {
		return new PaperDaoImpl().findPaperById(id);
	}
	
	public List<Paper> searchPaperByType(int type) {
		return new PaperDaoImpl().findPaperByType(type);
	}

	public boolean addPaper(Paper paper) {
		int count = new PaperDaoImpl().insertPaper(paper);
		return count > 0;
	}


	public boolean editPaper(Paper paper) {
		int count = new PaperDaoImpl().updatePaper(paper);
		return count > 0;
	}

	public boolean dropPaper(int id) {
		int count = new PaperDaoImpl().deletePaperById(id);
		return count > 0;
	}
}

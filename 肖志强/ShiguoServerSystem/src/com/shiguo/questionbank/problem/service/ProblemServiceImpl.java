package com.shiguo.questionbank.problem.service;

import java.util.List;
import com.shiguo.entity.Problem;
import com.shiguo.questionbank.problem.dao.ProblemDaoImpl;


public class ProblemServiceImpl {
	public List<Problem> searchProblemListByPaperId(int paperid) {
		return new ProblemDaoImpl().findProblemByPaperId(paperid);
	}
	
	public Problem searchProblemById(int id) {
		return new ProblemDaoImpl().findProblemById(id);
	}
	
	public List<Problem> searchProblemByName(String name) {
		String nameStr="%";
		char a[] = name.toCharArray();
		for(int i=0;i<a.length;i++) {
			nameStr+=a[i]+"%";
		}
		return new ProblemDaoImpl().findProblemByName(nameStr);
	}

	public List<Problem> searchProblemByType(int type) {
		return new ProblemDaoImpl().findProblemByType(type);
	}
	
	public boolean addProblem(Problem problem) {
		int count = new ProblemDaoImpl().insertProblem(problem);
		return count > 0;
	}


	public boolean editProblem(Problem problem) {
		int count = new ProblemDaoImpl().updateProblem(problem);
		return count > 0;
	}

	public boolean dropProblem(int id) {
		int count = new ProblemDaoImpl().deleteProblemById(id);
		return count > 0;
	}
}

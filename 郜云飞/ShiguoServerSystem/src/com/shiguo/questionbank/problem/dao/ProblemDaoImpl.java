package com.shiguo.questionbank.problem.dao;

import java.util.List;
import com.shiguo.entity.Problem;
import com.shiguo.util.DBUtil;

public class ProblemDaoImpl {
	public List<Problem> findAllProblem() {
		return DBUtil.findAll(Problem.class,"select * from problem");
	}
	
	public List<Problem> findProblemByPaperId(int paperid) {
		return DBUtil.find(Problem.class,"select * from problem where paperid=?",new Object[] {paperid});
	}
	
	public List<Problem> findProblemByName(String name) {
		return DBUtil.find(Problem.class,"select * from problem where name like ?",new Object[] {name});
	}
	
	public int insertProblem(Problem problem) {
		return DBUtil.executeUpdate("insert into problem (name,type,content,answer,paperid) values (?,?,?,?,?)", new Object[] {problem.getName(),problem.getType(),problem.getContent(),problem.getAnswer(),problem.getPaperid()});
	}

	public Problem findProblemById(int id) {
		return (Problem)DBUtil.findById(Problem.class,"select * from problem where problemid=?",id);
	}
	
	public List<Problem> findProblemByType(int type) {
		return DBUtil.find(Problem.class,"select * from problem where type=?",new Object[] {type});
	}
	
	public int updateProblem(Problem problem) {
		return DBUtil.executeUpdate("update problem set name=?, type=?,content=?,answer=?,paperid=? where problemid=?", new Object[] {problem.getName(),problem.getType(),problem.getContent(),problem.getAnswer(),problem.getPaperid(),problem.getProblemid()});
	}
	
	public int deleteProblemById(int id) {
		return DBUtil.executeUpdate("delete from problem where problemid=?", new Object[] {id});
	}
}

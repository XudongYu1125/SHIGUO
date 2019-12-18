package com.shiguo.questionbank.handbook.dao;

import java.util.List;

import com.shiguo.entity.HandBook;
import com.shiguo.util.DBUtil;

public class HandBookDaoImpl {
	public List<HandBook> findAllHandBook() {
		return DBUtil.findAll(HandBook.class,"select * from handbook");
	}
	
	public int insertHandBook(HandBook handbook) {
		return DBUtil.executeUpdate("insert into handbook (name,content) values (?,?)", new Object[] {handbook.getName(),handbook.getContent()});
	}

	public HandBook findHandBookById(int id) {
		return (HandBook)DBUtil.findById(HandBook.class,"select * from handbook where handbookid=?",id);
	}
	public int updateHandBook(HandBook handbook) {
		return DBUtil.executeUpdate("update handbook set name=?,content=? where handbookid=?", new Object[] {handbook.getName(),handbook.getContent(),handbook.getHandbookid()});
	}
	
	public int deleteHandBookById(int id) {
		return DBUtil.executeUpdate("delete from handbook where handbookid=?", new Object[] {id});
	}
}

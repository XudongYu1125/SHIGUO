package com.shiguo.questionbank.handbook.service;

import java.util.List;

import com.shiguo.entity.HandBook;
import com.shiguo.questionbank.handbook.dao.HandBookDaoImpl;


public class HandBookServiceImpl {
	public List<HandBook> handbookList() {
		return new HandBookDaoImpl().findAllHandBook();
	}
	
	public HandBook searchHandBookById(int id) {
		return new HandBookDaoImpl().findHandBookById(id);
	}

	public boolean addHandBook(HandBook handbook) {
		int count = new HandBookDaoImpl().insertHandBook(handbook);
		return count > 0;
	}


	public boolean editHandBook(HandBook handbook) {
		int count = new HandBookDaoImpl().updateHandBook(handbook);
		return count > 0;
	}

	public boolean dropHandBook(int id) {
		int count = new HandBookDaoImpl().deleteHandBookById(id);
		return count > 0;
	}
}

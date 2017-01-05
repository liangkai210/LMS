package com.kailiang.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.kailiang.lms.bean.BookLoans;
import com.kailiang.lms.dao.BookLoansDao;

public class BookLoanService {
	@Autowired
	BookLoansDao bLDao;

	@Transactional
	public void addBookLoans(BookLoans bookLoans) {
		bLDao.add(bookLoans);
	}

	public void updateBookLoans(BookLoans bookLoans, String property, Object value) {
		bLDao.update(bookLoans, property, value);
	}

	public void updateBookLoans(BookLoans bookLoans, String property, Object value, Object value2, Object value3,
			Object value4) {
		bLDao.update(bookLoans, property, value, value2, value3, value4);
	}

	public void deleteBookLoans(int bookId, int branchId, int cardNo) {
		bLDao.delete(bookId, branchId, cardNo);
	}

	public void deleteBookLoans(int id) {
		bLDao.delete(id);
	}

	public List<BookLoans> getBookLoaList(String property, Object value) {
		return bLDao.getList(property, value);
	}

	public List<BookLoans> getBookLoaList(String property1, String property2, Object value1, Object value2) {
		return bLDao.get(property1, property2, value1, value2);
	}

	public List<BookLoans> getBookLoaList(String property1, String property2, String property3, Object value1,
			Object value2, Object value3) {
		return bLDao.get(property1, property2, property3, value1, value2, value3);
	}

	public BookLoans getSingleBookLoa(String property, Object value) {
		return bLDao.get(property, value);
	}

	public BookLoans getSingleBookLoa(String property1, String property2, String property3, Object value1,
			Object value2, Object value3) {
		return bLDao.getSingle(property1, property2, property3, value1, value2, value3);
	}

	public List<BookLoans> getAllBookLoa() {
		return bLDao.getAll();
	}

}

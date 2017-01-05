package com.kailiang.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.kailiang.lms.bean.BookAuthors;
import com.kailiang.lms.dao.BookAuthorsDao;

public class BookAutService {
	@Autowired
	BookAuthorsDao bADao;

	@Transactional
	public void addBookAuthors(BookAuthors bookAuthors) {
		bADao.add(bookAuthors);
	}

	public void updateBookAuthors(BookAuthors ba, String property, Object value) {
		bADao.update(ba, property, value);
	}

	public void deleteBookAuthors(int id) {
		bADao.delete(id);
	}

	public List<BookAuthors> getBookAutList(String property, Object value) {
		return bADao.getList(property, value);
	}

	public BookAuthors getSingleBookAut(String property, Object value) {
		return bADao.get(property, value);
	}

	public List<BookAuthors> getAllBookAut() {
		return bADao.getAll();
	}

}

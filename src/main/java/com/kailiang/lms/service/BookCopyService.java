package com.kailiang.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.kailiang.lms.bean.BookCopies;
import com.kailiang.lms.dao.BookCopiesDao;

public class BookCopyService {

	@Autowired
	BookCopiesDao bCDao;

	@Transactional
	public void addBookCopies(BookCopies bookCopies) {
		bCDao.add(bookCopies);
	}

	public void updateBookCopies(BookCopies bookCopies, String property, Object value) {
		bCDao.update(bookCopies, property, value);
	}

	public void updateBookCopies(int bookId, int branchId, int copies) {
		bCDao.update(bookId, branchId, copies);
	}

	public void deleteBookCopies(int id) {
		bCDao.delete(id);
	}

	public List<BookCopies> getBookCopList(String property, Object value) {
		return bCDao.getList(property, value);
	}

	public BookCopies getSingleBookCop(String property, Object value) {
		return bCDao.get(property, value);
	}

	public List<BookCopies> getAllBookCop() {
		return bCDao.getAll();
	}

}

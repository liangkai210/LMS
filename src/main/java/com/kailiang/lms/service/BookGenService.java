package com.kailiang.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.kailiang.lms.bean.BookGenres;
import com.kailiang.lms.dao.BookGenresDao;

public class BookGenService {
	@Autowired
	BookGenresDao bGDao;

	@Transactional
	public void addBookGenres(BookGenres bookGenres) {
		bGDao.add(bookGenres);
	}

	public void updateBookGenres(BookGenres bookGenres, String property, Object value) {
		bGDao.update(bookGenres, property, value);
	}

	public void deleteBookGenres(int id) {
		bGDao.delete(id);
	}

	public List<BookGenres> getBookGenList(String property, Object value) {
		return bGDao.getList(property, value);
	}

	public BookGenres getSingleBookGen(String property, Object value) {
		return bGDao.get(property, value);
	}

	public List<BookGenres> getAllBookGen() {
		return bGDao.getAll();
	}
}

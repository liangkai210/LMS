package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.BookGenres;

public class BookGenresDao extends BasicDao<BookGenres> implements ResultSetExtractor<List<BookGenres>> {

	public void add(BookGenres t) {
		String sql = "INSERT INTO tbl_book_genres(genre_id, bookId) VALUES (?,?)";
		template.update(sql, t.getGenreId(), t.getBookId());
	}

	public void update(BookGenres t, String property, Object value) {
		String sql = "UPDATE tbl_book_genres SET " + property + " = ? WHERE bookId = ?";
		template.update(sql, value, t.getBookId());

	}

	public void delete(int id) {
		String sql = "DELETE FROM tbl_book_genres WHERE bookId = ?";
		template.update(sql, id);
	}
	public void deleteGenre(int id) {
		String sql = "DELETE FROM tbl_book_genres WHERE genre_id = ?";
		template.update(sql, id);
	}

	public List<BookGenres> getList(String property, Object value) {
		String sql = "SELECT * FROM tbl_book_genres WHERE " + property + " = ?";
		return template.query(sql, new Object[] { value }, this);

	}

	public BookGenres get(String property, Object value) {
		return getList(property, value).get(0);
	}

	public List<BookGenres> getAll() {
		String sql = "SELECT * FROM tbl_book_genres";
		return template.query(sql, this);
	}

	@Override
	public List<BookGenres> extractData(ResultSet rs) {
		List<BookGenres> list = new ArrayList<BookGenres>();
		try {
			while (rs.next()) {
				BookGenres bg = new BookGenres();
				bg.setBookId(rs.getInt("bookId"));
				bg.setGenreId(rs.getInt("genre_id"));
				list.add(bg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

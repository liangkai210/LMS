package com.kailiang.lms.bean;

public class BookGenres {

	private Integer genre_id;
	private Integer bookId;

	@Override
	public String toString() {
		return "BookGenres [genre_id=" + genre_id + ", bookId=" + bookId + "]";
	}

	public Integer getGenreId() {
		return genre_id;
	}

	public void setGenreId(Integer genre_id) {
		this.genre_id = genre_id;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

}

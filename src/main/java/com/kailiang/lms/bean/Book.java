package com.kailiang.lms.bean;

public class Book {

	private int bookId;
	private String title;
	private Integer pubId;

	public Book() {
	}

	public Book(String title) {
		this.title = title;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPubId() {
		return pubId;
	}

	public void setPubId(Integer pubId) {
		this.pubId = pubId;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", pubId=" + pubId + "]";
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Book book = (Book) o;

		if (bookId != book.bookId) return false;
		if (title != null ? !title.equals(book.title) : book.title != null) return false;
		return pubId != null ? pubId.equals(book.pubId) : book.pubId == null;
	}

	@Override
	public int hashCode() {
		int result = bookId;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (pubId != null ? pubId.hashCode() : 0);
		return result;
	}
}

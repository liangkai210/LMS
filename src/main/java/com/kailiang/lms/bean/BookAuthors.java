package com.kailiang.lms.bean;

public class BookAuthors {

	private int bookId;
	private int authorId;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "BookAuthors [bookId=" + bookId + ", authorId=" + authorId + "]";
	}

}

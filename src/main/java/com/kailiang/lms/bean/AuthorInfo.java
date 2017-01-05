package com.kailiang.lms.bean;

import java.util.List;

/**
 * Created by kailiang on 2016/12/27.
 */
public class AuthorInfo {

    Author author;
    Publisher publisher;
    List<Book> books;
    List<Genre> bookGenre;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Genre> getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(List<Genre> bookGenre) {
        this.bookGenre = bookGenre;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}

package com.kailiang.lms.service;

import com.kailiang.lms.bean.*;
import com.kailiang.lms.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class AuthorService {
    @Autowired
    AuthorDao authorDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    GenreDao genreDao;
    @Autowired
    BookAuthorsDao bADao;
    @Autowired
    BookGenresDao bGDao;

    @Transactional
    public void addAuthor(String authorName) {
        Author author = new Author();
        author.setAuthorName(authorName);
        authorDao.add(author);
    }

    public void updateAuthor(String authorId, String authorName, String[] bookId) {
        Author author = authorDao.get("authorId", Integer.parseInt(authorId));
        authorDao.update(author, "authorName", authorName);
        if (bookId != null) {
            bADao.deleteAuthor(Integer.parseInt(authorId));
            for (int i = 0; i < bookId.length; i++) {
                BookAuthors bookAuthors = new BookAuthors();
                bookAuthors.setAuthorId(author.getAuthorId());
                bookAuthors.setBookId(Integer.parseInt(bookId[i]));
                bADao.add(bookAuthors);
            }
        }
    }

    public void deleteAuthor(String authorId) {
        authorDao.delete(Integer.parseInt(authorId));
    }

    public List<Author> getAllAuthors(Integer pageNo, String searchString) {
        return authorDao.getAll(pageNo, searchString);
    }

    public Map<String, Object> getAuthorInfo(String authorName, Integer pageNo, Integer pageSize) {
        if (pageNo > 0 && pageNo != null) {
            authorDao.setPageNo(pageNo);
        }
        if (pageSize > 0 && pageSize != null) {
            authorDao.setPageSize(pageSize);
        }

        List<Author> authors = authorDao.getAll(pageNo, authorName);
        List<AuthorInfo> result = new ArrayList<>();
        for (Author author : authors) {
            AuthorInfo authorInfo = new AuthorInfo();
            authorInfo.setBooks(getBooks(bADao.getList("authorId", author.getAuthorId())));
            authorInfo.setAuthor(author);
            result.add(authorInfo);
        }
        Map<String, Object> map = new HashMap<>();
        if (authorName == null || authorName.length() == 0) {
            authorDao.setTotalItemNumber(authorDao.getCount());
        } else {
            authorDao.setTotalItemNumber(authorDao.getCount(authorName));
        }
        map.put("authorInfo", result);
        map.put("pageNo", authorDao.getPageNo());
        map.put("pageSize", authorDao.getPageSize());
        map.put("pages", authorDao.getTotalPageNumber());
        map.put("getPrev", authorDao.getPrevPage());
        map.put("getNext", authorDao.getNextPage());
        return map;
    }

    private List<Book> getBooks(List<BookAuthors> list) {
        if (list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        List<Book> result = new ArrayList<>();
        for (BookAuthors bookAuthors : list) {
            result.add(bookDao.get("bookId", bookAuthors.getBookId()));
        }
        return result;
    }

    public List<Author> getAuthorList(String property, Object value) {
        return authorDao.getList(property, value);
    }

    public Author getSingleAuthor(String property, Object value) {
        return authorDao.get(property, value);
    }

    public Map<String, Object> getAuthorBooks(String authorId) {
        Author author = authorDao.get("authorId", Integer.parseInt(authorId));
        List<BookAuthors> balist = bADao.getList("authorId", authorId);
        List<Book> books = bookDao.getAll();
        List<Book> bookList = new ArrayList<>();
        for (BookAuthors bookAuthors : balist) {
            Book book = bookDao.get("bookId", bookAuthors.getBookId());
            bookList.add(book);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("books", books);
        map.put("bookList", bookList);
        map.put("authorId", authorId);
        map.put("authorName", author.getAuthorName());
        return map;
    }
}

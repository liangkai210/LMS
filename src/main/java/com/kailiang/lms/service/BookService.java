package com.kailiang.lms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kailiang.lms.bean.*;
import com.kailiang.lms.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class BookService {
    private String alert;
    private boolean hasOperated;


    @Autowired
    BookDao bookDao;
    @Autowired
    BookGenresDao bGDao;
    @Autowired
    BookAuthorsDao bADao;
    @Autowired
    PublisherDao pDao;
    @Autowired
    GenreDao gDao;
    @Autowired
    AuthorDao aDao;
    @Autowired
    BookCopiesDao bCDao;
    @Autowired
    BranchDao braDao;
    @Autowired
    BookLoansDao bLDao;

    public void deleteBook(String bookId) {
        BookLoans bookLoans = bLDao.get("bookId", bookId);
        if (bookLoans != null) {
            setAlert("fail");
        } else {
            bookDao.delete(Integer.parseInt(bookId));
            setAlert("success");
        }
    }

    public Map<String, Object> updateBook(String bookId) {
        Book book = bookDao.get("bookId", Integer.parseInt(bookId));
        Publisher publisher = pDao.get("publisherId", book.getPubId());
        List<BookAuthors> baList = bADao.getList("bookId", book.getBookId());
        List<BookGenres> bgList = bGDao.getList("bookId", book.getBookId());
        List<Author> authors = aDao.getAll();
        List<Genre> genres = gDao.getAll();
        List<Publisher> publishers = pDao.getAll();

        List<Author> authorList = new ArrayList<>();
        List<Genre> genreList = new ArrayList<>();

        for (BookAuthors bookAuthors : baList) {
            List<Author> aus = aDao.getList("authorId", bookAuthors.getAuthorId());
            authorList.add(aus.get(0));
        }
        for (BookGenres bookGenres : bgList) {
            List<Genre> gens = gDao.getList("genre_id", bookGenres.getGenreId());
            genreList.add(gens.get(0));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("publishers", publishers);
        map.put("publisher", publisher);
        map.put("authors", authors);
        map.put("genres", genres);
        map.put("authorList", authorList);
        map.put("genreList", genreList);
        map.put("publisherId", publisher.getPublisherId());
        map.put("publisherName", publisher.getPublisherName());
        map.put("bookTitle", book.getTitle());
        map.put("bookId", bookId);

        return map;
    }

    public void saveBook(String bookName, String bookId, String pubId, String[] authorIds, String[] genreIds) {
        Book book = new Book();
        // saveWithId as add books
        book.setBookId(Integer.parseInt(bookId));
        bookDao.update(book, "pubId", Integer.parseInt(pubId));
        bookDao.update(book, "title", bookName);
        if (authorIds != null) {
            bADao.delete(Integer.parseInt(bookId));
            for (int i = 0; i < authorIds.length; i++) {
                BookAuthors bookAuthors = new BookAuthors();
                bookAuthors.setBookId(book.getBookId());
                bookAuthors.setAuthorId(Integer.parseInt(authorIds[i]));
                bADao.add(bookAuthors);
            }
        }
        if (genreIds != null && genreIds.length > 0) {
            bGDao.delete(Integer.parseInt(bookId));
            for (int i = 0; i < genreIds.length; i++) {
                BookGenres bookGenres = new BookGenres();
                bookGenres.setBookId(book.getBookId());
                bookGenres.setGenreId(Integer.parseInt(genreIds[i]));
                bGDao.add(bookGenres);
            }
        }
    }

    @Transactional
    public Map<String, Object> addBook(String title) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("publishers", pDao.getAll());
        map.put("authors", aDao.getAll());
        map.put("genres", gDao.getAll());
        map.put("branches", braDao.getAll());
        return map;
    }

    public void saveBookInDB(String bookName, String pubId, String[] authorIds, String[] genreIds, String[] branchIds) {
        Book book = new Book();
        book.setTitle(bookName);
        book.setPubId(Integer.parseInt(pubId));
        Integer bookId = bookDao.addWithId(book);
        if (authorIds != null) {
            for (int i = 0; i < authorIds.length; i++) {
                BookAuthors bookAuthors = new BookAuthors();
                bookAuthors.setBookId(bookId);
                bookAuthors.setAuthorId(Integer.parseInt(authorIds[i]));
                bADao.add(bookAuthors);
            }
        }
        if (genreIds != null && genreIds.length > 0) {
            for (int i = 0; i < genreIds.length; i++) {
                BookGenres bookGenres = new BookGenres();
                bookGenres.setBookId(bookId);
                bookGenres.setGenreId(Integer.parseInt(genreIds[i]));
                bGDao.add(bookGenres);
            }
        }
        if (branchIds != null) {
            BookCopies bookCopy = new BookCopies();
            for (String branId : branchIds) {
                // bookCopiesDao.update(bookId, Integer.parseInt(branId),
                // 1);
                bookCopy.setBookId(bookId);
                bookCopy.setBranchId(Integer.parseInt(branId));
                bookCopy.setNoOfCopies(1);
                bCDao.add(bookCopy);
            }
        }
    }

    public Map<String, Object> getBookInfo(String title, Integer pageNo, Integer pageSize) {
        if (pageSize != null && pageSize > 0) {
            bookDao.setPageSize(pageSize);
        }
        if (pageNo != null && pageNo > 0) {
            bookDao.setPageNo(pageNo);
        }
        List<Book> books = bookDao.getAll(pageNo, title);
        List<BookInfo> result = new ArrayList<>();
        for (Book book : books) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setGenres(getGenres(bGDao.getList("bookId", book.getBookId())));
            bookInfo.setAuthors(getAuthors(bADao.getList("bookId", book.getBookId())));
            bookInfo.setPublisher(pDao.get("publisherId", book.getPubId()));
            bookInfo.setBook(book);
            result.add(bookInfo);
        }
        Map<String, Object> map = new HashMap<>();
        if (title == null || title.length() == 0) {
            bookDao.setTotalItemNumber(bookDao.getCount());
        } else {
            bookDao.setTotalItemNumber(bookDao.getCount(title));
        }
        map.put("books", result);
        map.put("pageNo", bookDao.getPageNo());
        map.put("pageSize", bookDao.getPageSize());
        map.put("pages", bookDao.getTotalPageNumber());
        map.put("getPrev", bookDao.getPrevPage());
        map.put("getNext", bookDao.getNextPage());

        if (getAlert() != null) {
            map.put("alert", getAlert());
        }

        if (this.hasOperated) {
            this.setAlert("");
            this.setHasOperated(false);
        }
        System.out.print("alert!!! " + map.get("alert"));
        return map;
    }

    private List<Genre> getGenres(List<BookGenres> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<Genre> result = new ArrayList<>();
        for (BookGenres bookGenre : list) {
            result.add(gDao.get("genre_id", bookGenre.getGenreId()));
        }
        return result;
    }

    private List<Author> getAuthors(List<BookAuthors> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<Author> result = new ArrayList<>();
        for (BookAuthors bookAuthor : list) {
            result.add(aDao.get("authorId", bookAuthor.getAuthorId()));
        }
        return result;
    }

    @Transactional
    public void addBook(Book book) {
        bookDao.add(book);
    }

    @Transactional
    public Integer addBookWithId(Book book) {
        return bookDao.addWithId(book);
    }

    public void updateBook(Book book, String property, Object value) {
        bookDao.update(book, property, value);
    }

    public void updateBookWithId(Book book, String property, Object value) {
        bookDao.updateWithId(book, property, value);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public List<Book> getBookList(String property, Object value) {
        return bookDao.getList(property, value);
    }

    public List<Book> getAllBooksWithSearch(Integer pageNo, String searchString) {
        return bookDao.getAll(pageNo, searchString);
    }

    public Book getSingleBook(String property, Object value) {
        return bookDao.getSingle(property, value);
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String type) {
        if (type.equals("success")) {
            alert = "<div class='alert alert-success' role='alert'>Update Successful</div>";
        } else if (type.equals("fail")) {
            alert = "<div class='alert alert-danger' role='alert'>Operation Failed</div>";
        } else {
            alert = null;
        }
    }

    public boolean isHasOperated() {
        return hasOperated;
    }

    public void setHasOperated(boolean hasOperated) {
        this.hasOperated = hasOperated;
    }
}

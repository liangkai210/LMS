package com.kailiang.lms.service;

import com.kailiang.lms.bean.*;
import com.kailiang.lms.dao.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class GenreService {
    private String alert;
    @Autowired
    BookDao bookDao;
    @Autowired
    GenreDao genreDao;
    @Autowired
    BookAuthorsDao bADao;
    @Autowired
    BookGenresDao bGDao;

    @Transactional
    public void addGenre(String genreName) {
        Genre genre = new Genre();
        genre.setGenre_name(genreName);
        genreDao.add(genre);
    }

    public void updateGenre(String genreId, String genreName, String[] bookId) {
        Genre genre = genreDao.get("genre_id", Integer.parseInt(genreId));
        genreDao.update(genre, "genre_name", genreName);
        if (bookId != null) {
            bGDao.deleteGenre(Integer.parseInt(genreId));
            for (int i = 0; i < bookId.length; i++) {
                BookGenres bookGenres = new BookGenres();
                bookGenres.setGenreId(genre.getGenre_id());
                bookGenres.setBookId(Integer.parseInt(bookId[i]));
                bGDao.add(bookGenres);
            }
        }
    }

    public void deleteGenre(String genreId) {
        genreDao.delete(Integer.parseInt(genreId));
    }


    public JSONObject getGenreInfo(String genreName, Integer pageNo, Integer pageSize) {
        if (pageNo > 0 && pageNo != null) {
            genreDao.setPageNo(pageNo);
        }
        if (pageSize > 0 && pageSize != null) {
            genreDao.setPageSize(pageSize);
        }

        List<Genre> genres = genreDao.getAll(pageNo, genreName);
        JSONObject result = new JSONObject();
        JSONArray genresJson = new JSONArray();
        for (Genre genre : genres) {
            JSONObject genreJson = new JSONObject();
            genreJson.put("genre_id", genre.getGenre_id());
            genreJson.put("genre_name", genre.getGenre_name());

            JSONArray booksJson = new JSONArray();
            for (Book book : bookDao.getAllBooksFromGen(genre.getGenre_id())) {
                JSONObject bookJson = new JSONObject();
                bookJson.put("title", book.getTitle());
                bookJson.put("bookId", book.getBookId());
                booksJson.add(bookJson);
            }
            genreJson.put("books", booksJson);
            genresJson.add(genreJson);

        }


        if (genreName == null || genreName.length() == 0)

        {
            genreDao.setTotalItemNumber(genreDao.getCount());
        } else

        {
            genreDao.setTotalItemNumber(genreDao.getCount(genreName));
        }
        result.put("genres", genresJson);
        result.put("pageNo", genreDao.getPageNo());
        result.put("pageSize", genreDao.getPageSize());
        result.put("pages", genreDao.getTotalPageNumber());
        result.put("getPrev", genreDao.getPrevPage());
        result.put("getNext", genreDao.getNextPage());
        return result;
    }

    public List<Genre> getGenreList(String property, Object value) {
        return genreDao.getList(property, value);
    }

    public Genre getSingleGenre(String property, Object value) {
        return genreDao.get(property, value);
    }

    public Map<String, Object> getGenreBooks(String genreId) {
        Genre genre = genreDao.get("genre_id", Integer.parseInt(genreId));
        List<BookGenres> bgList = bGDao.getList("genre_id", genreId);
        List<Book> books = bookDao.getAll();
        List<Book> bookList = new ArrayList<>();
        for (BookGenres bookGenres : bgList) {
            Book book = bookDao.get("bookId", bookGenres.getBookId());
            bookList.add(book);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("books", books);
        map.put("bookList", bookList);
        map.put("genreId", genreId);
        map.put("genreName", genre.getGenre_name());
        return map;
    }

    public void setAlert(String type) {
        if (type.equals("success")) {
            alert = "<div class='alert alert-success' role='alert'>Update Successful</div>";
        }
        if (type.equals("fail")) {
            alert = "<div class='alert alert-danger' role='alert'>Operation Failed</div>";
        }
    }

    public String getAlert() {
        return alert;
    }
}

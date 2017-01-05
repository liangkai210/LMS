package com.kailiang.lms.service;

import java.util.*;

import com.kailiang.lms.bean.*;
import com.kailiang.lms.dao.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PublisherService {

    private String alert;

    @Autowired
    PublisherDao publisherDao;
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

    @Transactional
    public void addPublisher(String publisherName, String publisherAddress, String publisherPhone) {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(publisherName);
        publisher.setPublisherAddress(publisherAddress);
        publisher.setPublisherPhone(publisherPhone);
        publisherDao.add(publisher);
    }

    public void updatePublisher(String publisherId, String publisherName, String publisherAddress, String publisherPhone) {
        Publisher publisher = publisherDao.get("publisherId", Integer.parseInt(publisherId));
        publisherDao.update(publisher, "publisherName", publisherName);
        publisherDao.update(publisher, "publisherAddress", publisherAddress);
        publisherDao.update(publisher, "publisherPhone", publisherPhone);
    }

    public void deletePub(int id) {
        publisherDao.delete(id);
    }

    public Map<String, Object> pubDetail(String publisherId){
        Publisher publisher = publisherDao.get("publisherId", Integer.parseInt(publisherId));
        String publisherName = publisher.getPublisherName();
        String publisherAddress = publisher.getPublisherAddress();
        String publisherPhone = publisher.getPublisherPhone();
        Map<String, Object> map = new HashMap<>();
        map.put("publisherName", publisherName);
        map.put("publisherAddress", publisherAddress);
        map.put("publisherPhone", publisherPhone);
        map.put("publisherId", publisherId);
        return map;
    }

    public List<Publisher> getPubList(String property, Object value) {
        return publisherDao.getList(property, value);
    }

    public Publisher getSinglePub(String property, Object value) {
        return publisherDao.get(property, value);
    }

    public List<Publisher> getAllPub(Integer pageNo, String searchString) {
        return publisherDao.getAll(pageNo, searchString);
    }

    public JSONObject getPublisherInfo(String pubName, Integer pageNo, Integer pageSize) {
        if (pageNo > 0 && pageNo != null) {
            publisherDao.setPageNo(pageNo);
        }
        if (pageSize > 0 && pageSize != null) {
            publisherDao.setPageSize(pageSize);
        }
        List<Publisher> publishers = publisherDao.getAll(pageNo, pubName);
        JSONObject result = new JSONObject();
        JSONArray publishersJson = new JSONArray();

        for (Publisher publisher : publishers) {
            JSONObject publisherJson = new JSONObject();
            publisherJson.put("publisherId", publisher.getPublisherId());
            publisherJson.put("publisherName", publisher.getPublisherName());
            publisherJson.put("publisherAddress", publisher.getPublisherAddress());
            publisherJson.put("publisherPhone", publisher.getPublisherPhone());

            JSONArray booksJson = new JSONArray();
            for (Book book : bookDao.getAllBooksFromPub(publisher.getPublisherId())) {
                JSONObject bookJson = new JSONObject();
                bookJson.put("title", book.getTitle());
                bookJson.put("bookId", book.getBookId());

                List<Genre> genres = getGenres(bGDao.getList("bookId", book.getBookId()));
                JSONArray genresArray = new JSONArray();
                for (Genre genre : genres) {
                    JSONArray genreArray = new JSONArray();
                    genreArray.add(genre.getGenre_id());
                    genreArray.add(genre.getGenre_name());
                    genresArray.add(genreArray);
                }
                bookJson.put("genres", genresArray);
                List<Author> authors = getAuthors(bADao.getList("bookId", book.getBookId()));
                JSONArray authorsArray = new JSONArray();

                for (Author author : authors) {
                    JSONArray authorArray = new JSONArray();
                    authorArray.add(author.getAuthorId());
                    authorArray.add(author.getAuthorName());
                    authorsArray.add(authorArray);
                }
                bookJson.put("authors", authorsArray);
                booksJson.add(bookJson);
            }
            publisherJson.put("books", booksJson);
            publishersJson.add(publisherJson);
        }

        if (pubName == null || pubName.length() == 0) {
            publisherDao.setTotalItemNumber(publisherDao.getCount());
        } else {
            publisherDao.setTotalItemNumber(publisherDao.getCount(pubName));
        }

        result.put("publishers", publishersJson);
        result.put("pageNo", publisherDao.getPageNo());
        result.put("pageSize", publisherDao.getPageSize());
        result.put("pages", publisherDao.getTotalPageNumber());
        result.put("getPrev", publisherDao.getPrevPage());
        result.put("getNext", publisherDao.getNextPage());
        result.put("alert", getAlert());

        return result;
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
/*
publishers: [{
          publisherName: "name",
          publisherId: 3,
          books: [{
            title: "New title",
            authors: [
              [3, "JK Rowling"],
              [5, "New Author"]
            ],
            genres: [
              [1, "Romance"],
              [4, "Horror"]
            ],
          }, {
            title: "New title",
            authors: [
              [3, "JK Rowling"],
              [5, "New Author"]
            ],
            genres: [
              [1, "Romance"],
              [4, "Horror"]
            ],
          }]
        }]
*/
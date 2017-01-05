package com.kailiang.lms.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import com.kailiang.lms.bean.*;
import com.kailiang.lms.dao.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class BorService {
    private String alert;
    @Autowired
    BorrowerDao borDao;
    @Autowired
    BookLoansDao bLDao;
    @Autowired
    BookCopiesDao bCDao;
    @Autowired
    BranchDao brDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao aDao;
    @Autowired
    GenreDao gDao;
    @Autowired
    BookAuthorsDao bADao;
    @Autowired
    BookGenresDao bGDao;
    @Autowired
    PublisherDao pubDao;

    public JSONObject bookLoansList(String branchId, String cardNo, Integer pageNo, Integer pageSize, String searchString) {
        if (pageSize != null && pageSize > 0) {
            borDao.setPageSize(pageSize);
        }
        if (pageNo != null && pageNo > 0) {
            borDao.setPageNo(pageNo);
        }
        List<BookLoans> bookLoanList = bLDao.getBookLoansFromBranch(searchString, branchId, cardNo, pageNo);
        JSONObject result = new JSONObject();
        JSONArray bookLoans = new JSONArray();
        for (BookLoans bookLoan : bookLoanList) {
            JSONObject bLJson = new JSONObject();
            bLJson.put("title", bookDao.get("bookId", bookLoan.getBookId()).getTitle());
            bLJson.put("bookId", bookDao.get("bookId", bookLoan.getBookId()).getBookId());
            bLJson.put("dateOut", bookLoan.getDateOut().toString());
            bLJson.put("dueDate", bookLoan.getDueDate().toString());
            bookLoans.add(bLJson);
        }


        if (searchString == null || searchString.length() == 0) {
            bLDao.setTotalItemNumber(bLDao.getLoansCount(branchId, cardNo));
        } else {
            bLDao.setTotalItemNumber(bLDao.getLoansCountLike(searchString, branchId, cardNo));
        }

        result.put("bookLoans", bookLoans);
        result.put("pageNo", bLDao.getPageNo());
        result.put("pageSize", bLDao.getPageSize());
        result.put("pages", bLDao.getTotalPageNumber());
        result.put("getPrev", bLDao.getPrevPage());
        result.put("getNext", bLDao.getNextPage());
        result.put("cardNo", cardNo);
        result.put("branchId", branchId);

        return result;
    }

    public JSONObject bookCopyList(String branchId, String cardNo, Integer pageNo, Integer pageSize, String searchString) {
        if (pageSize != null && pageSize > 0) {
            borDao.setPageSize(pageSize);
        }
        if (pageNo != null && pageNo > 0) {
            borDao.setPageNo(pageNo);
        }

        List<Book> bookList = bookDao.getPossLoansFromBranch(searchString, branchId, cardNo, pageNo);
        JSONObject result = new JSONObject();
        JSONArray books = new JSONArray();
        for (Book book : bookList) {
            JSONObject bookJson = new JSONObject();
            bookJson.put("title", book.getTitle());
            bookJson.put("bookId", book.getBookId());

            JSONArray authors = new JSONArray();
            for (Author author : getAuthors(bADao.getList("bookId", book.getBookId()))) {
                JSONObject authorJson = new JSONObject();
                authorJson.put("authorName", author.getAuthorName());
                authorJson.put("authorId", author.getAuthorId());
                authors.add(authorJson);
            }

            JSONArray genres = new JSONArray();
            for (Genre genre : getGenres(bGDao.getList("bookId", book.getBookId()))) {
                JSONObject genreJson = new JSONObject();
                genreJson.put("genreName", genre.getGenre_name());
                genreJson.put("genreId", genre.getGenre_id());
                genres.add(genreJson);
            }

            JSONObject publisher = new JSONObject();
            publisher.put("publisherName", pubDao.get("publisherId", book.getPubId()).getPublisherName());
            publisher.put("publisherId", book.getPubId());

            bookJson.put("authors", authors);
            bookJson.put("genres", genres);
            bookJson.put("publisher", publisher);
            books.add(bookJson);

        }
        if (searchString == null || searchString.length() == 0) {
            bookDao.setTotalItemNumber(bookDao.getPossLoansCount(branchId, cardNo));
        } else {
            bookDao.setTotalItemNumber(bookDao.getPossLoansCountbyLike(searchString, branchId, cardNo));
        }
        result.put("books", books);
        result.put("pageNo", bookDao.getPageNo());
        result.put("pageSize", bookDao.getPageSize());
        result.put("pages", bookDao.getTotalPageNumber());
        result.put("getPrev", bookDao.getPrevPage());
        result.put("getNext", bookDao.getNextPage());
        result.put("branchId", branchId);
        result.put("cardNo", cardNo);

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

    public void updateDate(String cardNo, String bookId, String branchId, String newDueDate) {
        java.util.Date date = new Date(System.currentTimeMillis());
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(newDueDate);
            java.sql.Date dueDate = new Date(date.getTime());
            BookLoans bookLoans = bLDao.getSingle("cardNo", "bookId", "branchId", Integer.parseInt(cardNo),
                    Integer.parseInt(bookId), Integer.parseInt(branchId));
            bLDao.update(bookLoans, "dueDate", dueDate, Integer.parseInt(bookId),
                    Integer.parseInt(branchId), Integer.parseInt(cardNo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> listBranches(String cardNo) {
        Map<String, Object> map = new HashMap<>();
        List<Branch> list = brDao.getAll();
        Integer card = Integer.parseInt(cardNo);
        map.put("cardNo", card);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> checkOut(String cardNo, String branchId, String bookId) {
        BookLoans bookLoans = new BookLoans();
        BookCopies bc = bCDao.getNoOfCopies("bookId", "branchId", Integer.parseInt(bookId), Integer.parseInt(branchId));
        bCDao.update(Integer.parseInt(bookId), Integer.parseInt(branchId), bc.getNoOfCopies() - 1);
        bookLoans.setBookId(Integer.parseInt(bookId));
        bookLoans.setBranchId(Integer.parseInt(branchId));
        bookLoans.setCardNo(Integer.parseInt(cardNo));
        bookLoans.setDateOut(new Date(System.currentTimeMillis()));
        bookLoans.setDueDate(new Date(System.currentTimeMillis() + 7 * 24 * 3600 * 1000));
        bookLoans.setDateIn(null);
        bLDao.add(bookLoans);

        Long date = System.currentTimeMillis();
        Long dueDate = System.currentTimeMillis() + 7 * 24 * 3600 * 1000;
        Map<String, Object> map = new HashMap<>();
        map.put("dateOut", String.valueOf(date));
        map.put("dueDate", String.valueOf(dueDate));
        map.put("cardNo", cardNo);
        map.put("branchId", branchId);
        map.put("bookId", bookId);
        return map;
    }

    public void returnFinish(String bookId, String branchId, String cardNo) {
        bLDao.delete(Integer.parseInt(bookId), Integer.parseInt(branchId), Integer.parseInt(cardNo));
        BookCopies bookCopyNum = bCDao.getNoOfCopies("bookId", "branchId", Integer.parseInt(bookId), Integer.parseInt(branchId));
        bCDao.update(Integer.parseInt(bookId), Integer.parseInt(branchId), bookCopyNum.getNoOfCopies() + 1);
    }

    public void addBorrower(String name, String address, String phone, String username, String password) {
        Borrower borrower = new Borrower();
        borrower.setName(name);
        borrower.setAddress(address);
        borrower.setPhone(phone);
        borrower.setUsername(username);
        borrower.setPassword(password);
        borDao.add(borrower);
    }

    public void updateBorrower(String cardNo, String name, String address, String phone) {
        Borrower borrower = borDao.get("cardNo", Integer.parseInt(cardNo));
        borDao.update(borrower, "name", name);
        borDao.update(borrower, "address", address);
        borDao.update(borrower, "phone", phone);
        setAlert("success");
    }

    public void deleteBorrower(String cardNo) {
        borDao.delete(Integer.parseInt(cardNo));
    }

    public JSONObject getBorInfo(String borName, Integer pageNo, Integer pageSize) {
        if (pageNo > 0 && pageNo != null) {
            borDao.setPageNo(pageNo);
        }
        if (pageSize > 0 && pageSize != null) {
            borDao.setPageSize(pageSize);
        }
        List<Borrower> borrowers = borDao.getAll(pageNo, borName);
        JSONObject result = new JSONObject();
        JSONArray borrowersJson = new JSONArray();

        for (Borrower borrower : borrowers) {
            JSONObject borrowerJson = new JSONObject();
            borrowerJson.put("cardNo", borrower.getCardNo());
            borrowerJson.put("name", borrower.getName());
            borrowerJson.put("address", borrower.getAddress());
            borrowerJson.put("phone", borrower.getPhone());
            if (bLDao.get("cardNo", borrower.getCardNo()) != null) {
                borrowerJson.put("loanStatus", Boolean.TRUE);
            } else {
                borrowerJson.put("loanStatus", Boolean.FALSE);
            }
            borrowersJson.add(borrowerJson);
        }
        if (borName == null || borName.length() == 0) {
            borDao.setTotalItemNumber(borDao.getCount());
        } else {
            borDao.setTotalItemNumber(borDao.getCount(borName));
        }
        result.put("borrowers", borrowersJson);
        result.put("pageNo", borDao.getPageNo());
        result.put("pageSize", borDao.getPageSize());
        result.put("pages", borDao.getTotalPageNumber());
        result.put("getPrev", borDao.getPrevPage());
        result.put("getNext", borDao.getNextPage());
        result.put("alert", getAlert());
        return result;
    }

    public List<Borrower> getBorList(String property, Object value) {
        return borDao.getList(property, value);
    }

    public List<Borrower> getSpec(String property1, String property2, Object value1, Object value2) {
        return borDao.get(property1, property2, value1, value2);
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


    public Borrower getSingleBor(String property, Object value) {
        return borDao.get(property, value);
    }

    public List<Borrower> getAllBor(Integer pageNo, String searchString) {
        return borDao.getAll(pageNo, searchString);
    }

    public Map<String, Object> getBorrowerDetails(String cardNo) {
        Borrower borrower = borDao.get("cardNo", Integer.parseInt(cardNo));
        Map<String, Object> map = new HashMap<>();
        map.put("cardNo", cardNo);
        map.put("name", borrower.getName());
        map.put("address", borrower.getAddress());
        map.put("phone", borrower.getPhone());
        return map;
    }

    public Map<String, Object> showBookLoans(String searchString, Integer pageNo, Integer pageSize, String cardNo) {
        if (pageSize != null && pageSize > 0) {
            bLDao.setPageSize(pageSize);
        }
        if (pageNo != null && pageNo > 0) {
            bLDao.setPageNo(pageNo);
        }
        List<BookLoans> bookLoansList = bLDao.getBooksLoansLike(cardNo, pageNo, searchString);

        List<BookInfo> bookInfoList = new ArrayList<>();
        for (BookLoans bookLoans : bookLoansList) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBook(bookDao.get("bookId", bookLoans.getBookId()));
            bookInfo.setBranch(brDao.get("branchId", bookLoans.getBranchId()));
            bookInfo.setDateOut(bookLoans.getDateOut().toString());
            bookInfo.setDueDate(bookLoans.getDueDate().toString());
            bookInfoList.add(bookInfo);
        }
        Map<String, Object> map = new HashMap<>();
        if (searchString == null || searchString.length() == 0) {
            bLDao.setTotalItemNumber(bLDao.getCount(cardNo));
        } else {
            bLDao.setTotalItemNumber(bLDao.getCount(searchString, cardNo));
        }
        map.put("loans", bookInfoList);
        map.put("cardNo", cardNo);
        map.put("pageNo", bLDao.getPageNo());
        map.put("pageSize", bLDao.getPageSize());
        map.put("pages", bLDao.getTotalPageNumber());
        map.put("getPrev", bLDao.getPrevPage());
        map.put("getNext", bLDao.getNextPage());
        return map;
    }
}
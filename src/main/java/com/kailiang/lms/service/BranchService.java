package com.kailiang.lms.service;

import java.util.*;

import com.kailiang.lms.bean.*;
import com.kailiang.lms.dao.*;
import com.sun.tools.javac.jvm.Gen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class BranchService {

    private String alert;
    @Autowired
    BranchDao branchDao;
    @Autowired
    BookCopiesDao bCDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    GenreDao gDao;
    @Autowired
    AuthorDao aDao;
    @Autowired
    BookGenresDao bGDao;
    @Autowired
    BookAuthorsDao bADao;
    @Autowired
    PublisherDao pDao;

    public void setPageNo(int pageNo) {
        branchDao.setPageNo(pageNo);
    }

    public int getPageSize() {
        return branchDao.getPageSize();
    }

    @Transactional
    public void addBranch(String branchName, String branchAddress) {
        Branch branch = new Branch();
        branch.setBranchName(branchName);
        branch.setBranchAddress(branchAddress);
        branchDao.add(branch);
    }

    public void updateBranch(String selection, String branchId, String branchName, String branchAddress) {
        if (selection.equals("update")) {
            Branch branch;
            try {
                branch = branchDao.get("branchId", Integer.parseInt(branchId));
                branchDao.update(branch, "branchName", branchName);
                branchDao.update(branch, "branchAddress", branchAddress);
                setAlert("success");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (selection.equals("delete")) {
            try {
                branchDao.delete(Integer.parseInt(branchId));
                setAlert("success");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteBranch(int id) {
        branchDao.delete(id);
    }

    public List<Branch> getBranchList(String property, Object value) {
        return branchDao.getList(property, value);
    }

    public Branch getSingleBranch(String property, Object value) {
        return branchDao.get(property, value);
    }

    public List<Branch> getAllBranch() {
        return branchDao.getAll();
    }

    public int getCount() {
        return branchDao.getCount();
    }

    public Map<String, Object> getBranchInfo(String branchName, Integer pageNo, Integer pageSize) {
        if (pageSize != null && pageSize > 0) {
            branchDao.setPageSize(pageSize);
        }
        if (pageNo != null && pageNo > 0) {
            branchDao.setPageNo(pageNo);
        }
        List<Branch> branches = branchDao.getAll(pageNo, branchName);
        Map<String, Object> map = new HashMap<>();
        if (branchName == null || branchName.trim().length() == 0) {
            branchDao.setTotalItemNumber(branchDao.getCount());
        } else {
            branchDao.setTotalItemNumber(branchDao.getCount(branchName));
        }
        map.put("branches", branches);
        map.put("pageNo", branchDao.getPageNo());
        map.put("pageSize", branchDao.getPageSize());
        map.put("pages", branchDao.getTotalPageNumber());
        map.put("getPrev", branchDao.getPrevPage());
        map.put("getNext", branchDao.getNextPage());
        map.put("alert", getAlert());
        return map;
    }

    public Map<String, Object> getBranchDetails(String branchId) {
        Branch branch = branchDao.get("branchId", Integer.parseInt(branchId));
        Map<String, Object> map = new HashMap<>();
        map.put("branch", branch);
        return map;
    }

    public Map<String, Object> getBookCopiesDetails(String searchString, Integer pageNo, Integer pageSize, String branchId) {
        if (pageSize != null && pageSize > 0) {
            bookDao.setPageSize(pageSize);
        }
        if (pageNo != null && pageNo > 0) {
            bookDao.setPageNo(pageNo);
        }
        Map<String, Object> map = new HashMap<>();
        List<BookCopies> list = bCDao.getBooksCopiesLike(branchId, pageNo, searchString);
        List<BookInfo> bookInfoList = new ArrayList<>();
        for (BookCopies bookCopies : list) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setGenres(getGenres(bGDao.getList("bookId", bookCopies.getBookId())));
            bookInfo.setAuthors(getAuthors(bADao.getList("bookId", bookCopies.getBookId())));
            bookInfo.setPublisher(pDao.get("publisherId", bookDao.get("bookId", bookCopies.getBookId()).getPubId()));
            bookInfo.setBook(bookDao.get("bookId", bookCopies.getBookId()));
            bookInfo.setNoOfCopies(bCDao.getNoOfCopies("bookId", "branchId", bookCopies.getBookId(), branchId).getNoOfCopies());
            bookInfoList.add(bookInfo);
        }
        if (searchString == null || searchString.length() == 0) {
            bCDao.setTotalItemNumber(bCDao.getCount(branchId));
        } else {
            bCDao.setTotalItemNumber(bCDao.getCount(searchString, branchId));
        }
        map.put("bookInfoList", bookInfoList);
        map.put("branchId", branchId);
        map.put("pageNo", bCDao.getPageNo());
        map.put("pageSize", bCDao.getPageSize());
        map.put("pages", bCDao.getTotalPageNumber());
        map.put("getPrev", bCDao.getPrevPage());
        map.put("getNext", bCDao.getNextPage());
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

    public void addBookCopies(String bookId, String branchId, String noOfCopies) {
        if (bCDao.get("bookId", bookId).getNoOfCopies() != Integer
                .parseInt(noOfCopies)) {
            bCDao.update(Integer.parseInt(bookId), Integer.parseInt(branchId),
                    Integer.parseInt(noOfCopies));
        } else {
            bCDao.update(Integer.parseInt(bookId), Integer.parseInt(branchId),
                    bCDao.get("bookId", bookId).getNoOfCopies());
        }
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String type) {
        if (type.equals("success")) {
            alert = "<div class='alert alert-success' role='alert'>Update Successful</div>";
        }
        if (type.equals("fail")) {
            alert = "<div class='alert alert-danger' role='alert'>Operation Failed</div>";
        }
    }
}

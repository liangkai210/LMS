package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kailiang.lms.bean.Book;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.BookLoans;

public class BookLoansDao extends BasicDao<BookLoans> implements ResultSetExtractor<List<BookLoans>> {

    public void add(BookLoans t) {
        String sql = "INSERT INTO tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate, dateIn) "
                + "VALUES (?,?,?,?,?,?)";
        template.update(sql, new Object[]{t.getBookId(), t.getBranchId(), t.getCardNo(), t.getDateOut(),
                t.getDueDate(), t.getDateIn()});
    }

    public void update(BookLoans t, String property, Object value) {
        String sql = "UPDATE tbl_book_loans SET " + property + " = ? WHERE bookId = ?";
        template.update(sql, value, t.getBookId());

    }

    public void update(BookLoans t, String property, Object value, Object value2, Object value3, Object value4) {
        String sql = "UPDATE tbl_book_loans SET " + property + " = ? WHERE bookId = ? AND branchId = ? AND cardNo = ?";
        template.update(sql, new Object[]{value, value2, value3, value4});
    }

    public void delete(int bookId, int branchId, int cardNo) {
        String sql = "DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?";
        template.update(sql, new Object[]{bookId, branchId, cardNo});
    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_book_loans WHERE bookId = ?";
        template.update(sql, id);
    }

    public List<BookLoans> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_book_loans WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public BookLoans get(String property, Object value) {
        List<BookLoans> bl = getList(property, value);
        if (bl != null && bl.size() > 0) {
            return bl.get(0);
        }
        return null;
    }

    public BookLoans getSingle(String property1, String property2, String property3, Object value1, Object value2,
                               Object value3) {
        String sql = "SELECT * FROM tbl_book_loans WHERE " + property1 + " = ? AND " + property2 + " = ? AND "
                + property3 + " = ?";
        return template.query(sql, new Object[]{value1, value2, value3}, this).get(0);
    }

    public List<BookLoans> getAll() {
        String sql = "SELECT * FROM tbl_book_loans";
        return template.query(sql, this);
    }

    public List<BookLoans> get(String property1, String property2, Object value1, Object value2) {
        String sql = "SELECT * FROM tbl_book_loans WHERE " + property1 + " = ? AND " + property2 + " = ?";
        return template.query(sql, new Object[]{value1, value2}, this);
    }

    public List<BookLoans> get(String property1, String property2, String property3, Object value1, Object value2,
                               Object value3) {
        String sql = "SELECT * FROM tbl_book_loans WHERE " + property1 + " = ? AND " + property2 + " = ? AND "
                + property3 + " = ?";
        return template.query(sql, new Object[]{value1, value2, value3}, this);
    }

    public List<BookLoans> getBooksLoansLike(String cardNo, Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select bl.* from tbl_book_loans bl inner join tbl_book b on bl.bookId = b.bookId where bl.cardNo = ? and b.title like ?";
            return template.query(getPage(sql), new Object[]{Integer.parseInt(cardNo), searchString}, this);
        } else {
            String sql = "select bl.* from tbl_book_loans bl where bl.cardNo = ?";
            return template.query(getPage(sql), new Object[]{Integer.parseInt(cardNo)}, this);
        }
    }

    public List<BookLoans> getBookLoansFromBranch(String searchString, String branchId, String cardNo, Integer pageNo) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "SELECT * FROM tbl_book_loans INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_loans.bookId " +
                    "WHERE title LIKE ? AND branchId = ? AND cardNo = ?";
            return template.query(getPage(sql), new Object[]{searchString, branchId, cardNo}, this);
        } else {
            String sql = "SELECT * FROM tbl_book_loans WHERE branchId = ? AND cardNo = ?";
            return template.query(getPage(sql), new Object[]{branchId, cardNo}, this);
        }
    }

    public int getLoansCount(String branchId, String cardNo) {
        String sql = "SELECT count(*) FROM tbl_book_loans WHERE branchId = ? AND cardNo = ?";
        return template.queryForObject(sql, new Object[]{branchId, cardNo}, Integer.class);
    }

    public int getLoansCountLike(String searchString, String branchId, String cardNo) {
        String sql = "SELECT count(*) FROM tbl_book_loans INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_loans.bookId " +
                "WHERE title LIKE ? AND branchId = ? AND cardNo = ?";
        return template.queryForObject(sql, new Object[]{"%" + searchString + "%", branchId, cardNo}, Integer.class);
    }

    public int getCount(String cardNo) {
        String sql = "select count(*) from tbl_book_loans bl where cardNo = ?";
        return template.queryForObject(sql, new Object[]{cardNo}, Integer.class);
    }

    public int getCount(String title, String branchId) {
        String sql = "select COUNT(*) from tbl_book_loans bl inner join tbl_book b on bl.bookId = b.bookId where bl.cardNo = ? and b.title like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%", branchId}, Integer.class);
    }

    @Override
    public List<BookLoans> extractData(ResultSet rs) {
        List<BookLoans> list = new ArrayList<BookLoans>();
        try {
            while (rs.next()) {
                BookLoans bl = new BookLoans();
                bl.setBookId(rs.getInt("bookId"));
                bl.setBranchId(rs.getInt("branchID"));
                bl.setCardNo(rs.getInt("cardNo"));
                bl.setDateOut(rs.getDate("dateOut"));
                bl.setDueDate(rs.getDate("dueDate"));
                bl.setDateIn(rs.getDate("dateIn"));
                list.add(bl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

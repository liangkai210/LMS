package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.BookCopies;

public class BookCopiesDao extends BasicDao<BookCopies> implements ResultSetExtractor<List<BookCopies>> {

    public void add(BookCopies t) {
        String sql = "INSERT INTO tbl_book_copies(bookId, branchId, noOfCopies) VALUES (?,?,?)";
        template.update(sql, new Object[]{t.getBookId(), t.getBranchId(), t.getNoOfCopies()});

    }

    public void update(BookCopies t, String property, Object value) {

        String sql = "UPDATE tbl_book_copies SET " + property + " = ? WHERE bookId = ? AND branchId = ?";
        template.update(sql, new Object[]{value, t.getBookId(), t.getBranchId()});

    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_book_copies WHERE bookId = ?";
        template.update(sql, id);

    }

    public void update(int bookId, int branchId, int copies) {
        String sql = "UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?";
        template.update(sql, new Object[]{copies, bookId, branchId});
    }

    public List<BookCopies> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_book_copies WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public List<BookCopies> get(String property, String property1, Object value, Object value1) {
        String sql = "SELECT * FROM tbl_book_copies WHERE " + property + " = ? AND " + property1 + " = ?";
        return template.query(sql, new Object[]{value, value1}, this);
    }

    public BookCopies getNoOfCopies(String property, String property1, Object value, Object value1) {
        return get(property, property1, value, value1).get(0);
    }

    public List<BookCopies> getBooksCopiesLike(String branchId, Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            System.out.println("@@@!!!!!!!!!!!!!!!!!!!");
            searchString = "%" + searchString + "%";
            System.out.print("searchString is :" + searchString);
            System.out.println("branchId is: " + Integer.parseInt(branchId));
            String sql = "select bc.* from tbl_book_copies bc inner join tbl_book b on bc.bookId = b.bookId where bc.branchId = ? and b.title like ?";
            return template.query(getPage(sql), new Object[]{Integer.parseInt(branchId), searchString}, this);
        } else {
            String sql = "select bc.* from tbl_book_copies bc where bc.branchId = ?";
            return template.query(getPage(sql), new Object[]{Integer.parseInt(branchId)}, this);
        }
    }

    public int getCount(String branchId) {
        String sql = "select count(*) from tbl_book_copies bc where branchId = ?";
        return template.queryForObject(sql, new Object[]{branchId}, Integer.class);
    }

    public int getCount(String title, String branchId) {
        String sql = "select COUNT(*) from tbl_book_copies bc inner join tbl_book b on bc.bookId = b.bookId where bc.branchId = ? and b.title like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%", branchId}, Integer.class);
    }

    public BookCopies get(String property, Object value) {

        return getList(property, value).get(0);
    }

    public List<BookCopies> getAll() {
        String sql = "SELECT * FROM tbl_book_copies";
        return template.query(sql, this);
    }

    @Override
    public List<BookCopies> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<BookCopies> list = new ArrayList<BookCopies>();
        try {
            while (rs.next()) {
                BookCopies bc = new BookCopies();
                bc.setBookId(rs.getInt("bookId"));
                bc.setBranchId(rs.getInt("branchId"));
                bc.setNoOfCopies(rs.getInt("noOfCopies"));
                list.add(bc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

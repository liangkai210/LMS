package com.kailiang.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.kailiang.lms.bean.Book;

public class BookDao extends BasicDao<Book> implements ResultSetExtractor<List<Book>> {

    public Integer addWithId(Book t) {
        final String title = t.getTitle();
        final Integer pubId = t.getPubId();
        final String INSERT_SQL = "insert into tbl_book (title, pubId) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"bookId"});
                ps.setString(1, title);
                ps.setInt(2, pubId);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void add(Book t) {
        System.out.println(t.getTitle() + " " + t.getPubId());
        String sql = "INSERT INTO tbl_book(title, pubId) VALUES (?,?)";
        template.update(sql, t.getTitle(), t.getPubId());
    }

    public void update(Book t, String property, Object value) {
        String sql = "UPDATE tbl_book SET " + property + " = ? WHERE bookId = ?";
        template.update(sql, value, t.getBookId());

    }

    public Integer updateWithId(Book t, String property, final Object value) {
        String sql = "UPDATE tbl_book SET " + property + " = ? WHERE bookId = ?";
        final Integer bookId = t.getBookId();
        final String INSERT_SQL = sql;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"bookId"});
                ps.setObject(1, value);
                ps.setInt(2, bookId);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();

    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_book WHERE bookId = ?";
        template.update(sql, id);
    }

    public List<Book> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_book WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }


    public Book getSingle(String property, Object value) {
        String sql = "SELECT * FROM tbl_book WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this).get(0);
    }

    public Book get(String property, Object value) {
        return getList(property, value).get(0);
    }

    public List<Book> getAllBooksFromPub(Integer pubId) {
        String sql = "select * from tbl_book where pubId = ? ";
        return template.query(sql, new Object[]{pubId}, this);
    }

    public List<Book> getAllBooksFromGen(Integer genreId) {
        String sql = "select * from tbl_book b inner join tbl_book_genres bg on b.bookId = bg.bookId " +
                "where genre_id = ? ";
        return template.query(sql, new Object[]{genreId}, this);
    }

    public List<Book> getAll(Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_book where title like ?";
            return template.query(getPage(sql), new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_book";
            return template.query(getPage(sql), this);
        }
    }


    public List<Book> getPossLoansFromBranch(String searchString, String branchId, String cardNo, Integer pageNo) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies " +
                    "ON tbl_book_copies.bookId = b.bookId INNER " +
                    "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                    "WHERE b.title LIKE ? AND tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?" +
                    "AND b.bookId NOT IN(SELECT bl.bookId " +
                    "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
            return template.query(getPage(sql), new Object[]{searchString, branchId, cardNo}, this);
        } else {
            String sql = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies " +
                    "ON tbl_book_copies.bookId = b.bookId INNER " +
                    "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                    "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?" +
                    "AND b.bookId NOT IN(SELECT bl.bookId " +
                    "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
            return template.query(getPage(sql), new Object[]{branchId, cardNo}, this);
        }
    }

    public int getPossLoansCount(String branchId, String cardNo) {
        String sql = "SELECT count(*) FROM tbl_book b INNER JOIN tbl_book_copies " +
                "ON tbl_book_copies.bookId = b.bookId INNER " +
                "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                "WHERE tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?" +
                "AND b.bookId NOT IN(SELECT bl.bookId " +
                "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
        return template.queryForObject(sql, new Object[]{branchId, cardNo}, Integer.class);
    }

    public int getPossLoansCountbyLike(String title, String branchId, String cardNo) {
        String sql = "SELECT b.* FROM tbl_book b INNER JOIN tbl_book_copies " +
                "ON tbl_book_copies.bookId = b.bookId INNER " +
                "JOIN tbl_library_branch ON tbl_library_branch.branchId = tbl_book_copies.branchId " +
                "WHERE b.title LIKE ? AND tbl_book_copies.noOfCopies > 0 AND tbl_library_branch.branchId = ?" +
                "AND b.bookId NOT IN(SELECT bl.bookId " +
                "FROM tbl_book_loans bl WHERE bl.cardNo = ?)";
        return template.queryForObject(sql, new Object[]{"%" + title + "%", branchId, cardNo}, Integer.class);
    }


    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_book";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String title) {
        String sql = "SELECT count(*) FROM tbl_book WHERE title like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%"}, Integer.class);
    }

    @Override
    public List<Book> extractData(ResultSet rs) {
        List<Book> books = new ArrayList<Book>();
        try {
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("bookId"));
                b.setTitle(rs.getString("title"));
                b.setPubId(rs.getInt("pubId"));
                books.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAll() {
        return getAll(0, null);
    }

}

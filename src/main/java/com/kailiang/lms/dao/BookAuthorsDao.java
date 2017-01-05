package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.BookAuthors;

public class BookAuthorsDao extends BasicDao<BookAuthors> implements ResultSetExtractor<List<BookAuthors>> {

    public void add(BookAuthors t) {
        String sql = "INSERT INTO tbl_book_authors (bookId, authorId) VALUES (?,?)";
        template.update(sql, t.getBookId(), t.getAuthorId());
    }

    public void update(BookAuthors t, String property, Object value) {
        String sql = "UPDATE tbl_book_authors SET " + property + " = ? WHERE bookId = ?";
        template.update(sql, value, t.getBookId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_book_authors WHERE bookId = ?";
        template.update(sql, id);

    }

    public void deleteAuthor(int id) {
        String sql = "DELETE FROM tbl_book_authors WHERE authorId = ?";
        template.update(sql, id);
    }

    public List<BookAuthors> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_book_authors WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public BookAuthors get(String property, Object value) {
        return getList(property, value).get(0);
    }

    public List<BookAuthors> getAll() {
        String sql = "SELECT * FROM tbl_book_authors";
        return template.query(sql, this);
    }

    @Override
    public List<BookAuthors> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<BookAuthors> list = new ArrayList<BookAuthors>();
        try {
            while (rs.next()) {
                BookAuthors ba = new BookAuthors();
                ba.setBookId(rs.getInt("bookId"));
                ba.setAuthorId(rs.getInt("authorId"));
                list.add(ba);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

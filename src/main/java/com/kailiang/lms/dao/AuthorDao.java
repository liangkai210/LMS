package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.kailiang.lms.bean.Author;

public class AuthorDao extends BasicDao<Author> {

    public void add(Author t) {
        String sql = "INSERT INTO tbl_author(authorName) VALUES (?)";
        template.update(sql, t.getAuthorName());
    }

    public void update(Author t, String property, Object value) {
        template.update("UPDATE tbl_author SET " + property + " = ? WHERE authorId = ?",
                new Object[]{value, t.getAuthorId()});

    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_author WHERE authorId = ?";
        template.update(sql, id);
    }

    public List<Author> getAll(Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_author where authorName like ?";
            return template.query(getPage(sql), new Object[]{searchString},
                    this);
        } else {
            String sql = "select * from tbl_author";
            return template.query(getPage(sql), this);
        }
    }

    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_author";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String authorName) {
        String sql = "SELECT count(*) FROM tbl_author WHERE authorName like ?";
        return template.queryForObject(sql, new Object[]{"%" + authorName + "%"}, Integer.class);
    }

    public List<Author> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_author WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public Author get(String property, Object value) {
        return getList(property, value).get(0);
    }

    @Override
    public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Author> authors = new ArrayList<Author>();
        try {
            while (rs.next()) {
                Author a = new Author();
                a.setAuthorId(rs.getInt("authorId"));
                a.setAuthorName(rs.getString("authorName"));
                authors.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Author> getAll() {
        return getAll(0, null);
    }

}

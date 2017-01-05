package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.Genre;

public class GenreDao extends BasicDao<Genre> implements ResultSetExtractor<List<Genre>> {

    public void add(Genre t) {
        String sql = "INSERT INTO tbl_genre(genre_id, genre_name) " + "VALUES (?,?)";
        template.update(sql, new Object[]{t.getGenre_id(), t.getGenre_name()});

    }

    public void update(Genre t, String property, Object value) {
        String sql = "UPDATE tbl_genre SET " + property + " = ? WHERE genre_id = ?";
        template.update(sql, value, t.getGenre_id());
    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_genre WHERE genre_id = ?";
        template.update(sql, id);

    }

    public List<Genre> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_genre WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public Genre get(String property, Object value) {
        return getList(property, value).get(0);
    }

    public List<Genre> getAll(Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_genre where genre_name like ?";
            return template.query(getPage(sql), new Object[]{searchString},
                    this);
        } else {
            String sql = "select * from tbl_genre";
            return template.query(getPage(sql), this);
        }
    }

    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_genre";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String title) {
        String sql = "SELECT count(*) FROM tbl_genre WHERE genre_name like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%"}, Integer.class);
    }

    @Override
    public List<Genre> extractData(ResultSet rs) {
        List<Genre> list = new ArrayList<Genre>();
        try {
            while (rs.next()) {
                Genre g = new Genre();
                g.setGenre_id(rs.getInt("genre_id"));
                g.setGenre_name(rs.getString("genre_name"));
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Genre> getAll() {
        return getAll(0, null);
    }

}

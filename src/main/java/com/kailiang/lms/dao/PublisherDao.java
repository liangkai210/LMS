package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.Publisher;

public class PublisherDao extends BasicDao<Publisher> implements ResultSetExtractor<List<Publisher>> {

    public void add(Publisher t) {
        String sql = "INSERT INTO tbl_publisher(publisherId, publisherName, publisherAddress, publisherPhone) "
                + "VALUES (?,?,?,?)";
        template.update(sql, new Object[]{t.getPublisherId(), t.getPublisherName(), t.getPublisherAddress(),
                t.getPublisherPhone()});

    }

    public void update(Publisher t, String property, Object value) {
        String sql = "UPDATE tbl_publisher SET " + property + " = ? WHERE publisherId = ?";
        template.update(sql, value, t.getPublisherId());

    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_publisher WHERE publisherId = ?";
        template.update(sql, id);

    }

    public List<Publisher> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_publisher WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public Publisher get(String property, Object value) {
        return getList(property, value).get(0);
    }

    public Publisher getSingle(String property, Object value) {
        String sql = "SELECT * FROM tbl_publisher WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this).get(0);
    }

    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_publisher";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String publisherName) {
        String sql = "SELECT count(*) FROM tbl_publisher WHERE publisherName like ?";
        return template.queryForObject(sql, new Object[]{"%" + publisherName + "%"}, Integer.class);
    }

    public List<Publisher> getAll(Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_publisher where publisherName like ?";
            return template.query(getPage(sql), new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_publisher";
            return template.query(getPage(sql), this);
        }
    }

    @Override
    public List<Publisher> extractData(ResultSet rs) {
        List<Publisher> list = new ArrayList<Publisher>();
        try {
            while (rs.next()) {
                Publisher p = new Publisher();
                p.setPublisherId(rs.getInt("publisherId"));
                p.setPublisherName(rs.getString("publisherName"));
                p.setPublisherAddress(rs.getString("publisherAddress"));
                p.setPublisherPhone(rs.getString("publisherPhone"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Publisher> getAll() {
        return getAll(0, null);
    }

}

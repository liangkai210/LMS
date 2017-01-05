package com.kailiang.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.Borrower;

public class BorrowerDao extends BasicDao<Borrower> implements ResultSetExtractor<List<Borrower>> {

    public void add(Borrower t) {
        String sql = "INSERT INTO tbl_borrower(cardNo, name, address, phone, username, password) VALUES (?,?,?,?,?,?)";
        template.update(sql, new Object[]{t.getCardNo(), t.getName(), t.getAddress(), t.getPhone(), t.getUsername(), t.getPassword()});
    }

    public void update(Borrower t, String property, Object value) {
        String sql = "UPDATE tbl_borrower SET " + property + " = ? WHERE cardNo = ?";
        template.update(sql, value, t.getCardNo());

    }

    public Borrower getBorrowerByName(Object value) {
        String sql = "SELECT * FROM tbl_borrower WHERE username = ?";
        List<Borrower> list = template.query(sql, new Object[]{value}, this);
        return list.size() == 0 ? null : list.get(0);
    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_borrower WHERE cardNo = ?";
        template.update(sql, id);
    }

    public List<Borrower> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_borrower WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public List<Borrower> get(String property1, String property2, Object value1, Object value2) {
        String sql = "SELECT * FROM tbl_borrower WHERE " + property1 + " = ? AND " + property2 + " = ?";
        return template.query(sql, new Object[]{value1, value2}, this);
    }

    public Borrower get(String property, Object value) {
        return getList(property, value).get(0);
    }

    public List<Borrower> getAll(Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            return template.query("select * from tbl_borrower where name like ?", new Object[]{searchString}, this);
        } else {
            return template.query("select * from tbl_borrower", this);
        }
    }

    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_borrower";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String title) {
        String sql = "SELECT count(*) FROM tbl_borrower WHERE name like ?";
        return template.queryForObject(sql, new Object[]{"%" + title + "%"}, Integer.class);
    }

    @Override
    public List<Borrower> extractData(ResultSet rs) {
        List<Borrower> list = new ArrayList<Borrower>();
        try {
            while (rs.next()) {
                Borrower b = new Borrower();
                b.setCardNo(rs.getInt("cardNo"));
                b.setName(rs.getString("name"));
                b.setAddress(rs.getString("address"));
                b.setPhone(rs.getString("phone"));
                b.setUsername(rs.getString("username"));
                b.setPassword(rs.getString("password"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Borrower> getAll() {
        return getAll(0, null);
    }

}

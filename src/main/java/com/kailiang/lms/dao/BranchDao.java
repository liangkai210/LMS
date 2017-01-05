package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.kailiang.lms.bean.Branch;

public class BranchDao extends BasicDao<Branch> implements ResultSetExtractor<List<Branch>> {

    public void add(Branch t) {
        String sql = "INSERT INTO tbl_library_branch(branchId, branchName, branchAddress) " + "VALUES (?,?,?)";
        template.update(sql, new Object[]{t.getBranchId(), t.getBranchName(), t.getBranchAddress()});

    }

    public void update(Branch t, String property, Object value) {
        String sql = "UPDATE tbl_library_branch SET " + property + " = ? WHERE branchId = ?";
        template.update(sql, value, t.getBranchId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM tbl_library_branch WHERE branchId = ?";
        template.update(sql, id);

    }

    @Override
    public List<Branch> getAll() {
        return getAll(0, null);
    }

    public List<Branch> getList(String property, Object value) {
        String sql = "SELECT * FROM tbl_library_branch WHERE " + property + " = ?";
        return template.query(sql, new Object[]{value}, this);
    }

    public Branch get(String property, Object value) {
        return getList(property, value).get(0);
    }

    public List<Branch> getAll(Integer pageNo, String searchString) {
        setPageNo(pageNo);
        if (searchString != null && !searchString.isEmpty()) {
            searchString = "%" + searchString + "%";
            String sql = "select * from tbl_library_branch where branchName like ?";
            return template.query(getPage(sql), new Object[]{searchString}, this);
        } else {
            String sql = "select * from tbl_library_branch";
            return template.query(getPage(sql), this);
        }
    }


    public int getCount() {
        String sql = "SELECT count(*) FROM tbl_library_branch";
        return template.queryForObject(sql, Integer.class);
    }

    public int getCount(String branchName) {
        String sql = "SELECT count(*) FROM tbl_library_branch WHERE branchName like ?";
        return template.queryForObject(sql, new Object[]{"%" + branchName + "%"}, Integer.class);
    }


    @Override
    public List<Branch> extractData(ResultSet rs) {
        List<Branch> list = new ArrayList<Branch>();
        try {
            while (rs.next()) {
                Branch b = new Branch();
                b.setBranchId(rs.getInt("branchId"));
                b.setBranchName(rs.getString("branchName"));
                b.setBranchAddress(rs.getString("branchAddress"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}

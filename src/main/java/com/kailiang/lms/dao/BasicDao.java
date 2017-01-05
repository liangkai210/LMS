package com.kailiang.lms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class BasicDao<T> implements Dao<T>, ResultSetExtractor<List<T>> {

	@Autowired
	JdbcTemplate template;

	protected Integer pageNo;
	protected Integer pageSize = 10;
	protected List<T> list;
	protected Integer totalItemNumber;

	public Integer getPageNo() {
		if (pageNo < 0) {
			pageNo = 1;
		}
		if (pageNo > getTotalPageNumber()) {
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalPageNumber() {
		int totalPageNumber = totalItemNumber / pageSize;
		if (totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}

	public String getPage(String sql) {
		if (pageNo != null && pageNo > 0) {
			int limit = (pageNo - 1) * pageSize;
			sql += " LIMIT " + limit + ", " + pageSize;
		}
		return sql;
	}

	public void setTotalItemNumber(Integer totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	public Integer getTotalItemNumber() {
		return totalItemNumber;
	}

	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNumber()) {
			return true;
		}
		return false;
	}

	public boolean isHasPrev() {
		if (getPageNo() > 1) {
			return true;
		}
		return false;
	}

	public int getPrevPage() {
		if (isHasPrev()) {
			return pageNo - 1;
		}
		return getPageNo();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		}
		return getPageNo();
	}
}

package com.kailiang.lms.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

	public void add(T t);

	public void update(T t, String property, Object value)
			throws SQLException, IllegalAccessException, InvocationTargetException;

	public void delete(int id) throws SQLException;

	public List<T> getAll()
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException;

	public List<T> getList(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException;

	public T get(String property, Object value)
			throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException;

}

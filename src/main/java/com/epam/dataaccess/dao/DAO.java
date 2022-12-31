package com.epam.dataaccess.dao;

import java.util.List;

import com.epam.exception.dao.DAOException;

public interface DAO<T> {
	T get(int id) throws DAOException;

	List<T> getAll() throws DAOException;

	int insert(T t) throws DAOException;

	int update(T t) throws DAOException;

	int delete(T t) throws DAOException;
}

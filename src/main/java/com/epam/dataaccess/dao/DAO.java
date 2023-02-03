package com.epam.dataaccess.dao;

import java.util.List;

import com.epam.exception.dao.DAOException;

/**
 * General DAO interface that contains methods that every DAO should contain.
 * @author ruslan
 *
 * @param <T> type
 */

public interface DAO<T> {
	
	/**
	 *  Return record by its ID.
	 * @param id id of the record.
	 * @return record.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	T get(int id) throws DAOException;

	/**
	 * Return all records.
	 * @return All records.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	List<T> getAll() throws DAOException;

	/**
	 * Insert new record to the persistence layer.
	 * @param t record to add.
	 * @return 1 if inserted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	int insert(T t) throws DAOException;

	/**
	 * Update record in the persistence layer.
	 * @param t record to update.
	 * @return 1 if updated, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	int update(T t) throws DAOException;

	/**
	 * Delete record from the persistence layer.
	 * @param t record to delete.
	 * @return 1 if deleted, 0 if not.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	int delete(T t) throws DAOException;
}

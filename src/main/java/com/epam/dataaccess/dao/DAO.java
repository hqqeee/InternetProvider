package com.epam.dataaccess.dao;

import java.util.List;

import com.epam.exception.dao.DAOException;

/**
 * 
 * The {@code DAO} interface defines the standard operations that a Data Access
 * Object (DAO) should contain.
 * 
 * @param <T> The type of object the DAO will operate on.
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public interface DAO<T> {

	/**
	 * 
	 * Retrieves a record from the persistence layer by its ID.
	 * 
	 * @param id The ID of the record to retrieve.
	 * @return The record with the specified ID.
	 * @throws DAOException If a SQL Exception occurs.
	 */
	T get(int id) throws DAOException;

	/**
	 * 
	 * Retrieves all records from the persistence layer.
	 * 
	 * @return A list containing all records in the persistence layer.
	 * @throws DAOException If a SQL Exception occurs.
	 */
	List<T> getAll() throws DAOException;

	/**
	 * 
	 * Inserts a new record into the persistence layer.
	 * 
	 * @param t The record to insert.
	 * @return 1 if the record was inserted, 0 if not.
	 * @throws DAOException If a SQL Exception occurs.
	 */
	int insert(T t) throws DAOException;

	/**
	 * 
	 * Updates an existing record in the persistence layer.
	 * 
	 * @param t The record to update.
	 * @return 1 if the record was updated, 0 if not.
	 * @throws DAOException If a SQL Exception occurs.
	 */
	int update(T t) throws DAOException;

	/**
	 * 
	 * Deletes a record from the persistence layer.
	 * 
	 * @param t The record to delete.
	 * @return 1 if the record was deleted, 0 if not.
	 * @throws DAOException If a SQL Exception occurs.
	 */
	int delete(T t) throws DAOException;
}

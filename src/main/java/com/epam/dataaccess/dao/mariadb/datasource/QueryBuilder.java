package com.epam.dataaccess.dao.mariadb.datasource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 
 * The QueryBuilder class is used to construct and execute SQL queries. It
 * provides a convenient interface to build, set parameters and execute various
 * types of SQL statements. The queries are executed using a MariaDB connection
 * pool.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class QueryBuilder {
	private ConnectionPool connectionPool;
	private Connection connection;
	private Deque<PreparedStatement> preparedStatements;
	private int fieldNumber = 1;
	/**
	 * 
	 * Constructs a new QueryBuilder object and initializes the connection to
	 * MariaDB.
	 * 
	 * @throws SQLException if a database access error occurs or the url is null
	 */
	public QueryBuilder() throws SQLException {
		connectionPool = ConnectionPool.getInstance();
		preparedStatements = new ArrayDeque<>();
		connection = connectionPool.getConnection();
	}

	/**
	 * 
	 * Adds a new prepared statement to the queue of prepared statements.
	 * 
	 * @param query the SQL query that is executed when the executeQuery() or
	 *              executeUpdate() method is called.
	 * @return the current {@code QueryBuilder} instance
	 * @throws SQLException if a database access error occurs or the statement could
	 *                      not be prepared.
	 */
	public QueryBuilder addPreparedStatement(String query) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		preparedStatements.push(statement);
		fieldNumber = 1;
		return this;
	}

	/**
	 * 
	 * Sets the integer value of the next placeholder in the current prepared
	 * statement.
	 * 
	 * @param value the value to be set
	 * @return the current QueryBuilder instance
	 * @throws SQLException if a database access error occurs.
	 */
	public QueryBuilder setIntField(int value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setInt(fieldNumber++, value);
		return this;
	}

	/**
	 * 
	 * Sets the string value of the next placeholder in the current prepared
	 * statement.
	 * 
	 * @param value the value to be set
	 * @return the current QueryBuilder instance
	 * @throws SQLException if a database access error occurs.
	 */

	public QueryBuilder setStringField(String value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setString(fieldNumber++, value);
		return this;
	}

	/**
	 * 
	 * Sets the big decimal value of the next placeholder in the current prepared
	 * statement.
	 * 
	 * @param value the value to be set
	 * @return the current QueryBuilder instance
	 * @throws SQLException if a database access error occurs.
	 */
	public QueryBuilder setBigDecimalField(BigDecimal value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setBigDecimal(fieldNumber++, value);
		return this;
	}

	/**
	 * 
	 * Sets the boolean value of the next placeholder in the current prepared
	 * statement.
	 * 
	 * @param value the value to be set
	 * @return the current QueryBuilder instance
	 * @throws SQLException if a database access error occurs.
	 */
	public QueryBuilder setBooleanField(boolean value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setBoolean(fieldNumber++, value);
		return this;
	}

	/**
	 * 
	 * Sets the timestamp value of the next placeholder in the current prepared
	 * statement.
	 * 
	 * @param value the value to be set
	 * @return the current QueryBuilder instance
	 * @throws SQLException if a database access error occurs.
	 */
	public QueryBuilder setTimestamField(Timestamp value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setTimestamp(fieldNumber++, value);
		return this;
	}

	/**
	 * 
	 * Executes the current SQL query and returns the ResultSet.
	 * 
	 * @return ResultSet of the executed query
	 * @throws SQLException if a database access error occurs or this method is
	 *                      called on a closed statement
	 */
	public ResultSet executeQuery() throws SQLException {
		ResultSet resultSet = preparedStatements.pop().executeQuery();
		close();
		return resultSet;
	}

	/**
	 * 
	 * Executes the current prepared statement as an update and returns the number
	 * of affected rows. This method closes the connection and any prepared
	 * statements after execution.
	 * 
	 * @return the number of affected rows
	 * @throws SQLException if a database access error occurs or the statement could
	 *                      not be executed.
	 */
	public int executeUpdate() throws SQLException {
		int result = 0;
		connection.setAutoCommit(false);
		while (!preparedStatements.isEmpty()) {
			result += preparedStatements.pop().executeUpdate();
		}
		connection.commit();
		close();
		return result;
	}

	/**
	 * 
	 * Closes the prepared statements and releases the connection from the
	 * connection pool.
	 * 
	 * @throws SQLException if a database access error occurs
	 */
	private void close() throws SQLException {
		while (!preparedStatements.isEmpty()) {
			preparedStatements.pop().close();
		}
		connectionPool.releaseConnection(connection);
	}
}

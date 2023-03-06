package com.epam.dataaccess.dao.mariadb.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enum representing the connection pool for accessing MariaDB data source. It
 * uses a singleton pattern to ensure that only one instance of this pool exists
 * at a time.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public enum ConnectionPool {
	INSTANCE;

	private final Logger logger = LogManager.getLogger(ConnectionPool.class);

	private DataSource dataSource = null;

	private ConnectionPool() {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/internetprovider");
		} catch (NamingException ex) {
			logger.error("Cannot create connection pool.", ex);
			System.exit(1);
		}
	}

	/**
	 * Returns the single instance of this connection pool.
	 *
	 * @return the single instance of this connection pool.
	 */
	public static ConnectionPool getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets a connection from the data source.
	 *
	 * @return a {@link java.sql.Connection} object representing a database
	 *         connection.
	 * @throws SQLException if a database access error occurs.
	 */
	public Connection getConnection() throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException ex) {
			logger.error("Cannot get connection.", ex);
			throw new SQLException(ex);
		}
	}

	/**
	 * Releases a connection back to the pool.
	 *
	 * @param c the connection to be released.
	 * @return true if the connection is successfully released, false otherwise.
	 * @throws SQLException if a database access error occurs.
	 */
	public boolean releaseConnection(Connection c) throws SQLException {
		if (c != null) {
			try {
				c.close();
				return true;
			} catch (SQLException ex) {
				logger.error("Cannot release connection.", ex);
				throw new SQLException(ex);
			}
		}
		return false;
	}

}

package com.epam.dataaccess.dao.mariadb.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	public static ConnectionPool getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException ex) {
			logger.error("Cannot get connection.", ex);
			throw new SQLException(ex);
		}
	}

	public boolean releaseConnection(Connection c) throws SQLException {
		if (c != null) {
			try {
				c.close();
				c=null;
				return true;
			} catch (SQLException ex) {
				logger.error("Cannot release connection.", ex);
				throw new SQLException(ex);
			}
		}
		return false;
	}

}

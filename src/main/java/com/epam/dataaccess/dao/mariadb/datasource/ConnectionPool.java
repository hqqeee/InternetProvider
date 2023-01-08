package com.epam.dataaccess.dao.mariadb.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public enum ConnectionPool {
	INSTANCE;

	private DataSource dataSource = null; 

	private ConnectionPool() {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/internetprovider");
		} catch (NamingException ex) {
			Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static ConnectionPool getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public boolean releaseConnection(Connection c) {
		if (c != null) {
			try {
				c.close();
				c=null;
				return true;
			} catch (SQLException ex) {
				Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);

			}
		}
		return false;
	}

}

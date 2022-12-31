package com.epam.dataaccess.dao.mariadb.datasource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Stack;

public class QueryBuilder {
	private ConnectionPool connectionPool = ConnectionPool.getInstance();
	private Connection connection;
	private Stack<PreparedStatement> preparedStatements;
	private int fieldNumber = 1;

	public QueryBuilder() throws SQLException {
		preparedStatements = new Stack<>();
		connection = connectionPool.getConnection();
	}

	public QueryBuilder addPreparedStatement(String query) throws SQLException {
		preparedStatements.push(connection.prepareStatement(query));
		fieldNumber = 1;
		return this;
	}

	public QueryBuilder setIntField(int value) throws SQLException {
		preparedStatements.peek().setInt(fieldNumber++, value);
		return this;
	}

	public QueryBuilder setStringField(String value) throws SQLException {
		preparedStatements.peek().setString(fieldNumber++, value);
		return this;
	}

	public QueryBuilder setBigDecimalField(BigDecimal value) throws SQLException {
		preparedStatements.peek().setBigDecimal(fieldNumber++, value);
		return this;
	}
	
	public QueryBuilder setBooleanField(boolean value) throws SQLException {
		preparedStatements.peek().setBoolean(fieldNumber++, value);
		return this;
	}
	
	public QueryBuilder setTimestamField(Timestamp value) throws SQLException {
		preparedStatements.peek().setTimestamp(fieldNumber++, value);
		return this;
	}
	
	public ResultSet executeQuery() throws SQLException {
		ResultSet resultSet = preparedStatements.peek().executeQuery();
		close();
		return resultSet;
	}

	public int executeUpdate() throws SQLException {
		int result = 0;
		connection.setAutoCommit(false);
		while(!preparedStatements.isEmpty()) {
			result += preparedStatements.pop().executeUpdate();
		}
		connection.commit();

		close();
		return result;
	}

	private void close() throws SQLException {
		while(!preparedStatements.isEmpty()) {
			preparedStatements.pop().close();
		}
		connectionPool.releaseConnection(connection);

	}
}

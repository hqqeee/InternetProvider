package com.epam.dataaccess.dao.mariadb.datasource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;

public class QueryBuilder {
	private ConnectionPool connectionPool;
	private Connection connection;
	private Deque<PreparedStatement> preparedStatements;

	public QueryBuilder() throws SQLException {
		connectionPool = ConnectionPool.getInstance();
		preparedStatements = new ArrayDeque<>();
		connection = connectionPool.getConnection();
	}

	public QueryBuilder addPreparedStatement(String query) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		preparedStatements.push(statement);
		return this;
	}

	public QueryBuilder setIntField(int value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setInt(statement.getParameterMetaData().getParameterCount(), value);
		return this;
	}

	public QueryBuilder setStringField(String value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setString(statement.getParameterMetaData().getParameterCount(), value);
		return this;
	}

	public QueryBuilder setBigDecimalField(BigDecimal value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setBigDecimal(statement.getParameterMetaData().getParameterCount(), value);
		return this;
	}

	public QueryBuilder setBooleanField(boolean value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setBoolean(statement.getParameterMetaData().getParameterCount(), value);
		return this;
	}

	public QueryBuilder setTimestamField(Timestamp value) throws SQLException {
		PreparedStatement statement = preparedStatements.peek();
		statement.setTimestamp(statement.getParameterMetaData().getParameterCount(), value);
		return this;
	}

	public ResultSet executeQuery() throws SQLException {
		ResultSet resultSet = preparedStatements.pop().executeQuery();
		close();
		return resultSet;
	}

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

	private void close() throws SQLException {
		while (!preparedStatements.isEmpty()) {
			preparedStatements.pop().close();
		}
		connectionPool.releaseConnection(connection);
	}
}

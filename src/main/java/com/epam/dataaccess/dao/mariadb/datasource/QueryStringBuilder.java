package com.epam.dataaccess.dao.mariadb.datasource;

public class QueryStringBuilder {
	private StringBuilder query = new StringBuilder();

	public String getQuery() {
		return query.toString();
	}

	public QueryStringBuilder addSelect(String fields) {
		query.append("SELECT " + fields);
		return this;
	}

	public QueryStringBuilder addFrom(String table) {
		query.append(" FROM " + table);
		return this;
	}

	public QueryStringBuilder addInsert(String table, String fields) {
		query.append("INSERT INTO " + table + " (" + fields + ")");
		return this;
	}

	public QueryStringBuilder addValues(String values) {
		query.append(" VALUES (" + values + ")");
		return this;
	}
	
	public QueryStringBuilder addUpdate(String table) {
		query.append("UPDATE " + table);
		return this;
	}
	
	public QueryStringBuilder addSet(String fieldAndValue) {
		query.append(" SET " + fieldAndValue);
		return this;
	}
	
	public QueryStringBuilder addDelete() {
		query.append("DELETE ");
		return this;
	}
	
	public QueryStringBuilder addLike() {
		query.append(" LIKE ");
		return this;
	}

	public QueryStringBuilder addWhere(String statement) {
		query.append(" WHERE " + statement);
		return this;
	}

	public QueryStringBuilder addOr(String statement) {
		query.append(" OR " + statement);
		return this;
	}

	public QueryStringBuilder addAnd(String statement) {
		query.append(" AND " + statement);
		return this;
	}

	public QueryStringBuilder addJoin(String criteria, String on) {
		query.append(" JOIN " + criteria + " ON " + on);
		return this;
	}

	public QueryStringBuilder addInnerJoin(String table, String on) {
		query.append(" INNER JOIN " + table + " ON " + on);
		return this;
	}

	public QueryStringBuilder addGroupBy(String criteria) {
		query.append(" GROUP BY " + criteria);
		return this;
	}

	public QueryStringBuilder addOrderBy(String criteria) {
		query.append(" ORDER BY " + criteria);
		return this;
	}
	
	public QueryStringBuilder addCount(String field) {
		query.append(" COUNT(" + field +")");
		return this;
	}
	
	public QueryStringBuilder addLimit() {
		query.append(" LIMIT ?, ?");
		return this;
	}
}

package com.epam.dataaccess.dao.mariadb.impl;

import javax.management.Query;

import com.epam.dataaccess.dao.mariadb.datasource.QueryStringBuilder;

public final class MariaDBConstants {
	private MariaDBConstants() {
	};

	/** USER FIELDS */
	public static final String USER_TABLE = "user";
	public static final String USER_ID_FIELD = "user.id";
	public static final String USER_PASSWORD_FIELD = "user.password";
	public static final String USER_SALT_FIELD = "user.salt";
	public static final String USER_LOGIN_FIELD = "user.login";
	public static final String USER_ROLE_ID_FIELD = "user.role_id";
	public static final String USER_BLOCKED_FIELD = "user.blocked";
	public static final String USER_EMAIL_FIELD = "user.email";
	public static final String USER_FIRST_NAME_FIELD = "user.first_name";
	public static final String USER_LAST_NAME_FIELD = "user.last_name";
	public static final String USER_CITY_FIELD = "user.city";
	public static final String USER_ADDRESS_FIELD = "user.address";
	public static final String USER_BALANCE_FIELD = "user.balance";
	/** TARIFF FIELD */
	public static final String TARIFF_TABLE = "tariff";
	public static final String TARIFF_ID_FIELD = "tariff.id";
	public static final String TARIFF_NAME_FIELD = "tariff.name";
	public static final String TARIFF_DESCRIPTION_FIELD = "tariff.description";
	public static final String TARIFF_PAYMENT_PERIOD_FIELD = "tariff.payment_period_days";
	public static final String TARIFF_RATE_FIELD = "tariff.rate";
	public static final String TARIFF_SERVICE_ID_FIELD = "tariff.service_id";
	/** USER_HAS_TARIFF FIELDS */
	public static final String USER_HAS_TARIFF_TABLE = "user_has_tariff";
	public static final String USER_HAS_TARIFF_USER_ID_FIELD = "user_has_tariff.user_id";
	public static final String USER_HAS_TARIFF_TARIFF_ID_FIELD = "user_has_tariff.tariff_id";
	public static final String USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD = "user_has_tariff.days_until_next_payment";
	/** TRANSACTION FIELDS */
	public static final String TRANSACTION_TABLE = "transaction";
	public static final String TRANSACTION_ID_FIELD = "transaction.id";
	public static final String TRANSACTION_USER_ID_FIELD = "transaction.user_id";
	public static final String TRANSACTION_TIMESTAMP_FIELD = "transaction.timestamp";
	public static final String TRANSACTION_AMOUNT_FIELD = "transaction.amount";
	public static final String TRANSACTION_DESCRIPTION_FIELD = "transaction.description";
	/** SERVICE FIELDS */
	public static final String SERVICE_TABLE = "service";
	public static final String SERVICE_ID_FIELD = "service.id";
	public static final String SERVICE_NAME_FIELD = "service.name";
	public static final String SERVICE_DESCRIPTION_FIELD = "service.description";
	/** ROLE FIELDS */
	public static final String ROLE_TABLE = "role";
	public static final String ROLE_ID_FIELD = "role.id";
	public static final String ROLE_NAME_FIELD = "role.name";
	public static final String ROLE_DESCRIPTION_FIELD = "role.description";

	/** USER QUERIES */
	public static final String GET_USER_BY_ID = new QueryStringBuilder()
			.addSelect(USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD
					+ ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", "
					+ USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", "
					+ USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD)
			.addFrom(USER_TABLE).addWhere(USER_ID_FIELD + "=?").getQuery();
	public static final String GET_ALL_USERS = new QueryStringBuilder().addSelect(USER_ID_FIELD + ", "
			+ USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", "
			+ USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD
			+ ", " + USER_CITY_FIELD + ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD).addFrom(USER_TABLE)
			.getQuery();
	public static final String ADD_USER = new QueryStringBuilder().addInsert(USER_TABLE,
			USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", "
					+ USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", "
					+ USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", " + USER_ADDRESS_FIELD + ", "
					+ USER_BALANCE_FIELD)
			.addValues("?,?,?,?,?,?,?,?,?,?,?").getQuery();
	public static final String UPDATE_USER = new QueryStringBuilder().addUpdate(USER_TABLE)
			.addSet(USER_PASSWORD_FIELD + " = ? " + USER_SALT_FIELD + " = ? " + USER_LOGIN_FIELD + " = ? "
					+ USER_ROLE_ID_FIELD + " = ? " + USER_BLOCKED_FIELD + " = ? " + USER_EMAIL_FIELD + " = ? "
					+ USER_FIRST_NAME_FIELD + " = ? " + USER_LAST_NAME_FIELD + " = ? " + USER_CITY_FIELD + " = ? "
					+ USER_ADDRESS_FIELD + " = ? " + USER_BALANCE_FIELD)
			.addWhere(USER_ID_FIELD + "= ?").getQuery();
	public static final String DELETE_USER = new QueryStringBuilder().addDelete().addFrom(USER_TABLE)
			.addWhere(USER_ID_FIELD + " = ?").getQuery();

	/** */

	public static final String GET_SALT_BY_LOGIN = new QueryStringBuilder().addSelect(USER_SALT_FIELD)
			.addFrom(USER_TABLE).addWhere(USER_LOGIN_FIELD + " = ?").getQuery();
	public static final String GET_USER_BY_LOGIN_AND_PASSWORD = new QueryStringBuilder()
			.addSelect(USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD
					+ ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", "
					+ USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", "
					+ USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD)
			.addFrom(USER_TABLE).addWhere(USER_LOGIN_FIELD + " = ? AND " + USER_PASSWORD_FIELD + " = ?").getQuery();
	public static final String CHANGE_BLOCK_STATUS = new QueryStringBuilder().addUpdate(USER_TABLE)
			.addSet(USER_BLOCKED_FIELD + " =? ").addWhere(USER_ID_FIELD + "=? ").getQuery();
	public static final String ADD_BALANCE_TO_USER = new QueryStringBuilder().addUpdate(USER_TABLE)
			.addSet(USER_BALANCE_FIELD + " = " + USER_BALANCE_FIELD + " + ?").addWhere(USER_ID_FIELD + " = ?")
			.getQuery();
//	public static final String ADD_TARIFF_TO_USER = new QueryStringBuilder()
//			.addInsert(USER_HAS_TARIFF_TABLE, USER_HAS_TARIFF_USER_ID_FIELD + ", " + USER_HAS_TARIFF_TARIFF_ID_FIELD + ", " 
//	+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD).getQuery() + "(" + new QueryStringBuilder()
//			.addSelect("?,?, " + TARIFF_PAYMENT_PERIOD_FIELD).addFrom(TARIFF_TABLE).addWhere(TARIFF_ID_FIELD + " = ?)")
//			.getQuery();
	public static final String ADD_TARIFF_TO_USER = new QueryStringBuilder()
			.addInsert(USER_HAS_TARIFF_TABLE, USER_HAS_TARIFF_USER_ID_FIELD + ", " + USER_HAS_TARIFF_TARIFF_ID_FIELD + ", " 
	+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD).addValues("?,?,?").getQuery();
	public static final String GET_ALL_SUBSCRIBERS = new QueryStringBuilder()
			.addSelect(USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD
					+ ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", "
					+ USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", "
					+ USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD)
			.addFrom(USER_TABLE).addWhere(USER_ROLE_ID_FIELD + " = ?").getQuery();
	public static final String GET_ALL_UNBLOCKED_SUBSCRIBERS = new QueryStringBuilder()
			.addSelect(USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD
					+ ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", "
					+ USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", "
					+ USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD)
			.addFrom(USER_TABLE).addWhere(USER_ROLE_ID_FIELD + " = ?").addAnd(USER_BLOCKED_FIELD + " = false").getQuery();
	public static final String GET_USERS_FOR_VIEW_1 = new QueryStringBuilder()
			.addSelect(USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD
					+ ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", "
					+ USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", "
					+ USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD)
			.addFrom(USER_TABLE)
			.addWhere(USER_ROLE_ID_FIELD + " = ?").addAnd("LOWER(" + USER_LOGIN_FIELD + ")")
			.addLike().getQuery();
	public static final String GET_SUBSCRIBER_COUNT = new QueryStringBuilder().addSelect("").addCount("*")
			.addFrom(USER_TABLE).addWhere(USER_ROLE_ID_FIELD + "= 2 ").addAnd("LOWER(" + USER_LOGIN_FIELD + ")")
			.addLike().getQuery();
	public static final String CHANGE_USER_PASSWORD = new QueryStringBuilder().addUpdate(USER_TABLE)
			.addSet(USER_PASSWORD_FIELD + " = ?, " + USER_SALT_FIELD + " =? ").addWhere(USER_ID_FIELD + " = ?")
			.getQuery();
	public static final String REMOVE_USER_TARIFF = new QueryStringBuilder().addDelete().addFrom(USER_HAS_TARIFF_TABLE)
			.addWhere(USER_HAS_TARIFF_USER_ID_FIELD + " = ? ").addAnd(USER_HAS_TARIFF_TARIFF_ID_FIELD + " = ?")
			.getQuery();
	public static final String GET_UNBLOCKED_AND_PAYMENT_NEEDED_SUBSCRIBERS = new QueryStringBuilder()
			.addSelect("DISTINCT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD
					+ ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", " + USER_EMAIL_FIELD + ", "
					+ USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD + ", "
					+ USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD)
			.addFrom(USER_TABLE).addInnerJoin(USER_HAS_TARIFF_TABLE, USER_ID_FIELD + " = " + USER_HAS_TARIFF_USER_ID_FIELD)
			.addWhere(USER_BLOCKED_FIELD + " = false ").addAnd(USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " = 0").getQuery();
	public static final String GET_USER_BALANCE = new QueryStringBuilder().addSelect(USER_BALANCE_FIELD).addFrom(USER_TABLE)
			.addWhere(USER_ID_FIELD + " = ?").getQuery();
	public static final String GET_USER_STATUS = new QueryStringBuilder().addSelect(USER_BLOCKED_FIELD).addFrom(USER_TABLE)
			.addWhere(USER_ID_FIELD + " = ?").getQuery();
	
	
	/** TARIFF QUERIES */
	public static final String GET_TARIFF_BY_ID = new QueryStringBuilder()
			.addSelect(TARIFF_ID_FIELD + ", " + TARIFF_NAME_FIELD + ", " + TARIFF_DESCRIPTION_FIELD + ", "
					+ TARIFF_PAYMENT_PERIOD_FIELD + ", " + TARIFF_RATE_FIELD + ", " + TARIFF_SERVICE_ID_FIELD)
			.addFrom(TARIFF_TABLE).addWhere(TARIFF_ID_FIELD + " = ? ").getQuery();
	public static final String GET_ALL_TARIFFS = new QueryStringBuilder()
			.addSelect(TARIFF_ID_FIELD + ", " + TARIFF_NAME_FIELD + ", " + TARIFF_DESCRIPTION_FIELD + ", "
					+ TARIFF_PAYMENT_PERIOD_FIELD + ", " + TARIFF_RATE_FIELD + ", " + TARIFF_SERVICE_ID_FIELD)
			.addFrom(TARIFF_TABLE).getQuery();
	public static final String ADD_TARIFF = new QueryStringBuilder()
			.addInsert(TARIFF_TABLE, TARIFF_NAME_FIELD + ", " + TARIFF_DESCRIPTION_FIELD + ", "
					+ TARIFF_PAYMENT_PERIOD_FIELD + ", " + TARIFF_RATE_FIELD + ", " + TARIFF_SERVICE_ID_FIELD)
			.addValues("?, ?, ?, ?, ?").getQuery();
	public static final String UPDATE_TARIFF = new QueryStringBuilder()
			.addUpdate(TARIFF_TABLE).addSet(TARIFF_NAME_FIELD + " = ?, " + TARIFF_DESCRIPTION_FIELD + " = ?, "
					+ TARIFF_PAYMENT_PERIOD_FIELD + " = ?, " + TARIFF_RATE_FIELD + " = ?, " + TARIFF_SERVICE_ID_FIELD + " = ? ")
			.addWhere(TARIFF_ID_FIELD + " = ?").getQuery();
	public static final String DELETE_TARIFF = new QueryStringBuilder().addDelete().addFrom(TARIFF_TABLE)
			.addWhere(TARIFF_ID_FIELD + " = ?").getQuery();
	public static final String UPDATE_DAYS_UNTIL_PAYMENT_FOR_UNBLOCKED_USERS = new QueryStringBuilder()
			.addUpdate(USER_HAS_TARIFF_TABLE).addLeftJoin(USER_TABLE, USER_ID_FIELD + " = " + USER_HAS_TARIFF_USER_ID_FIELD)
			.addSet(USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " = " + USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " - 1")
			.addWhere(USER_BLOCKED_FIELD + " = false").addAnd(USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " > 0").getQuery();
	public static final String GET_USERS_TARIFFS_BY_ID = new QueryStringBuilder()
			.addSelect(TARIFF_ID_FIELD + ", " + TARIFF_NAME_FIELD + ", " + TARIFF_DESCRIPTION_FIELD + ", "
					+ TARIFF_PAYMENT_PERIOD_FIELD + ", " + TARIFF_RATE_FIELD + ", " + TARIFF_SERVICE_ID_FIELD)
			.addFrom(TARIFF_TABLE)
			.addInnerJoin(USER_HAS_TARIFF_TABLE, USER_HAS_TARIFF_TARIFF_ID_FIELD + "=" + TARIFF_ID_FIELD)
			.addWhere(USER_HAS_TARIFF_USER_ID_FIELD + " =?").getQuery();
	public static final String GET_USERS_TARIFFS_BY_ID_WITH_DAY_TO_PAY = new QueryStringBuilder()
			.addSelect(TARIFF_ID_FIELD + ", " + TARIFF_NAME_FIELD + ", " + TARIFF_DESCRIPTION_FIELD + ", "
					+ TARIFF_PAYMENT_PERIOD_FIELD + ", " + TARIFF_RATE_FIELD + ", " + TARIFF_SERVICE_ID_FIELD 
					+ ", " + USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD)
			.addFrom(TARIFF_TABLE)
			.addInnerJoin(USER_HAS_TARIFF_TABLE, USER_HAS_TARIFF_TARIFF_ID_FIELD + "=" + TARIFF_ID_FIELD)
			.addWhere(USER_HAS_TARIFF_USER_ID_FIELD + " =?").getQuery();
	public static final String GET_USERS_UNPAID_TARIFFS = new QueryStringBuilder()
			.addSelect(TARIFF_ID_FIELD + ", " + TARIFF_NAME_FIELD + ", " + TARIFF_DESCRIPTION_FIELD + ", "
					+ TARIFF_PAYMENT_PERIOD_FIELD + ", " + TARIFF_RATE_FIELD + ", " + TARIFF_SERVICE_ID_FIELD)
			.addFrom(TARIFF_TABLE).addInnerJoin(USER_HAS_TARIFF_TABLE, TARIFF_ID_FIELD + " = " + USER_HAS_TARIFF_TARIFF_ID_FIELD)
			.addWhere(USER_HAS_TARIFF_USER_ID_FIELD + " = ? ").addAnd(USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " = 0")
			.getQuery();
	public static final String GET_COUNT_OF_TARIFFS = new QueryStringBuilder().addSelect("").addCount("*")
			.addFrom(TARIFF_TABLE).getQuery();
	public static final String GET_COUNT_OF_TARIFF_BY_SERVICE_ID = new QueryStringBuilder().addSelect("").addCount("*")
			.addFrom(TARIFF_TABLE).addWhere(TARIFF_SERVICE_ID_FIELD + "= ? ").getQuery();
	public static final String FILTER_TARIFF_BY_SERVICE_ID = new QueryStringBuilder()
			.addWhere(TARIFF_SERVICE_ID_FIELD + "= ? ").getQuery();
	public static final String ORDER_BY = new QueryStringBuilder().addOrderBy("").getQuery();
	public static final String LIMIT = new QueryStringBuilder().addLimit().getQuery();

	/** TRANSACTION QUERIES */
	public static final String GET_TRANSACTION_BY_ID = new QueryStringBuilder()
			.addSelect(TRANSACTION_ID_FIELD + ", " + TRANSACTION_USER_ID_FIELD + ", " + TRANSACTION_TIMESTAMP_FIELD
					+ ", " + TRANSACTION_AMOUNT_FIELD + ", " + TRANSACTION_DESCRIPTION_FIELD)
			.addFrom(TRANSACTION_TABLE).addWhere(TRANSACTION_ID_FIELD + " = ?").getQuery();
	public static final String GET_ALL_TRANSACTIONS = new QueryStringBuilder()
			.addSelect(TRANSACTION_ID_FIELD + ", " + TRANSACTION_USER_ID_FIELD + ", " + TRANSACTION_TIMESTAMP_FIELD
					+ ", " + TRANSACTION_AMOUNT_FIELD + ", " + TRANSACTION_DESCRIPTION_FIELD)
			.addFrom(TRANSACTION_TABLE).getQuery();
	public static final String ADD_TRANSACTION = new QueryStringBuilder()
			.addInsert(TRANSACTION_TABLE,
					TRANSACTION_USER_ID_FIELD + ", " + TRANSACTION_AMOUNT_FIELD + ", " + TRANSACTION_DESCRIPTION_FIELD)
			.addValues("?,?,?").getQuery();
	public static final String UPDATE_TRANSACTION = new QueryStringBuilder().addUpdate(TRANSACTION_TABLE)
			.addSet(TRANSACTION_USER_ID_FIELD + " = ?, " + TRANSACTION_TIMESTAMP_FIELD + " = ?, "
					+ TRANSACTION_AMOUNT_FIELD + " = ?, " + TRANSACTION_DESCRIPTION_FIELD + " = ? ")
			.addWhere(TRANSACTION_ID_FIELD + " = ?").getQuery();
	public static final String DELETE_TRANSACTION = new QueryStringBuilder().addDelete().addFrom(TRANSACTION_TABLE)
			.addWhere(TRANSACTION_ID_FIELD + " = ?").getQuery();
	public static final String GET_USERS_TRANSACTION = new QueryStringBuilder()
			.addSelect(TRANSACTION_ID_FIELD + ", " + TRANSACTION_USER_ID_FIELD + ", " + TRANSACTION_TIMESTAMP_FIELD
					+ ", " + TRANSACTION_AMOUNT_FIELD + ", " + TRANSACTION_DESCRIPTION_FIELD)
			.addFrom(TRANSACTION_TABLE)
			.addWhere(TRANSACTION_USER_ID_FIELD + " = ? ORDER BY " + TRANSACTION_TIMESTAMP_FIELD + " DESC ").addLimit()
			.getQuery();
	public static final String GET_TRANSACTION_NUMBER_OF_USER = new QueryStringBuilder().addSelect("").addCount("*")
			.addFrom(TRANSACTION_TABLE).addWhere(TRANSACTION_USER_ID_FIELD + "= ? ").getQuery();
	public static final String CHARGE_FOR_USING_TARIFF = new QueryStringBuilder().addUpdate(USER_TABLE)
			.addSet(USER_BALANCE_FIELD + " = " + USER_BALANCE_FIELD + " - (" +
			new QueryStringBuilder().addSelect(TARIFF_RATE_FIELD).addFrom(TARIFF_TABLE).addWhere(TARIFF_ID_FIELD + " = ?").getQuery() + ")")
			.addWhere(USER_ID_FIELD + " = ?").getQuery();
	public static final String UPDATE_DAYS_UNTIL_NEXT_PAYMENT = new QueryStringBuilder().addUpdate(USER_HAS_TARIFF_TABLE)
			.addSet(USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " = (" + 
					new QueryStringBuilder().addSelect(TARIFF_PAYMENT_PERIOD_FIELD).addFrom(TARIFF_TABLE).addWhere(TARIFF_ID_FIELD + " = ?").getQuery() + ")")
			.addWhere(USER_HAS_TARIFF_USER_ID_FIELD + " = ? ").addAnd(USER_HAS_TARIFF_TARIFF_ID_FIELD + " = ? ").getQuery();
	public static final String ADD_TARIFF_CHARGE_TRANSACTION = new QueryStringBuilder().addInsert(TRANSACTION_TABLE,
			TRANSACTION_USER_ID_FIELD + ", " + TRANSACTION_AMOUNT_FIELD + ", " + TRANSACTION_DESCRIPTION_FIELD)
	.addValues("? , -1*(" + new QueryStringBuilder().addSelect(TARIFF_RATE_FIELD).addFrom(TARIFF_TABLE).addWhere(TARIFF_ID_FIELD + " = ?").getQuery() + 
			"), ?").getQuery();
	
	/** SERVICE QUERIES */
	public static final String GET_SERVICE_BY_ID = new QueryStringBuilder()
			.addSelect(SERVICE_ID_FIELD + ", " + SERVICE_NAME_FIELD + ", " + SERVICE_DESCRIPTION_FIELD)
			.addFrom(SERVICE_TABLE).addWhere(SERVICE_ID_FIELD + "= ? ").getQuery();
	public static final String GET_ALL_SERVICES = new QueryStringBuilder()
			.addSelect(SERVICE_ID_FIELD + ", " + SERVICE_NAME_FIELD + ", " + SERVICE_DESCRIPTION_FIELD)
			.addFrom(SERVICE_TABLE).getQuery();
	public static final String ADD_SERVICE = new QueryStringBuilder()
			.addInsert(SERVICE_TABLE, SERVICE_NAME_FIELD + ", " + SERVICE_DESCRIPTION_FIELD).addValues("?,?")
			.getQuery();
	public static final String UPDATE_SERVICE = new QueryStringBuilder().addUpdate(SERVICE_TABLE)
			.addSet(SERVICE_NAME_FIELD + " =?, " + SERVICE_DESCRIPTION_FIELD + " =? ")
			.addWhere(SERVICE_ID_FIELD + "= ? ").getQuery();
	public static final String DELETE_SERVICE = new QueryStringBuilder().addDelete().addFrom(SERVICE_TABLE)
			.addWhere(SERVICE_ID_FIELD + "= ? ").getQuery();

	/** ROLE QUERIES */
	public static final String GET_ROLE_BY_ID = new QueryStringBuilder()
			.addSelect(ROLE_ID_FIELD + ", " + ROLE_NAME_FIELD + ", " + ROLE_DESCRIPTION_FIELD).addFrom(ROLE_TABLE)
			.addWhere(ROLE_ID_FIELD + " = ? ").getQuery();
	public static final String GET_ALL_ROLES = new QueryStringBuilder()
			.addSelect(ROLE_ID_FIELD + ", " + ROLE_NAME_FIELD + ", " + ROLE_DESCRIPTION_FIELD).addFrom(ROLE_TABLE)
			.getQuery();
	public static final String ADD_ROLE = new QueryStringBuilder()
			.addInsert(ROLE_TABLE, ROLE_NAME_FIELD + ", " + ROLE_DESCRIPTION_FIELD).addValues("?, ?").getQuery();
	public static final String UPDATE_ROLE = new QueryStringBuilder().addUpdate(ROLE_TABLE)
			.addSet(ROLE_NAME_FIELD + " = ?, " + ROLE_DESCRIPTION_FIELD + " = ?").addWhere(ROLE_ID_FIELD + " = ? ")
			.getQuery();
	public static final String DELETE_ROLE = new QueryStringBuilder().addDelete().addFrom(ROLE_TABLE)
			.addWhere(ROLE_ID_FIELD + " = ? ").getQuery();

}
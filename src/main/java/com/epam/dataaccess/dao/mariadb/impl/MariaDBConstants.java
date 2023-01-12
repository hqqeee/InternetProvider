package com.epam.dataaccess.dao.mariadb.impl;


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
	public static final String GET_USER_BY_ID = "SELECT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", "
			+ USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", "
			+ USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD
			+ ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE + " WHERE " + USER_ID_FIELD
			+ " =?";

	public static final String GET_ALL_USERS = "SELECT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", "
			+ USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", "
			+ USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD
			+ ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE;

	public static final String ADD_USER = "INSERT INTO " + USER_TABLE + "(" + USER_PASSWORD_FIELD + ", "
			+ USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + ", "
			+ USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD
			+ ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + ")" + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_USER = "UPDATE " + USER_TABLE + USER_PASSWORD_FIELD + " = ? " + USER_SALT_FIELD
			+ " = ? " + USER_LOGIN_FIELD + " = ? " + USER_ROLE_ID_FIELD + " = ? " + USER_BLOCKED_FIELD + " = ? "
			+ USER_EMAIL_FIELD + " = ? " + USER_FIRST_NAME_FIELD + " = ? " + USER_LAST_NAME_FIELD + " = ? "
			+ USER_CITY_FIELD + " = ? " + USER_ADDRESS_FIELD + " = ? " + USER_BALANCE_FIELD + " WHERE " + USER_ID_FIELD
			+ " = ?";
	public static final String DELETE_USER = "DELETE FROM " + USER_TABLE + " WHERE " + USER_ID_FIELD + " = ?";

	/** */

	public static final String GET_SALT_BY_LOGIN = "SELECT " + USER_SALT_FIELD + " FROM " + USER_TABLE + " WHERE "
			+ USER_LOGIN_FIELD + " = ?";
	// TODO make dto logged user
	public static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD
			+ ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD
			+ " , " + USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + "," + " "
			+ USER_CITY_FIELD + ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE
			+ " WHERE " + USER_LOGIN_FIELD + " = ? AND " + USER_PASSWORD_FIELD + " = ?";
	public static final String CHANGE_BLOCK_STATUS = "UPDATE " + USER_TABLE + " SET " + USER_BLOCKED_FIELD
			+ "  =?  WHERE " + USER_ID_FIELD + "=?";
	public static final String ADD_BALANCE_TO_USER = "UPDATE " + USER_TABLE + " SET " + USER_BALANCE_FIELD + " = "
			+ USER_BALANCE_FIELD + " + ? WHERE " + USER_ID_FIELD + " = ?";

//	public static final String ADD_TARIFF_TO_USER = new QueryStringBuilder()
//			.addInsert(USER_HAS_TARIFF_TABLE, USER_HAS_TARIFF_USER_ID_FIELD + ", " + USER_HAS_TARIFF_TARIFF_ID_FIELD + ", " 
//	+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD).getQuery() + "(" + new QueryStringBuilder()
//			.addSelect("?,?, " + TARIFF_PAYMENT_PERIOD_FIELD).addFrom(TARIFF_TABLE).addWhere(TARIFF_ID_FIELD + " = ?)")
//			.getQuery();
	public static final String ADD_TARIFF_TO_USER = "INSERT INTO  " + USER_HAS_TARIFF_TABLE + " ( "
			+ USER_HAS_TARIFF_USER_ID_FIELD + ",  " + USER_HAS_TARIFF_TARIFF_ID_FIELD + ",  "
			+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + ") VALUES (?,?,?)";
	public static final String GET_ALL_USERS_WITH_ROLE_ID = "SELECT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", "
			+ USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + " , "
			+ USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD
			+ ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE + " WHERE "
			+ USER_ROLE_ID_FIELD + " = ?";
	public static final String GET_ALL_UNBLOCKED_SUBSCRIBERS = "SELECT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD
			+ ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD
			+ " , " + USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", "
			+ USER_CITY_FIELD + ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE
			+ " WHERE " + USER_ROLE_ID_FIELD + " = ? AND " + USER_BLOCKED_FIELD + "  = false";
	public static final String GET_USERS_FOR_VIEW = "SELECT " + USER_ID_FIELD + ", " + USER_PASSWORD_FIELD + ", "
			+ USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", " + USER_BLOCKED_FIELD + " , "
			+ USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD + ", " + USER_CITY_FIELD
			+ ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE + " WHERE "
			+ USER_ROLE_ID_FIELD + " = ? AND LOWER(" + USER_LOGIN_FIELD + ") LIKE ";

	public static final String GET_SUBSCRIBER_COUNT = "SELECT  COUNT(*) FROM " + USER_TABLE + " WHERE "
			+ USER_ROLE_ID_FIELD + "= 2  AND LOWER(" + USER_LOGIN_FIELD + ") LIKE ";

	public static final String CHANGE_USER_PASSWORD = "UPDATE " + USER_TABLE + " SET " + USER_PASSWORD_FIELD + " = ?, "
			+ USER_SALT_FIELD + " =?  WHERE " + USER_ID_FIELD + " = ?";
	public static final String REMOVE_USER_TARIFF = "DELETE  FROM  " + USER_HAS_TARIFF_TABLE + " WHERE  "
			+ USER_HAS_TARIFF_USER_ID_FIELD + " = ?  AND  " + USER_HAS_TARIFF_TARIFF_ID_FIELD + " = ?";
	public static final String GET_UNBLOCKED_AND_PAYMENT_NEEDED_SUBSCRIBERS = "SELECT DISTINCT " + USER_ID_FIELD + ", "
			+ USER_PASSWORD_FIELD + ", " + USER_SALT_FIELD + ", " + USER_LOGIN_FIELD + ", " + USER_ROLE_ID_FIELD + ", "
			+ USER_BLOCKED_FIELD + " , " + USER_EMAIL_FIELD + ", " + USER_FIRST_NAME_FIELD + ", " + USER_LAST_NAME_FIELD
			+ ", " + USER_CITY_FIELD + ", " + USER_ADDRESS_FIELD + ", " + USER_BALANCE_FIELD + " FROM " + USER_TABLE
			+ " INNER JOIN  " + USER_HAS_TARIFF_TABLE + " ON " + USER_ID_FIELD + " =  " + USER_HAS_TARIFF_USER_ID_FIELD
			+ " WHERE " + USER_BLOCKED_FIELD + "  = false  AND  " + USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD
			+ " = 0";
	public static final String GET_USER_BALANCE = "SELECT " + USER_BALANCE_FIELD + " FROM " + USER_TABLE + " WHERE "
			+ USER_ID_FIELD + " = ?";
	public static final String GET_USER_BLOCKED_STATUS = "SELECT " + USER_BLOCKED_FIELD + "  FROM " + USER_TABLE + " WHERE "
			+ USER_ID_FIELD + " = ?";

	/** TARIFF QUERIES */
	public static final String GET_TARIFF_BY_ID = "SELECT  " + TARIFF_ID_FIELD + ",  " + TARIFF_NAME_FIELD + ",  "
			+ TARIFF_DESCRIPTION_FIELD + ",  " + TARIFF_PAYMENT_PERIOD_FIELD + ",  " + TARIFF_RATE_FIELD + ",  "
			+ TARIFF_SERVICE_ID_FIELD + " FROM " + TARIFF_TABLE + " WHERE  " + TARIFF_ID_FIELD + " = ? ";
	public static final String GET_ALL_TARIFFS = "SELECT  " + TARIFF_ID_FIELD + ",  " + TARIFF_NAME_FIELD + ",  "
			+ TARIFF_DESCRIPTION_FIELD + ",  " + TARIFF_PAYMENT_PERIOD_FIELD + ",  " + TARIFF_RATE_FIELD + ",  "
			+ TARIFF_SERVICE_ID_FIELD + " FROM " + TARIFF_TABLE;
	public static final String ADD_TARIFF = "INSERT INTO " + TARIFF_TABLE + " ( " + TARIFF_NAME_FIELD + ",  "
			+ TARIFF_DESCRIPTION_FIELD + ",  " + TARIFF_PAYMENT_PERIOD_FIELD + ",  " + TARIFF_RATE_FIELD + ",  "
			+ TARIFF_SERVICE_ID_FIELD + ") VALUES (?, ?, ?, ?, ?)";
	public static final String UPDATE_TARIFF = "UPDATE " + TARIFF_TABLE + " SET  " + TARIFF_NAME_FIELD + " = ?,  "
			+ TARIFF_DESCRIPTION_FIELD + " = ?,  " + TARIFF_PAYMENT_PERIOD_FIELD + " = ?,  " + TARIFF_RATE_FIELD
			+ " = ?,  " + TARIFF_SERVICE_ID_FIELD + " = ?  WHERE  " + TARIFF_ID_FIELD + " = ?";
	public static final String DELETE_TARIFF = "DELETE  FROM " + TARIFF_TABLE + " WHERE  " + TARIFF_ID_FIELD + " = ?";

	public static final String UPDATE_DAYS_UNTIL_PAYMENT_FOR_UNBLOCKED_USERS = "UPDATE  " + USER_HAS_TARIFF_TABLE
			+ " LEFT JOIN " + USER_TABLE + " ON " + USER_ID_FIELD + " =  " + USER_HAS_TARIFF_USER_ID_FIELD + " SET  "
			+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " =  " + USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD
			+ " - 1 WHERE " + USER_BLOCKED_FIELD + " = false AND  " + USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD
			+ " > 0";

	public static final String GET_USERS_TARIFFS_BY_ID = "SELECT  " + TARIFF_ID_FIELD + ",  " + TARIFF_NAME_FIELD
			+ ",  " + TARIFF_DESCRIPTION_FIELD + ",  " + TARIFF_PAYMENT_PERIOD_FIELD + ",  " + TARIFF_RATE_FIELD + ",  "
			+ TARIFF_SERVICE_ID_FIELD + " FROM " + TARIFF_TABLE + " INNER JOIN  " + USER_HAS_TARIFF_TABLE + " ON  "
			+ USER_HAS_TARIFF_TARIFF_ID_FIELD + "= " + TARIFF_ID_FIELD + " WHERE  " + USER_HAS_TARIFF_USER_ID_FIELD
			+ " =?";
	public static final String GET_USERS_TARIFFS_BY_ID_WITH_DAY_TO_PAY = "SELECT  " + TARIFF_ID_FIELD + ",  "
			+ TARIFF_NAME_FIELD + ",  " + TARIFF_DESCRIPTION_FIELD + ",  " + TARIFF_PAYMENT_PERIOD_FIELD + ",  "
			+ TARIFF_RATE_FIELD + ",  " + TARIFF_SERVICE_ID_FIELD + ",  "
			+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " FROM " + TARIFF_TABLE + " INNER JOIN  "
			+ USER_HAS_TARIFF_TABLE + " ON  " + USER_HAS_TARIFF_TARIFF_ID_FIELD + "= " + TARIFF_ID_FIELD + " WHERE  "
			+ USER_HAS_TARIFF_USER_ID_FIELD + " =?";

	public static final String GET_USERS_UNPAID_TARIFFS = "SELECT  " + TARIFF_ID_FIELD + ",  " + TARIFF_NAME_FIELD
			+ ",  " + TARIFF_DESCRIPTION_FIELD + ",  " + TARIFF_PAYMENT_PERIOD_FIELD + ",  " + TARIFF_RATE_FIELD + ",  "
			+ TARIFF_SERVICE_ID_FIELD + " FROM " + TARIFF_TABLE + " INNER JOIN  " + USER_HAS_TARIFF_TABLE + " ON  "
			+ TARIFF_ID_FIELD + " =  " + USER_HAS_TARIFF_TARIFF_ID_FIELD + " WHERE  " + USER_HAS_TARIFF_USER_ID_FIELD
			+ " = ?  AND  " + USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " = 0";

	public static final String GET_COUNT_OF_TARIFFS = "SELECT  COUNT(*) FROM " + TARIFF_TABLE;

	public static final String GET_COUNT_OF_TARIFF_BY_SERVICE_ID = "SELECT  COUNT(*) FROM " + TARIFF_TABLE + " WHERE  "
			+ TARIFF_SERVICE_ID_FIELD + "= ? ";
	public static final String FILTER_TARIFF_BY_SERVICE_ID = " WHERE  " + TARIFF_SERVICE_ID_FIELD + "= ? ";

	public static final String ORDER_BY = " ORDER BY ";
	public static final String LIMIT = " LIMIT ?, ?";

	/** TRANSACTION QUERIES */
	public static final String GET_TRANSACTION_BY_ID = "SELECT  " + TRANSACTION_ID_FIELD + ",  "
			+ TRANSACTION_USER_ID_FIELD + ",  " + TRANSACTION_TIMESTAMP_FIELD + ",  " + TRANSACTION_AMOUNT_FIELD + ",  "
			+ TRANSACTION_DESCRIPTION_FIELD + " FROM " + TRANSACTION_TABLE + " WHERE  " + TRANSACTION_ID_FIELD + " = ?";
	public static final String GET_ALL_TRANSACTIONS = "SELECT  " + TRANSACTION_ID_FIELD + ",  "
			+ TRANSACTION_USER_ID_FIELD + ",  " + TRANSACTION_TIMESTAMP_FIELD + ",  " + TRANSACTION_AMOUNT_FIELD + ",  "
			+ TRANSACTION_DESCRIPTION_FIELD + " FROM " + TRANSACTION_TABLE;
	public static final String ADD_TRANSACTION = "INSERT INTO " + TRANSACTION_TABLE + " ( " + TRANSACTION_USER_ID_FIELD
			+ ",  " + TRANSACTION_AMOUNT_FIELD + ",  " + TRANSACTION_DESCRIPTION_FIELD + ") VALUES (?,?,?)";
	public static final String UPDATE_TRANSACTION = "UPDATE " + TRANSACTION_TABLE + " SET  " + TRANSACTION_USER_ID_FIELD
			+ " = ?,  " + TRANSACTION_TIMESTAMP_FIELD + " = ?,  " + TRANSACTION_AMOUNT_FIELD + " = ?,  "
			+ TRANSACTION_DESCRIPTION_FIELD + " = ?  WHERE  " + TRANSACTION_ID_FIELD + " = ?";
	public static final String DELETE_TRANSACTION = "DELETE  FROM " + TRANSACTION_TABLE + " WHERE  "
			+ TRANSACTION_ID_FIELD + " = ?";
	public static final String GET_USERS_TRANSACTION = "SELECT  " + TRANSACTION_ID_FIELD + ",  "
			+ TRANSACTION_USER_ID_FIELD + ",  " + TRANSACTION_TIMESTAMP_FIELD + ",  " + TRANSACTION_AMOUNT_FIELD + ",  "
			+ TRANSACTION_DESCRIPTION_FIELD + " FROM " + TRANSACTION_TABLE + " WHERE  " + TRANSACTION_USER_ID_FIELD
			+ " = ? ORDER BY  " + TRANSACTION_TIMESTAMP_FIELD + " DESC  LIMIT ?, ?";
	public static final String GET_TRANSACTION_NUMBER_OF_USER = "SELECT  COUNT(*) FROM " + TRANSACTION_TABLE
			+ " WHERE  " + TRANSACTION_USER_ID_FIELD + "= ? ";
	public static final String CHARGE_FOR_USING_TARIFF = "UPDATE " + USER_TABLE + " SET " + USER_BALANCE_FIELD + " = "
			+ USER_BALANCE_FIELD + " - (SELECT  " + TARIFF_RATE_FIELD + " FROM " + TARIFF_TABLE + " WHERE  "
			+ TARIFF_ID_FIELD + " = ?) WHERE " + USER_ID_FIELD + " = ?";

	public static final String UPDATE_DAYS_UNTIL_NEXT_PAYMENT = "UPDATE  " + USER_HAS_TARIFF_TABLE + " SET  "
			+ USER_HAS_TARIFF_DAYS_UNTIL_NEXT_PAYMENT_FIELD + " = (SELECT  " + TARIFF_PAYMENT_PERIOD_FIELD + " FROM "
			+ TARIFF_TABLE + " WHERE  " + TARIFF_ID_FIELD + " = ?) WHERE  " + USER_HAS_TARIFF_USER_ID_FIELD
			+ " = ?  AND  " + USER_HAS_TARIFF_TARIFF_ID_FIELD + " = ? ";

	public static final String ADD_TARIFF_CHARGE_TRANSACTION = "INSERT INTO " + TRANSACTION_TABLE + " ( "
			+ TRANSACTION_USER_ID_FIELD + ",  " + TRANSACTION_AMOUNT_FIELD + ",  " + TRANSACTION_DESCRIPTION_FIELD
			+ ") VALUES (? , -1*(SELECT  " + TARIFF_RATE_FIELD + " FROM " + TARIFF_TABLE + " WHERE  " + TARIFF_ID_FIELD
			+ " = ?), ?)";

	/** SERVICE QUERIES */
	public static final String GET_SERVICE_BY_ID = "SELECT  " + SERVICE_ID_FIELD + ",  " + SERVICE_NAME_FIELD + ",  "
			+ SERVICE_DESCRIPTION_FIELD + " FROM " + SERVICE_TABLE + " WHERE  " + SERVICE_ID_FIELD + "= ?";
	public static final String GET_ALL_SERVICES = "SELECT  " + SERVICE_ID_FIELD + ",  " + SERVICE_NAME_FIELD + ",  "
			+ SERVICE_DESCRIPTION_FIELD + " FROM " + SERVICE_TABLE;
	public static final String ADD_SERVICE = "INSERT INTO " + SERVICE_TABLE + " ( " + SERVICE_NAME_FIELD + ",  "
			+ SERVICE_DESCRIPTION_FIELD + ") VALUES (?,?)";
	public static final String UPDATE_SERVICE = "UPDATE " + SERVICE_TABLE + " SET  " + SERVICE_NAME_FIELD + " =?,  "
			+ SERVICE_DESCRIPTION_FIELD + " =?  WHERE  " + SERVICE_ID_FIELD + "= ? ";
	public static final String DELETE_SERVICE = "DELETE  FROM " + SERVICE_TABLE + " WHERE  " + SERVICE_ID_FIELD + "= ?";

	/** ROLE QUERIES */
	public static final String GET_ROLE_BY_ID = "SELECT  " + ROLE_ID_FIELD + ",  " + ROLE_NAME_FIELD + ",  "
			+ ROLE_DESCRIPTION_FIELD + " FROM " + ROLE_TABLE + " WHERE  " + ROLE_ID_FIELD + " = ? ";
	public static final String GET_ALL_ROLES = "SELECT  " + ROLE_ID_FIELD + ",  " + ROLE_NAME_FIELD + ",  "
			+ ROLE_DESCRIPTION_FIELD + " FROM " + ROLE_TABLE;
	public static final String ADD_ROLE = "INSERT INTO " + ROLE_TABLE + " ( " + ROLE_NAME_FIELD + ",  "
			+ ROLE_DESCRIPTION_FIELD + ") VALUES (?, ?)";
	public static final String UPDATE_ROLE = "UPDATE " + ROLE_TABLE + " SET  " + ROLE_NAME_FIELD + " = ?,  "
			+ ROLE_DESCRIPTION_FIELD + " = ? WHERE  " + ROLE_ID_FIELD + " = ?";
	public static final String DELETE_ROLE = "DELETE  FROM " + ROLE_TABLE + " WHERE  " + ROLE_ID_FIELD + " = ? ";

}
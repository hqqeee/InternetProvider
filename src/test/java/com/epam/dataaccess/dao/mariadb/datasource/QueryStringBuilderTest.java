package com.epam.dataaccess.dao.mariadb.datasource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.epam.dataaccess.dao.mariadb.impl.MariaDBConstants;

class QueryStringBuilderTest {

    @Test
    void simpleTest() {
	assertEquals("SELECT tariff.id, tariff.name, tariff.description, tariff.price, "
		+ "tariff.service_id FROM tariff INNER JOIN user_has_tariff ON "
		+ "user_has_tariff.tariff_id=tariff.id WHERE user_has_tariff.user_id = ?",
		new QueryStringBuilder().addSelect("tariff.id, tariff.name, tariff.description, tariff.price, "
			+ "tariff.service_id")
			.addFrom("tariff")
			.addInnerJoin("user_has_tariff", "user_has_tariff.tariff_id=tariff.id")
			.addWhere("user_has_tariff.user_id = ?")
			.getQuery()
		);
	assertEquals(new QueryStringBuilder().addUpdate(MariaDBConstants.USER_TABLE)
			.addSet(MariaDBConstants.USER_PASSWORD_FIELD + " = ? " + MariaDBConstants.USER_SALT_FIELD + " = ? " + MariaDBConstants.USER_LOGIN_FIELD + " = ? "
					+ MariaDBConstants.USER_ROLE_ID_FIELD + " = ? " + MariaDBConstants.USER_BLOCKED_FIELD + " = ? " + MariaDBConstants.USER_EMAIL_FIELD + " = ? "
					+ MariaDBConstants.USER_FIRST_NAME_FIELD + " = ? " + MariaDBConstants.USER_LAST_NAME_FIELD + " = ? " + MariaDBConstants.USER_CITY_FIELD + " = ? "
					+ MariaDBConstants.USER_ADDRESS_FIELD + " = ? " + MariaDBConstants.USER_BALANCE_FIELD)
			.addWhere(MariaDBConstants.USER_ID_FIELD + "= ?").getQuery(), 
			"UPDATE user SET user.password = ? user.salt = ? user.login = ? user.role_id = ? user.blocked = ? user.email = ? user.first_name = ? user.last_name = ?"
			+ " user.city = ? user.address = ? user.balance WHERE user.id= ?"
			);
	
    }

}

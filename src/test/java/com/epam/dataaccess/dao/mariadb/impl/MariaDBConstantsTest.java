package com.epam.dataaccess.dao.mariadb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MariaDBConstantsTest {

	@Test
	void test() {
		assertEquals("SELECT user.id, user.password, user.salt, user.login, user.role_id, user.blocked, user.email, "
				+ "user.first_name, user.last_name, user.city, user.address, user.balance FROM user WHERE user.id=?",
				MariaDBConstants.GET_USER_BY_ID);

	}

}

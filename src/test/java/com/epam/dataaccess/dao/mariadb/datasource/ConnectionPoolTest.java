package com.epam.dataaccess.dao.mariadb.datasource;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class ConnectionPoolTest {

	@Test
	void test() {
		assert(ConnectionPool.getInstance() == ConnectionPool.INSTANCE);
		ConnectionPool cp = ConnectionPool.getInstance();
		assertFalse(cp.releaseConnection(null));
		
	}

}

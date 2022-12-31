package com.epam.dataaccess.dao.mariadb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;

class UserDAOMariaDBTest {

	@Mock
	UserDAOMariaDB userDAO;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testUserDAOMariaDBGet() throws DAOException {
		User user = null;
		try {
			Mockito.when(userDAO.get(1)).thenReturn(new User(1, "password", "salt", "admin", 1, false, "email",
					"first_name", "last_name", "city", "address", BigDecimal.ZERO));
			user = userDAO.get(1);
		} catch (DAOException e) {
			fail("DAO Exception");
		}
		assert (user.getId() == 1);
		assert (user.getPassword().equals("password"));
		assert (user.getSalt().equals("salt"));
		assert (user.getLogin().equals("admin"));
		assert (user.getRoleId() == 1);
		assert (user.getEmail().equals("email"));
		assert (user.getFirstName().equals("first_name"));
		assert (user.getLastName().equals("last_name"));
		assert (user.getCity().equals("city"));
		assert (user.getAddress().equals("address"));
		assert (user.getBalance().equals(BigDecimal.ZERO));
	}

	@Test
	void testUserDAOMariaDBGetAll() {
		List<User> users = null;
		try {
			List<User> mockedUsers = new ArrayList<>();
			mockedUsers.add(new User(1, "password", "salt", "admin", 1, false, "email",
					"first_name", "last_name", "city", "address", BigDecimal.ZERO));
			mockedUsers.add(new User(2, "hash", "salt", "login", 1, false, "em",
					"fn", "ln", "ct", "ad", BigDecimal.ONE));
			Mockito.when(
					userDAO.getAll())
					.thenReturn(mockedUsers);
			users = userDAO.getAll();
		} catch (DAOException e) {
			fail("DAO Exception");
		}
		assert(users.size() == 2);
		assertEquals("address",users.get(0).getAddress());
		assertEquals("hash",users.get(1).getPassword());
	}
	
	@Test
	void testUserDAOMariaDBGetAllEmpty() {
		List<User> users = null;
		try {
			Mockito.when(
					userDAO.getAll())
					.thenReturn(new ArrayList<User>(
							));
			users = userDAO.getAll();
		} catch (DAOException e) {
			fail("DAO Exception");
		}
		assertTrue(users.isEmpty());
	}
	
	

}

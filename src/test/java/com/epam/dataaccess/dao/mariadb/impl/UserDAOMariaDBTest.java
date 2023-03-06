package com.epam.dataaccess.dao.mariadb.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.dataaccess.dao.mariadb.datasource.QueryBuilder;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;

class UserDAOMariaDBTest {

	@Mock
	private QueryBuilder mockQueryBuilder = mock(QueryBuilder.class);

	@Mock
	private ResultSet mockResultSet = mock(ResultSet.class);

	private User testUser;

	private UserDAOMariaDB userDAOMariaDB;

	@BeforeEach
	void setUp() throws Exception {
		testUser = new User();
		testUser.setId(1);
		testUser.setFirstName("firstName");
		testUser.setRoleId(2);
		testUser.setLastName("lastName");
		testUser.setPassword("password");
		testUser.setSalt("salt");
		testUser.setAddress("address");
		testUser.setCity("city");
		testUser.setEmail("email");
		testUser.setBlocked(false);
		testUser.setLogin("login");
		testUser.setBalance(BigDecimal.ONE);
		userDAOMariaDB = new UserDAOMariaDB() {
			@Override
			protected QueryBuilder getQueryBuilder() throws SQLException {
				return mockQueryBuilder;
			}
		};
	}

	@Test
	void testGetUser() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USER_BY_ID)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId());
		when(mockResultSet.getString(MariaDBConstants.USER_PASSWORD_FIELD)).thenReturn(testUser.getPassword());
		when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin());
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId());
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(testUser.isBlocked());
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail());
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName());
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName());
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity());
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress());
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance());
		
		assertEquals(testUser, userDAOMariaDB.get(testUser.getId()));
	}

	@Test
	void testGetAll() throws SQLException, DAOException {
		List<User> expectedUsers = new ArrayList<>();
		expectedUsers.add(testUser);

		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_USERS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId());
		when(mockResultSet.getString(MariaDBConstants.USER_PASSWORD_FIELD)).thenReturn(testUser.getPassword());
		when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin());
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId());
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(testUser.isBlocked());
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail());
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName());
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName());
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity());
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress());
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance());

		assertEquals(expectedUsers, userDAOMariaDB.getAll());
	}

	@Test
	void testInsert() throws DAOException, SQLException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_USER)).thenReturn(mockQueryBuilder);
		QueryBuilder mockedQueryBuilde2 = mock(QueryBuilder.class);
		ResultSet mockedResultSet2 = mock(ResultSet.class) ;
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.EXISTS_USER_BY_EMAIL)).thenReturn(mockedQueryBuilde2);
		when(mockedQueryBuilde2.setStringField(testUser.getEmail())).thenReturn(mockedQueryBuilde2);
		when(mockedQueryBuilde2.executeQuery()).thenReturn(mockedResultSet2);
		when(mockedResultSet2.next()).thenReturn(false);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.EXISTS_USER_BY_LOGIN)).thenReturn(mockedQueryBuilde2);
		when(mockedQueryBuilde2.setStringField(testUser.getLogin())).thenReturn(mockedQueryBuilde2);
		when(mockedQueryBuilde2.executeQuery()).thenReturn(mockedResultSet2);
		when(mockedResultSet2.next()).thenReturn(false);
		when(mockQueryBuilder.setStringField(testUser.getFirstName())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getLastName())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getLogin())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getPassword())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getSalt())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getRoleId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBooleanField(testUser.isBlocked())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getEmail())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getCity())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getAddress())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(testUser.getBalance())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		assertEquals(1, userDAOMariaDB.insert(testUser));
	}

	@Test
	void testUpdateUser() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.UPDATE_USER)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getFirstName())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getLastName())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getPassword())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getSalt())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getAddress())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getCity())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getEmail())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBooleanField(testUser.isBlocked())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getLogin())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBigDecimalField(testUser.getBalance())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getRoleId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);

		assertEquals(1, userDAOMariaDB.update(testUser));
	}

	@Test
	void testDeleteUser() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.DELETE_USER)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(testUser.getId());
		
		assertEquals(1, userDAOMariaDB.delete(testUser));
	}

	@Test
	void testGetSalt() throws SQLException, DAOException {
	when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_SALT_BY_LOGIN)).thenReturn(mockQueryBuilder);
	when(mockQueryBuilder.setStringField(testUser.getLogin())).thenReturn(mockQueryBuilder);
	when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
	when(mockResultSet.next()).thenReturn(true).thenReturn(false);
	when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());

	assertEquals(testUser.getSalt(), userDAOMariaDB.getSalt(testUser.getLogin()));
	}

	@Test
	void testGetUserByLoginAndPassword() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USER_BY_LOGIN_AND_PASSWORD)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getLogin())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(testUser.getPassword())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId());
		when(mockResultSet.getString(MariaDBConstants.USER_PASSWORD_FIELD)).thenReturn(testUser.getPassword());
		when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin());
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId());
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(testUser.isBlocked());
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail());
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName());
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName());
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity());
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress());
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance());
		
		assertEquals(testUser, userDAOMariaDB.getUser(testUser.getLogin(), testUser.getPassword()));
	}

	@Test
	void testChangeBlocked() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.CHANGE_BLOCK_STATUS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setBooleanField(testUser.isBlocked())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);

		assertEquals(1, userDAOMariaDB.changeBlocked(testUser.isBlocked(), testUser.getId()));
	}

	@Test
	void testAddTariffToUser() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.ADD_TARIFF_TO_USER)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUser.getId())).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(0)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);
		
		assertDoesNotThrow(() -> userDAOMariaDB.addTariffToUser(testUser.getId(), testUser.getId()));
	}

	@Test
	void testGetAllSubscribers() throws SQLException, DAOException {
		List<User> expectedUsers = new ArrayList<>();
		expectedUsers.add(testUser);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_USERS_WITH_ROLE_ID))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(2)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId());
		when(mockResultSet.getString(MariaDBConstants.USER_PASSWORD_FIELD)).thenReturn(testUser.getPassword());
		when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin());
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId());
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(testUser.isBlocked());
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail());
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName());
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName());
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity());
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress());
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance());

		assertEquals(expectedUsers, userDAOMariaDB.getAllSubscriber());
	}

	@Test
	void testGetAllUnblockedSubscribers() throws SQLException, DAOException {
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_ALL_UNBLOCKED_SUBSCRIBERS)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(mockQueryBuilder.setIntField(2)).thenReturn(mockQueryBuilder);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId()).thenReturn(testUser.getId() + 1);
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin()).thenReturn(testUser.getLogin() + "_1");
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId()).thenReturn(testUser.getRoleId() + 1);
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(false).thenReturn(false);
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail()).thenReturn(testUser.getEmail() + "_1");
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName()).thenReturn(testUser.getFirstName() + "_1");
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName()).thenReturn(testUser.getLastName() + "_1");
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity()).thenReturn(testUser.getCity() + "_1");
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress()).thenReturn(testUser.getAddress() + "_1");
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance()).thenReturn(testUser.getBalance().add(BigDecimal.ONE));

		List<User> users = userDAOMariaDB.getAllUnblockedSubscriber();
		assertEquals(2, users.size());
	}

	@Test
	void testGetSubscriberForCharging() throws SQLException, DAOException {
		List<User> expectedUserList = Arrays.asList(testUser, testUser);
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_UNBLOCKED_AND_PAYMENT_NEEDED_SUBSCRIBERS))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId());
		when(mockResultSet.getString(MariaDBConstants.USER_PASSWORD_FIELD)).thenReturn(testUser.getPassword());
		when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin());
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId());
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(testUser.isBlocked());
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail());
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName());
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName());
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity());
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress());
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance());

		assertEquals(expectedUserList, userDAOMariaDB.getSubscriberForCharging());
	}

	@Test
	void testGetSubscriberForView() throws SQLException, DAOException {
		List<User> expectedUsers = new ArrayList<>();
		expectedUsers.add(testUser);
		String searchField = "name";
		int offset = 10;
		int entriesPerPage = 5;
		when(mockQueryBuilder.addPreparedStatement(
				MariaDBConstants.GET_USERS_FOR_VIEW + "LOWER('%" + searchField + "%')" + MariaDBConstants.LIMIT))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(searchField)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(offset)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(entriesPerPage)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(2)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(MariaDBConstants.USER_ID_FIELD)).thenReturn(testUser.getId());
		when(mockResultSet.getString(MariaDBConstants.USER_PASSWORD_FIELD)).thenReturn(testUser.getPassword());
		when(mockResultSet.getString(MariaDBConstants.USER_SALT_FIELD)).thenReturn(testUser.getSalt());
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(testUser.getLogin());
		when(mockResultSet.getInt(MariaDBConstants.USER_ROLE_ID_FIELD)).thenReturn(testUser.getRoleId());
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(testUser.isBlocked());
		when(mockResultSet.getString(MariaDBConstants.USER_EMAIL_FIELD)).thenReturn(testUser.getEmail());
		when(mockResultSet.getString(MariaDBConstants.USER_FIRST_NAME_FIELD)).thenReturn(testUser.getFirstName());
		when(mockResultSet.getString(MariaDBConstants.USER_LAST_NAME_FIELD)).thenReturn(testUser.getLastName());
		when(mockResultSet.getString(MariaDBConstants.USER_CITY_FIELD)).thenReturn(testUser.getCity());
		when(mockResultSet.getString(MariaDBConstants.USER_ADDRESS_FIELD)).thenReturn(testUser.getAddress());
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testUser.getBalance());

		List<User> actualUsers = userDAOMariaDB.getSubscriberForView(searchField, offset, entriesPerPage);
		assertEquals(expectedUsers.get(0), actualUsers.get(0));
	}

	@Test
	void testGetSubscriberNumber() throws SQLException, DAOException {
		String searchField = "123";
		int expectedResult = 456;

		when(mockQueryBuilder
				.addPreparedStatement(MariaDBConstants.GET_SUBSCRIBER_COUNT + "LOWER('%" + searchField + "%')"))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(searchField)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(1)).thenReturn(expectedResult);

		int result = userDAOMariaDB.getSubscriberNumber(searchField);

		assertEquals(expectedResult, result);
	}

	@Test
	void testChangePassword() throws SQLException, DAOException {
		int expectedUserId = 1;
		String expectedNewPassword = "newPassword";
		String expectedSalt = "salt";

		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(expectedUserId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(expectedNewPassword)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(expectedSalt)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);

		int result = userDAOMariaDB.changePassword(expectedUserId, expectedNewPassword, expectedSalt);

		assertEquals(1, result);
	}

	@Test
	void testChangePasswordDAOException() throws SQLException, DAOException {
		int expectedUserId = 1;
		String expectedNewPassword = "newPassword";
		String expectedSalt = "salt";

		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(expectedUserId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(expectedNewPassword)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(expectedSalt)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenThrow(SQLException.class);

		assertThrows(DAOException.class, () -> {
			userDAOMariaDB.changePassword(expectedUserId, expectedNewPassword, expectedSalt);
		});
	}

	@Test
	void testChangePasswordWithEmail() throws SQLException, DAOException {
		String email = "test@email.com";
		String newPassword = "newPassword";
		String salt = "salt";

		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.CHANGE_USER_PASSWORD_BY_EMAIL))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(email)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(newPassword)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(salt)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);

		int result = userDAOMariaDB.changePassword(email, newPassword, salt);
		assertEquals(1, result);
	}

	@Test
	void testRemoveTariffFromUser() throws SQLException, DAOException {
		int userId = 1;
		int tariffId = 2;
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.REMOVE_USER_TARIFF)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(userId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(tariffId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeUpdate()).thenReturn(1);

		int result = userDAOMariaDB.removeTariffFromUser(userId, tariffId);
		assertEquals(1, result);
	}

	@Test
	void testGetUserBalance() throws SQLException, DAOException {
		int testUserId = 1;
		BigDecimal testBalance = BigDecimal.ONE;

		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USER_BALANCE)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(testUserId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getBigDecimal(MariaDBConstants.USER_BALANCE_FIELD)).thenReturn(testBalance);

		BigDecimal actualBalance = userDAOMariaDB.getUserBalance(testUserId);
		assertEquals(testBalance, actualBalance);
	}

	@Test
	void testGetUserStatus() throws SQLException, DAOException {
		int userId = 1;
		boolean expectedStatus = false;

		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_USER_BLOCKED_STATUS))
				.thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setIntField(userId)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);

		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getBoolean(MariaDBConstants.USER_BLOCKED_FIELD)).thenReturn(expectedStatus);

		boolean actualStatus = userDAOMariaDB.getUserStatus(userId);

		assertEquals(expectedStatus, actualStatus);
	}
	
	@Test
	void testGetLoginByEmail() throws SQLException, DAOException {
		String email = "test@example.com";
		String login = "test_user";
		when(mockQueryBuilder.addPreparedStatement(MariaDBConstants.GET_LOGIN_BY_EMAIL)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.setStringField(email)).thenReturn(mockQueryBuilder);
		when(mockQueryBuilder.executeQuery()).thenReturn(mockResultSet);
		
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getString(MariaDBConstants.USER_LOGIN_FIELD)).thenReturn(login);
		
		String result = userDAOMariaDB.getLoginByEmail(email);
		assertEquals(login, result);
	}
}

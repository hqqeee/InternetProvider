package com.epam.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAORecordAlreadyExistsException;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.services.dto.UserForm;
import com.epam.util.PasswordUtil;

class UserServiceImplTest {

	private User testUser = new User(2, "password", "yYMg0RzhJNM=", "login", 2, false, "email@dot.com", "First", "Last",
			"City", "Address", BigDecimal.ZERO);
	private UserDTO testUserDTO = null;
	private UserForm testUserForm = new UserForm(testUser.getFirstName(), testUser.getLastName(), testUser.getLogin(),
			testUser.getEmail(), testUser.getCity(), testUser.getAddress());

	private Tariff tariff = new Tariff(1, "tariffName", "desc", 14, BigDecimal.ONE, 1);

	@Mock
	private UserServiceImpl userService;

	@Mock
	private UserDAO userDAO;

	@Mock
	private TariffDAO tariffDAO;

	@Mock
	private TransactionDAO transactionDAO;

	@Mock
	private DAOFactory daoFactory;

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MockitoAnnotations.openMocks(this);

		@SuppressWarnings("unchecked")
		Constructor<UserServiceImpl> userServiceConstructor = (Constructor<UserServiceImpl>) Class
				.forName(UserServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		userService = userServiceConstructor.newInstance(daoFactory);
		testUserDTO = userService.convertUserToUserDTO(testUser);
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(daoFactory.getTariffDAO()).thenReturn(tariffDAO);
		Mockito.when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
	}

	@Test
	void testLoginUserExists() throws UserServiceException {
		try {
			Mockito.when(userDAO.getUser("login", "e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9"))
					.thenReturn(testUser);
			Mockito.when(daoFactory.getUserDAO().getSalt("login")).thenReturn("yYMg0RzhJNM=");
		} catch (DAOException e) {
			e.printStackTrace();
		}

		try {
			assertEquals(testUserDTO, userService.login("login", "password"));
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testLoginUserNotExists() {
		try {
			Mockito.when(daoFactory.getUserDAO().getUser("login",
					"e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9")).thenReturn(testUser);
			Mockito.when(daoFactory.getUserDAO().getSalt("login")).thenReturn("yYMg0RzhJNM=");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertThrows(UserNotFoundException.class, () -> userService.login("login", "password1"));
	}

	@Test
	void testUserRegistrationExistsEmail()
			throws DAOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Mockito.when(userDAO.insert(testUser)).thenThrow(
				new DAORecordAlreadyExistsException("User with login " + testUser.getLogin() + " already exists."));
		@SuppressWarnings("unchecked")
		Constructor<UserServiceImpl> userServiceConstructor = (Constructor<UserServiceImpl>) Class
				.forName(UserServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		UserServiceImpl userServiceImpl = Mockito.spy(userServiceConstructor.newInstance(daoFactory));
		try (MockedStatic<PasswordUtil> passwdUtil = Mockito.mockStatic(PasswordUtil.class)) {
			passwdUtil.when(() -> PasswordUtil.getRandomString(12)).thenReturn("yYMg0RzhJNM=");
			passwdUtil.when(() -> PasswordUtil.getRandomString(10)).thenReturn("password");
			doReturn(testUser).when(userServiceImpl).convertUserFormToUser(testUserForm, "yYMg0RzhJNM=", "password");
			assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.registerUser(testUserForm));
		}

	}

	@Test
	void testUserRegistrationExistsLogin()
			throws DAOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Mockito.when(userDAO.insert(testUser)).thenThrow(
				new DAORecordAlreadyExistsException("User with email " + testUser.getEmail() + " already exists."));
		@SuppressWarnings("unchecked")
		Constructor<UserServiceImpl> userServiceConstructor = (Constructor<UserServiceImpl>) Class
				.forName(UserServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		UserServiceImpl userServiceImpl = Mockito.spy(userServiceConstructor.newInstance(daoFactory));
		try (MockedStatic<PasswordUtil> passwdUtil = Mockito.mockStatic(PasswordUtil.class)) {
			passwdUtil.when(() -> PasswordUtil.getRandomString(12)).thenReturn("yYMg0RzhJNM=");
			passwdUtil.when(() -> PasswordUtil.getRandomString(10)).thenReturn("password");
			doReturn(testUser).when(userServiceImpl).convertUserFormToUser(testUserForm, "yYMg0RzhJNM=", "password");
			assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.registerUser(testUserForm));
		}

	}

	@Test
	void testGetAllSubscribers() throws DAOException, UserServiceException {
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		User user2 = new User();
		users.add(user2);
		Mockito.when(userDAO.getAllSubscriber()).thenReturn(users);
		List<UserDTO> userDTOs = new ArrayList<>();
		userDTOs.add(testUserDTO);
		userDTOs.add(userService.convertUserToUserDTO(user2));
		assertEquals(userDTOs, userService.getAllSubscribers());
	}

	@Test
	void testGetAllUnblocedSubscribers() throws DAOException, UserServiceException {
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		User user2 = new User();
		users.add(user2);
		Mockito.when(userDAO.getAllUnblockedSubscriber()).thenReturn(users);
		List<UserDTO> usersDTOS = new ArrayList<>();
		usersDTOS.add(userService.convertUserToUserDTO(testUser));
		usersDTOS.add(userService.convertUserToUserDTO(user2));
		assertEquals(usersDTOS, userService.getAllUnblockedSubscribers());
	}

	@Test
	void testGetAllubscribersForCharging() throws DAOException, UserServiceException {
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		User user2 = (new User());
		users.add(user2);

		Mockito.when(userDAO.getSubscriberForCharging()).thenReturn(users);
		List<UserDTO> userDTOs = new ArrayList<>();
		userDTOs.add(testUserDTO);
		userDTOs.add(userService.convertUserToUserDTO(user2));
		assertEquals(userDTOs, userService.getSubscriberForCharging());
	}

	@Test
	void testViewSubscribers() throws DAOException, UserServiceException {
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		Mockito.when(userDAO.getSubscriberForView("", 0, 5)).thenReturn(users);
		assertEquals(5, userService.viewSubscribers("", 1, 5).size());
	}

	@Test
	void testGetSubscriberNumber() throws DAOException, UserServiceException {
		Mockito.when(userDAO.getSubscriberNumber("")).thenReturn(5);
		assertEquals(5, userService.getSubscribersNumber(""));
	}

	@Test
	void testGetUserById() throws DAOException, UserNotFoundException, UserServiceException {
		Mockito.when(userDAO.get(1)).thenReturn(testUser);
		assertEquals(testUserDTO, userService.getUserById(1));
	}

	@Test
	void testGetUserByIdNotFount() throws DAOException, UserServiceException {
		Mockito.when(userDAO.get(1)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
	}

	@Test
	void testRemoveUser() {
		try {
			userService.removeUser(1);
		} catch (UserServiceException e) {
			fail();
		}
	}

	@Test
	void testChangeUserStatus() {
		try {
			userService.changeUserStatus(true, 1);
		} catch (UserServiceException e) {
			fail();
		}
	}

	@Test
	void testChangeUserBalance() {
		try {
			Mockito.when(userDAO.get(1)).thenReturn(testUser);
			userService.changeUserBalance(1, BigDecimal.ZERO, "");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testChangeUserBalanceLongDescription() {

		assertThrows(UserServiceException.class,
				() -> userService.changeUserBalance(1, BigDecimal.ZERO,
						"12345678901234567890123456789012345678901234567890"
								+ "123456789012345678901234567890123456789012345678901234567890"
								+ "12345678901234567890123456789012345678901234567890"
								+ "123456789012345678901234567890123456789012345678901234567890"));

	}

	@Test
	void testChangePasswordUserNotFound() throws DAOException {

		Mockito.when(userDAO.get(1)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> userService.changePassword(1, null, null));
	}

	@Test
	void testChangePasswordIncorrectOldPassword() throws DAOException {

		Mockito.when(userDAO.get(0)).thenReturn(testUser);
		Mockito.when(userDAO.getSalt("login")).thenReturn("yYMg0RzhJNM=");
		assertThrows(PasswordNotMatchException.class, () -> userService.changePassword(0, "password", "asd"));

	}

	@Test
	void testResetPassword() throws DAOException, UserServiceException {

		try (MockedStatic<PasswordUtil> passwdUtil = Mockito.mockStatic(PasswordUtil.class)) {
			passwdUtil.when(() -> PasswordUtil.getRandomString(12)).thenReturn("Ne3Sa9lrat=");
			passwdUtil.when(() -> PasswordUtil.getRandomString(10)).thenReturn("newPassword");
			User user = new User();
			String hashedPassword = PasswordUtil.hashPassword("newPasswordNe3Sa9lrat=");
			doAnswer((i) -> {
				user.setPassword(hashedPassword);
				return 1;
			}).when(userDAO).changePassword("email", hashedPassword, "Ne3Sa9lrat=");
			userService.resetPassword("email");
			assertEquals(hashedPassword, user.getPassword());

		}
	}

	@Test
	void testAddTariffForUserNotEnoughMoney() throws DAOException {
		Mockito.when(tariffDAO.get(1)).thenReturn(tariff);
		Mockito.when(userDAO.getUserBalance(1)).thenReturn(BigDecimal.ZERO);
		assertThrows(NegativeUserBalanceException.class, () -> userService.addTariffToUser(1, 1));
	}

	@Test
	void testAddTariffForUserRecordExists() throws DAOException {
		doThrow(DAORecordAlreadyExistsException.class).when(userDAO).addTariffToUser(1, 1);
		Mockito.when(tariffDAO.get(1)).thenReturn(tariff);
		Mockito.when(userDAO.getUserBalance(1)).thenReturn(BigDecimal.ONE);
		assertThrows(UserAlreadyHasTariffException.class, () -> userService.addTariffToUser(1, 1));
	}

	@Test
	void testChargeUserForTariffsUsingNotEnoughMoney() throws DAOException {
		List<TariffDTO> tariffDTOs = new ArrayList<>();
		tariffDTOs.add(new TariffDTO(1, "name", "desk", 13, BigDecimal.ONE, Service.CABLE_TV));
		tariffDTOs.add(new TariffDTO(2, "name2", "desk2", 13, BigDecimal.TEN, Service.CABLE_TV));
		Mockito.when(userDAO.get(1)).thenReturn(testUser);
		assertThrows(NegativeUserBalanceException.class, () -> userService.chargeUserForTariffsUsing(1, tariffDTOs));
	}

	@Test
	void testGetUserBalance() throws UserServiceException, DAOException {
		Mockito.when(userDAO.getUserBalance(1)).thenReturn(BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, userService.getUserBalance(1));
	}

	@Test
	void testGetUserStatus() throws UserServiceException, DAOException {
		Mockito.when(userDAO.getUserStatus(1)).thenReturn(true);
		assertTrue(userService.getUserStatus(1));
	}

	@Test
	void testConvertUserFormToUser() {
		testUser.setId(0);
		assertEquals(testUser.toString(),
				userService.convertUserFormToUser(testUserForm, "yYMg0RzhJNM=", "password").toString());
	}

	@Test
	void testDAOExceptionsLogin() throws DAOException {
		Mockito.when(userDAO.getUser("login", "e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9"))
				.thenThrow(DAOException.class);
		Mockito.when(daoFactory.getUserDAO().getSalt("login")).thenReturn("yYMg0RzhJNM=");
		assertThrows(UserServiceException.class, () -> userService.login("login", "password"));
	}

	@Test
	void testDAOExceptionsRegister()
			throws DAOException, NoSuchMethodException, SecurityException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mockito.when(userDAO.insert(testUser)).thenThrow(DAOException.class);
		@SuppressWarnings("unchecked")
		Constructor<UserServiceImpl> userServiceConstructor = (Constructor<UserServiceImpl>) Class
				.forName(UserServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		UserServiceImpl userServiceImpl = Mockito.spy(userServiceConstructor.newInstance(daoFactory));
		try (MockedStatic<PasswordUtil> passwdUtil = Mockito.mockStatic(PasswordUtil.class)) {
			passwdUtil.when(() -> PasswordUtil.getRandomString(12)).thenReturn("yYMg0RzhJNM=");
			passwdUtil.when(() -> PasswordUtil.getRandomString(10)).thenReturn("password");
			doReturn(testUser).when(userServiceImpl).convertUserFormToUser(testUserForm, "yYMg0RzhJNM=", "password");
			assertThrows(UserServiceException.class, () -> userServiceImpl.registerUser(testUserForm));
		}
	}

	@Test
	void testDAOExceptionGetAllSubscribers() throws DAOException {
		Mockito.when(userDAO.getAllSubscriber()).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.getAllSubscribers());
	}

	@Test
	void testDAOExceptionGetAllUnblockedSubscriber() throws DAOException {
		Mockito.when(userDAO.getAllUnblockedSubscriber()).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.getAllUnblockedSubscribers());
	}

	@Test
	void testDAOExceptionGetSubscribersForChargin() throws DAOException {
		Mockito.when(userDAO.getSubscriberForCharging()).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.getSubscriberForCharging());
	}

	@Test
	void testDAOExceptionViewSubscribers() throws DAOException {
		Mockito.when(userDAO.getSubscriberForView("", 0, 5)).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.viewSubscribers("", 1, 5));
	}

	@Test
	void testDAOExceptionGetSubscribersNumber() throws DAOException {
		Mockito.when(userDAO.getSubscriberNumber("")).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.getSubscribersNumber(""));
	}

	@Test
	void testDAOExceptionGetUserById() throws DAOException {
		Mockito.when(userDAO.get(1)).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.getUserById(1));
	}

	@Test
	void testDAOExceptionChangeUserStatus() throws DAOException {
		Mockito.when(userDAO.changeBlocked(true, 0)).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.changeUserStatus(false, 0));
	}

	@Test
	void testDAOExceptionChangeUserBalance() throws DAOException {
		Mockito.when(userDAO.get(1)).thenReturn(testUser);
		Mockito.when(transactionDAO.changeUserBalance(1, BigDecimal.ONE, "desc")).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.changeUserBalance(1, BigDecimal.ONE, "desc"));
	}

	@Test
	void testDAOExceptionChangePassword() throws DAOException {
		try (MockedStatic<PasswordUtil> passwdUtil = Mockito.mockStatic(PasswordUtil.class)) {
			passwdUtil.when(() -> PasswordUtil.getRandomString(12)).thenReturn("Ne3Sa9lrat=");
			passwdUtil.when(() -> PasswordUtil.getRandomString(10)).thenReturn("newPassword");
			passwdUtil.when(() -> PasswordUtil.hashPassword("passwordyYMg0RzhJNM="))
			.thenReturn("e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9");
			Mockito.when(userDAO.get(1)).thenReturn(testUser);
			testUser.setPassword("e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9");
			testUser.setSalt("yYMg0RzhJNM=");
			Mockito.when(userDAO.changePassword(1, PasswordUtil.hashPassword("newPasswordNe3Sa9lrat="), "Ne3Sa9lrat="))
			.thenThrow(DAOException.class);
			assertThrows(UserServiceException.class, () ->  userService.changePassword(1, "password", "newPassword"));
			
		}
	}
	
	@Test
	void testDAOExpcetionResetPassword() throws DAOException {
		try (MockedStatic<PasswordUtil> passwdUtil = Mockito.mockStatic(PasswordUtil.class)) {
			passwdUtil.when(() -> PasswordUtil.getRandomString(12)).thenReturn("Ne3Sa9lrat=");
			passwdUtil.when(() -> PasswordUtil.getRandomString(10)).thenReturn("newPassword");
			String hashedPassword = PasswordUtil.hashPassword("newPasswordNe3Sa9lrat=");
			Mockito.when(userDAO.changePassword("email", hashedPassword, "Ne3Sa9lrat=")).thenThrow(DAOException.class);
			assertThrows(UserServiceException.class, () ->  userService.resetPassword("email"));

		}
	}
	
	@Test
	void testDAOExceptionAddTariffToUser() throws DAOException{
		Mockito.when(transactionDAO.chargeUserForTariffUsing(1, 1, "For using tariff tariffName.")).thenThrow(DAOException.class);
		Mockito.when(tariffDAO.get(1)).thenReturn(tariff);
		Mockito.when(userDAO.get(1)).thenReturn(testUser);
		Mockito.when(userDAO.getUserBalance(1)).thenReturn(BigDecimal.TEN);
		assertThrows(UserServiceException.class, () -> userService.addTariffToUser(1, 1));
	}
	
	@Test
	void testDAOExceptionRemoveTariffFromUser() throws DAOException {
		Mockito.when(userDAO.removeTariffFromUser(1, 1)).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, () -> userService.removeTariffFromUser(1, 1));
	}
	
	@Test
	void testDAOExceptionGetUserBalance() throws DAOException {
		Mockito.when(userDAO.getUserBalance(1)).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, ()-> userService.getUserBalance(1)); 
	}
	
	@Test
	void testDAOExceptionGetUserStatus() throws DAOException {
		Mockito.when(userDAO.getUserStatus(1)).thenThrow(DAOException.class);
		assertThrows(UserServiceException.class, ()-> userService.getUserStatus(1)); 
	}
}

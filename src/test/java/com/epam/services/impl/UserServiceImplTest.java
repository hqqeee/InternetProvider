package com.epam.services.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.dao.mariadb.impl.UserDAOMariaDB;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAOReadException;
import com.epam.exception.dao.DAORecordAlreadyExistsException;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.UserService;
import com.epam.services.forms.UserForm;
import com.epam.util.AppContext;

class UserServiceImplTest {

	private User testUser = new User(2, "password", "yYMg0RzhJNM=", "login", 2, false, "email@dot.com",
			"First", "Last", "City", "Address", BigDecimal.ZERO);
	private UserService userService;
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private TransactionDAO transactionDAO;
	
	@Mock
	DAOFactory daoFactory;
	
	
	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MockitoAnnotations.openMocks(this);
	
		Constructor<UserService> userServiceConstructor = (Constructor<UserService>) Class
				.forName(UserServiceImpl.class.getName()).getDeclaredConstructor(DAOFactory.class);
		userServiceConstructor.setAccessible(true);
		userService = userServiceConstructor.newInstance(daoFactory); 
		
	}
	
	@Test
	void testLoginUserExists() throws UserServiceException{
		
		try {
			Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
			Mockito.when(daoFactory.getUserDAO()
					.getUser("login", "e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9"))
			.thenReturn(testUser);
			Mockito.when(daoFactory.getUserDAO().getSalt("login")).thenReturn("yYMg0RzhJNM=");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		
		try {
			assertEquals(testUser, userService.login("login", "password"));
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testLoginUserNotExists(){
		
		try {
			Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
			Mockito.when(daoFactory.getUserDAO()
					.getUser("login", "e3be1c3aae45bcdf77890c7557f7c424ef8b945fad5ed0c6bb9668deac075cd9"))
			.thenReturn(testUser);
			Mockito.when(daoFactory.getUserDAO().getSalt("login")).thenReturn("yYMg0RzhJNM=");
		} catch (DAOException e) {
			e.printStackTrace();
		}
		assertThrows(UserNotFoundException.class,() -> userService.login("login", "password1"));
	}
	
	
	
	@Test
	void testRegisterUserInvalideInput1(){
		UserForm userForm = new UserForm();
		userForm.setFirstName("");
		userForm.setLastName("");
		userForm.setCity("");
		userForm.setEmail("");
		userForm.setAddress("");
		userForm.setLogin("");
		ValidationErrorException thrown = assertThrows(ValidationErrorException.class,() -> userService.registerUser(userForm, "123"));
		assertEquals(thrown.getErrors().size(), 8);
	}
	
	@Test
	void testRegisterUserInvalideInput2(){
		UserForm userForm = new UserForm();
		userForm.setFirstName("12345678901234567");
		userForm.setLastName("12345678901234567");
		userForm.setCity("1234567890123456712345678901234567");
		userForm.setEmail("123456789012345671234567890123456712345678901234567");
		userForm.setAddress("1234567890123456712345678901234567123456789012345671234567890123456712345678901234567");
		userForm.setLogin("1234567890123456712345678901234567");
		ValidationErrorException thrown = assertThrows(ValidationErrorException.class,() -> userService.registerUser(userForm, "1234567890123456712345678901234567123456789012345671234567890123456712345678901234567"));
		assertEquals(thrown.getErrors().size(), 8);
	}
	
	@Test
	void testRegisterUserInvalideEmail(){
		UserForm userForm = new UserForm();
		userForm.setFirstName("firstName");
		userForm.setLastName("lastName");
		userForm.setCity("City");
		userForm.setEmail("asdf@ads.a");
		userForm.setAddress("asdf");
		userForm.setLogin("asdfa");
		ValidationErrorException thrown = assertThrows(ValidationErrorException.class,() -> userService.registerUser(userForm, "12345678"));
		assertEquals(thrown.getErrors().size(), 1);
	}
	
	@Test
	void testRegisterUser() throws UserAlreadyExistException, ValidationErrorException, UserServiceException{
		UserForm userForm = new UserForm();
		userForm.setFirstName("First");
		userForm.setLastName("Last");
		userForm.setCity("City");
		userForm.setEmail("email@dot.com");
		userForm.setAddress("Address");
		userForm.setLogin("login");
		String password = "password";
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		userService.registerUser(userForm, password);
	}

	@Test
	void testGetAllSubscribers() throws DAOException, UserServiceException{
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		users.add(new User());
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.getAllSubscriber()).thenReturn(users);
		assertEquals(users, userService.getAllSubscribers());
	}
	

	@Test
	void testGetAllUnblocedSubscribers() throws DAOException, UserServiceException{
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		users.add(new User());
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.getAllUnblockedSubscriber()).thenReturn(users);
		assertEquals(users, userService.getAllUnblockedSubscribers());
	}
	

	@Test
	void testGetAllubscribersForCharging() throws DAOException, UserServiceException{
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		users.add(new User());
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.getSubscriberForCharging()).thenReturn(users);
		assertEquals(users, userService.getSubscriberForCharging());
	}
	
	@Test
	void testViewSubscribers() throws DAOException, UserServiceException{
		List<User> users = new ArrayList<User>();
		users.add(testUser);
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.getSubscriberForView("",0,5)).thenReturn(users);
		assertEquals(5, userService.viewSubscribers("", 1,5).size());
	}
	
	@Test
	void testGetSubscriberNumber() throws DAOException, UserServiceException {
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.getSubscriberNumber("")).thenReturn(5);
		assertEquals(5, userService.getSubscribersNumber(""));
	}
	
	@Test
	void testGetUserById() throws DAOException, UserNotFoundException, UserServiceException {
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.get(1)).thenReturn(testUser);
		assertEquals(testUser, userService.getUserById(1));
	}
	
	@Test
	void testGetUserByIdNotFount() throws DAOException, UserServiceException {
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(userDAO.get(1)).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
	}
	
	@Test
	void testRemoveUser() {
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		try {
			userService.removeUser(1);
		} catch (UserServiceException e) {
			fail();
		}
	}
	
	@Test
	void testChangeUserStatus() {
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		try {
			userService.changeUserStatus(true, 1);
		} catch (UserServiceException e) {
			fail();
		}
	}
	
	@Test
	void testChangeUserBalance() {
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		Mockito.when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
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
		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
		assertThrows(UserServiceException.class, () -> userService.changeUserBalance(1, BigDecimal.ZERO, "12345678901234567890123456789012345678901234567890"
				+ "123456789012345678901234567890123456789012345678901234567890"
				+ "12345678901234567890123456789012345678901234567890"
				+ "123456789012345678901234567890123456789012345678901234567890"));
			
		}
	
}

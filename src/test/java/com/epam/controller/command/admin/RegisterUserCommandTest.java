package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.services.dto.UserForm;
import com.epam.util.AppContext;

class RegisterUserCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);
	
	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);
	
	@Mock
	UserService userService = mock(UserService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);

	@BeforeEach
	public void setup() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mockito.when(req.getAttribute("locale")).thenReturn(new Locale("en"));
	}
	
	@Test
	public void testValidationException() {
		Mockito.when(req.getParameter("firstName")).thenReturn(null);
		Mockito.when(req.getParameter("lastName")).thenReturn(null);
		Mockito.when(req.getParameter("login")).thenReturn(null);
		Mockito.when(req.getParameter("email")).thenReturn(null);
		Mockito.when(req.getParameter("city")).thenReturn(null);
		Mockito.when(req.getParameter("address")).thenReturn(null);
		
		assertEquals(Page.USER_REGISTRATION_PAGE, new RegisterUserCommand().execute(req, resp));
	}
	
	@Test
	public void testUserAlreadyExistsException() throws UserAlreadyExistException, UserServiceException {
		Mockito.when(req.getParameter("firstName")).thenReturn("firstName");
		Mockito.when(req.getParameter("lastName")).thenReturn("lastName");
		Mockito.when(req.getParameter("login")).thenReturn("login");
		Mockito.when(req.getParameter("email")).thenReturn("email@mail.com");
		Mockito.when(req.getParameter("city")).thenReturn("city");
		Mockito.when(req.getParameter("address")).thenReturn("address");
		UserForm userForm = new UserForm("firstName", "lastName", "login", "email@mail.com", "city", "address");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			doThrow(UserAlreadyExistException.class).when(userService).registerUser(userForm);
			assertEquals(Page.USER_REGISTRATION_PAGE, new RegisterUserCommand().execute(req, resp));
		}
	}
	
	@Test
	public void testUserServiceException() throws UserAlreadyExistException, UserServiceException {
		Mockito.when(req.getParameter("firstName")).thenReturn("firstName");
		Mockito.when(req.getParameter("lastName")).thenReturn("lastName");
		Mockito.when(req.getParameter("login")).thenReturn("login");
		Mockito.when(req.getParameter("email")).thenReturn("email@mail.com");
		Mockito.when(req.getParameter("city")).thenReturn("city");
		Mockito.when(req.getParameter("address")).thenReturn("address");
		UserForm userForm = new UserForm("firstName", "lastName", "login", "email@mail.com", "city", "address");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			doThrow(UserServiceException.class).when(userService).registerUser(userForm);
			assertEquals(Page.USER_REGISTRATION_PAGE, new RegisterUserCommand().execute(req, resp));
		}
	}
	
	@Test
	public void testException() throws UserAlreadyExistException, UserServiceException {
		Mockito.when(req.getParameter("firstName")).thenReturn("firstName");
		Mockito.when(req.getParameter("lastName")).thenReturn("lastName");
		Mockito.when(req.getParameter("login")).thenReturn("login");
		Mockito.when(req.getParameter("email")).thenReturn("email@mail.com");
		Mockito.when(req.getParameter("city")).thenReturn("city");
		Mockito.when(req.getParameter("address")).thenReturn("address");
		UserForm userForm = new UserForm("firstName", "lastName", "login", "email@mail.com", "city", "address");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			doThrow(NumberFormatException.class).when(userService).registerUser(userForm);
			assertEquals(Page.USER_REGISTRATION_PAGE, new RegisterUserCommand().execute(req, resp));
		}
	}
	
	@Test
	public void testSuccess() {
		Mockito.when(req.getParameter("firstName")).thenReturn("firstName");
		Mockito.when(req.getParameter("lastName")).thenReturn("lastName");
		Mockito.when(req.getParameter("login")).thenReturn("login");
		Mockito.when(req.getParameter("email")).thenReturn("email@mail.com");
		Mockito.when(req.getParameter("city")).thenReturn("city");
		Mockito.when(req.getParameter("address")).thenReturn("address");
		UserForm userForm = new UserForm("firstName", "lastName", "login", "email@mail.com", "city", "address");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			assertEquals(Page.REDIRECTED, new RegisterUserCommand().execute(req, resp));
		}
		
	}

}

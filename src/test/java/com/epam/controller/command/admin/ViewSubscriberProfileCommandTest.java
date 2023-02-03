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
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class ViewSubscriberProfileCommandTest {

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
	void testViewSubscriberProfile() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			assertEquals(Page.PROFILE_PAGE, new ViewSubscriberProfileCommand().execute(req, resp));
		}
	}

	@Test
	void testViewSubscriberProfileUserServiceException() throws UserNotFoundException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			doThrow(UserServiceException.class).when(userService).getUserById(1);
			assertEquals(new AdminMenuCommand().execute(req, resp), new ViewSubscriberProfileCommand().execute(req, resp));
		}
	}
	
	@Test
	void testViewSubscriberProfileUserNotFoundException() throws UserNotFoundException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("1");
			doThrow(UserNotFoundException.class).when(userService).getUserById(1);
			assertEquals(new AdminMenuCommand().execute(req, resp), new ViewSubscriberProfileCommand().execute(req, resp));
		}
	}
	
	@Test
	void testViewSubscriberProfileException() throws UserNotFoundException, UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("1asdf");
			assertEquals(new AdminMenuCommand().execute(req, resp), new ViewSubscriberProfileCommand().execute(req, resp));
		}
	}
}

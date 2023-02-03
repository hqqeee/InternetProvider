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
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class AdminMenuCommandTest {


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
	void testAdminMenu1() throws UserServiceException {
		Mockito.when(req.getParameter("page")).thenReturn("2");
		Mockito.when(req.getParameter("rowNumber")).thenReturn("5");
		Mockito.when(req.getParameter("searchField")).thenReturn("qwe");
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			doThrow(UserServiceException.class).when(userService).viewSubscribers("qwe",2,5);
			assertEquals(Page.ADMIN_MENU_PAGE, new AdminMenuCommand().execute(req, resp));
		}
	}
	
	@Test
	void testAdminMenu2() throws UserServiceException {
		Mockito.when(req.getParameter("page")).thenReturn("2asdf");
		Mockito.when(req.getParameter("rowNumber")).thenReturn("5asdf");
		Mockito.when(req.getParameter("searchField")).thenReturn("qwe");
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			assertEquals(Page.ADMIN_MENU_PAGE, new AdminMenuCommand().execute(req, resp));
		}
	}

}

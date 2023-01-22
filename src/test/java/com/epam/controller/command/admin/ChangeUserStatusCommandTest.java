package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class ChangeUserStatusCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);
	
	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);
	
	@Mock
	UserService userService = mock(UserService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);
	
	@Test
	void testChangeUserStatusUserServiceException() throws UserServiceException {
		Mockito.when(req.getParameter("userBlocked")).thenReturn("true");
		Mockito.when(req.getParameter("userId")).thenReturn("1");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			doThrow(UserServiceException.class).when(userService).changeUserStatus(true,1);
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserStatusCommand().execute(req, resp));
		}
	}

	@Test
	void testChangeUserStatusException() {
		Mockito.when(req.getParameter("userBlocked")).thenReturn("asdfsdaf");
		Mockito.when(req.getParameter("userId")).thenReturn("1asdf");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			assertEquals(new AdminMenuCommand().execute(req, resp), new ChangeUserStatusCommand().execute(req, resp));
		}
	}
	
	@Test
	void testChangeUserStatusSuccess() {
		Mockito.when(req.getParameter("userBlocked")).thenReturn("true");
		Mockito.when(req.getParameter("userId")).thenReturn("1");
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			assertEquals(Page.REDIRECTED, new ChangeUserStatusCommand().execute(req, resp));
		}
	}
}

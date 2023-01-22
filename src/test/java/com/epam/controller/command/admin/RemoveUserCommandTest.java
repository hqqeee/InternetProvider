package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class RemoveUserCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);

	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);

	@Mock
	UserService userService = mock(UserService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);

	@Test
	void testRemoveUserCommand() {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("2");
			assertEquals(Page.REDIRECTED, new RemoveUserCommand().execute(req, resp));
		}
	}

	@Test
	void testRemoveUserCommandUserServiceException() throws UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("2");
			doThrow(UserServiceException.class).when(userService).removeUser(2);
			assertEquals(new AdminMenuCommand().execute(req, resp), new RemoveUserCommand().execute(req, resp));
		}
	}
	@Test
	void testRemoveUserCommandException() throws UserServiceException {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(req.getParameter("userId")).thenReturn("2asdf");
			assertEquals(new AdminMenuCommand().execute(req, resp), new RemoveUserCommand().execute(req, resp));
		}
	}
}

package com.epam.controller.command.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.services.UserService;
import com.epam.util.AppContext;

class ResetPasswordCommandTest {

	private ResetPasswordCommand resetPasswordCommand;

	@Mock
	private HttpServletRequest request = mock(HttpServletRequest.class);

	@Mock
	private HttpServletResponse response = mock(HttpServletResponse.class);

	@Mock
	private UserService userService = mock(UserService.class);

	@Mock
	private AppContext appContext = mock(AppContext.class);

	@BeforeEach
	public void setup() {
		resetPasswordCommand = new ResetPasswordCommand();
		Mockito.when(request.getAttribute("locale")).thenReturn(new Locale("en"));
	}

	@Test
	void testExecuteSuccess() throws Exception {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			when(appContext.getUserService()).thenReturn(userService);
			when(request.getParameter("resetEmail")).thenReturn("test@example.com");
			when(request.getContextPath()).thenReturn("");

			String result = resetPasswordCommand.execute(request, response);

			verify(userService).resetPassword("test@example.com");
			verify(response).sendRedirect(Page.HOME_PAGE + "?success=reset_password");
			assertEquals(Page.REDIRECTED, result);
		}
	}

	@Test
	void testExecuteFailure() throws Exception {
		try (MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)) {
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			when(request.getParameter("resetEmail")).thenReturn("test@example.com");
			when(request.getAttribute("locale")).thenReturn(Locale.US);

			String result = resetPasswordCommand.execute(request, response);

			assertEquals(Page.HOME_PAGE, result);
		}
	}
}
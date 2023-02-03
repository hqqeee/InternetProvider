package com.epam.controller.command.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.epam.controller.command.Page;

class LogoutCommandTest {
  private LogoutCommand logoutCommand;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private HttpSession session;
  
  @BeforeEach
  void setUp() {
    logoutCommand = new LogoutCommand();
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    session = mock(HttpSession.class);
  }
  
  @Test
  void testExecute_WithValidSession() throws Exception {
    when(request.getSession()).thenReturn(session);
    doNothing().when(session).invalidate();
    doNothing().when(response).sendRedirect(Page.HOME_PAGE + "?success=logout");
    String expectedResult = Page.REDIRECTED;
    String actualResult = logoutCommand.execute(request, response);
    assertEquals(expectedResult, actualResult);
  }
  
  @Test
  void testExecute_WithoutSession() throws Exception {
    when(request.getSession()).thenReturn(null);
    String expectedResult = Page.HOME_PAGE;
    String actualResult = logoutCommand.execute(request, response);
    assertEquals(expectedResult, actualResult);
  }
}
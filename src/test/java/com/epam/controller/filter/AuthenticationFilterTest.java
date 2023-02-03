package com.epam.controller.filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.controller.command.CommandNames;
import com.epam.services.dto.Role;
import com.epam.services.dto.UserDTO;

class AuthenticationFilterTest {

	@Mock
	private HttpServletRequest request = mock(HttpServletRequest.class);
	@Mock
	private HttpSession session = mock(HttpSession.class);
	@Mock
	private ServletResponse response = mock(ServletResponse.class);
	@Mock
	private FilterChain chain = mock(FilterChain.class);

	private AuthenticationFilter authenticationFilter;

	@BeforeEach
	public void setup() {
		authenticationFilter = new AuthenticationFilter();
	}

	@Test
    void checkAccess_ValidAdminCommand_ShouldReturnTrue() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn(CommandNames.ADMIN_MENU);
        when(request.getSession()).thenReturn(session);
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(Role.ADMIN);
        when(session.getAttribute("loggedUser")).thenReturn(userDTO);
        
        assertTrue(authenticationFilter.checkAccess(request));
    }

	@Test
    void checkAccess_InvalidAdminCommand_ShouldReturnFalse() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn(CommandNames.VIEW_PROFILE);
        when(request.getSession()).thenReturn(session);
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(Role.ADMIN);
        when(session.getAttribute("loggedUser")).thenReturn(userDTO);
        
        assertFalse(authenticationFilter.checkAccess(request));
    }

	@Test
    void checkAccess_ValidUserCommand_ShouldReturnTrue() throws ServletException, IOException {
            when(request.getParameter("action")).thenReturn(CommandNames.VIEW_PROFILE);
            when(request.getSession()).thenReturn(session);
            UserDTO userDTO = new UserDTO();
            userDTO.setRole(Role.SUBSCRIBER);
            when(session.getAttribute("loggedUser")).thenReturn(userDTO);
            
            assertTrue(authenticationFilter.checkAccess(request));
        }

	@Test
        void checkAccess_InvalidUserCommand_ShouldReturnFalse() throws ServletException, IOException {
            when(request.getParameter("action")).thenReturn(CommandNames.ADMIN_MENU);
            when(request.getSession()).thenReturn(session);
            UserDTO userDTO = new UserDTO();
            userDTO.setRole(Role.SUBSCRIBER);
            when(session.getAttribute("loggedUser")).thenReturn(userDTO);
            
            assertFalse(authenticationFilter.checkAccess(request));
        }

	@Test
        void checkAccess_UnauthenticatedUser_ShouldReturnFalse() throws ServletException, IOException {
            when(request.getParameter("action")).thenReturn(CommandNames.VIEW_PROFILE);
            when(request.getSession()).thenReturn(session);
            when(session.getAttribute("loggedUser")).thenReturn(null);
            
            assertFalse(authenticationFilter.checkAccess(request));
        }
}

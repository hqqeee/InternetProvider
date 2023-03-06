package com.epam.controller.command.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.epam.controller.command.Page;


class ChangeLanguageCommandTest {

    private ChangeLanguageCommand changeLanguageCommand;

    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);

    @Mock
    private HttpServletResponse response = mock(HttpServletResponse.class);

    @BeforeEach
	public void setup() {
    	changeLanguageCommand = new ChangeLanguageCommand();
		
	}


    @Test
    void testExecuteWithValidLanguage() {
    	Mockito.when(request.getAttribute("locale")).thenReturn(new Locale("en"));
        when(request.getParameter("lang")).thenReturn("en");
        doNothing().when(response).addCookie(any(Cookie.class));
        when(request.getContextPath()).thenReturn("/");

        String actualResult = changeLanguageCommand.execute(request, response);
        assertEquals(Page.REDIRECTED, actualResult);
        verify(response).addCookie(any(Cookie.class));
    }


}
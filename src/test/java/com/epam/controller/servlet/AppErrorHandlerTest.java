package com.epam.controller.servlet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.epam.controller.command.Page;

class AppErrorHandlerTest {

    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);
    
    @Mock
    private HttpServletResponse response = mock(HttpServletResponse.class);
    
    @Mock
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
    
    private AppErrorHandler appErrorHandler;
    
    @BeforeEach
    void setup() {
        appErrorHandler = new AppErrorHandler();
    }
    
    
    @Test
    void testProcessErrorSuccess() throws ServletException, IOException {
        Throwable throwable = new Throwable("Exception message");
        when(request.getRequestDispatcher(Page.HOME_PAGE)).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        when(request.getAttribute("javax.servlet.error.exception")).thenReturn(throwable);
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(500);
        when(request.getAttribute("javax.servlet.error.request_uri")).thenReturn("requestUri");
        
        appErrorHandler.doGet(request, response);
        
        List<String> expectedError = new ArrayList<>();
        expectedError.add("Something went wrong.");
        expectedError.add("Exception Name: java.lang.Throwable");
        expectedError.add("Requested URI: requestUri");
        expectedError.add("Exception Message:Exception message");
        
        verify(request, times(1)).setAttribute("errorMessages", expectedError);
    }
    

    @Test
    void testProcessErrorUnknown() throws ServletException, IOException {
        Throwable throwable = new Throwable("Exception message");
        when(request.getRequestDispatcher(Page.HOME_PAGE)).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        when(request.getAttribute("javax.servlet.error.exception")).thenReturn(throwable);
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(123);
        when(request.getAttribute("javax.servlet.error.request_uri")).thenReturn(null);
        
        appErrorHandler.doPost(request, response);
        
        List<String> expectedError = new ArrayList<>();
        expectedError.add("Something went wrong.");
        expectedError.add("Status Code: 123");
        expectedError.add("Requested URI: Unknown");
        
        verify(request, times(1)).setAttribute("errorMessages", expectedError);
    }
    
}

package com.epam.controller.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class LocaleSetFilterTest {


    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);

    @Mock
    private ServletResponse response = mock(ServletResponse.class);

    @Mock
    private FilterChain chain = mock(FilterChain.class);

    private LocaleSetFilter filter = new LocaleSetFilter();

    @Test
    void doFilter_NoCookies_DefaultLocaleSet() throws IOException, ServletException {
        when(request.getCookies()).thenReturn(null);
        
        filter.doFilter(request, response, chain);
        
        verify(request).setAttribute("locale", new Locale("en"));
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_CookiesWithLang_LocaleSetFromCookie() throws IOException, ServletException {
        Cookie[] cookies = new Cookie[] { new Cookie("lang", "fr") };
        when(request.getCookies()).thenReturn(cookies);
        
        filter.doFilter(request, response, chain);
        
        verify(request).setAttribute("locale", new Locale("fr"));
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_CookiesWithoutLang_DefaultLocaleSet() throws IOException, ServletException {
        Cookie[] cookies = new Cookie[] { new Cookie("notLang", "value") };
        when(request.getCookies()).thenReturn(cookies);
        
        filter.doFilter(request, response, chain);
        
        verify(request).setAttribute("locale", new Locale("en"));
        verify(chain).doFilter(request, response);
    }

}

package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.epam.controller.command.Page;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.TariffServiceException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.TariffService;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.util.AppContext;

class DailyWithdrawCommandTest {

	@Mock
	HttpServletRequest req = mock(HttpServletRequest.class);
	
	@Mock
	HttpServletResponse resp = mock(HttpServletResponse.class);
	
	@Mock
	UserService userService = mock(UserService.class);
	
	@Mock
	TariffService tariffService = mock(TariffService.class);

	@Mock
	AppContext appContext = mock(AppContext.class);
	
	@Test
	void testDailyWithdrawCommand() throws TariffServiceException, UserServiceException, NegativeUserBalanceException {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			List<UserDTO> subscribersForChargins = new ArrayList<>();
			UserDTO user1 = new UserDTO(1, false, "login1", BigDecimal.TEN, "fisrtName", "lastName", "asd@asda.add",
					"city","address", Role.SUBSCRIBER);
			UserDTO user2 = new UserDTO(2, false, "login2", BigDecimal.ZERO, "fisrtName", "lastName", "asd@asda.add",
					"city","address", Role.SUBSCRIBER);	
			subscribersForChargins.add(user1);
			subscribersForChargins.add(user2);
			Mockito.when(userService.getSubscriberForCharging()).thenReturn(subscribersForChargins);
			List<TariffDTO> usersUnpaidTariffs = new ArrayList<>();
			TariffDTO tariff1 = new TariffDTO(1, "name", "desc", 13, BigDecimal.ONE, Service.IP_TV);
			usersUnpaidTariffs.add(tariff1);
			Mockito.when(tariffService.getUnpaidTariffs(1)).thenReturn(usersUnpaidTariffs);
			Mockito.when(tariffService.getUnpaidTariffs(2)).thenReturn(usersUnpaidTariffs);
			doThrow(NegativeUserBalanceException.class).when(userService).chargeUserForTariffsUsing(2, usersUnpaidTariffs);
			assertEquals(Page.HOME_PAGE, new DailyWithdrawCommand().execute(req, resp));
		}
	}
	
	@Test
	void testDailyWithdrawCommandUserServiceException() throws UserServiceException {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			doThrow(UserServiceException.class).when(userService).getSubscriberForCharging();
			assertEquals(Page.HOME_PAGE, new DailyWithdrawCommand().execute(req, resp));
		}
	}
	
	@Test
	void testDailyWithdrawCommandTariffServiceException() throws  TariffServiceException {
		try(MockedStatic<AppContext> appContextStatic = Mockito.mockStatic(AppContext.class)){
			appContextStatic.when(() -> AppContext.getInstance()).thenReturn(appContext);
			Mockito.when(appContext.getUserService()).thenReturn(userService);
			Mockito.when(appContext.getTariffService()).thenReturn(tariffService);
			doThrow(TariffServiceException.class).when(tariffService).updateDaysUntilPayments();
			assertEquals(Page.HOME_PAGE, new DailyWithdrawCommand().execute(req, resp));
		}
	}

}

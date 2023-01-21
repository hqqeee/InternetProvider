package com.epam.services;

import java.math.BigDecimal;
import java.util.List;

import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.services.dto.UserForm;

public interface UserService {
	public UserDTO login(String login, String password) throws UserNotFoundException, UserServiceException;

	public void registerUser(UserForm userForm) throws UserAlreadyExistException, UserServiceException;

	public List<UserDTO> getAllSubscribers() throws UserServiceException;

	public List<UserDTO> getAllUnblockedSubscribers() throws UserServiceException;

	public List<UserDTO> getSubscriberForCharging() throws UserServiceException;

	public List<UserDTO> viewSubscribers(String searchField, int page, int entriesPerPage) throws UserServiceException;

	public UserDTO getUserById(int userId) throws UserNotFoundException, UserServiceException;

	public BigDecimal getUserBalance(int userId) throws UserServiceException;

	public boolean getUserStatus(int userId) throws UserServiceException;

	public int getSubscribersNumber(String searchField) throws UserServiceException;

	public void removeUser(int userId) throws UserServiceException;

	public void changeUserStatus(boolean blocked, int id) throws UserServiceException;

	public void changeUserBalance(int userId, BigDecimal diffrence, String description)
			throws UserServiceException, NegativeUserBalanceException;

	public void chargeUserForTariffsUsing(int userId, List<TariffDTO> unpaidTariffs)
			throws UserServiceException, NegativeUserBalanceException;

	public void changePassword(int userId, String currentPassword, String newPassword)
			throws UserServiceException, UserNotFoundException, PasswordNotMatchException;

	public void resetPassword(String email) throws UserServiceException;

	public void addTariffToUser(int userId, int tariffId)
			throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException;

	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException;
}

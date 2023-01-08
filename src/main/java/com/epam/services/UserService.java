package com.epam.services;

import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UnableToRemoveUser;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.forms.UserForm;


public interface UserService {
	public User login(String login, String password) throws UserNotFoundException;
	public void registerUser(UserForm userForm, String password) throws UserAlreadyExistException, ValidationErrorException;
	public List<User> getAllSubscribers() throws UserServiceException;
	public List<User> getAllUnblockedSubscribers() throws UserServiceException;
	public List<User> getSubscriberForCharging() throws UserServiceException;
	public List<User> viewSubscribers(String searchField, int page, int entriesPerPage);
	public User getUserById(int userId) throws UserNotFoundException, UserServiceException;
	public BigDecimal getUserBalance(int userId) throws UserServiceException;
	public boolean getUserStatus(int userId) throws UserServiceException;
	public int getSubscribersNumber(String searchField);
	public void removeUser(int userId) throws UnableToRemoveUser;
	public void changeUserStatus(boolean blocked, int id) throws UserServiceException;
	public void changeUserBalance(int userId, BigDecimal diffrence, String description) throws UserServiceException, NegativeUserBalanceException;
	public void chargeUserForTariffsUsing(int userId, List<Tariff> unpaidTariffs) throws UserServiceException, NegativeUserBalanceException;
	public void changePassword(int userId, String currentPassword, String newPassword) throws ValidationErrorException, UserServiceException, UserNotFoundException, PasswordNotMatchException;
	public void addTariffToUser(int userId, int tariffId) throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException ;
	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException;
}

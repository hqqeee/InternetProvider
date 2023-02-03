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

/**
 * 
 * User Service interface.
 * It contains methods to interact with the DAO and has some business logic.
 * 
 * @author ruslan
 *
 */

public interface UserService {
	
	/**
	 * This method takes a credential and checks if a user with those credentials exists, if so returns a UserDTo for that user
	 * @param login login of the user.
	 * @param password password of the user.
	 * @return return UserDTO of the user with this credentials.
	 * @throws UserNotFoundException is thrown when user with these credentials does not exists.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public UserDTO login(String login, String password) throws UserNotFoundException, UserServiceException;

	/**
	 * This method takes the user form and tries to add user to DAO. It generates random password for the user
	 * and sends it to user's email.
	 * @param userForm contains all necessary fields to add new subscriber. It should be valid, method not validate this form.
	 * @throws UserAlreadyExistException is thrown when user is already in the persistent layer.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void registerUser(UserForm userForm) throws UserAlreadyExistException, UserServiceException;

	/**
	 * This method returns all subscribers from the persistent layer.
	 * @return all subscribers from the persistent layer.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<UserDTO> getAllSubscribers() throws UserServiceException;

	/**
	 * This method returns all subscribers from the persistent layer that has unblocked status.
	 * @return all subscribers from the persistent layer that has unblocked status.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<UserDTO> getAllUnblockedSubscribers() throws UserServiceException;

	/**
	 * This method returns all subscribers from the persistent layer that has unblocked status and has tariffs with days
	 * until next payment equals to 0.
	 * @return all subscribers from the persistent layer that has unblocked status and has tariffs with days until next payment equals to 0.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<UserDTO> getSubscriberForCharging() throws UserServiceException;

	/**
	 * This method return filtered and sorted by some parameters list of UserDTO.
	 * @param searchField A string that represents the sortable field.(e.g. "name", "rate")
	 * @param page A number of the page to view. Starts from 1.
	 * @param entriesPerPage A number of the records in the page.
	 * @return List of UserDTO for given sorting and filtering parameters.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public List<UserDTO> viewSubscribers(String searchField, int page, int entriesPerPage) throws UserServiceException;

	/**
	 * This method return UserDTO form from the  persistent layer for specific user id.
	 * @param userId id of the user to get.
	 * @return UserDTO for user with this id.
	 * @throws UserNotFoundException is thrown when user with this id not exists
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public UserDTO getUserById(int userId) throws UserNotFoundException, UserServiceException;

	/**
	 * This method returns the balance of the user with specific uses id.
	 * @param userId the user id to get the balance of which.
	 * @return return BigDecimal that represents balance of this user.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public BigDecimal getUserBalance(int userId) throws UserServiceException;

	/**
	 * This method checks the blocked status of the user with specific uses id.
	 * @param userId the user id to checks the blocked status of which.
	 * @return true if user is blocked, false if unblocked.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public boolean getUserStatus(int userId) throws UserServiceException;

	/**
	 * Returns the number of the subscribers that contains some text in their login. 
	 * @param searchField part of the login to search.
	 * @return number of subscriber that contains some text in their login. 
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public int getSubscribersNumber(String searchField) throws UserServiceException;

	/**
	 * This method removes the user with the specified ID from the persistent layer.
	 * @param userId id of the to remove.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void removeUser(int userId) throws UserServiceException;

	/**
	 * This method changes the status of the user.
	 * @param blocked new user status. True if blocked, false if unblocked
	 * @param id id of the user.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void changeUserStatus(boolean blocked, int id) throws UserServiceException;

	/**
	 * This method changes the balance of the user.
	 * @param userId id of the user to change balance.
	 * @param difference the difference by which to change the balance
	 * @param description description of the transaction.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 * @throws NegativeUserBalanceException is thrown when user's will be negative after transaction.
	 */
	public void changeUserBalance(int userId, BigDecimal difference, String description)
			throws UserServiceException, NegativeUserBalanceException;

	/**
	 * This method charges the user a fee for using the tariff.
	 * @param userId id of the user to be charged.
	 * @param unpaidTariffs list of Tariffs for which the user have to pay.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 * @throws NegativeUserBalanceException is thrown when user's will be negative after transaction.
	 */
	public void chargeUserForTariffsUsing(int userId, List<TariffDTO> unpaidTariffs)
			throws UserServiceException, NegativeUserBalanceException;

	/**
	 * This method changes user's password. 
	 * @param userId id of the user whose password will be changed.
	 * @param currentPassword current password of the user.
	 * @param newPassword new user password.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 * @throws UserNotFoundException is thrown when user with this id not exists
	 * @throws PasswordNotMatchException is thrown when current user's password is incorrect.
	 */
	public void changePassword(int userId, String currentPassword, String newPassword)
			throws UserServiceException, UserNotFoundException, PasswordNotMatchException;

	/**
	 * This method change user password for random one and sends this password to the provided email.
	 * @param email email of the user which password will be reseted.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void resetPassword(String email) throws UserServiceException;

	/**
	 * This method adds record for user has tariff.
	 * @param userId id of the user who will be added to the tariff.
	 * @param tariffId id of the tariff who will be added to the user.
	 * @throws UserAlreadyHasTariffException is thrown when record with this userId and tariffId already exists.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 * @throws NegativeUserBalanceException is thrown when user doesn't have enough balance to pay for first tariff period.
	 */
	public void addTariffToUser(int userId, int tariffId)
			throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException;

	/**
	 * This method removes record about user has tariff.
	 * @param userId id of the user.
	 * @param tariffId id of the tariff.
	 * @throws UserServiceException is thrown when something wrong happens in the DAO layer.
	 */
	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException;
}

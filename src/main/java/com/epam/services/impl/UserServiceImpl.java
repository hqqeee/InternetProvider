package com.epam.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.TariffDAO;
import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.entity.Tariff;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAORecordAlreadyExistsException;
import com.epam.exception.services.NegativeUserBalanceException;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.services.dto.UserForm;
import com.epam.util.EmailUtil;
import com.epam.util.PasswordUtil;

/**
 * 
 * User Service implementation. It contains methods to interact with the DAO and
 * has some business logic.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */

public class UserServiceImpl implements UserService {

	private DAOFactory daoFactory;

	/**
	 * A constructor that assigns a DAO Factory.
	 * 
	 * @param daoFactory DAO Factory impl.
	 */
	private UserServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * This method takes a credential and checks if a user with those credentials
	 * exists, if so returns a UserDTo for that user. It uses PasswordUtil to hash
	 * password.
	 * 
	 * @param login    login of the user.
	 * @param password password of the user.
	 * @return return UserDTO of the user with this credentials.
	 * @throws UserNotFoundException is thrown when user with these credentials does
	 *                               not exists.
	 * @throws UserServiceException  is thrown when something wrong happens in the
	 *                               DAO layer.
	 */
	@Override
	public UserDTO login(String login, String password) throws UserNotFoundException, UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			String hashedPassword = PasswordUtil.hashPassword(password + userDAO.getSalt(login));
			User user = userDAO.getUser(login, hashedPassword);
			if (user == null) {
				throw new UserNotFoundException(login);
			} else {
				return convertUserToUserDTO(user);
			}
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get user with login " + login + " and password " + password + ".",e);
		}
	}

	/**
	 * This method takes the user form and tries to add user to DAO. It generates
	 * random password for the user and sends it to user's email. It uses
	 * PasswordUtil to generate password for the user.
	 * 
	 * @param userForm contains all necessary fields to add new subscriber. It
	 *                 should be valid, method not validate this form.
	 * @throws UserAlreadyExistException is thrown when user is already in the
	 *                                   persistent layer.
	 * @throws UserServiceException      is thrown when something wrong happens in
	 *                                   the DAO layer.
	 */
	@Override
	public void registerUser(UserForm userForm) throws UserAlreadyExistException, UserServiceException {
		String salt = PasswordUtil.getRandomString(12);
		String password = PasswordUtil.getRandomString(10);
		User user = convertUserFormToUser(userForm, salt, password);
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.insert(user);
			EmailUtil.INSTANCE.addRegistrationEmailToQueue(userForm.getEmail(), userForm.getLogin(), password);
			EmailUtil.INSTANCE.sendMails();
		} catch (DAORecordAlreadyExistsException e) {
			if (e.getMessage().contains("email")) {
				throw new UserAlreadyExistException("email", userForm.getEmail());
			} else if (e.getMessage().contains("login")) {
				throw new UserAlreadyExistException("login", userForm.getLogin());
			}
		} catch (DAOException e) {
			throw new UserServiceException("Cannot register user with login " + userForm.getLogin() + ".", e);
		}

	}

	/**
	 * This method returns all subscribers from the persistent layer.
	 * 
	 * @return all subscribers from the persistent layer.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public List<UserDTO> getAllSubscribers() throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getAllSubscriber();
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get all subscribers.", e);
		}
		return subscribersDTOs;
	}

	/**
	 * This method returns all subscribers from the persistent layer that has
	 * unblocked status.
	 * 
	 * @return all subscribers from the persistent layer that has unblocked status.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public List<UserDTO> getAllUnblockedSubscribers() throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getAllUnblockedSubscriber();
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get all unblocked subscribers.", e);
		}
		return subscribersDTOs;
	}

	/**
	 * This method returns all subscribers from the persistent layer that has
	 * unblocked status and has tariffs with days until next payment equals to 0.
	 * 
	 * @return all subscribers from the persistent layer that has unblocked status
	 *         and has tariffs with days until next payment equals to 0.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public List<UserDTO> getSubscriberForCharging() throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getSubscriberForCharging();
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get all subscribers for charging.", e);
		}
		return subscribersDTOs;
	}

	/**
	 * This method return filtered and sorted by some parameters list of UserDTO.
	 * 
	 * @param searchField    A string that represents the sortable field.(e.g.
	 *                       "name", "rate")
	 * @param page           A number of the page to view. Starts from 1.
	 * @param entriesPerPage A number of the records in the page.
	 * @return List of UserDTO for given sorting and filtering parameters.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public List<UserDTO> viewSubscribers(String searchField, int page, int entriesPerPage) throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getSubscriberForView(searchField, ((page - 1) * entriesPerPage),
					entriesPerPage);
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get users for view with pararameters: serachField " + searchField
					+ " page " + page + " entriesPerPage " + entriesPerPage + ".", e);
		}
		return subscribersDTOs;
	}

	/**
	 * Returns the number of the subscribers that contains some text in their login.
	 * 
	 * @param searchField part of the login to search.
	 * @return number of subscriber that contains some text in their login.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public int getSubscribersNumber(String searchField) throws UserServiceException {
		int result = 0;
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			result = userDAO.getSubscriberNumber(searchField);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get number of subscriber by searchField " + searchField + ".", e);
		}
		return result;
	}

	/**
	 * This method return UserDTO form from the persistent layer for specific user
	 * id.
	 * 
	 * @param userId id of the user to get.
	 * @return UserDTO for user with this id.
	 * @throws UserNotFoundException is thrown when user with this id not exists
	 * @throws UserServiceException  is thrown when something wrong happens in the
	 *                               DAO layer.
	 */
	@Override
	public UserDTO getUserById(int userId) throws UserNotFoundException, UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			User user = userDAO.get(userId);
			if (user == null) {
				throw new UserNotFoundException("Cannot get user with id " + userId + ".");
			}
			return convertUserToUserDTO(user);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get user by id " + userId);
		}
	}

	/**
	 * This method removes the user with the specified ID from the persistent layer.
	 * 
	 * @param userId id of the to remove.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public void removeUser(int userId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			User user = new User();
			user.setId(userId);
			userDAO.delete(user);
		} catch (DAOException e) {
			throw new UserServiceException("Unable to remove user with id " + userId, e);
		}

	}

	/**
	 * This method changes the status of the user.
	 * 
	 * @param blocked new user status. True if blocked, false if unblocked
	 * @param id      id of the user.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public void changeUserStatus(boolean blocked, int id) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.changeBlocked(!blocked, id);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot change status for user with id: " + id, e);
		}

	}

	/**
	 * This method changes the balance of the user.
	 * 
	 * @param userId      id of the user to change balance.
	 * @param difference  the difference by which to change the balance
	 * @param description description of the transaction.
	 * @throws UserServiceException         is thrown when something wrong happens
	 *                                      in the DAO layer.
	 * @throws NegativeUserBalanceException is thrown when user's will be negative
	 *                                      after transaction.
	 */
	@Override
	public void changeUserBalance(int userId, BigDecimal difference, String description)
			throws UserServiceException, NegativeUserBalanceException {
		if (description.length() > 128) {
			throw new UserServiceException("Description is too long");
		}
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
			if ((userDAO.get(userId).getBalance().add(difference)).signum() < 0) {
				throw new NegativeUserBalanceException();
			}
			transactionDAO.changeUserBalance(userId, difference, description);
		} catch (DAOException e) {
			throw new UserServiceException();
		}

	}

	/**
	 * This method changes user's password.
	 * 
	 * @param userId          id of the user whose password will be changed.
	 * @param currentPassword current password of the user.
	 * @param newPassword     new user password.
	 * @throws UserServiceException      is thrown when something wrong happens in
	 *                                   the DAO layer.
	 * @throws UserNotFoundException     is thrown when user with this id not exists
	 * @throws PasswordNotMatchException is thrown when current user's password is
	 *                                   incorrect.
	 */
	@Override
	public void changePassword(int userId, String currentPassword, String newPassword)
			throws UserServiceException, UserNotFoundException, PasswordNotMatchException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			User user = userDAO.get(userId);
			if (user == null)
				throw new UserNotFoundException(userId);
			String oldHashedPassword = PasswordUtil.hashPassword(currentPassword + user.getSalt());
			if (!oldHashedPassword.equals(user.getPassword()))
				throw new PasswordNotMatchException();
			String salt = PasswordUtil.getRandomString(12);
			String newHashedPassword = PasswordUtil.hashPassword(newPassword + salt);
			userDAO.changePassword(userId, newHashedPassword, salt);
		} catch (DAOException e) {
			throw new UserServiceException("Something went wrong with DAO. User password was not changed.");
		}
	}

	/**
	 * This method change user password for random one and sends this password to
	 * the provided email.
	 * 
	 * @param email email of the user which password will be reseted.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public void resetPassword(String email) throws UserServiceException {
		String salt = PasswordUtil.getRandomString(12);
		String password = PasswordUtil.getRandomString(10);
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			if (userDAO.changePassword(email, PasswordUtil.hashPassword(password + salt), salt) != 0) {
				String login = userDAO.getLoginByEmail(email);
				if (login != null) {
					EmailUtil.INSTANCE.addSendNewPasswordEmailToQueue(email, login, password);
					EmailUtil.INSTANCE.sendMails();
				}
			}
		} catch (DAOException e) {
			throw new UserServiceException("Cannot reset password for user with email " + email + ".", e);
		}

	}

	/**
	 * This method adds record for user has tariff.
	 * 
	 * @param userId   id of the user who will be added to the tariff.
	 * @param tariffId id of the tariff who will be added to the user.
	 * @throws UserAlreadyHasTariffException is thrown when record with this userId
	 *                                       and tariffId already exists.
	 * @throws UserServiceException          is thrown when something wrong happens
	 *                                       in the DAO layer.
	 * @throws NegativeUserBalanceException  is thrown when user doesn't have enough
	 *                                       balance to pay for first tariff period.
	 */
	@Override
	public void addTariffToUser(int userId, int tariffId)
			throws UserAlreadyHasTariffException, UserServiceException, NegativeUserBalanceException {

		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			TariffDAO tariffDAO = daoFactory.getTariffDAO();
			Tariff tariff = tariffDAO.get(tariffId);
			TariffDTO tariffDTO = new TariffDTO(tariff.getId(), tariff.getName(), tariff.getDescription(),
					tariff.getPaymentPeriod(), tariff.getRate(), Service.valueOf(tariff.getServiceId()));
			if (userDAO.getUserBalance(userId).compareTo(tariff.getRate()) < 0) {
				throw new NegativeUserBalanceException();
			}
			userDAO.addTariffToUser(userId, tariffId);
			processChargingTransaction(userId, tariffDTO);
		} catch (DAORecordAlreadyExistsException e) {
			throw new UserAlreadyHasTariffException(e.getMessage(), e);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot add tariff(ID - " + tariffId + " to user(ID - " + userId + ".");
		}

	}

	/**
	 * This method charges the user a fee for using the tariff.
	 * 
	 * @param userId        id of the user to be charged.
	 * @param unpaidTariffs list of Tariffs for which the user have to pay.
	 * @throws UserServiceException         is thrown when something wrong happens
	 *                                      in the DAO layer.
	 * @throws NegativeUserBalanceException is thrown when user's will be negative
	 *                                      after transaction.
	 */
	@Override
	public void chargeUserForTariffsUsing(int userId, List<TariffDTO> unpaidTariffs)
			throws UserServiceException, NegativeUserBalanceException {
		BigDecimal amountToPay = unpaidTariffs.stream().map(TariffDTO::getRate).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		User user = null;
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.get(userId);
			if (user.getBalance().compareTo(amountToPay) < 0) {
				throw new NegativeUserBalanceException(amountToPay.subtract(user.getBalance()));
			}
		} catch (DAOException e) {
			throw new UserServiceException("Cannot charge user for tariffs using.", e);
		}
		for (TariffDTO tariff : unpaidTariffs) {
			processChargingTransaction(userId, tariff);
		}
	}

	/**
	 * This method creates description and calls transaction DAO methods to charg
	 * user for tariff using.
	 * 
	 * @param userId id of the user
	 * @param tariff id of the tariff
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	private void processChargingTransaction(int userId, TariffDTO tariff) throws UserServiceException {
		String description = "For using tariff " + tariff.getName() + ".";
		try {
			TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
			transactionDAO.chargeUserForTariffUsing(userId, tariff.getId(), description);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot charger user with id " + userId + " for tariff " + tariff + ".", e);
		}
	}

	/**
	 * This method removes record about user has tariff.
	 * 
	 * @param userId   id of the user.
	 * @param tariffId id of the tariff.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.removeTariffFromUser(userId, tariffId);
		} catch (DAOException e) {
			throw new UserServiceException(
					"Cannot remove tariff with id " + userId + " from user with id " + userId + ".", e);
		}
	}

	/**
	 * This method returns the balance of the user with specific uses id.
	 * 
	 * @param userId the user id to get the balance of which.
	 * @return return BigDecimal that represents balance of this user.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public BigDecimal getUserBalance(int userId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			return userDAO.getUserBalance(userId);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get user balance.", e);
		}
	}

	/**
	 * This method checks the blocked status of the user with specific uses id.
	 * 
	 * @param userId the user id to checks the blocked status of which.
	 * @return true if user is blocked, false if unblocked.
	 * @throws UserServiceException is thrown when something wrong happens in the
	 *                              DAO layer.
	 */
	@Override
	public boolean getUserStatus(int userId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			return userDAO.getUserStatus(userId);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get status of the user.", e);
		}
	}

	/**
	 * This meth takes user(DAO) entity and converts it to UserDTO
	 * 
	 * @param user DAO user entity.
	 * @return UserDTO
	 */
	protected UserDTO convertUserToUserDTO(User user) {
		return new UserDTO(user.getId(), user.isBlocked(), user.getLogin(), user.getBalance(), user.getFirstName(),
				user.getLastName(), user.getEmail(), user.getCity(), user.getAddress(), Role.valueOf(user.getRoleId()));
	}

	/**
	 * This method creates User form UserForm, salt(for hashing) and password.
	 * 
	 * @param userForm userForm to create user.
	 * @param salt     salt for hashing.
	 * @param password hashed password.
	 * @return return User(DAO) entity.
	 */
	protected User convertUserFormToUser(UserForm userForm, String salt, String password) {
		return new User(0, PasswordUtil.hashPassword(password + salt), salt, userForm.getLogin(), 2, false,
				userForm.getEmail(), userForm.getFirstName(), userForm.getLastName(), userForm.getCity(),
				userForm.getAddress(), BigDecimal.ZERO);
	}

}

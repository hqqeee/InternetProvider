package com.epam.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.UserService;
import com.epam.services.dto.Role;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffDTO;
import com.epam.services.dto.UserDTO;
import com.epam.services.dto.UserForm;
import com.epam.util.EmailUtil;
import com.epam.util.PasswordUtil;

public class UserServiceImpl implements UserService {

	private DAOFactory daoFactory;

	private UserServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public UserDTO login(String login, String password) throws UserNotFoundException, UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			String hashedPassword = PasswordUtil.hashPassword(password + userDAO.getSalt(login));
			User user = userDAO.getUser(login, hashedPassword);
			if (user == null) {
				throw new UserNotFoundException(login);
			} else {
				System.out.println("Successful login");
				return convertUserToUserDTO(user);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			System.out.println(e);
			throw new UserServiceException("Cannot get user with login " + login + " and password " + password + ".");
		}
	}

	@Override
	public void registerUser(UserForm userForm)
			throws UserAlreadyExistException, ValidationErrorException, UserServiceException {
		validateUser(userForm);
		String salt = PasswordUtil.getRandomString(12);
		String password = PasswordUtil.getRandomString(10);
		EmailUtil.INSTANCE.sendRegistrationEmail(userForm.getEmail(), userForm.getLogin(), password);
		User user = new User(0, PasswordUtil.hashPassword(password + salt), salt, userForm.getLogin(), 2, false,
				userForm.getEmail(), userForm.getFirstName(), userForm.getLastName(), userForm.getCity(),
				userForm.getAddress(), BigDecimal.ZERO);
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.insert(user);
		} catch (DAORecordAlreadyExistsException e) {
			if (e.getMessage().contains("email")) {
				throw new UserAlreadyExistException("email", userForm.getEmail());
			} else if (e.getMessage().contains("email")) {
				throw new UserAlreadyExistException("login", userForm.getLogin());
			}
		} catch (DAOException e) {
			throw new UserServiceException(userForm.getLogin());
		}

	}

	@Override
	public List<UserDTO> getAllSubscribers() throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getAllSubscriber();
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			System.out.println(e);
			throw new UserServiceException("Cannot get all subscribers.", e);
		}
		return subscribersDTOs;
	}

	@Override
	public List<UserDTO> getAllUnblockedSubscribers() throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getAllUnblockedSubscriber();
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			System.out.println(e);
			throw new UserServiceException("Cannot get all unblocked subscribers.", e);
		}
		return subscribersDTOs;
	}

	@Override
	public List<UserDTO> getSubscriberForCharging() throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getSubscriberForCharging();
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			System.out.println(e);
			throw new UserServiceException("Cannot get all subscribers for charging.", e);
		}
		return subscribersDTOs;
	}

	@Override
	public List<UserDTO> viewSubscribers(String searchField, int page, int entriesPerPage) throws UserServiceException {
		List<UserDTO> subscribersDTOs = new ArrayList<>();
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			List<User> subscribers = userDAO.getSubscriberForView(searchField, ((page - 1) * entriesPerPage),
					entriesPerPage);
			subscribers.forEach(u -> subscribersDTOs.add(convertUserToUserDTO(u)));
		} catch (DAOException e) {
			e.printStackTrace();
			System.out.println(e);
			throw new UserServiceException("Cannot get users for view with pararameters: serachField " + searchField
					+ " page " + page + " entriesPerPage " + entriesPerPage + ".", e);
		}
		return subscribersDTOs;
	}

	@Override
	public int getSubscribersNumber(String searchField) throws UserServiceException {
		int result = 0;
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			result = userDAO.getSubscriberNumber(searchField);
		} catch (DAOException e) {
			e.printStackTrace();
			System.out.println(e);
			throw new UserServiceException("Cannot get number of subscriber by searchField " + searchField + ".", e);
		}
		return result;
	}

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

	@Override
	public void changeUserStatus(boolean blocked, int id) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.changeBlocked(!blocked, id);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot change status for user with id: " + id, e);
		}

	}

	@Override
	public void changeUserBalance(int userId, BigDecimal diffrence, String description)
			throws UserServiceException, NegativeUserBalanceException {
		if (description.length() > 128) {
			throw new UserServiceException("Description is too long");
		}
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
			if ((userDAO.get(userId).getBalance().add(diffrence)).signum() < 0) {
				throw new NegativeUserBalanceException();
			}
			transactionDAO.changeUserBalance(userId, diffrence, description);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UserServiceException();
		}

	}

	@Override
	public void changePassword(int userId, String currentPassword, String newPassword)
			throws ValidationErrorException, UserServiceException, UserNotFoundException, PasswordNotMatchException {
		try {
			{
				List<String> errors = new ArrayList<>();
				validatePassword(newPassword, errors);
				if (!errors.isEmpty())
					throw new ValidationErrorException(errors);
			}
			UserDAO userDAO = daoFactory.getUserDAO();
			User user = userDAO.get(userId);
			if (user == null)
				throw new UserNotFoundException(userId);
			String oldHashedPassword = PasswordUtil.hashPassword(currentPassword + user.getSalt());
			System.out.println(currentPassword);
			System.out.println(user.getSalt());
			if (!oldHashedPassword.equals(user.getPassword()))
				throw new PasswordNotMatchException();
			String salt = PasswordUtil.getRandomString(12);
			String newHashedPassword = PasswordUtil.hashPassword(newPassword + salt);
			userDAO.changePassword(userId, newHashedPassword, salt);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UserServiceException("Something went wrong with DAO. User password was not changed.");
		}
	}

	@Override
	public void resetPassword(String email) throws UserServiceException {
		String salt = PasswordUtil.getRandomString(12);
		String password = PasswordUtil.getRandomString(10);
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			if (userDAO.changePassword(email, PasswordUtil.hashPassword(password + salt), salt) != 0) {
				String login = userDAO.getLoginByEmail(email);
				if (login != null) {
					EmailUtil.INSTANCE.sendNewPassword(email, login, password);
				} else {
					System.out.println("Attempt to resety not existing user.");
				}

			} else {
				System.out.println("Attempt to resety not existing user.");
			}
		} catch (DAOException e) {
			throw new UserServiceException("Cannot reset password for user with email " + email + ".", e);
		}

	}

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
			e.printStackTrace();
			throw new UserServiceException("Cannot add tariff(ID - " + tariffId + " to user(ID - " + userId + ".");
		}

	}

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
				throw new NegativeUserBalanceException();
			}
		} catch (DAOException e) {
			throw new UserServiceException("Cannot charge user for tariffs using.", e);
		}
		for (TariffDTO tariff : unpaidTariffs) {
			processChargingTransaction(userId, tariff);
		}
	}

	private void processChargingTransaction(int userId, TariffDTO tariff) throws UserServiceException {
		String description = "For using tariff " + tariff.getName() + ".";
		try {
			TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
			transactionDAO.chargeUserForTariffUsing(userId, tariff.getId(), description);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UserServiceException("Cannot charger user with id " + userId + " for tariff " + tariff + ".", e);
		}
	}

	@Override
	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.removeTariffFromUser(userId, tariffId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UserServiceException(
					"Cannot remove tariff with id " + userId + " from user with id " + userId + ".", e);
		}
	}

	private static void validateUser(UserForm form) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, form.getFirstName(), "First Name", 16);
		validateTextFieldValues(errors, form.getLastName(), "Last Name", 16);
		validateTextFieldValues(errors, form.getCity(), "City", 32);
		validateTextFieldValues(errors, form.getAddress(), "Address", 32);
		validateTextFieldValues(errors, form.getLogin(), "Login", 32);
		validateTextFieldValues(errors, form.getEmail(), "Email", 32);
		if (!Pattern.compile(
				"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
				.matcher(form.getEmail()).matches()) {
			errors.add("Incorrect email.");
		}
		if (errors.isEmpty())
			return;
		throw new ValidationErrorException(errors);

	}

	private static void validateTextFieldValues(List<String> errors, String fieldValue, String fieldName,
			int maxLength) {
		if (isEmpty(fieldValue)) {
			errors.add(fieldName + " is empty.");
		} else if (fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " must not exceed " + maxLength + " characters.");
		}
	}

	private static boolean isEmpty(String fieldValue) {
		return fieldValue == null || fieldValue.trim().isEmpty();
	}

	private static void validatePassword(String password, List<String> errors) {
		if (isEmpty(password)) {
			errors.add("Password cannot be empty");
		} else if (password.length() < 8) {
			errors.add("Password must be at least 8 characters long.");
		} else if (password.length() > 32) {
			errors.add("Password must not exceed 32 characters long.");
		}
	}

	@Override
	public BigDecimal getUserBalance(int userId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			return userDAO.getUserBalance(userId);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get user balance.", e);
		}
	}

	@Override
	public boolean getUserStatus(int userId) throws UserServiceException {
		try {
			UserDAO userDAO = daoFactory.getUserDAO();
			return userDAO.getUserStatus(userId);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot get status of the user.", e);
		}
	}

	private UserDTO convertUserToUserDTO(User user) {
		return new UserDTO(user.getId(), user.isBlocked(), user.getLogin(), user.getBalance(), user.getFirstName(),
				user.getLastName(), user.getEmail(), user.getCity(), user.getAddress(), Role.valueOf(user.getRoleId()));
	}

}

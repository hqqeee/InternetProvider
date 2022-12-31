package com.epam.services.impl;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import com.epam.dataaccess.dao.TransactionDAO;
import com.epam.dataaccess.dao.UserDAO;
import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;
import com.epam.exception.dao.DAORecordAlreadyExistsException;
import com.epam.exception.services.PasswordNotMatchException;
import com.epam.exception.services.UnableToRemoveUser;
import com.epam.exception.services.UserAlreadyExistException;
import com.epam.exception.services.UserAlreadyHasTariffException;
import com.epam.exception.services.UserNotFoundException;
import com.epam.exception.services.UserServiceException;
import com.epam.exception.services.ValidationErrorException;
import com.epam.services.UserService;
import com.epam.services.forms.UserForm;

public class UserServiceImpl implements UserService{

	private final UserDAO userDAO;
	private final TransactionDAO transactionDAO;
	
	
	
	public UserServiceImpl(UserDAO userDAO, TransactionDAO transactionDAO) {

		this.transactionDAO = transactionDAO;
		this.userDAO = userDAO;
	}


	@Override
	public User login(String login, String password) throws UserNotFoundException{
		try {
			String hashedPassword = hashPassword(password + userDAO.getSalt(login));
			User user = userDAO.getUser(login, hashedPassword);
			if(user == null) {
				throw new UserNotFoundException(login);
			}
			else {
				System.out.println("Seccsefull login");
				return user;
			}
		} catch (DAOException e) {
			System.out.println(e);
			throw new UserNotFoundException(login);
		}
	}

	@Override
	public void registerUser(UserForm userForm, String password)
			throws UserAlreadyExistException, ValidationErrorException {
		validateUser(userForm , password);
		String salt = getSalt();
		User user = new User(
				0,
				hashPassword(password + salt),
				salt,
				userForm.getLogin(),
				2,
				false,
				userForm.getEmail(),
				userForm.getFirstName(),
				userForm.getLastName(),
				userForm.getCity(),
				userForm.getAddress(),
				BigDecimal.ZERO
				);
		try {
			userDAO.insert(user);
		} catch (DAOException e) {
			throw new UserAlreadyExistException(userForm.getLogin());
		}
		
	}
	
	@Override
	public List<User> getAllSubscribers() {
		List<User> subscribers = new ArrayList<>();
		try {
			subscribers = userDAO.getAllSubscriber();
		} catch(DAOException e) {
			System.out.println(e);
		}
		return subscribers;
	}
	
	

	@Override
	public List<User> viewSubscribers(String searchField, int page,
			int entriesPerPage) {
		List<User> subscribers = new ArrayList<>();
		try {
			subscribers = userDAO.getSubscriberForView(searchField, ((page - 1) * entriesPerPage), entriesPerPage);
		} catch(DAOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return subscribers;
	}
	

	@Override
	public int getSubscribersNumber(String searchField) {
		int result = 0;
		try {
			result = userDAO.getSubscriberNumber(searchField);
		} catch(DAOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return result;
	}
	

	@Override
	public void removeUser(int userId) throws UnableToRemoveUser {
		try {
			User user = new User();
			user.setId(userId);
			userDAO.delete(user);
		} catch(DAOException e) {
			throw new UnableToRemoveUser("Unable to remove user with id " + userId, e);
		}
		
	}
	

	@Override
	public void changeUserStatus(boolean blocked, int id) throws UserServiceException {
		try {
			userDAO.changeBlocked(!blocked, id);
		} catch (DAOException e) {
			throw new UserServiceException("Cannot change status for user with id: " + id, e);
		}
		
	}
	


	@Override
	public void changeUserBalance(int userId, BigDecimal diffrence, String description) throws UserServiceException {
		if(description.length() > 128) {
			throw new UserServiceException("Description is too long");
		}
		try {
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
				List<String> errors= new ArrayList<>();
				validatePassword(newPassword, errors);
				if(!errors.isEmpty()) throw new ValidationErrorException(errors);
			}
			User user = userDAO.get(userId);
			if(user == null) throw new UserNotFoundException(userId);
			String oldHashedPassword = hashPassword(currentPassword + user.getSalt());
			System.out.println(currentPassword);
			System.out.println(user.getSalt());
			if(!oldHashedPassword.equals(user.getPassword())) throw new PasswordNotMatchException();
			String salt = getSalt();
			String newHashedPassword = hashPassword(newPassword + salt);
			userDAO.changePassword(userId, newHashedPassword,salt);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UserServiceException("Something went wrong with DAO. User password was not changed.");
		} 
	}
	

	@Override
	public void addTariffToUser(int userId, int tariffId) throws UserAlreadyHasTariffException, UserServiceException {
	
			try {
				userDAO.addTariffToUser(userId, tariffId);
			}catch (DAORecordAlreadyExistsException e) {
				throw new UserAlreadyHasTariffException(e.getMessage(), e);
			}
			catch (DAOException e) {
				throw new UserServiceException("Cannot add tariff(ID - " +tariffId+" to user(ID - " + userId +".");
			}

		
	}
	

	@Override
	public void removeTariffFromUser(int userId, int tariffId) throws UserServiceException {
		try {
			userDAO.removeTariffFromUser(userId, tariffId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UserServiceException("Cannot remove tariff with id " + userId + " from user with id " + userId + ".",e );
		}
	}
	
	private String hashPassword(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		md.reset();
		md.update(password.getBytes());
		byte[] mdArray = md.digest();
		// Convert the array of bytes (which is 8 bits) to a string
		// of character (which is 16 bits in Java)
		StringBuilder sb = new StringBuilder(mdArray.length*2);
		for(byte b : mdArray) {
			int v = b & 0xff;
			if(v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}
	
	private static String getSalt() {
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[8];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	
	private static void validateUser(UserForm form, String password) throws ValidationErrorException{
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, form.getFirstName(), "First Name", 16);
		validateTextFieldValues(errors, form.getLastName(), "Last Name", 16);
		validateTextFieldValues(errors, form.getCity(), "City", 32);
		validateTextFieldValues(errors, form.getAddress(), "Address", 32);
		validateTextFieldValues(errors, form.getLogin(), "Login", 32);
		validateTextFieldValues(errors, form.getEmail(), "Email", 32);
		if(!Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
				.matcher(form.getEmail())
				.matches()
				) {
			errors.add("Incorrect email.");
		}
		validatePassword(password, errors);
		if(errors.isEmpty()) return;
		throw new ValidationErrorException(errors);
			
			
	}
	
	private static void validateTextFieldValues(List<String> errors, String fieldValue, String fieldName, int maxLength) {
		if(isEmpty(fieldValue)) {
			errors.add(fieldName + " is empty.");
		} else if(fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " must not exceed " + maxLength +" characters.");
		} 
	}
	
	private static boolean isEmpty(String fieldValue) {
		return fieldValue ==null || fieldValue.trim().isEmpty();
	}
	
	private static void validatePassword(String password, List<String> errors) {
		if(isEmpty(password)) {
			errors.add("Password cannot be empty");
		} else if(password.length() < 8) {
			errors.add("Password must be at least 8 characters long.");
		}
	}




}

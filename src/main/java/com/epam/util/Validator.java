package com.epam.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffForm;
import com.epam.services.dto.UserForm;

public final class Validator {

	public static void validateTariffForm(TariffForm tariffForm, ResourceBundle rb) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, tariffForm.getName(), "name", 32, rb);
		validateTextFieldValues(errors, tariffForm.getDescription(), "description", 255,rb);
		if (tariffForm.getService() == null || tariffForm.getService() == Service.ALL) {
			errors.add("Invalid service. Try again.");
		}
		if (tariffForm.getRate() == null || tariffForm.getRate().signum() <= 0) {
			errors.add("Invalid price. Price must be greater than 0.");
		}
		if (tariffForm.getPaymentPeriod() <= 0) {
			errors.add("Payment period must be greater than 0.");
		}
		if (errors.isEmpty()) {
			return;
		} else
			throw new ValidationErrorException(errors);
	}
	
	public static void validateUserForm(UserForm userForm, ResourceBundle rb) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, userForm.getFirstName(), "First Name", 16, rb);
		validateTextFieldValues(errors, userForm.getLastName(), "Last Name", 16, rb);
		validateTextFieldValues(errors, userForm.getCity(), "City", 32, rb);
		validateTextFieldValues(errors, userForm.getAddress(), "Address", 32, rb);
		validateLogin(userForm.getLogin(), errors, rb);
		validateEmail(userForm.getEmail(), errors, rb);		
		if (errors.isEmpty())
			return;
		throw new ValidationErrorException(errors);
	}
	
	public static void validatePassword(String password, ResourceBundle rb) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, password, "password", 32, rb);		
		if (errors.isEmpty() && password.length() < 8) {
			errors.add("Password must be at least 8 characters long.");
		} 
		if (errors.isEmpty())
			return;
		throw new ValidationErrorException(errors);
	}
	
	private static void validateLogin(String login, List<String> errors, ResourceBundle rb) throws ValidationErrorException {
		boolean isNullOrEmpty = validateTextFieldValues(errors, login, "login", 32, rb);	
		if (!isNullOrEmpty && !Pattern.compile(
				"[A-Za-z0-9]*$")
				.matcher(login).matches()) {
			errors.add("Login must contain only a-Z or 0-9 characters.");
		}
	}
	
	private static void validateEmail(String email, List<String> errors, ResourceBundle rb) throws ValidationErrorException {
		boolean isNullOrEmpty = validateTextFieldValues(errors, email, "email", 32, rb);	
		if (!isNullOrEmpty && !Pattern.compile(
				"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
				.matcher(email).matches()) {
			errors.add("Incorrect email.");
		}
	}
	
	private static boolean validateTextFieldValues(List<String> errors, String fieldValue, String fieldName, int maxLength,
			ResourceBundle rb) {
		if (isEmpty(fieldValue)) {
			errors.add(fieldName + " is empty.");
		} else if (fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " must not exceed " + maxLength + " characters.");
		} else {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String fieldValue) {
		return fieldValue == null || fieldValue.isBlank();
	}
}

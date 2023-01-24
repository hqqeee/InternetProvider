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
		validateTextFieldValues(errors, tariffForm.getName(), rb.getString("validator.tariff_name"), 32, rb);
		validateTextFieldValues(errors, tariffForm.getDescription(), rb.getString("tariffs.description"), 255,rb);
		if (tariffForm.getService() == null || tariffForm.getService() == Service.ALL) {
			errors.add(rb.getString("validator.invalid_service"));
		}
		if (tariffForm.getRate() == null || tariffForm.getRate().signum() <= 0) {
			errors.add(rb.getString("validator.invalid_price"));
		}
		if (tariffForm.getPaymentPeriod() <= 0) {
			errors.add(rb.getString("validator.invalid_payment_period"));
		}
		if (errors.isEmpty()) {
			return;
		} else
			throw new ValidationErrorException(errors);
	}
	
	public static void validateUserForm(UserForm userForm, ResourceBundle rb) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, userForm.getFirstName(), rb.getString("user_registration.first_name"), 16, rb);
		validateTextFieldValues(errors, userForm.getLastName(), rb.getString("user_registration.last_name"), 16, rb);
		validateTextFieldValues(errors, userForm.getCity(), rb.getString("user_registration.city"), 32, rb);
		validateTextFieldValues(errors, userForm.getAddress(), rb.getString("user_registration.address"), 32, rb);
		validateLogin(userForm.getLogin(), errors, rb);
		validateEmail(userForm.getEmail(), errors, rb);		
		if (errors.isEmpty())
			return;
		throw new ValidationErrorException(errors);
	}
	
	public static void validatePassword(String password, ResourceBundle rb) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, password, rb.getString("validator.password"), 32, rb);		
		if (errors.isEmpty() && password.length() < 8) {
			errors.add(rb.getString("validator.too_short_password"));
		} 
		if (errors.isEmpty())
			return;
		throw new ValidationErrorException(errors);
	}
	
	private static void validateLogin(String login, List<String> errors, ResourceBundle rb) throws ValidationErrorException {
		boolean isNullOrEmpty = validateTextFieldValues(errors, login, rb.getString("admin_menu.login"), 32, rb);	
		if (!isNullOrEmpty && !Pattern.compile(
				"[A-Za-z0-9]*$")
				.matcher(login).matches()) {
			errors.add(rb.getString("validator.invalid_login"));
		}
	}
	
	private static void validateEmail(String email, List<String> errors, ResourceBundle rb) throws ValidationErrorException {
		boolean isNullOrEmpty = validateTextFieldValues(errors, email, "email", 32, rb);	
		if (!isNullOrEmpty && !Pattern.compile(
				"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
				.matcher(email).matches()) {
			errors.add(rb.getString("validator.invalid_email"));
		}
	}
	
	private static boolean validateTextFieldValues(List<String> errors, String fieldValue, String fieldName, int maxLength,
			ResourceBundle rb) {
		if (isEmpty(fieldValue)) {
			errors.add(fieldName + " " + rb.getString("validator.is_empty"));
		} else if (fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " " + rb.getString("validator.must_not_exeed") +" " + maxLength + " " + rb.getString("validator.characters"));
		} else {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String fieldValue) {
		return fieldValue == null || fieldValue.isBlank();
	}
}

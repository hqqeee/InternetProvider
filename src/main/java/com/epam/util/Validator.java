package com.epam.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffForm;
import com.epam.services.dto.UserForm;

/**
 * 
 * The Validator class provides methods for validating UserForm, TariffForm and
 * password input and creating error messages informing about failed
 * validations.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public final class Validator {
	private Validator() {}
	/**
	 * 
	 * Validates a TariffForm and throws a ValidationErrorException if any errors
	 * are found.
	 * 
	 * @param tariffForm the TariffForm to be validated.
	 * @param rb         the ResourceBundle containing error messages.
	 * @throws ValidationErrorException if errors are found in the TariffForm.
	 */
	public static void validateTariffForm(TariffForm tariffForm, ResourceBundle rb) throws ValidationErrorException {
		List<String> errors = new ArrayList<>();
		validateTextFieldValues(errors, tariffForm.getName(), rb.getString("validator.tariff_name"), 32, rb);
		validateTextFieldValues(errors, tariffForm.getDescription(), rb.getString("tariffs.description"), 255, rb);
		if (tariffForm.getService() == null || tariffForm.getService() == Service.ALL) {
			errors.add(rb.getString("validator.invalid_service"));
		}
		if (tariffForm.getRate() == null || tariffForm.getRate().signum() <= 0) {
			errors.add(rb.getString("validator.invalid_price"));
		}
		if (tariffForm.getPaymentPeriod() <= 0) {
			errors.add(rb.getString("validator.invalid_payment_period"));
		}
		if (!errors.isEmpty()) {
			throw new ValidationErrorException(errors);
		} 
	}

	/**
	 * 
	 * Validates a UserForm and throws a ValidationErrorException if any errors are
	 * found.
	 * 
	 * @param userForm the UserForm to be validated.
	 * @param rb       the ResourceBundle containing error messages.
	 * @throws ValidationErrorException if errors are found in the UserForm.
	 */
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

	/**
	 * 
	 * Validates a password and throws a ValidationErrorException if any errors are
	 * found.
	 * 
	 * @param password the password to be validated.
	 * @param rb       the ResourceBundle containing error messages.
	 * @throws ValidationErrorException if errors are found in the password.
	 */
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

	/**
	 * 
	 * Validates a login and adds error messages to the provided list if any errors
	 * are found.
	 * 
	 * @param login  the login to be validated.
	 * @param errors the list of error messages.
	 * @param rb     the ResourceBundle containing error messages.
	 * @throws ValidationErrorException if errors are found in the login.
	 */
	private static void validateLogin(String login, List<String> errors, ResourceBundle rb){
		boolean isNullOrEmpty = validateTextFieldValues(errors, login, rb.getString("admin_menu.login"), 32, rb);
		if (!isNullOrEmpty && !Pattern.compile("[A-Za-z0-9]*$").matcher(login).matches()) {
			errors.add(rb.getString("validator.invalid_login"));
		}
	}

	/**
	 * 
	 * Validates an email and adds error messages to the provided list if any errors
	 * are found.
	 * 
	 * @param email  the email to be validated.
	 * @param errors the list of error messages.
	 * @param rb     the ResourceBundle containing error messages.
	 */
	private static void validateEmail(String email, List<String> errors, ResourceBundle rb){
		boolean isNullOrEmpty = validateTextFieldValues(errors, email, "email", 32, rb);
		if (!isNullOrEmpty && !Pattern.compile(
				"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
				.matcher(email).matches()) {
			errors.add(rb.getString("validator.invalid_email"));
		}
	}

	/**
	 * 
	 * The validateTextFieldValues method is used to validate a text field and add
	 * any errors to a list.
	 * 
	 * @param errors     A list of error messages to be populated with any
	 *                   validation errors found.
	 * @param fieldValue The value of the text field being validated.
	 * @param fieldName  The name of the field being validated, used in error
	 *                   messages.
	 * @param maxLength  The maximum allowed length of the fieldValue.
	 * @param rb         A resource bundle used to retrieve localized error
	 *                   messages.
	 * @return A boolean indicating whether the field is valid (false) or not
	 *         (true).
	 */
	private static boolean validateTextFieldValues(List<String> errors, String fieldValue, String fieldName,
			int maxLength, ResourceBundle rb) {
		if (isEmpty(fieldValue)) {
			errors.add(fieldName + " " + rb.getString("validator.is_empty"));
		} else if (fieldValue.trim().length() > maxLength) {
			errors.add(fieldName + " " + rb.getString("validator.must_not_exeed") + " " + maxLength + " "
					+ rb.getString("validator.characters"));
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * The isEmpty method is used to determine if a string is empty or consists only
	 * of whitespace.
	 * 
	 * @param fieldValue The string to be checked for emptiness.
	 * @return A boolean indicating whether the string is empty (true) or not
	 *         (false).
	 */
	public static boolean isEmpty(String fieldValue) {
		return fieldValue == null || fieldValue.isBlank();
	}
}

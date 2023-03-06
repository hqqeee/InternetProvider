package com.epam.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import com.epam.exception.services.ValidationErrorException;
import com.epam.services.dto.Service;
import com.epam.services.dto.TariffForm;
import com.epam.services.dto.UserForm;

class ValidatorTest {

	ResourceBundle rs = ResourceBundle.getBundle("lang", new Locale("en"));

	@Test
	void testValidateInvalidTariffForm1() {
		TariffForm invalidTariffForm = new TariffForm("", -10, BigDecimal.ONE.negate(), Service.ALL, "");
		ValidationErrorException ex = assertThrows(ValidationErrorException.class,
				() -> Validator.validateTariffForm(invalidTariffForm, rs));
		assertEquals(5, ex.getErrors().size());
	}

	@Test
	void testValidateInvalidTariffForm2() {
		TariffForm invalidTariffForm = new TariffForm("Ruaqwersadasdasndnasdnasndasndqweq", 0, BigDecimal.ZERO,
				Service.ALL,
				"asdasdasndnasdnasndasndqweqasdf12341234asdfasdfasdfasdfassdfasdfdfqwerqwerzsxdffasdfasdfjaksdfhjquwehriuqwhberiubzoixucvbuioasbdgfouiqbwqiouwebruioqwerbuiqwerbiuobdziuosbfasuibfauiobdfu1293804h1029834gh978absdf0bua89dsfbikasdfaiojskdfbaisdfoyiubqweirouqwoeiruqwoieuroqiwuerbqweiuor");
		ValidationErrorException ex = assertThrows(ValidationErrorException.class,
				() -> Validator.validateTariffForm(invalidTariffForm, rs));
		assertEquals(5, ex.getErrors().size());
	}

	@Test
	void testValidateInvalidTariffForm3() {
		TariffForm invalidTariffForm = new TariffForm(null, 0, null, null, null);
		ValidationErrorException ex = assertThrows(ValidationErrorException.class,
				() -> Validator.validateTariffForm(invalidTariffForm, rs));
		assertEquals(5, ex.getErrors().size());
	}

	@Test
	void testValidateValidForm() {
		TariffForm validTariffForm = new TariffForm("Tariff Name", 14, BigDecimal.ONE, Service.INTERNET,
				"Description 1 2 3 ");
		assertDoesNotThrow(() -> Validator.validateTariffForm(validTariffForm, rs));
	}

	@Test
	void testValidateInvalidUserForm1() {
		UserForm invalidUserForm = new UserForm(null, null, null, null, null, null);
		ValidationErrorException ex = assertThrows(ValidationErrorException.class,
				() -> Validator.validateUserForm(invalidUserForm, rs));
		assertEquals(6, ex.getErrors().size());
	}

	@Test
	void testValidateInvalidUserForm2() {
		UserForm invalidUserForm = new UserForm("", "", "фівафівайцй123?!", "asdf", "", "");
		ValidationErrorException ex = assertThrows(ValidationErrorException.class,
				() -> Validator.validateUserForm(invalidUserForm, rs));
		assertEquals(6, ex.getErrors().size());
	}

	@Test
	void testValidateInvalidUserForm3() {
		UserForm invalidUserForm = new UserForm("First Name", "Last Name", "^&**H(ASD?!", "asdf@asd.a", "City",
				"Address");
		ValidationErrorException ex = assertThrows(ValidationErrorException.class,
				() -> Validator.validateUserForm(invalidUserForm, rs));
		assertEquals(2, ex.getErrors().size());
	}

	@Test
	void testValidateValidUserForm() {
		UserForm invalidUserForm = new UserForm("First Name", "Last Name", "login", "asdf@asd.aa", "City", "Address");
		assertDoesNotThrow(() -> Validator.validateUserForm(invalidUserForm, rs));
	}

	@Test
	void testValidatePasswordInvalid1() {
		String password = null;
		assertThrows(ValidationErrorException.class, () -> Validator.validatePassword(password, rs));
	}
	

	@Test
	void testValidatePasswordInvalid2() {
		String password = "123asd";
		assertThrows(ValidationErrorException.class, () -> Validator.validatePassword(password, rs));
	}
	

	@Test
	void testValidatePasswordValid() {
		String password = "123as123d";
		assertDoesNotThrow(() -> Validator.validatePassword(password, rs));
	}

}

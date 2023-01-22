package com.epam.controller.command.admin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.epam.controller.command.Page;

class OpenUserRegistrationCommandTest {

	@Test
	void testOpenUserRegistration() {
		assertEquals(Page.USER_REGISTRATION_PAGE, new OpenUserRegistrationCommand().execute(null, null));
	}

}

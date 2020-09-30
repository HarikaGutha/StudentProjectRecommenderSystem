package com.prs.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * LoginControllerTest is to test the LoginController.
 * 
 * @author 190026870
 *
 */
@SpringBootTest
class LoginControllerTest {

	@Autowired
	private LoginController loginController;

	/**
	 * this method test if login page is displayed when given valid URL.
	 */
	@Test
	void loginSuccessTest() {
		assertNotNull(loginController.login());
		assertNotEquals("Loginin.html", loginController.login());
		assertEquals("Login.html", loginController.login());

	}

	/**
	 * This method tests error() method of the login controller.
	 */
	/*@Test
	void redirectErrorToLogin() {
		assertNotNull(loginController.error());
		assertNotEquals("Loginin.html", loginController.error());
		assertEquals("Login.html", loginController.error());
	}*/

	/**
	 * this method tests accessDenied() of the login controller.
	 */
	@Test
	void accessDeniedTest() {
		assertNotNull(loginController.accessDenied());
		assertEquals("AccessDenied.html", loginController.accessDenied());
	}

}

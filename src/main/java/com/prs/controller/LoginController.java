package com.prs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LoginController is a controller class for login page of the application and
 * is annotated with @Controller to indicate that the class serves the role of a
 * controller.
 * 
 * @author 190026870
 *
 */

@Controller
public class LoginController {

	/**
	 * login() method displays the login page of the application.
	 * 
	 * @return login page of the application
	 */
	@GetMapping({ "/", "/login" })
	public String login() {
		return "Login.html";
	}

	/**
	 * error() method redirects to login page.
	 * 
	 * @return login page of the application
	 */
	@GetMapping("/error")
	public String error() {
		return "Login.html";
	}

	/**
	 * accessDenied() method displays the accessDenied page.
	 * 
	 * @return AccessDenied page
	 */
	@GetMapping("/AccessDenied")
	public String accessDenied() {
		return "AccessDenied.html";
	}
}

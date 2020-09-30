package com.prs.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.prs.model.User;
import com.prs.repositories.UserRepository;

/**
 * CommonMethodsForController is a service class having common methods used by
 * various services.
 * 
 * @author 190026870
 *
 */
@Service
public class CommonMethodsForController {

	@Autowired
	private UserRepository userRepository;

	/**
	 * getCurrentLoggedInUser() returns the current logged in user name.
	 * 
	 * @return user name of the current logged in user
	 */
	public String getCurrentLoggedInUser() {
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();

		}
		return currentUserName;

	}

	/**
	 * getLoggedInUserImage() returns the current logged in user image.
	 * 
	 * @param currentUserName user name of the current logged in user
	 * @return current logged in user image
	 */
	public String getLoggedInUserImage(String currentUserName) {
		Optional<User> user = userRepository.findByUsername(currentUserName);
		String userImage = null;
		if (user != null) {
			byte[] image = user.get().getUserImage();
			userImage = new String(image);
		}
		return userImage;
	}

	/**
	 * getLoggedInFullUserName() return the name of logged in user.
	 * 
	 * @param currentUserName user name of logged in user
	 * @return name of the logged in user
	 */
	public String getLoggedInFullUserName(String currentUserName) {
		Optional<User> user = userRepository.findByUsername(currentUserName);
		String username = null;
		if (user != null) {
			username = user.get().getFirstName() + " " + user.get().getLastName();
		}
		return username;
	}

	/**
	 * getSupervisorImage() gets the image of specified supervisor.
	 * 
	 * @param username user name if the supervisor
	 * @return imgae of the supervisor
	 */
	public String getSupervisorImage(String username) {
		String supervisorImage = null;
		Optional<User> supervisor = userRepository.findByUsername(username);
		if (supervisor != null) {
			byte[] image = supervisor.get().getUserImage();
			supervisorImage = new String(image);

		}
		return supervisorImage;
	}

	/**
	 * getLoggedInUserRole() returns the role of logged in user.
	 * 
	 * @param username username of the logged in user
	 * @return role of the logged in user
	 */
	public String getLoggedInUserRole(String username) {
		String role = null;
		Optional<User> user = userRepository.findByUsername(username);
		if (user != null) {
			role = user.get().getRole().getRole();
		}
		return role;
	}

}

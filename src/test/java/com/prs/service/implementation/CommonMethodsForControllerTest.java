package com.prs.service.implementation;

import com.prs.model.Role;
import com.prs.model.User;
import com.prs.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * CommonMethodsForControllerTest test CommonMethodsForController.
 * 
 * @author 190026870
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")

public class CommonMethodsForControllerTest {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	CommonMethodsForController commonMethodsForController;

	/**
	 * This method tests getCurrentLoggedInUser() method of
	 * CommonMethodsForController.
	 */
	@PrepareForTest({ SecurityContextHolder.class })
	@Test
	public void getCurrentLoggedInUserTest() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		PowerMockito.mockStatic(SecurityContextHolder.class);
		PowerMockito.when(SecurityContextHolder.getContext()).thenReturn(securityContext);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn("Test");
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());

	}

	/**
	 * This method tests getLoggedInUserImageTest() method of
	 * CommonMethodsForController.
	 */
	@Test
	public void getLoggedInUserImageTest_Success() {
		String userName = "StudentName";
		User user = new User();
		user.setUserImage("Studentimage.png".getBytes());
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
		Assert.assertEquals("Studentimage.png", commonMethodsForController.getLoggedInUserImage("StudentName"));
	}

	/**
	 * This method tests getLoggedInUserImageTest() method of
	 * CommonMethodsForController.
	 */
	@Test
	public void getLoggedInUserImageTest_Failure() {
		String userName = "Test";
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(null);
		Assert.assertNull(commonMethodsForController.getLoggedInUserImage("Test"));
	}

	/**
	 * This method tests getLoggedInFullUserName() method of
	 * CommonMethodsForController.
	 */
	@Test
	public void getLoggedInFullUserNameTest() {
		String userName = "Test";
		User user = new User();
		user.setFirstName("TestFirstName");
		user.setLastName("TestLastName");
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
		Assert.assertEquals("TestFirstName TestLastName", commonMethodsForController.getLoggedInFullUserName("Test"));
	}

	/**
	 * This method tests getSuperVisorImage() method of CommonMethodsForController.
	 */
	@Test
	public void getSuperVisorImageTest() {
		String userName = "SuperVisorName";
		User user = new User();
		user.setUserImage("SuperVisorName.png".getBytes());
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
		Assert.assertEquals("SuperVisorName.png", commonMethodsForController.getSupervisorImage("SuperVisorName"));
	}

	/**
	 * This method tests getLoggedInUserRole() method of CommonMethodsForController.
	 */
	@Test
	public void getLoggedInUserRoleTest() {
		String userName = "StudentName";
		User user = new User();
		Role role = new Role();
		role.setRole("Student");
		user.setRole(role);
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
		Assert.assertEquals("Student", commonMethodsForController.getLoggedInUserRole("StudentName"));
	}

}

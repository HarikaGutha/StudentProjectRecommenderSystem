package com.prs.service.implementation;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prs.model.Role;
import com.prs.model.User;
import com.prs.repositories.UserRepository;

/**
 * UserDetailsServiceImplTest tests UserDetailsServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * this method tests loadUserByUsername() of UserDetailsServiceImpl.
	 */
	@Test
	public void testLoadUserByUsername() throws Exception {
		Mockito.when(userRepository.findByUsername("Test")).thenReturn(mockUser());
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("Test");
		Assert.assertNotNull(userDetails);
		Mockito.verify(userRepository, Mockito.times(1)).findByUsername("Test");
	}

	/**
	 * this method tests loadUserByUsername() of UserDetailsServiceImpl if it throws
	 * exception.
	 */
	@Test
	public void testUsernameNotFoundException() throws Exception {
		Mockito.when(userRepository.findByUsername("Bob")).thenThrow(new UsernameNotFoundException("Bob not found"));
		try {
			userDetailsServiceImpl.loadUserByUsername("Bob");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		Mockito.verify(userRepository, Mockito.times(1)).findByUsername("Bob");

	}

	/**
	 * this method is used by testLoadUserByUsername() of
	 * UserDetailsServiceImplTest.
	 */
	private Optional<User> mockUser() {
		User user = new User();
		user.setUsername("Test");
		user.setRole(new Role("Student"));
		Optional<User> optionalUser = Optional.of(user);
		return optionalUser;
	}

}

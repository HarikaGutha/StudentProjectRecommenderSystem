package com.prs.service.implementation;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prs.repositories.UserRepository;
import com.prs.model.User;

/**
 * UserDetailsServiceImplementationClass implements UserDetailsService which is
 * used to retrieve the information of users authentication and authorisation.
 * 
 * @author 190026870
 *
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * loadUserByUsername() is the read-only method of UserDetailsService and is
	 * used to find users based on the username.
	 * 
	 * method referenced from-
	 * https://github.com/Java-Techie-jt/spring-security-jpa/blob/master/src/main/java/com/javatechie/spring/security/api/service/CustomUserDetailsService.java
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		return user.map(UserDetailsImpl::new).get();

	}
}

package com.prs.service.implementation;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.prs.model.Role;
import com.prs.model.User;

import lombok.Getter;
import lombok.Setter;

/**
 * UserDetailsImplementationClass implements UserDetails interface which
 * provides core information of the users.
 * 
 * @Getter and @Setter generates the getters and setters
 * 
 * @author 190026870
 *
 */
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

	/**
	 * serialVersionUID unique identifier for the class which is used during
	 * deserialization.
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private User user;

	private String username;
	private String password;
	private Role role;
	private List<GrantedAuthority> grantedAuthorities;

	/**
	 * parameterised constructor.
	 * 
	 * @param user the User object
	 */
	public UserDetailsImpl(User user) {
		grantedAuthorities = new ArrayList<GrantedAuthority>();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.role = user.getRole();
		this.grantedAuthorities = Arrays.stream(user.getRole().toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String roleName = "";
		if (role.getRoleId().equals(1)) {
			roleName = "Student";
		} else if (role.getRoleId().equals(2)) {
			roleName = "Supervisor";
		} else {
			roleName = "Invalid role";
		}
		authorities.add(new SimpleGrantedAuthority(roleName));
		return authorities;
	}

	/**
	 * getAuthorities() method returns all the authorities granted to the users
	 */
	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return grantedAuthorities; }
	 */

	/*
	 * -----------------------------------------------------------------------------
	 * ---------------------------------------------------
	 * -----------------------------------------------------------------------------
	 * ---------------------------------------------------
	 * --------------------method below are mandatory to override so, they are set
	 * to true by default ---------------------------------
	 * -----------------------------------------------------------------------------
	 * ---------------------------------------------------
	 * -----------------------------------------------------------------------------
	 * ---------------------------------------------------
	 */
	/**
	 * isAccountNonExpired() verifies whether the user account has expired.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * isAccountNonLocked() verifies whether the user account is locked or unlocked.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * isCredentialsNonExpired() verifies whether the user credentials has expired
	 * or not.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * isEnabled() verifies whether the user enabled or disabled.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}

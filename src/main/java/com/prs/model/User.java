package com.prs.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User is a model class mapped to database table and annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * @author 190026870
 *
 */

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/**
	 * private field userId maps to user_id column of user entity.
	 */
	@Id
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * private field username maps to username column of user entity.
	 */
	@Column(name = "username", unique = true)
	private String username;

	/**
	 * private field password maps to password column of user entity.
	 */
	@Column(name = "password")
	private String password;

	/**
	 * private field first_name maps to first_name column of user entity.
	 */
	@Column(name = "first_name", nullable = false)
	private String firstName;

	/**
	 * private field last_name maps to last_name column of user entity.
	 */
	@Column(name = "last_name", nullable = false)
	private String lastName;

	/**
	 * private field email maps to email column of user entity.
	 */
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	/**
	 * private field userImage maps to image column of user entity used to store
	 * images.
	 */
	@Lob
	@Column(name = "image")
	private byte[] userImage;

	/**
	 * private field role mapped as join column from role entity.
	 */
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "role", nullable = false)
	private Role role;

	/**
	 * private lastLogin maps to last_login column of users entity.
	 */
	@Column(name = "last_login")
	private Date lastLogin;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_research_group", joinColumns = @JoinColumn(name = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<ResearchGroup> researchGroups;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_research_publication", joinColumns = @JoinColumn(name = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "publication_id"))
	private Set<ResearchPublication> researchPublication;

}

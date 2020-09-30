package com.prs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Role is a model class mapped to database table and annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * @author 190026870
 *
 */

@Entity
@Table(name = "role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	/**
	 * Constructor with string roleName.
	 * 
	 * @param roleName the name of the role
	 */
	public Role(String roleName) {
	}

	/**
	 * private field roleId maps to role_id column of role entity.
	 */
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleId;

	/**
	 * private field role maps to role column of role entity.
	 */
	@Column(name = "role", unique = true)
	private String role;

	/**
	 * private field set users mapped by role from role entity.
	 */
	@OneToMany(mappedBy = "role")
	private Set<User> user;

}

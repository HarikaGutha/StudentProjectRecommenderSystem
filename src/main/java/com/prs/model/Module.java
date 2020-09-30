package com.prs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modules is a model class mapped to database table and annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * 
 * @author 190026870
 *
 */

@Entity
@Table(name = "module")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Module {

	/**
	 * private moduleId maps to module_id column of module entity.
	 */
	@Id
	@Column(name = "module_id")
	private String moduleId;

	/**
	 * private moduleName maps to module_name column of module entity.
	 */
	@Column(name = "module_name", nullable = false, unique = true)
	private String moduleName;

	/**
	 * private field topicModules mapped as join column from Project entity.
	 */
	@ManyToMany(mappedBy = "topics")
	@JsonIgnore
	Set<Project> topicModules;

	/**
	 * private field previousModules mapped as join column from Project entity.
	 */
	@ManyToMany(mappedBy = "previousModules")
	@JsonIgnore
	Set<Project> previousModules;

}

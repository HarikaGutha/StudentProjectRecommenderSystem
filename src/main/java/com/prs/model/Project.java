package com.prs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Project is a model class mapped to database table and annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * 
 * @author 190026870
 *
 */

@Entity
@Table(name = "project")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {
	/**
	 * private field projectId maps to project_id column of project entity.
	 */
	@Id
	@Column(name = "project_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int projectId;

	/**
	 * private title maps to title column of project table.
	 */
	@Column(name = "title", nullable = false, unique = true)
	@Size(max = 255)
	private String title;

	/**
	 * private description maps to description column of project entity.
	 */
	@Column(name = "description", nullable = false)
	@Size(max = 5000)
	private String description;

	/**
	 * private additionalInformaation maps to additional_information column of
	 * project entity.
	 */
	@Size(max = 3000)
	@Column(name = "additional_information")
	private String additionalInformation;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User userId;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "topic", joinColumns = @JoinColumn(name = "project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "module_id"))
	@JsonIgnore
	private Set<Module> topics;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "previous_module", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "module_id"))
	@JsonIgnore
	private Set<Module> previousModules;

}

package com.prs.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * StudentSubmittedProject is a model class mapped to database table and
 * annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * 
 * @author 190026870
 *
 */

@Entity
@Table(name = "student_submitted_project")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentSubmittedProject extends Project {

	/**
	 * private projectType maps to project_type column of StudentSubmittedProject
	 * entity.
	 */
	@Column(name = "project_type")
	private String projectType;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "potential_supervisor", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> potentialSupervisors;

	/**
	 * private field projectState having ManyToOne mapping from
	 * StudentSubmittedProjectState entity.
	 */
	@ManyToOne
	@JoinColumn(name = "state")
	private StudentSubmittedProjectState projectState;

	/**
	 * private datePosted maps to date_posted column of StudentSubmittedProject
	 * entity.
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submitted_on")
	private Date submittedOn = new Date();

}

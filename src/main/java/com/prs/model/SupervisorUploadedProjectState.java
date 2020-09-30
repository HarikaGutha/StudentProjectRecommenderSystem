package com.prs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SupervisorUploadedProjectState is a model class mapped to database table and
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
@Table(name = "supervisor_uploaded_project_state")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorUploadedProjectState {

	/**
	 * private field stateId maps to state_id column of
	 * SupervisorUploadedProjectState entity.
	 */
	@Id
	@Column(name = "state_id", columnDefinition = "int default 1")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stateId;

	/**
	 * private field state maps to state column of SupervisorUploadedProjectState
	 * entity.
	 */
	@Column(name = "state", nullable = false, unique = true)
	private String state;
}

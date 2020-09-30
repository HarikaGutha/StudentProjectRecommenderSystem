package com.prs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DissertationModule is a model class mapped to database table and annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * 
 * @author 190026870
 *
 */

@Entity
@Table(name = "dissertation_module")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DissertationModule {

	/**
	 * private field dissertationModuleId maps to dissertation_module_id column of
	 * dissertation_module entity.
	 */
	@Id
	@Column(name = "dissertation_module_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dissertationModuleId;

	/**
	 * private field dissertationModule maps to dissertation_module column of
	 * dissertation_module entity.
	 */
	@Column(name = "dissertation_module", nullable = false, unique = true)
	private String dissertationModule;

	/**
	 * private field dissertationType maps to dissertation_type column of
	 * dissertation_module entity.
	 */
	@Column(name = "dissertation_type", nullable = false)
	private String dissertationType;

	/**
	 * private field dissertationModules mapped as join column from
	 * SupervisorUploadedProject entity.
	 */
	@NotBlank(message = "Atleast one topic should be selected")
	@ManyToMany(mappedBy = "dissertationModules")
	@JsonIgnore
	Set<SupervisorUploadedProject> dissertationModules;

}

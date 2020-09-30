package com.prs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * SupervisorUploadedProject is a model class mapped to database table and
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
@Table(name = "supervisor_uploaded_project")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupervisorUploadedProject extends Project implements Serializable {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * private background maps to background column of SupervisorUploadedProject
	 * entity.
	 */
	@Size(max = 3000)
	@Column(name = "background")
	private String background;

	/**
	 * private artefacts maps to artefacts column of SupervisorUploadedProject
	 * entity.
	 */
	@Size(max = 3000)
	@Column(name = "artefact")
	private String artefact;

	/**
	 * private filed projectState having ManyToOne mapping from
	 * SupervisorUploadedProjectState entity
	 */
	@ManyToOne
	@JoinColumn(name = "state")
	@JsonIgnore
	private SupervisorUploadedProjectState projectState;

	/**
	 * private datePosted maps to date_posted column of SupervisorUploadedProject
	 * entity.
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_posted")
	private Date datePosted = new Date();

	/**
	 * private lastEdited maps to last_edited column of SupervisorUploadedProject
	 * entity.
	 */
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_edited", insertable = false)
	private Date lastEdited;

	/**
	 * private externalCollaboration maps to external_collaboration column of
	 * SupervisorUploadedProject entity.
	 */
	@Column(name = "external_collaboration")
	private String externalCollaboration;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "internal_collaboration", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private Set<User> internalCollaboration;

	/**
	 * Joint table for many to many mapping.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "project_dissertation_module", joinColumns = @JoinColumn(name = "project_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "dissertation_module_id"))
	/**
	 * private filed dissertationModules of type DissertationModule.
	 */
	@JsonIgnore
	private Set<DissertationModule> dissertationModules;

	/**
	 * Boolean variable to indicate if the project is saved.
	 */
	@Transient
	public boolean isSavedProject;

}

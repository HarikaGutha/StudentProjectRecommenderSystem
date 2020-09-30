package com.prs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ResearchGroup is a model class mapped to database table and annotated as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * @author 190026870
 *
 */

@Entity
@Table(name = "research_group")
@Setter
@Getter
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class ResearchGroup {
	/**
	 * private field GroupId maps to group_id column of research_group entity.
	 */
	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int groupId;

	/**
	 * private field groupName maps to group_name column of research_group entity.
	 */
	@Column(name = "group_name", unique = true, nullable = false)
	private String groupName;

	/**
	 * private field groupImage maps to group_image column of research_group entity
	 * used to store images.
	 */
	@Lob
	@Column(name = "group_image")
	private byte[] groupImage;

	/**
	 * private researchGroups role mapped as join column from ResearchGroup entity.
	 */
	@ManyToMany(mappedBy = "researchGroups")
	private Set<User> supervisorResearchGroups;
}

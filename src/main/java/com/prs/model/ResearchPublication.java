package com.prs.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ResearchPublication is a model class mapped to database table and annotated
 * as
 * 
 * @Entity to specify that the class is an entity mapped to database table
 * @Setter and @Getter generates getters and setters for the methods
 * @NoArgsConstructor and @AllArgsConstructor generates constructors.
 * @author 190026870
 *
 */

@Entity
@Table(name = "research_publication")
@Setter
@Getter
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class ResearchPublication {

	/**
	 * private field publicationId maps to publication_id column of
	 * research_publication entity.
	 */
	@Id
	@Column(name = "publication_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int publicationId;

	/**
	 * private field publicationName maps to publication_name column of
	 * research_publication entity.
	 */
	@Column(name = "publication_name", unique = true, nullable = false)
	private String publicationName;

	/**
	 * private field publicationSource maps to publication_source column of
	 * research_publication entity.
	 */
	@Column(name = "publication_source", unique = true, nullable = false)
	private String publicationSource;

	/**
	 * private supervisorResearchPublications role mapped as join column from
	 * researchPublication entity.
	 */
	@ManyToMany(mappedBy = "researchPublication")
	private Set<User> supervisorResearchPublications;
}

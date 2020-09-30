package com.prs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prs.model.ResearchPublication;

/**
 * ResearchPublicationRepository is a repository for entity ResearchPublication
 * annotated with @Repository and extends JpaRepository for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface ResearchPublicationRepository extends JpaRepository<ResearchPublication, Integer> {
}

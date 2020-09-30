package com.prs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prs.model.DissertationModule;

/**
 * DissertationModuleRepository is a repository for the entity
 * DissertationModule annotated with @Repository and extends JpaRepository for
 * CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface DissertationModuleRepository extends JpaRepository<DissertationModule, Long> {
}

package com.prs.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prs.model.ResearchGroup;

/**
 * ResearchGroupRepository is a repository for the entity ResearchGroup
 * annotated with @Repository and extends JpaRepository for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface ResearchGroupRepository extends JpaRepository<ResearchGroup, Integer> {

	/**
	 * findUsersBasedOnResearchGroups() finds users based on research groups.
	 * 
	 * @param groupId  research group id.
	 * @param pageable Pageable reference
	 * @return list of research groups
	 */
	@Query(value = "select urg.user_id from research_group rg inner join "
			+ "user_research_group urg on rg.group_id = urg.group_id where "
			+ "urg.group_id=:groupId", nativeQuery = true)
	Page<Integer> findUsersBasedOnResearchGroups(@Param("groupId") Integer groupId, Pageable pageable);

	/**
	 * findAllResearchGroups() finds all research groups.
	 * 
	 * @return list of research groups
	 */
	@Query(value = "select * from research_group", nativeQuery = true)
	List<ResearchGroup> findAllResearchGroups();
}

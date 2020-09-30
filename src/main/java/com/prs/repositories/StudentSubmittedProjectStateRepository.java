package com.prs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prs.model.StudentSubmittedProjectState;

/**
 * StudentSubmittedProjectStateRepository is a repository for
 * StudentSubmittedProjectState annotated with @Repository and extends
 * JpaRepository for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface StudentSubmittedProjectStateRepository extends JpaRepository<StudentSubmittedProjectState, Integer> {

	/**
	 * getState() gets id of the given state.
	 * 
	 * @param state the state value
	 * @return returns id of state
	 */
	@Query(value = "select state_id from student_submitted_project_state where state=:state", nativeQuery = true)
	int getState(@Param("state") String state);
}

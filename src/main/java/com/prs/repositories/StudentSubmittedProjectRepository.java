package com.prs.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prs.model.Project;
import com.prs.model.StudentSubmittedProject;
import com.prs.model.User;

/**
 * StudentSubmittedProjectRepository is a repository for entity
 * StudentSubmittedProjectannotated with @Repository and extends JpaRepository
 * for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface StudentSubmittedProjectRepository extends JpaRepository<StudentSubmittedProject, Integer> {

	/**
	 * findProjectWithTitle() finds project with given titles.
	 * 
	 * @param title title of the project
	 * @return returns title
	 */
	@Query(value = "select p.title from Project p where p.title = :title")
	List<String> findProjectWithTitle(String title);

	/**
	 * findProjectsBySupervisorId() finds all projects of given supervisor
	 * 
	 * @param userId supervisor id
	 * @return returns list of projects
	 */
	@Query(value = "select p.*, ssp.project_type, ssp.state, ssp.submitted_on from project p "
			+ "inner join student_submitted_project ssp on p.project_id = ssp.project_id "
			+ "inner join potential_supervisor ps on p.project_id = ps.project_id "
			+ "where ps.user_id =:userId", nativeQuery = true)
	List<StudentSubmittedProject> findProjectsBySupervisorId(@Param("userId") Integer userId);

	/**
	 * findByStudentRole() finds students from project table.
	 * 
	 * @return list of student id's
	 */
	@Query(value = "select distinct p.user_id from project p inner join student_submitted_project ssp "
			+ "on p.project_id = ssp.project_id inner join user u on p.user_id = u.user_id "
			+ "inner join role r on u.role = r.role_id where r.role='Student'", nativeQuery = true)
	List<Integer> findByStudentRole();

	/**
	 * findByDatePosted() finds projects by date posted.
	 * 
	 * @return List of objects.
	 */
	@Query(value = "select date_format(submittedOn,'%M %Y'), count(*) from StudentSubmittedProject "
			+ "group by  date_format(submittedOn,'%M %Y')")
	List<Object[]> findByDatePosted();

	/**
	 * updateState() updates the state of projects.
	 * 
	 * @param stateId the state id
	 * @param id      the project id
	 */
	@Transactional
	@Modifying
	@Query(value = "update student_submitted_project p set p.state= :stateId"
			+ " where project_id = :id", nativeQuery = true)
	void updateState(@Param("stateId") Integer stateId, @Param("id") Integer id);

}

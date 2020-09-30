package com.prs.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;

/**
 * SupervisorUploadedProjectRepository is a repository for
 * SupervisorUploadedProject annotated with @Repository and extends
 * JpaRepository for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface SupervisorUploadedProjectRepository extends JpaRepository<SupervisorUploadedProject, Integer> {

	/**
	 * findByDatePosted() method returns project count grouped by date posted.
	 * 
	 * @return list of objects
	 */
	@Query(value = "select date_format(datePosted,'%M %Y'), count(*) from SupervisorUploadedProject "
			+ "group by  date_format(datePosted,'%M %Y')")
	List<Object[]> findByDatePosted();

	/**
	 * findByUserId() finds projects based on given userId.
	 * 
	 * @param userId the userId
	 * @return list of SupervisorUploadedProject
	 */
	@Query(value = "select p from SupervisorUploadedProject p where p.userId = :userId")
	List<SupervisorUploadedProject> findByUserId(@Param("userId") User userId);

	/**
	 * findByCollaboration() finds collaboration project for given user.
	 * 
	 * @param userId the userId
	 * @return list of SupervisorUploadedProject
	 */
	@Query(value = "select * from supervisor_uploaded_project p inner join internal_collaboration i "
			+ "on p.project_id = i.project_id where i.user_id = :userId", nativeQuery = true)
	List<SupervisorUploadedProject> findByCollaboration(@Param("userId") Integer userId);

	/**
	 * findAllTitles() finds the titles of project that matches the given title.
	 * 
	 * @param title the title of the project
	 * @return list of titles
	 */
	@Query(value = "select p.title from Project p where p.title = :title")
	List<String> findAllTitles(@Param("title") String title);

	/**
	 * getDateInYYYYMMMFormatForGivenProject() gets date in the specified format for
	 * the given project.
	 * 
	 * @param projectId the projectId
	 * @return date
	 */
	@Query(value = "select date_format(datePosted,'%M %Y') from SupervisorUploadedProject p "
			+ "where p.projectId = :projectId")
	Optional<String> getDateInYYYYMMMFormatForGivenProject(@Param("projectId") Integer projectId);

	/**
	 * findAdditionalSupervisors() finds additional supervisors for the given
	 * project.
	 * 
	 * @param projectId the projectId
	 * @return list of supervisorIds
	 */
	@Query(value = "select i.user_id from supervisor_uploaded_project p inner join "
			+ "internal_collaboration i on p.project_id = i.project_id "
			+ "where i.project_id = :projectId", nativeQuery = true)
	List<Integer> findAdditionalSupervisors(@Param("projectId") Integer projectId);

	/**
	 * findAllProjectsFromGivenId() finds all projects from the given project.
	 * 
	 * @param projectId the projectId
	 * @return list of projects
	 */
	@Query(value = "select p from SupervisorUploadedProject p where p.projectId > :projectId")
	List<SupervisorUploadedProject> findAllProjectsFromGivenId(@Param("projectId") Integer projectId);

	/**
	 * findAllProjectsBeforeGivenId() finds all projects before given project.
	 * 
	 * @param projectId the projectId
	 * @return list of projects
	 */
	@Query(value = "select p from SupervisorUploadedProject p where p.projectId < :projectId")
	List<SupervisorUploadedProject> findAllProjectsBeforeGivenId(@Param("projectId") Integer projectId);

	/**
	 * saveProject() inserts entry into save_project table.
	 * 
	 * @param projectId the projectId
	 * @param userId    the userId
	 */
	@Transactional
	@Modifying
	@Query(value = "insert into save_project (project_id,user_id) values (:projectId, :userId)", nativeQuery = true)
	void saveProject(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

	/**
	 * unSaveProject() deleted an entry from the save_project.
	 * 
	 * @param projectId the projectId
	 * @param userId    the userId
	 */
	@Transactional
	@Modifying
	@Query(value = "delete from save_project where project_id = :projectId and user_id = :userId", nativeQuery = true)
	void unSaveProject(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

	/**
	 * checkSavedProject() checks if the given project is saved by the given user.
	 * 
	 * @param projectId the projectId
	 * @param userId    the userId
	 * @return list of projectIds
	 */
	@Query(value = "select project_id from save_project where project_id = :projectId "
			+ "and user_id = :userId", nativeQuery = true)
	List<Integer> checkSavedProject(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

	/**
	 * getSavedProjectsOfCurrentLoggedInUser() finds savedProjects for the given
	 * user.
	 * 
	 * @param userId the userId
	 * @return list of saved projects
	 */
	@Query(value = "select project_id from save_project where user_id = :userId", nativeQuery = true)
	List<Integer> getSavedProjectsOfCurrentLoggedInUser(@Param("userId") Integer userId);

	/**
	 * updateState() updates the state of the project.
	 * 
	 * @param stateId the stateId
	 * @param id      the projectId
	 */
	@Transactional
	@Modifying
	@Query(value = "update supervisor_uploaded_project p set p.state= :stateId where "
			+ "project_id = :id", nativeQuery = true)
	void updateState(@Param("stateId") Integer stateId, @Param("id") Integer id);

	/**
	 * getFourProjects() gets four projects for the given user.
	 * 
	 * @param id the userId
	 * @return list of projects
	 */
	@Query(value = "select * from supervisor_uploaded_project sup inner join project p "
			+ "on sup.project_id = p.project_id where p.user_id=:id "
			+ "order by sup.project_id limit 4", nativeQuery = true)
	List<SupervisorUploadedProject> getFourProjects(@Param("id") Integer id);

	/**
	 * findProjectCountPerTopic() finds number of projects per module or topic.
	 * 
	 * @param id the userId
	 * @return list of projectsCounts and modules
	 */
	@Query(value = "select m.module_name,count(p.project_id) from project p inner join topic t "
			+ "on t.project_id = p.project_id inner join module m on m.module_id = t.module_id"
			+ " where p.user_id = :userId group by m.module_name", nativeQuery = true)
	List<Object[]> findProjectCountPerTopic(@Param("userId") Integer id);

	/**
	 * getSavedProjectsOfCurrentLoggedInUserLimitFour() gets four saved projects for
	 * the logged in user.
	 * 
	 * @param userId the userId
	 * @return list of projectIds
	 */
	@Query(value = "select project_id from save_project where user_id = :userId limit 4", nativeQuery = true)
	List<Integer> getSavedProjectsOfCurrentLoggedInUserLimitFour(@Param("userId") Integer userId);

	/**
	 * findByUserIdLimitFive() finds five projects for the given User.
	 * 
	 * @param userId the userId
	 * @return list of projects
	 */
	@Query(value = "select * from project p inner join supervisor_uploaded_project sup "
			+ "on p.project_id = sup.project_id where p.user_id = :userId "
			+ "order by sup.date_posted desc limit 5", nativeQuery = true)
	List<SupervisorUploadedProject> findByUserIdLimitFive(@Param("userId") Integer userId);

	/**
	 * findCountByMonth() finds count of available projects per month.
	 * 
	 * @param userId the userId
	 * @param date   the date
	 * @param state  the project state
	 * @return list of maps with month and count
	 */
	@Query(value = "select date_format(sup.date_Posted,'%b') as label, count(p.project_id) as y "
			+ "from project p inner join supervisor_uploaded_project sup on "
			+ "p.project_id = sup.project_id where p.user_id = :userId and "
			+ "year(sup.date_posted) = :date and sup.state = :state "
			+ "group by date_format(sup.date_Posted,'%b')", nativeQuery = true)
	List<Map<String, Integer>> findCountByMonth(@Param("userId") Integer userId, @Param("date") String date,
			@Param("state") Integer state);

	/**
	 * numberOfProjectsFromLastLogin() finds number of projects uploaded from user
	 * last login.
	 * 
	 * @param timestamp the last login time.
	 * @return projects count
	 */
	@Query(value = "select count(project_id) from supervisor_uploaded_project "
			+ "where date_posted > :timestamp", nativeQuery = true)
	public int numberOfProjectsFromLastLogin(@Param("timestamp") String timestamp);

	/**
	 * getAvailableProjectsCount() finds all available projects count.
	 * 
	 * @return count of available projects
	 */
	@Query(value = "select count(project_id) from supervisor_uploaded_project where state = 1", nativeQuery = true)
	int getAvailableProjectsCount();

	/**
	 * getProjectsCount() gets projects count for the given user.
	 * 
	 * @param user the user
	 * @return count of projects
	 */
	@Query(value = "select count(p) from SupervisorUploadedProject p where p.userId =:user")
	int getProjectsCount(@Param("user") User user);

	/**
	 * getSupervisorProjects() gets projects of the given list of supervisors.
	 * 
	 * @param supervisorsList the supervisors list
	 * @param pageable        the Pageable reference
	 * @return Page of SupervisorUploadedProject
	 */
	@Query(value = "select p from SupervisorUploadedProject p where p.userId in (:supervisorsList)")
	Page<SupervisorUploadedProject> getSupervisorProjects(@Param("supervisorsList") List<User> supervisorsList,
			Pageable pageable);
}

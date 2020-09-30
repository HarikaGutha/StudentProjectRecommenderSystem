package com.prs.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prs.model.User;

/**
 * UserRepository is a repository for entity User annotated with @Repository and
 * extends JpaRepository for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * findByUsername() is the method implemented by JpaRepository.
	 * 
	 * @param username takes the username as argument
	 * @return user details with the given username
	 */
	Optional<User> findByUsername(String username);

	/**
	 * findByRoleSupervisor() is the custom method for finding supervisors.
	 * 
	 * @param role is the argument
	 * @return users with the given role
	 */
	@Query(value = "select * from user inner join role on user.role = role.role_id where role.role = 'supervisor'", nativeQuery = true)
	List<User> findByRoleSupervisor();

	/**
	 * updateLastLogin() custom query to update user last login.
	 * 
	 * @param timestamp is the login time
	 * @param id        is the user id to be updated
	 */
	@Transactional
	@Modifying
	@Query(value = "update user set last_login = :timestamp where user_id = :id", nativeQuery = true)
	void updateLastLogin(@Param("timestamp") Timestamp timestamp, @Param("id") int id);

}

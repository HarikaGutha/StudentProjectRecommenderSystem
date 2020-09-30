package com.prs.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prs.model.Module;

/**
 * ModulesRepository is a repository for the entity Module annotated
 * with @Repository and extends JpaRepository for CRUD operations.
 * 
 * @author 190026870
 *
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {

	/**
	 * findProjectsPerModule() returns number of projects per module and modules as
	 * a list of maps.
	 * 
	 * @return list of maps
	 */
	@Query(value = "select count(project_id) as y, module_name as label from topic inner join module on topic.module_id = module.module_id group by module_name;", nativeQuery = true)
	List<Map<String, Integer>> findProjectsPerModule();
}

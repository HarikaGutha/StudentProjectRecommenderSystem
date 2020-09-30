package com.prs.service;

/**
 * SaveProjectService is an interface which provides service to
 * SupervisorNavigationController.
 * 
 * @author 190026870
 *
 */
public interface SaveProjectService {

	/**
	 * saveProject() method used to save a project.
	 * 
	 * @param id the project id
	 */
	public void saveProject(int id);

	/**
	 * unsaveProject() method used to un save a project.
	 * 
	 * @param id the project id
	 */
	public void unsaveProject(int id);
}

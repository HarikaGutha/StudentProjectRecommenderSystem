package com.prs.service;

import org.springframework.ui.Model;

/**
 * ProjectManagementService is an interface which provides service to
 * SupervisorNavigationController.
 * 
 * @author 190026870
 *
 */
public interface ProjectManagementService {

	/**
	 * setModelAttributesForProjectManagement() method used to set model attributes.
	 * 
	 * @param model model attribute
	 */
	void setModelAttributesForProjectManagement(Model model);
}

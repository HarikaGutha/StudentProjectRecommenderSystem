package com.prs.service;

import org.springframework.ui.Model;

/**
 * DashboardService is an interface which provides service to dashboard
 * controller.
 * 
 * @author 190026870
 *
 */
public interface DashboardService {

	/**
	 * setModelAttributesForDashboard() method used to set model attributes.
	 * 
	 * @param model model attribute
	 */
	void setModelAttributesForDashboard(Model model);

	/**
	 * setModelAttributesForStudentDashboard(Model model) method used to set model
	 * attributes.
	 * 
	 * @param model model attribute
	 */
	void setModelAttributesForStudentDashboard(Model model);

	/**
	 * setModelAttributesForSupervisorDashboard(Model model) method used to set
	 * model attributes.
	 * 
	 * @param model model attribute
	 */
	void setModelAttributesForSupervisorDashboard(Model model);
}

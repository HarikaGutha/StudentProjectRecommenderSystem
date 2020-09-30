package com.prs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.prs.service.DashboardService;
import com.prs.service.implementation.UserDetailsServiceImpl;

/**
 * Dashboard Controller is a controller class for dashboard of the application
 * and is annotated with @Controller to indicate that the class serves the role
 * of a controller.
 * 
 * @author 190026870
 *
 */

@Controller
public class DashboardController {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImplementationClass;

	@Autowired
	private DashboardService dashboardService;

	/**
	 * dashboard() methods is invoked when the login authentication is successful.
	 * based on the role this method returns student dashboard and supervisor
	 * dashboard pages
	 * 
	 * @return dashboard page
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/dashboard")
	public ModelAndView dashboard(Model model) {
		dashboardService.setModelAttributesForDashboard(model);
		String role = (String) model.getAttribute("role");
		if (role != null) {
			if (role.equalsIgnoreCase("Student")) {
				dashboardService.setModelAttributesForStudentDashboard(model);
				return new ModelAndView("/students/StudentDashboard.html");
			} else if (role.equalsIgnoreCase("Supervisor")) {
				dashboardService.setModelAttributesForSupervisorDashboard(model);
				return new ModelAndView("/supervisors/SupervisorDashboard.html");
			}
			return new ModelAndView("Error.html");
		} else {
			return new ModelAndView("Error.html");
		}
	}
}

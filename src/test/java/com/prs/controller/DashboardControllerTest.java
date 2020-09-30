package com.prs.controller;

import static org.mockito.ArgumentMatchers.isA;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.prs.service.DashboardService;

/**
 * DashboardControllerTest is to test the DashboardController.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class DashboardControllerTest {

	@Mock
	private DashboardService dashboardService;

	@InjectMocks
	private DashboardController dashboardController;

	/**
	 * This method tests if the dashboard() is returning student dashboard.
	 */
	@Test
	public void studentDashBoardTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(dashboardService).setModelAttributesForDashboard(isA(Model.class));
		Mockito.when(model.getAttribute("role")).thenReturn("Student");
		ModelAndView modelAndView = dashboardController.dashboard(model);
		Assert.assertEquals("/students/StudentDashboard.html", modelAndView.getViewName());
	}

	/**
	 * This method tests if the dashboard() is returning supervisor dashboard.
	 */
	@Test
	public void supervisorDashBoardTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(dashboardService).setModelAttributesForDashboard(isA(Model.class));
		Mockito.when(model.getAttribute("role")).thenReturn("Supervisor");
		ModelAndView modelAndView = dashboardController.dashboard(model);
		Assert.assertEquals("/supervisors/SupervisorDashboard.html", modelAndView.getViewName());
	}

	/**
	 * This method tests if the dashboard() is returning Error page if not a valid
	 * role.
	 */
	@Test
	public void noRoleDashBoardTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(dashboardService).setModelAttributesForDashboard(isA(Model.class));
		Mockito.when(model.getAttribute("role")).thenReturn("otherRole");
		ModelAndView modelAndView = dashboardController.dashboard(model);
		Assert.assertEquals("Error.html", modelAndView.getViewName());
	}

	/**
	 * This method tests if the dashboard() is returning error pagw if no role
	 * exists.
	 */
	@Test
	public void roleNotFoundTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(dashboardService).setModelAttributesForDashboard(isA(Model.class));
		ModelAndView modelAndView = dashboardController.dashboard(model);
		Assert.assertEquals("Error.html", modelAndView.getViewName());
	}

}

package com.prs.controller;

import static org.mockito.ArgumentMatchers.isA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prs.model.Module;
import com.prs.model.StudentSubmittedProject;
import com.prs.service.StudentNavigationService;
import com.prs.service.SupervisorResearchAreaService;

/**
 * StudentNavigationControllerTest tests StudentNavigationController.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class StudentNavigationControllerTest {

	@Mock
	private StudentNavigationService studentNavigationService;

	@Mock
	private SupervisorResearchAreaService supervisorResearchAreaService;

	@InjectMocks
	private StudentNavigationController studentNavigationController;

	/**
	 * This method tests savedProjects() method of the StudentNavigationController.
	 */
	@Test
	public void savedProjectsTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForSavedProjects(isA(Model.class));
		ModelAndView modelAndView = studentNavigationController.savedProjects(model);
		Assert.assertEquals("students/SavedProjects.html", modelAndView.getViewName());
	}

	/**
	 * This method tests allProjects() method of the StudentNavigationController.
	 */
	@Test
	public void allProjectsTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForAllProjects(isA(Model.class));
		ModelAndView modelAndView = studentNavigationController.allProjects(model);
		Assert.assertEquals("students/AllProjects.html", modelAndView.getViewName());
	}

	/**
	 * This method tests supervisors() method of the StudentNavigationController.
	 */
	@Test
	public void supervisorsTest() {
		Model model = Mockito.mock(Model.class);
		Pageable pageable = Mockito.mock(Pageable.class);
		Mockito.doNothing().when(supervisorResearchAreaService).addModelAttributesForSupervisor(isA(Pageable.class),
				isA(Model.class), isA(HashMap.class));
		ModelAndView modelAndView = studentNavigationController.supervisors(pageable, model);
		Assert.assertEquals("students/Supervisors.html", modelAndView.getViewName());
	}

	/**
	 * This method tests supervisor() method of the StudentNavigationController.
	 */
	@Test
	public void supervisorProfileTestForStudent() {
		Model model = Mockito.mock(Model.class);
		Integer id = 0;
		Mockito.doNothing().when(supervisorResearchAreaService)
				.addModelAttributesForSupervisorProfile(isA(Integer.class), isA(Model.class));
		Mockito.when(model.getAttribute("role")).thenReturn("Student");
		ModelAndView modelAndView = studentNavigationController.supervisorProfile(id, model);
		Assert.assertEquals("students/SupervisorProfile", modelAndView.getViewName());
	}

	/**
	 * This method tests supervisorProfile() method of the
	 * StudentNavigationController if the logged in user is a supervisor.
	 */
	@Test
	public void supervisorProfileTestForSupervisor() {
		Model model = Mockito.mock(Model.class);
		Integer id = 0;
		Mockito.doNothing().when(supervisorResearchAreaService)
				.addModelAttributesForSupervisorProfile(isA(Integer.class), isA(Model.class));
		Mockito.when(model.getAttribute("role")).thenReturn("Supervisor");
		ModelAndView modelAndView = studentNavigationController.supervisorProfile(id, model);
		Assert.assertEquals("supervisors/SupervisorProfile", modelAndView.getViewName());
	}

	/**
	 * This method tests individualProjectDescription() method of the
	 * StudentNavigationController.
	 */
	@Test
	public void individualProjectDescriptionTest() {
		Model model = Mockito.mock(Model.class);
		Integer id = 0;
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForProjectDescription(isA(Integer.class),
				isA(Model.class));
		ModelAndView modelAndView = studentNavigationController.individualProjectDescription(id, model);
		Assert.assertEquals("students/Project.html", modelAndView.getViewName());
	}

	/**
	 * This method tests nextProjectDescription() method of the
	 * StudentNavigationController.
	 */
	@Test
	public void nextProjectDescriptionTest() {
		Model model = Mockito.mock(Model.class);
		Integer id = 0;
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForNextAndPreviousProjectDescription(
				isA(Integer.class), isA(Model.class), isA(String.class));
		ModelAndView modelAndView = studentNavigationController.nextProjectDescription(id, model);
		Assert.assertEquals("students/Project.html", modelAndView.getViewName());
	}

	/**
	 * This method tests previousProjectDescription() method of the
	 * StudentNavigationController.
	 */
	@Test
	public void previousProjectDescriptionTest() {
		Model model = Mockito.mock(Model.class);
		Integer id = 0;
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForNextAndPreviousProjectDescription(
				isA(Integer.class), isA(Model.class), isA(String.class));
		ModelAndView modelAndView = studentNavigationController.previousProjectDescription(id, model);
		Assert.assertEquals("students/Project.html", modelAndView.getViewName());
	}

	/**
	 * This method tests projectProposal() method of the
	 * StudentNavigationController.
	 */
	@Test
	public void projectProposalTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForProjectProposal(isA(Model.class));
		ModelAndView modelAndView = studentNavigationController.projectProposal(model);
		Assert.assertEquals("students/ProjectProposal.html", modelAndView.getViewName());
	}

	/**
	 * This method tests submitProject() method of the StudentNavigationController
	 * if project has no errors.
	 */
	@Test
	public void submitProjectTest() {
		Model model = Mockito.mock(Model.class);
		StudentSubmittedProject project = Mockito.mock(StudentSubmittedProject.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		if (!(bindingResult.hasErrors())) {
			Mockito.doNothing().when(studentNavigationService).addModelAttributesForUploadProjects(
					isA(StudentSubmittedProject.class), isA(Model.class), isA(RedirectAttributes.class));
			ModelAndView modelAndView = studentNavigationController.uploadProjects(project, bindingResult, model,
					redirectAttributes);
			Assert.assertEquals("redirect:/allProjects", modelAndView.getViewName());
		}
	}

	/**
	 * This method tests submitProject() method of the StudentNavigationController
	 * if project has errors.
	 */
	@Test
	public void submitProjectErrorTest() {
		Model model = Mockito.mock(Model.class);
		StudentSubmittedProject project = Mockito.mock(StudentSubmittedProject.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = studentNavigationController.uploadProjects(project, bindingResult, model,
					redirectAttributes);
			Assert.assertEquals("students/ProjectProposal.html", modelAndView.getViewName());
		}
	}

	/**
	 * This method tests supervisor() method of the StudentNavigationController if
	 * the logged in user is a student.
	 */
	@Test
	public void studentSupervisorPageTest() {
		Model model = Mockito.mock(Model.class);
		Pageable pageable = Mockito.mock(Pageable.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.doNothing().when(supervisorResearchAreaService).addModelAttributesForSupervisors(isA(Pageable.class),
				isA(Model.class), isA(HttpServletRequest.class));
		Mockito.when(request.getParameter("isStudentsSupervisor")).thenReturn("yes");
		ModelAndView modelAndView = studentNavigationController.supervisor(pageable, model, request);
		Assert.assertEquals("students/Supervisors.html", modelAndView.getViewName());
	}

	/**
	 * This method tests supervisor() method of the StudentNavigationController if
	 * the logged in user is a supervisor.
	 */
	@Test
	public void supervisorSupervisorPageTest() {
		Model model = Mockito.mock(Model.class);
		Pageable pageable = Mockito.mock(Pageable.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		// servlet.setAttribute("isStudentsSupervisor", "isStudentsSupervisor");
		Mockito.doNothing().when(supervisorResearchAreaService).addModelAttributesForSupervisors(isA(Pageable.class),
				isA(Model.class), isA(HttpServletRequest.class));
		Mockito.when(request.getParameter("isStudentsSupervisor")).thenReturn("no");
		ModelAndView modelAndView = studentNavigationController.supervisor(pageable, model, request);
		Assert.assertEquals("supervisors/Supervisors.html", modelAndView.getViewName());
	}

	/**
	 * This method tests getProjects() method of the StudentNavigationController.
	 */
	@Test
	public void getProjectsTest() {
		Model model = Mockito.mock(Model.class);
		Module module = new Module();
		module.setModuleId("1");
		module.setModuleName("hello");
		List<Module> moduleList = new ArrayList<Module>();
		moduleList.add(module);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.doNothing().when(studentNavigationService).addModelAttributesForAllProjects(isA(Model.class));
		Mockito.when(request.getParameter("selectedModule")).thenReturn("hello");
		Mockito.when(model.getAttribute("module")).thenReturn(moduleList);
		ModelAndView modelAndView = studentNavigationController.getProjects(model, request);
		Assert.assertEquals("students/AllProjects.html", modelAndView.getViewName());
	}

	/**
	 * This method tests logout() of the StudentNavigationController.
	 */
	@Test
	public void logoutTest() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.doNothing().when(studentNavigationService).logout(isA(HttpServletRequest.class));
		ModelAndView modelAndView = studentNavigationController.logout(request);
		Assert.assertEquals("redirect:/login", modelAndView.getViewName());

	}
}

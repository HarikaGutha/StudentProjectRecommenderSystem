package com.prs.controller;

import static org.mockito.ArgumentMatchers.isA;

import java.util.HashMap;

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

import com.prs.model.SupervisorUploadedProject;
import com.prs.service.EditAndUploadProjectService;
import com.prs.service.ProjectManagementService;
import com.prs.service.SupervisorResearchAreaService;

/**
 * SupervisorNavigationContollerTest tests SupervisorNavigationContoller.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class SupervisorNavigationContollerTest {

	@Mock
	private ProjectManagementService projectManagementService;

	@Mock
	private EditAndUploadProjectService editAndUploadProjectService;

	@InjectMocks
	private SupervisorNavigationContoller supervisorNavigationContoller;

	@Mock
	private SupervisorResearchAreaService supervisorResearchAreaService;

	/**
	 * this method tests projectManagement() method of
	 * SupervisorNavigationContoller.
	 */
	@Test
	public void projectManagementTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(projectManagementService).setModelAttributesForProjectManagement(isA(Model.class));
		ModelAndView modelAndView = supervisorNavigationContoller.projectManagement(model);
		Assert.assertEquals("supervisors/ProjectManagement.html", modelAndView.getViewName());
	}

	/**
	 * this method tests editProject() method of SupervisorNavigationContoller.
	 */
	@Test
	public void editProjectTest() {
		Model model = Mockito.mock(Model.class);
		Integer id = 0;
		Mockito.doNothing().when(editAndUploadProjectService).addAttributesToEditProjectModel(isA(Integer.class),
				isA(Model.class));
		ModelAndView modelAndView = supervisorNavigationContoller.editProject(id, model);
		Assert.assertEquals("supervisors/EditProject.html", modelAndView.getViewName());
	}

	/**
	 * this method tests uploadProject() method of SupervisorNavigationContoller.
	 */
	@Test
	public void uploadProjectTest() {
		Model model = Mockito.mock(Model.class);
		Mockito.doNothing().when(editAndUploadProjectService).addAttributesToUploadProjectModel(isA(Model.class));
		ModelAndView modelAndView = supervisorNavigationContoller.uploadProjects(model);
		Assert.assertEquals("supervisors/UploadProject.html", modelAndView.getViewName());
	}

	/**
	 * this method tests submitProject() method of SupervisorNavigationContoller.
	 */
	@Test
	public void submitProjectTest() {
		Model model = Mockito.mock(Model.class);
		SupervisorUploadedProject project = Mockito.mock(SupervisorUploadedProject.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		if (!(bindingResult.hasErrors())) {
			Mockito.doNothing().when(editAndUploadProjectService).saveProjectService(
					isA(SupervisorUploadedProject.class), isA(Model.class), isA(RedirectAttributes.class));
			ModelAndView modelAndView = supervisorNavigationContoller.saveProjects(project, bindingResult, model,
					redirectAttributes);
			Assert.assertEquals("redirect:/projectManagement", modelAndView.getViewName());
		}
	}

	/**
	 * this method tests submitProject() method of SupervisorNavigationContoller if
	 * it has any errors.
	 */
	@Test
	public void submitProjectErrorTest() {
		Model model = Mockito.mock(Model.class);
		SupervisorUploadedProject project = Mockito.mock(SupervisorUploadedProject.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		// BindingResult bindingResult.hasErrors()=false;
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = supervisorNavigationContoller.saveProjects(project, bindingResult, model,
					redirectAttributes);
			Assert.assertEquals("supervisors/UploadProject.html", modelAndView.getViewName());
		}
	}

	/**
	 * this method tests uploadProject() method of SupervisorNavigationContoller.
	 */
	@Test
	public void updateProjectErrorTest() {
		int id = 1;
		Model model = Mockito.mock(Model.class);
		SupervisorUploadedProject project = Mockito.mock(SupervisorUploadedProject.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = supervisorNavigationContoller.updateProject(id, model, project, bindingResult,
					redirectAttributes);
			Assert.assertEquals("supervisors/EditProject.html('id'=id)", modelAndView.getViewName());
		}
	}

	/**
	 * this method tests updateProject() method of SupervisorNavigationContoller
	 */
	@Test
	public void updateProjectTest() {
		Model model = Mockito.mock(Model.class);
		SupervisorUploadedProject project = Mockito.mock(SupervisorUploadedProject.class);
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		Mockito.when(bindingResult.hasErrors()).thenReturn(false);
		if (!(bindingResult.hasErrors())) {
			Mockito.doNothing().when(editAndUploadProjectService).saveEditedProject(isA(Integer.class),
					isA(Model.class), isA(RedirectAttributes.class), isA(SupervisorUploadedProject.class));
			ModelAndView modelAndView = supervisorNavigationContoller.updateProject(1, model, project, bindingResult,
					redirectAttributes);
			Assert.assertEquals("redirect:/projectManagement", modelAndView.getViewName());
		}
	}

	/**
	 * this method tests supervisors() method of SupervisorNavigationContoller
	 */
	@Test
	public void supervisorsTest() {
		Model model = Mockito.mock(Model.class);
		Pageable pageable = Mockito.mock(Pageable.class);
		Mockito.doNothing().when(supervisorResearchAreaService).addModelAttributesForSupervisor(isA(Pageable.class),
				isA(Model.class), isA(HashMap.class));
		ModelAndView modelAndView = supervisorNavigationContoller.otherSupervisors(pageable, model);
		Assert.assertEquals("supervisors/Supervisors.html", modelAndView.getViewName());
	}

}

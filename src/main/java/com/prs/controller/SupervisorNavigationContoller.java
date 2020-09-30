package com.prs.controller;

import java.util.HashMap;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prs.model.SupervisorUploadedProject;
import com.prs.service.EditAndUploadProjectService;
import com.prs.service.ProjectManagementService;
import com.prs.service.SupervisorResearchAreaService;

/**
 * SupervisorNavigationController is a controller class that controls the
 * navigation in supervisors perspective and is annotated with @Controller to
 * indicate that the class serves the role of a controller.
 * 
 * @author 190026870
 *
 */
@Controller
public class SupervisorNavigationContoller {

	@Autowired
	private ProjectManagementService projectManagementService;

	@Autowired
	private EditAndUploadProjectService editAndUploadProjectService;

	@Autowired
	private SupervisorResearchAreaService supervisorResearchAreaService;

	/**
	 * projectManagement() method is invoked when the request is /projectManagement
	 * and request mapping type is get.
	 * 
	 * @return ProjectManagement page
	 */
	@GetMapping("/projectManagement")
	public ModelAndView projectManagement(Model model) {
		projectManagementService.setModelAttributesForProjectManagement(model);
		return new ModelAndView("supervisors/ProjectManagement.html");

	}

	/**
	 * otherSupervisors() method is invoked when the request is /otherSupervisors
	 * and request mapping type is get.
	 * 
	 * @return supervisors page
	 */
	@GetMapping("/otherSupervisors")
	public ModelAndView otherSupervisors(@PageableDefault(size = 6) Pageable pageable, Model model) {
		supervisorResearchAreaService.addModelAttributesForSupervisor(pageable, model, new HashMap<Integer, Integer>());
		model.addAttribute("isStudentsSupervisor", "no");
		return new ModelAndView("supervisors/Supervisors.html");

	}

	/**
	 * editProject() method is invoked when the request is /editProject and request
	 * mapping type is get.
	 * 
	 * @return EditProject page
	 */
	@GetMapping(path = "/editProject")
	public ModelAndView editProject(@PathParam("id") Integer id, Model model) {
		editAndUploadProjectService.addAttributesToEditProjectModel(id, model);
		return new ModelAndView("supervisors/EditProject.html");

	}

	/**
	 * updateProject() method is invoked when the request is /updateProject and
	 * request mapping type is get.
	 * 
	 * @return ProjectManagement page is project is updated and if binding result
	 *         has any error it returns EditProject page
	 */
	@PostMapping(path = "/updateProject")

	public ModelAndView updateProject(@PathParam("id") Integer id, Model model,
			@Valid @ModelAttribute("project") SupervisorUploadedProject project, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("supervisors/EditProject.html('id'=id)");
		} else {
			editAndUploadProjectService.saveEditedProject(id, model, redirectAttributes, project);
			return new ModelAndView("redirect:/projectManagement");
		}
	}

	/**
	 * uploadProject() method is invoked when the request is /uploadProject and
	 * request mapping type is get.
	 * 
	 * @return upload project page
	 */
	@GetMapping("/uploadProject")
	public ModelAndView uploadProjects(Model model) {
		editAndUploadProjectService.addAttributesToUploadProjectModel(model);
		return new ModelAndView("supervisors/UploadProject.html");
	}

	/**
	 * saveProject() method is invoked when the request is /saveProject and request
	 * mapping type is post.
	 * 
	 * @return UploadProject page
	 */
	@PostMapping("/saveProject")
	public ModelAndView saveProjects(@Valid @ModelAttribute("project") SupervisorUploadedProject project,
			BindingResult bindingResult, Model model, final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("supervisors/UploadProject.html");
		} else {
			editAndUploadProjectService.saveProjectService(project, model, redirectAttributes);
			return new ModelAndView("redirect:/projectManagement");
		}
	}

}

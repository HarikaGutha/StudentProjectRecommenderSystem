package com.prs.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.prs.model.StudentSubmittedProject;
import com.prs.model.Module;
import com.prs.service.StudentNavigationService;
import com.prs.service.SupervisorResearchAreaService;

/**
 * StudentNavigationController is a controller class that controls the
 * navigation in students' perspective. It is annotated with @Controller to
 * indicate that the class serves the role of a controller.
 * 
 * @author 190026870
 *
 */

@Controller
public class StudentNavigationController {

	@Autowired
	private StudentNavigationService studentNavigationService;

	@Autowired
	private SupervisorResearchAreaService supervisorResearchAreaService;

	/**
	 * savedProjects() method is invoked when the request is /savedProjects and
	 * request mapping type is get.
	 * 
	 * @return saved projects page
	 */

	@GetMapping("/savedProjects")
	public ModelAndView savedProjects(Model model) {
		studentNavigationService.addModelAttributesForSavedProjects(model);
		return new ModelAndView("students/SavedProjects.html");
	}

	/**
	 * allProjects() method is invoked when the request is /allProjects and request
	 * mapping type is get.
	 * 
	 * @return all projects page
	 */
	@GetMapping("/allProjects")
	public ModelAndView allProjects(Model model) {
		studentNavigationService.addModelAttributesForAllProjects(model);
		return new ModelAndView("students/AllProjects.html");
	}

	/**
	 * getProjects() method is invoked when the request is /Project and request
	 * mapping type is get.
	 *
	 * @return all projects page
	 */
	@PostMapping("/project")
	public ModelAndView getProjects(Model model, HttpServletRequest request) {
		studentNavigationService.addModelAttributesForAllProjects(model);
		String selectedProject = request.getParameter("selectedModule");
		@SuppressWarnings("unchecked")
		List<Module> module = (List<Module>) model.getAttribute("module");
		for (Module projectModule : module) {
			if (projectModule.getModuleName().trim().equalsIgnoreCase(selectedProject.trim())) {
				model.addAttribute("moduleId", projectModule.getModuleId());
				model.addAttribute("isFilterApplied", "yes");
			}
		}

		return new ModelAndView("students/AllProjects.html");
	}

	/**
	 * supervisors() method is invoked when the request is /supervisors and request
	 * mapping type is get.
	 * 
	 * @return supervisors page
	 */
	@GetMapping("/supervisors")
	public ModelAndView supervisors(@PageableDefault(size = 6) Pageable pageable, Model model) {
		supervisorResearchAreaService.addModelAttributesForSupervisor(pageable, model, new HashMap<Integer, Integer>());
		model.addAttribute("isStudentsSupervisor", "yes");
		return new ModelAndView("students/Supervisors.html");
	}

	/**
	 * supervisors() method is invoked when the request is /supervisor and request
	 * mapping type is get. This request will be made from JavaScript
	 * 
	 * @return supervisors page
	 */
	@PostMapping("/supervisor")
	public ModelAndView supervisor(@PageableDefault(size = 6) Pageable pageable, Model model,
			HttpServletRequest request) {
		supervisorResearchAreaService.addModelAttributesForSupervisors(pageable, model, request);
		String isStudentsSupervisor = request.getParameter("isStudentsSupervisor");
		if ("yes".equals(isStudentsSupervisor)) {
			model.addAttribute("isStudentsSupervisor", "yes");
			return new ModelAndView("students/Supervisors.html");
		} else {
			model.addAttribute("isStudentsSupervisor", "no");
			return new ModelAndView("supervisors/Supervisors.html");
		}
	}

	/**
	 * supervisorProfile() method is invoked when the request is /supervisorProfile
	 * and request mapping type is get.
	 * 
	 * @return supervisorProfile page
	 */
	@GetMapping("/supervisorProfile")
	public ModelAndView supervisorProfile(@PathParam("id") Integer id, Model model) {
		supervisorResearchAreaService.addModelAttributesForSupervisorProfile(id, model);
		String role = (String) model.getAttribute("role");
		if (role.equalsIgnoreCase("Student")) {
			return new ModelAndView("students/SupervisorProfile");
		} else {
			return new ModelAndView("supervisors/SupervisorProfile");
		}
	}

	/**
	 * projectProposal() method is invoked when the request is /projectProposal and
	 * request mapping type is get.
	 * 
	 * @return project proposal page
	 */
	@GetMapping("/individualProjectDescription")
	public ModelAndView individualProjectDescription(@PathParam("id") Integer id, Model model) {
		studentNavigationService.addModelAttributesForProjectDescription(id, model);
		return new ModelAndView("students/Project.html");
	}

	/**
	 * nextProjectDescription() method is invoked when the request is
	 * /nextProjectDescription and request mapping type is get.
	 * 
	 * @return project page
	 */
	@GetMapping("/nextProjectDescription")
	public ModelAndView nextProjectDescription(@PathParam("id") Integer id, Model model) {
		studentNavigationService.addModelAttributesForNextAndPreviousProjectDescription(id, model, "nextProject");
		return new ModelAndView("students/Project.html");

	}

	/**
	 * previousProjectDescription() method is invoked when the request is
	 * /previousProjectDescription and request mapping type is get.
	 * 
	 * @return project page
	 */
	@GetMapping("/previousProjectDescription")
	public ModelAndView previousProjectDescription(@PathParam("id") Integer id, Model model) {
		studentNavigationService.addModelAttributesForNextAndPreviousProjectDescription(id, model, "previousProject");
		return new ModelAndView("students/Project.html");

	}

	/**
	 * projectProposal() method is invoked when the request is /projectProposal and
	 * request mapping type is get.
	 * 
	 * @return project proposal page
	 */
	@GetMapping("/projectProposal")
	public ModelAndView projectProposal(Model model) {
		studentNavigationService.addModelAttributesForProjectProposal(model);
		return new ModelAndView("students/ProjectProposal.html");
	}

	/**
	 * uploadProject() method is invoked when the request is /submitProject and
	 * request mapping type is post.
	 * 
	 * @return ProjectProposal page
	 */
	@PostMapping("/submitProject")
	public ModelAndView uploadProjects(@Valid @ModelAttribute("project") StudentSubmittedProject project,
			BindingResult bindingResult, Model model, final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("students/ProjectProposal.html");
		} else {
			studentNavigationService.addModelAttributesForUploadProjects(project, model, redirectAttributes);

			return new ModelAndView("redirect:/allProjects");
		}
	}

	/**
	 * logout() method is invoked when the request is /logout and request mapping
	 * type is get.
	 * 
	 * @return login page
	 */
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		studentNavigationService.logout(request);
		return new ModelAndView("redirect:/login");
	}
}

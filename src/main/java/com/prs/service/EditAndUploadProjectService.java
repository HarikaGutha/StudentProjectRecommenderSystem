package com.prs.service;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prs.model.SupervisorUploadedProject;

/**
 * EditAndUploadProjectService is an interface which provides service to
 * SupervisorNavigationController.
 * 
 * @author 190026870
 *
 */
public interface EditAndUploadProjectService {

	/**
	 * addAttributesToEditProjectModel() method used to set model attributes.
	 * 
	 * @param id    the project id
	 * @param model the model attribute
	 */
	void addAttributesToEditProjectModel(Integer id, Model model);

	/**
	 * addAttributesToUploadProjectModel() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	void addAttributesToUploadProjectModel(Model model);

	/**
	 * saveProjectService() methods used to save uploaded project
	 * 
	 * @param project            the project
	 * @param model              the model attribute
	 * @param redirectAttributes the redirectAttributes
	 */
	void saveProjectService(@Valid SupervisorUploadedProject project, Model model,
			RedirectAttributes redirectAttributes);

	/**
	 * saveEditedProject() methods used to save edited project
	 * 
	 * @param project            the project
	 * @param model              the model attribute
	 * @param redirectAttributes the redirectAttributes
	 */
	void saveEditedProject(Integer id, Model model, RedirectAttributes redirectAttributes,
			@Valid SupervisorUploadedProject project);
}

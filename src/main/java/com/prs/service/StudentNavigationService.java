package com.prs.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prs.model.StudentSubmittedProject;

/**
 * StudentNavigationService is an interface which provides service to
 * StudentNavigationController.
 * 
 * @author 190026870
 *
 */
public interface StudentNavigationService {

	/**
	 * addModelAttributesForSavedProjects() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	public void addModelAttributesForSavedProjects(Model model);

	/**
	 * addModelAttributesForAllProjects() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	public void addModelAttributesForAllProjects(Model model);

	/**
	 * addModelAttributesForProjectDescription() method used to set model
	 * attributes.
	 * 
	 * @param id    the project id
	 * @param model the model attribute
	 */
	public void addModelAttributesForProjectDescription(Integer id, Model model);

	/**
	 * addModelAttributesForNextAndPreviousProjectDescription() method used to set
	 * model attributes.
	 * 
	 * @param id         the project id
	 * @param model      the model attribute
	 * @param navigation direction of navigation
	 */
	public void addModelAttributesForNextAndPreviousProjectDescription(Integer id, Model model, String navigation);

	/**
	 * addModelAttributesForProjectProposal() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	public void addModelAttributesForProjectProposal(Model model);

	/**
	 * addModelAttributesForUploadProjects() method used to set model attributes.
	 * 
	 * @param project            the project
	 * @param model              the model object
	 * @param redirectAttributes the redirectAttributes
	 */
	public void addModelAttributesForUploadProjects(@Valid StudentSubmittedProject project, Model model,
			RedirectAttributes redirectAttributes);

	/**
	 * logout() method destroys sessions and logout the user.
	 * 
	 * @param request the HttpServletRequest
	 */
	public void logout(HttpServletRequest request);

}

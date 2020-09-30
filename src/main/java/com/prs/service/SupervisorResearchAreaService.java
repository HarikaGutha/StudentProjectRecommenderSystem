package com.prs.service;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * SupervisorResearchAreaService is an interface which provides service to
 * Supervisor and Student Navigation Controller.
 * 
 * @author 190026870
 *
 */
public interface SupervisorResearchAreaService {

	/**
	 * addModelAttributesForSupervisor() method used to set model attributes for
	 * first page.
	 * 
	 * @param pageable the Pageable object
	 * @param model    the model attribute
	 */
	void addModelAttributesForSupervisor(Pageable pageable, Model model, HashMap<Integer, Integer> selectedPageMap);

	/**
	 * addModelAttributesForSupervisorProfile() method used to set model attributes.
	 * 
	 * @param id    supervisor id
	 * @param model the model attribute
	 */
	void addModelAttributesForSupervisorProfile(Integer id, Model model);

	/**
	 * addModelAttributesForSupervisor() method used to set model attributes from
	 * second page.
	 * 
	 * @param pageable the Pageable object
	 * @param model    the model attribute
	 */
	void addModelAttributesForSupervisors(Pageable pageable, Model model, HttpServletRequest request);
}

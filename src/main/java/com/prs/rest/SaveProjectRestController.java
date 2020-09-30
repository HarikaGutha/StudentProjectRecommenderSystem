package com.prs.rest;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.service.SaveProjectService;
import com.prs.service.implementation.SaveProjectServiceImpl;

/**
 * SaveProjectRestController is the rest controller to save and unsave projects
 * 
 * @author 190026870
 *
 */
@RestController
public class SaveProjectRestController {

	@Autowired
	private SaveProjectService saveProjectService;

	/**
	 * saveProject() invoked when request is of type get and mapping is
	 * /saveProject.
	 * 
	 * @param id the project id.
	 */
	@RequestMapping("/saveProject")
	public void saveProject(@PathParam("id") Integer id) {
		saveProjectService.saveProject(id);
	}

	/**
	 * unSaveProject() is invoked when request is of type get and mapping is
	 * unSaveProject.
	 * 
	 * @param id
	 * @param model
	 */
	@RequestMapping("/unSaveProject")
	public void unSaveProject(@PathParam("id") Integer id) {
		saveProjectService.unsaveProject(id);
	}
}

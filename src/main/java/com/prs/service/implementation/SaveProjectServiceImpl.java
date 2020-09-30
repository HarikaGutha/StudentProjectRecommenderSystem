package com.prs.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;
import com.prs.service.SaveProjectService;

/**
 * SaveProjectServiceImpl implements SaveProjectService.
 * 
 * @author 190026870
 *
 */
@Service
public class SaveProjectServiceImpl implements SaveProjectService {

	@Autowired
	private SupervisorUploadedProjectRepository supervisorUploadedProjectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommonMethodsForController commonMethodsForController;

	/**
	 * saveProject() method used to save a project.
	 * 
	 * @param id the project id
	 */
	@Override
	public void saveProject(int id) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		supervisorUploadedProjectRepository.saveProject(id,
				userRepository.findByUsername(currentUserName).get().getUserId());
	}

	/**
	 * unsaveProject() method used to un save a project.
	 * 
	 * @param id the project id
	 */
	@Override
	public void unsaveProject(int id) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		List<Integer> savedProjects = supervisorUploadedProjectRepository.checkSavedProject(id,
				userRepository.findByUsername(currentUserName).get().getUserId());
		if (savedProjects.size() > 0) {
			supervisorUploadedProjectRepository.unSaveProject(id,
					userRepository.findByUsername(currentUserName).get().getUserId());
		}

	}

}

package com.prs.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;
import com.prs.service.implementation.CommonMethodsForController;

/**
 * FilterProjectsBySupervisor is a RestController to filter projects by
 * Supervisor.
 * 
 * @author 190026870
 *
 */
@RestController
public class FilterProjectsBySupervisor {

	@Autowired
	private SupervisorUploadedProjectRepository supervisorUploadedProjectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommonMethodsForController commonMethods;

	/**
	 * filterBySupervisor() filters projects of given supervisor.
	 */
	@GetMapping(path = "/filterBySupervisor", produces = "application/json")
	public ResponseEntity<Page<SupervisorUploadedProject>> filterBySupervisor(
			@PathParam("supervisors") Integer[] supervisors, @PageableDefault(size = 3) Pageable pageable) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		List<Integer> supervisorsList = Arrays.asList(supervisors);
		List<User> supervisorsByIdList = new ArrayList<User>();
		for (int i : supervisorsList) {
			supervisorsByIdList.add(userRepository.findById(i).get());
		}
		Page<SupervisorUploadedProject> projects = supervisorUploadedProjectRepository
				.getSupervisorProjects(supervisorsByIdList, pageable);
		List<Integer> savedProjects = supervisorUploadedProjectRepository.getSavedProjectsOfCurrentLoggedInUser(
				userRepository.findByUsername(currentUserName).get().getUserId());
		for (SupervisorUploadedProject allProjects : projects) {
			if (savedProjects.contains(allProjects.getProjectId())) {
				allProjects.setSavedProject(true);
			}
		}
		return new ResponseEntity<Page<SupervisorUploadedProject>>(projects, HttpStatus.OK);

	}
}

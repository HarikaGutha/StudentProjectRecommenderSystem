package com.prs.rest;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;
import com.prs.service.implementation.CommonMethodsForController;

/**
 * FilterBySupervisorTest tests FilterBySupervisorTest
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class FilterBySupervisorTest {

	@InjectMocks
	private FilterProjectsBySupervisor filterBySupervisor;

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private SupervisorUploadedProjectRepository supervisorUploadedProjectRepository;

	@Mock
	private UserRepository userRepository;

	private String currentUserName = "Test";

	/**
	 * This method test the filterBySupervisor() of FilterProjectsBySupervisor.
	 */
	@Test
	public void testFilterBySupervisor() {
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(currentUserName);
		Integer[] supervisors = { 1 };
		User newUser = new User();
		newUser.setUserId(1);
		Pageable pageable = Mockito.mock(Pageable.class);
		List<SupervisorUploadedProject> supervisorUploadedProjects = new ArrayList<>();
		SupervisorUploadedProject supervisorUploadedProject = new SupervisorUploadedProject();
		supervisorUploadedProject.setProjectId(1);
		supervisorUploadedProjects.add(supervisorUploadedProject);
		Page<SupervisorUploadedProject> projects = Mockito.mock(Page.class);
		when(projects.iterator()).thenReturn(supervisorUploadedProjects.listIterator());
		User userRepo = new User();
		userRepo.setUserId(1);
		Optional<User> userRepositoryuser =  Optional.of(userRepo);
		List<User> user = new ArrayList<User>();
		List<Integer> savedProjects = new ArrayList<Integer>();
		savedProjects.add(1);
		when(userRepository.findByUsername(currentUserName)).thenReturn(userRepositoryuser);
		when(userRepository.findById(1)).thenReturn(userRepositoryuser);
		when(supervisorUploadedProjectRepository.getSupervisorProjects(anyList(), any(Pageable.class))).thenReturn(projects);
		when(supervisorUploadedProjectRepository.getSavedProjectsOfCurrentLoggedInUser(1)).thenReturn(savedProjects);
		ResponseEntity<Page<SupervisorUploadedProject>> responseEntity = filterBySupervisor
				.filterBySupervisor(supervisors, pageable);
		Assert.assertEquals(200, responseEntity.getStatusCode().value());

	}

}

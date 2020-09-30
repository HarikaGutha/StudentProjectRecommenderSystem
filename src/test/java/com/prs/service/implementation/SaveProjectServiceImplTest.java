package com.prs.service.implementation;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.prs.model.User;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;

/**
 * SaveProjectServiceImplTest implements SaveProjectServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveProjectServiceImplTest {

	@InjectMocks
	private SaveProjectServiceImpl saveProjectServiceImpl;

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private SupervisorUploadedProjectRepository supervisorUploadedProjectRepository;

	@Mock
	private UserRepository userRepository;

	private String currentUserName = "Test";

	@Before
	public void setUp() {
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(currentUserName);
	}


	/**
	 * this method tests saveProject() of SaveProjectServiceImpl.
	 */
	@Test
	public void testSaveProject() {
		int id = 1;
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		saveProjectServiceImpl.saveProject(id);
	}

	/**
	 * this method tests unsaveProject() of SaveProjectServiceImpl.
	 */
	@Test
	public void testUnsaveProject() {
		int id = 1;
		List<Integer> savedProjects = new ArrayList<Integer>();
		savedProjects.add(1);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(supervisorUploadedProjectRepository.checkSavedProject(id,
				userRepository.findByUsername(currentUserName).get().getUserId())).thenReturn(savedProjects);
		saveProjectServiceImpl.unsaveProject(id);
	}


}

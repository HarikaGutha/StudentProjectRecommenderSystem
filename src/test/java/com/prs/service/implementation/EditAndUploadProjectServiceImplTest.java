package com.prs.service.implementation;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prs.model.DissertationModule;
import com.prs.model.Module;
import com.prs.model.SupervisorUploadedProject;
import com.prs.model.SupervisorUploadedProjectState;
import com.prs.model.User;
import com.prs.repositories.DissertationModuleRepository;
import com.prs.repositories.ModuleRepository;
import com.prs.repositories.StudentSubmittedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectStateRepository;
import com.prs.repositories.UserRepository;

/**
 * EditAndUploadProjectServiceImplTest test EditAndUploadProjectServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EditAndUploadProjectServiceImplTest {

	private Model model;

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private ModuleRepository moduleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SupervisorUploadedProjectRepository projectRepository;

	@Mock
	private DissertationModuleRepository dissertationModuleRepository;

	@Mock
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	@Mock
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	@InjectMocks
	private EditAndUploadProjectServiceImpl editAndUploadProjectServiceImpl;

	/**
	 * this method sets values to the fields.
	 */
	@Before
	public void setUp() {
		model = new BindingAwareConcurrentModel();
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn("Test");
		when(commonMethodsForController.getLoggedInUserImage("Test")).thenReturn("StudentImage.png");
	}

	/**
	 * this method tests addAttributesToEditProjectModel() of
	 * EditAndUploadProjectServiceImpl.
	 */
	@Test
	public void testAddAttributesToEditProjectModel() {
		User user = new User();
		user.setUserId(1);
		List<Module> modules = new ArrayList<Module>();
		List<User> supervisors = new ArrayList<User>();
		supervisors.add(user);
		List<DissertationModule> dissertationModule = new ArrayList<DissertationModule>();
		Optional<SupervisorUploadedProject> project = Optional.of(new SupervisorUploadedProject());
		when(moduleRepository.findAll()).thenReturn(modules);
		when(dissertationModuleRepository.findAll()).thenReturn(dissertationModule);
		when(userRepository.findByRoleSupervisor()).thenReturn(supervisors);
		when(userRepository.findByUsername("Test")).thenReturn(Optional.of(user));
		when(projectRepository.findById(1)).thenReturn(project);
		editAndUploadProjectServiceImpl.addAttributesToEditProjectModel(1, model);
		Assert.assertEquals(modules, model.getAttribute("module"));
		Assert.assertEquals(supervisors, model.getAttribute("userByRole"));
		Assert.assertEquals(project.get(), model.getAttribute("editProject"));
		Assert.assertEquals(dissertationModule, model.getAttribute("dissertationModule"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests addAttributesToUploadProjectModel() of
	 * EditAndUploadProjectServiceImpl.
	 */
	@Test
	public void testAddAttributesToUploadProjectModel() {
		User user = new User();
		user.setUserId(1);
		List<Module> modules = new ArrayList<Module>();
		List<User> supervisors = new ArrayList<User>();
		supervisors.add(user);
		List<DissertationModule> dissertationModules = new ArrayList<DissertationModule>();
		when(moduleRepository.findAll()).thenReturn(modules);
		when(userRepository.findByRoleSupervisor()).thenReturn(supervisors);
		when(dissertationModuleRepository.findAll()).thenReturn(dissertationModules);
		when(userRepository.findByUsername("Test")).thenReturn(Optional.of(user));
		editAndUploadProjectServiceImpl.addAttributesToUploadProjectModel(model);
		Assert.assertEquals(modules, model.getAttribute("module"));
		Assert.assertEquals(supervisors, model.getAttribute("userByRole"));
		Assert.assertEquals(dissertationModules, model.getAttribute("dissertationModule"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests saveProjectService() of EditAndUploadProjectServiceImpl.
	 */
	@Test
	public void testSaveProjectService() {
		User user = new User();
		user.setUserId(1);
		user.setUsername("Test");
		SupervisorUploadedProjectState projectState = new SupervisorUploadedProjectState();
		projectState.setStateId(1);
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState = new ArrayList<SupervisorUploadedProjectState>();
		supervisorUploadedProjectState.add(projectState);
		List<String> supervisorProjectTitles = new ArrayList<String>();
		List<String> studentProjectTitles = new ArrayList<String>();
		Optional<User> loggedInUser = Optional.of(user);
		SupervisorUploadedProject project = Mockito.mock(SupervisorUploadedProject.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		when(userRepository.findByUsername("Test")).thenReturn(loggedInUser);
		when(projectRepository.findAllTitles("Test")).thenReturn(supervisorProjectTitles);
		when(studentSubmittedProjectRepository.findProjectWithTitle("Test")).thenReturn(studentProjectTitles);
		when(supervisorUploadedProjectStateRepository.findAll()).thenReturn(supervisorUploadedProjectState);
		editAndUploadProjectServiceImpl.saveProjectService(project, model, redirectAttributes);
		Assert.assertEquals(supervisorProjectTitles, projectRepository.findAllTitles("Test"));
		Assert.assertEquals(studentProjectTitles, studentSubmittedProjectRepository.findProjectWithTitle("Test"));
		Assert.assertEquals(supervisorUploadedProjectState, supervisorUploadedProjectStateRepository.findAll());
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests saveProjectService() of EditAndUploadProjectServiceImpl if
	 * there is an error.
	 */
	@Test
	public void testSaveProjectErrorService() {
		User user = new User();
		user.setUserId(1);
		user.setUsername("Test");
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState = new ArrayList<SupervisorUploadedProjectState>();
		List<String> supervisorProjectTitles = new ArrayList<String>();
		supervisorProjectTitles.add("Test");
		List<String> studentProjectTitles = new ArrayList<String>();
		studentProjectTitles.add("Test");
		Optional<User> loggedInUser = Optional.of(user);
		SupervisorUploadedProject project = Mockito.mock(SupervisorUploadedProject.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		when(project.getTitle()).thenReturn("Test");
		when(userRepository.findByUsername("Test")).thenReturn(loggedInUser);
		when(projectRepository.findAllTitles("Test")).thenReturn(supervisorProjectTitles);
		when(studentSubmittedProjectRepository.findProjectWithTitle("Test")).thenReturn(studentProjectTitles);
		when(supervisorUploadedProjectStateRepository.findAll()).thenReturn(supervisorUploadedProjectState);
		editAndUploadProjectServiceImpl.saveProjectService(project, model, redirectAttributes);
		Assert.assertEquals(supervisorProjectTitles, projectRepository.findAllTitles("Test"));
		Assert.assertEquals(studentProjectTitles, studentSubmittedProjectRepository.findProjectWithTitle("Test"));
		Assert.assertEquals(supervisorUploadedProjectState, supervisorUploadedProjectStateRepository.findAll());
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests saveEditedProject() of EditAndUploadProjectServiceImpl.
	 */
	@Test
	public void saveEditedProjectTest() {
		int id = 1;
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		SupervisorUploadedProject project = new SupervisorUploadedProject();
		project.setProjectId(1);
		Optional<SupervisorUploadedProject> existingProject = Optional.of(project);
		when(projectRepository.findById(id)).thenReturn(existingProject);
		editAndUploadProjectServiceImpl.saveEditedProject(id, model, redirectAttributes, project);
	}

}

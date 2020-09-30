package com.prs.service.implementation;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.prs.model.StudentSubmittedProject;
import com.prs.model.StudentSubmittedProjectState;
import com.prs.model.SupervisorUploadedProject;
import com.prs.model.SupervisorUploadedProjectState;
import com.prs.model.User;
import com.prs.repositories.DissertationModuleRepository;
import com.prs.repositories.ModuleRepository;
import com.prs.repositories.StudentSubmittedProjectRepository;
import com.prs.repositories.StudentSubmittedProjectStateRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectStateRepository;
import com.prs.repositories.UserRepository;

/**
 * StudentNavigationServiceImplTest tests StudentNavigationServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentNavigationServiceImplTest {

	private Model model;

	private String currentUserName = "Test";

	@Mock
	private DissertationModuleRepository dissertationModuleRepository;

	@Mock
	private ModuleRepository moduleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SupervisorUploadedProjectRepository projectRepository;

	@Mock
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private StudentSubmittedProjectStateRepository studentSubmittedProjectStateRepository;

	@Mock
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	@InjectMocks
	private StudentNavigationServiceImpl StudentNavigationServiceImpl;

	/**
	 * this method sets values to the fields.
	 */
	@Before
	public void setUp() {
		model = new BindingAwareConcurrentModel();
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn("Test");
		when(commonMethodsForController.getLoggedInUserImage(currentUserName)).thenReturn("StudentImage.png");
	}

	/**
	 * this method tests addModelAttributesForSavedProjects() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForSavedProjects() {
		List<Integer> savedProjects = new ArrayList<Integer>();
		savedProjects.add(1);
		SupervisorUploadedProject supervisorUploadedProject = new SupervisorUploadedProject();
		supervisorUploadedProject.setTitle("test");
		User projectUser = new User();
		projectUser.setUserImage("Test".getBytes());
		supervisorUploadedProject.setUserId(projectUser);
		Optional<SupervisorUploadedProject> project = Optional.of(supervisorUploadedProject);
		Mockito.when(projectRepository.
				findById(1)).thenReturn(project);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(projectRepository.getSavedProjectsOfCurrentLoggedInUser
				(userRepository.findByUsername(currentUserName).get().getUserId())).thenReturn(savedProjects);
		StudentNavigationServiceImpl.addModelAttributesForSavedProjects(model);
		Assert.assertNotNull( model.getAttribute("allSavedProjects"));
		Assert.assertNotNull( model.getAttribute("imageMap"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));

	}

	/**
	 * this method tests addModelAttributesForAllProjects() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForAllProjects() {
		List<DissertationModule> dissertationModule = new ArrayList<DissertationModule>();
		List<Module> module = new ArrayList<Module>();
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState =
				new ArrayList<SupervisorUploadedProjectState>();
		List<SupervisorUploadedProject> project = new ArrayList<SupervisorUploadedProject>();
		SupervisorUploadedProject supervisorUploadedProject = new SupervisorUploadedProject();
		supervisorUploadedProject.setTitle("test");
		supervisorUploadedProject.setProjectId(1);
		User projectUser = new User();
		projectUser.setUserImage("test".getBytes());
		supervisorUploadedProject.setUserId(projectUser);
		project.add(supervisorUploadedProject);
		List<Integer> savedProjects = new ArrayList<Integer>();
		List<Object[]> datePostedObject = new ArrayList<Object[]>();
		String[] datePostedArray =  {"test","test"};
		datePostedObject.add(datePostedArray);
		List<User> allSupervisors = new ArrayList<User>();
		when(dissertationModuleRepository.findAll()).thenReturn(dissertationModule);
		when(moduleRepository.findAll()).thenReturn(module);
		when(supervisorUploadedProjectStateRepository.findAll()).thenReturn(supervisorUploadedProjectState);
		when(projectRepository.findAll()).thenReturn(project);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(projectRepository.getSavedProjectsOfCurrentLoggedInUser
				(userRepository.findByUsername(currentUserName).get().getUserId())).thenReturn(savedProjects);
		when(projectRepository.findByDatePosted()).thenReturn(datePostedObject);
		when(userRepository.findByRoleSupervisor()).thenReturn(allSupervisors);
		StudentNavigationServiceImpl.addModelAttributesForAllProjects(model);
		Assert.assertEquals(dissertationModule, model.getAttribute("dissertationModule"));
		Assert.assertEquals(module, model.getAttribute("module"));
		Assert.assertEquals(supervisorUploadedProjectState, model.getAttribute("supervisorUploadedProjectState"));
		Assert.assertEquals(project, model.getAttribute("project"));
		Assert.assertNotNull( model.getAttribute("datePosted"));
		Assert.assertNotNull( model.getAttribute("imageMap"));
		Assert.assertEquals(savedProjects, model.getAttribute("savedProjects"));
		Assert.assertEquals(allSupervisors, model.getAttribute("allSupervisors"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests addModelAttributesForProjectDescription() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForProjectDescription() {
		int id = 1;
		User user = new User();
		user.setUserId(1);
		user.setUsername("Test");
		SupervisorUploadedProject newProject = new SupervisorUploadedProject();
		newProject.setUserId(user);
		Optional<SupervisorUploadedProject> project = Optional.ofNullable(newProject);
		String supervisorImage = "TestImage";
		Optional<String> date = Optional.of(new String());
		List<Integer> additionalSupervisors = new ArrayList<Integer>();
		List<User> allSupervisors = new ArrayList<User>();
		List<Integer> savedProjects = new ArrayList<Integer>();
		savedProjects.add(1);
		when(projectRepository.findById(id)).thenReturn(project);
		when(commonMethodsForController.getSupervisorImage(
				project.get().getUserId().getUsername())).thenReturn(supervisorImage);
		when(projectRepository.getDateInYYYYMMMFormatForGivenProject(id)).thenReturn(date);
		when(projectRepository.findAdditionalSupervisors(id)).thenReturn(additionalSupervisors);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(projectRepository.getSavedProjectsOfCurrentLoggedInUser
				(userRepository.findByUsername(currentUserName).get().getUserId())).thenReturn(savedProjects);
		StudentNavigationServiceImpl.addModelAttributesForProjectDescription(id, model);
		Assert.assertEquals(project.get(), model.getAttribute("project"));
		Assert.assertEquals(supervisorImage, model.getAttribute("supervisorImage"));
		Assert.assertEquals(date.get(), model.getAttribute("date"));
		Assert.assertEquals(allSupervisors, model.getAttribute("allSupervisors"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));

	}

	/**
	 * this method tests
	 * addModelAttributesForNextAndPreviousProjectDescriptionIfNextProject() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForNextAndPreviousProjectDescriptionIfNextProject() {
		int id=1;
		User user = new User();
		user.setUserId(1);
		user.setUsername("Test");
		SupervisorUploadedProject newProject = new SupervisorUploadedProject();
		newProject.setUserId(user);
		String supervisorImage = "TestImage";
		Optional<User> user1 = Optional.of(new User());
		when(userRepository.findById(1)).thenReturn(user1);
		Optional<SupervisorUploadedProject> project = Optional.ofNullable(newProject);
		List<SupervisorUploadedProject> allProjects = new ArrayList<SupervisorUploadedProject>();
		List<Integer> savedProjects = new ArrayList<Integer>();
		allProjects.add(0, new SupervisorUploadedProject());
		allProjects.add(1, new SupervisorUploadedProject());
		List<Integer> additionalSupervisors = new ArrayList<Integer>();
		additionalSupervisors.add(1);
		List<User> allSupervisors = new ArrayList<User>();
		when(projectRepository.findAdditionalSupervisors(0)).thenReturn(additionalSupervisors);
		when(projectRepository.findAllProjectsFromGivenId(id)).thenReturn(allProjects);
		when(projectRepository.findById(allProjects.get(0).getProjectId())).thenReturn(project);
		when(commonMethodsForController.getSupervisorImage(
				project.get().getUserId().getUsername())).thenReturn(supervisorImage);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(projectRepository.getSavedProjectsOfCurrentLoggedInUser
				(userRepository.findByUsername(currentUserName).get().getUserId())).thenReturn(savedProjects);
		StudentNavigationServiceImpl.addModelAttributesForNextAndPreviousProjectDescription(id, model, "nextProject");
		Assert.assertEquals(project.get(), model.getAttribute("project"));
		Assert.assertEquals(supervisorImage, model.getAttribute("supervisorImage"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
		Assert.assertNotNull(model.getAttribute("allSupervisors"));
	}

	/**
	 * this method tests
	 * addModelAttributesForNextAndPreviousProjectDescriptionIfPreviousProject() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForNextAndPreviousProjectDescriptionIfPreviousProject() {
		int id = 1;
		User user = new User();
		user.setUserId(1);
		user.setUsername("Test");
		SupervisorUploadedProject newProject = new SupervisorUploadedProject();
		newProject.setUserId(user);
		String supervisorImage = "TestImage";
		Optional<SupervisorUploadedProject> project = Optional.ofNullable(newProject);
		List<SupervisorUploadedProject> allProjects = new ArrayList<SupervisorUploadedProject>();
		List<Integer> savedProjects = new ArrayList<Integer>();
		allProjects.add(0, new SupervisorUploadedProject());
		allProjects.add(1, new SupervisorUploadedProject());
		List<Integer> additionalSupervisors = new ArrayList<Integer>();
		List<User> allSupervisors = new ArrayList<User>();
		when(projectRepository.findAdditionalSupervisors(0)).thenReturn(additionalSupervisors);
		when(projectRepository.findAllProjectsBeforeGivenId(id)).thenReturn(allProjects);
		when(projectRepository.findById(allProjects.get(allProjects.size()-1).getProjectId())).thenReturn(project);
		when(commonMethodsForController.getSupervisorImage(
				project.get().getUserId().getUsername())).thenReturn(supervisorImage);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(projectRepository.getSavedProjectsOfCurrentLoggedInUser
				(userRepository.findByUsername(currentUserName).get().getUserId())).thenReturn(savedProjects);
		StudentNavigationServiceImpl.addModelAttributesForNextAndPreviousProjectDescription(id, model, "previousProject");
		Assert.assertEquals(project.get(), model.getAttribute("project"));
		Assert.assertEquals(supervisorImage, model.getAttribute("supervisorImage"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
		Assert.assertEquals(allSupervisors, model.getAttribute("allSupervisors"));

	}

	/**
	 * this method tests addModelAttributesForProjectProposal() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForProjectProposal() {
		List<DissertationModule> dissertationModule = new ArrayList<DissertationModule>();
		List<Module> module = new ArrayList<Module>();
		List<User> userByRole= new ArrayList<User>();
		when(dissertationModuleRepository.findAll()).thenReturn(dissertationModule);
		when(moduleRepository.findAll()).thenReturn(module);
		when(userRepository.findByRoleSupervisor()).thenReturn(userByRole);
		StudentNavigationServiceImpl.addModelAttributesForProjectProposal(model);
		Assert.assertEquals(dissertationModule, model.getAttribute("dissertationModule"));
		Assert.assertEquals(module, model.getAttribute("module"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests addModelAttributesForUploadProjects() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForUploadProjects() {
		User user = new User();
		user.setUserId(1);
		user.setUsername("Test");
		List<StudentSubmittedProjectState> studentSubmittedProjectState = new ArrayList<StudentSubmittedProjectState>();
		List<String> supervisorProjectTitles = new ArrayList<String>();
		List<String> studentProjectTitles = new ArrayList<String>();
		Optional<User> loggedInUser = Optional.of(user);
		StudentSubmittedProject project = Mockito.mock(StudentSubmittedProject.class);
		RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
		when(userRepository.findByUsername("Test")).thenReturn(loggedInUser);
		when(projectRepository.findAllTitles("Test")).thenReturn(supervisorProjectTitles);
		when(studentSubmittedProjectRepository.findProjectWithTitle("Test")).thenReturn(studentProjectTitles);
		when(studentSubmittedProjectStateRepository.findAll()).thenReturn(studentSubmittedProjectState);
		StudentNavigationServiceImpl.addModelAttributesForUploadProjects(project,model,redirectAttributes);
		Assert.assertEquals(supervisorProjectTitles, projectRepository.findAllTitles("Test"));
		Assert.assertEquals(studentProjectTitles, studentSubmittedProjectRepository.findProjectWithTitle("Test"));
		Assert.assertEquals(studentSubmittedProjectState, supervisorUploadedProjectStateRepository.findAll());
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));
	}

	/**
	 * this method tests logout() of StudentNavigationServiceImpl.
	 */
	@Test
	public void logoutTest() {
		try {
			when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn("Test");
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpSession session = Mockito.mock(HttpSession.class);
			User user = new User();
			user.setUserId(1);
			Optional<User> user1 = Optional.of(user);
			when(userRepository.findByUsername("Test")).thenReturn(user1);
			when(request.getSession(false)).thenReturn(session);
			StudentNavigationServiceImpl.logout(request);
		} catch (Exception ex) {
			Assert.fail("logoutTest test case failed");
		}
	}

}

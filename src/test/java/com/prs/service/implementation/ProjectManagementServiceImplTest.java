package com.prs.service.implementation;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;

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
 * ProjectManagementServiceImplTest tests ProjectManagementServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectManagementServiceImplTest {

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private DissertationModuleRepository dissertationModuleRepository;

	@Mock
	private ModuleRepository moduleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SupervisorUploadedProjectRepository projectRepository;

	@Mock
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	@Mock
	private StudentSubmittedProjectStateRepository studentSubmittedProjectSateRepository;

	@Mock
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	@InjectMocks
	private ProjectManagementServiceImpl projectManagementServiceImpl;

	/**
	 * this method tests setModelAttributesForProjectManagement() of
	 * ProjectManagementServiceImpl.
	 */
	@Test
	public void testSetModelAttributesForProjectManagement() {
		String currentUserName = "Test";
		Model model = new BindingAwareConcurrentModel();
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(currentUserName);
		when(commonMethodsForController.getLoggedInUserImage(currentUserName)).thenReturn("StudentImage.png");
		List<DissertationModule> dissertationModule = new ArrayList<DissertationModule>();
		List<Module> module = new ArrayList<Module>();
		List<Integer> studentId = new ArrayList<Integer>();
		studentId.add(1);
		List<User> allStudents = new ArrayList<User>();		
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState = 
				new ArrayList<SupervisorUploadedProjectState>();
		List<StudentSubmittedProjectState> studentSubmittedProjectState = 
				new ArrayList<StudentSubmittedProjectState>();		
		List<SupervisorUploadedProject> project = new ArrayList<SupervisorUploadedProject>();
		List<StudentSubmittedProject> studentProposals = new ArrayList<StudentSubmittedProject>();
		List<SupervisorUploadedProject> allProjects = new ArrayList<SupervisorUploadedProject>();
		List<SupervisorUploadedProject> internalCollaborationProjects = new ArrayList<SupervisorUploadedProject>();
		List<Object[]> datePostedObject =new ArrayList<Object[]>();
		String[] test = { "Test", "Test"};
		datePostedObject.add(test);
		List<Map<String,Object>> datePosted = new ArrayList<Map<String,Object>>();
		List<Object[]> submittedOnObject = new ArrayList<Object[]>();
		submittedOnObject.add(test);
		List<Map<String,Object>> submittedOn = new ArrayList<Map<String,Object>>();
		Optional<User>  oprtionalUser = Optional.of(new User());
		when(userRepository.findById(1)).thenReturn(oprtionalUser);
		when(dissertationModuleRepository.findAll()).thenReturn(dissertationModule);
		when(moduleRepository.findAll()).thenReturn(module);
		when(studentSubmittedProjectRepository.findByStudentRole()).thenReturn(studentId);
		when(supervisorUploadedProjectStateRepository.findAll()).thenReturn(supervisorUploadedProjectState);
		when(studentSubmittedProjectSateRepository.findAll()).thenReturn(studentSubmittedProjectState);
		when(userRepository.findByUsername(currentUserName)).thenReturn(Optional.of(new User()));
		when(projectRepository.findByUserId(userRepository.findByUsername(currentUserName).get())).thenReturn(project);
		when(studentSubmittedProjectRepository.findProjectsBySupervisorId(userRepository.
				findByUsername(currentUserName).get().getUserId())).thenReturn(studentProposals);
		when(projectRepository.findAll()).thenReturn(allProjects);
		when(projectRepository.findByCollaboration(userRepository.
				findByUsername(currentUserName).get().getUserId())).thenReturn(internalCollaborationProjects);
		when(projectRepository.findByDatePosted()).thenReturn(datePostedObject);
		when(studentSubmittedProjectRepository.findByDatePosted()).thenReturn(submittedOnObject);
		projectManagementServiceImpl.setModelAttributesForProjectManagement(model);
		Assert.assertEquals(dissertationModule, model.getAttribute("dissertationModule"));
		Assert.assertEquals(module, model.getAttribute("module"));
		Assert.assertEquals(supervisorUploadedProjectState, model.getAttribute("supervisorUploadedProjectState"));
		Assert.assertEquals(project, model.getAttribute("project"));
		Assert.assertNotNull(model.getAttribute("datePosted"));
		Assert.assertEquals(internalCollaborationProjects, model.getAttribute("internalCollaborationProjects"));
		Assert.assertEquals(allProjects, model.getAttribute("allProjects"));
		Assert.assertEquals(studentProposals, model.getAttribute("studentProposals"));
		Assert.assertNotNull( model.getAttribute("allStudents"));
		Assert.assertNotNull(model.getAttribute("submittedOn"));
		Assert.assertEquals("Test", commonMethodsForController.getCurrentLoggedInUser());
		Assert.assertEquals("StudentImage.png", model.getAttribute("userImage"));

	}

}

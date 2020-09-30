package com.prs.service.implementation;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;

import com.prs.model.ResearchGroup;
import com.prs.model.StudentSubmittedProject;
import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;
import com.prs.repositories.ModuleRepository;
import com.prs.repositories.ResearchGroupRepository;
import com.prs.repositories.StudentSubmittedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;

/**
 * DashboardServiceImplTest tests DashboardServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class DashboardServiceImplTest {

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SupervisorUploadedProjectRepository projectRepository;

	@Mock
	private ResearchGroupRepository researchGroupRepository;

	@Mock
	private ModuleRepository moduleRepository;

	@InjectMocks
	private DashboardServiceImpl dashboardService;

	@Mock
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	private Model model;
	private String studentName;
	private String role;
	private String image;

	/**
	 * this method sets values to the fields.
	 */
	@Before
	public void setUp() {
		model = new BindingAwareConcurrentModel();
		studentName = "Test";
		role = "Student";
		image = "image";
	}

	/**
	 * this method tests setModelAttributesForDashboard() of DashboardServiceImpl.
	 */
	@Test
	public void setModelAttributesForDashboardIfRoleExists() {
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(studentName);
		when(commonMethodsForController.getLoggedInUserImage(studentName)).thenReturn(image);
		when(commonMethodsForController.getLoggedInFullUserName(studentName)).thenReturn(studentName);
		when(commonMethodsForController.getLoggedInUserRole(studentName)).thenReturn(role);
		dashboardService.setModelAttributesForDashboard(model);
		Assert.assertEquals(image, model.getAttribute("userImage"));
		Assert.assertEquals(studentName, model.getAttribute("username"));
		Assert.assertEquals(role, model.getAttribute("role"));
	}

	/**
	 * this method tests setModelAttributesForDashboard() of DashboardServiceImpl if
	 * there is no role.
	 */
	@Test
	public void setModelAttributesForDashboardIfNoDashboardForRole() {
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(studentName);
		when(commonMethodsForController.getLoggedInUserImage(studentName)).thenReturn(image);
		when(commonMethodsForController.getLoggedInFullUserName(studentName)).thenReturn(studentName);
		when(commonMethodsForController.getLoggedInUserRole(studentName)).thenReturn("AdMin");
		dashboardService.setModelAttributesForDashboard(model);
		Assert.assertEquals(image, model.getAttribute("userImage"));
		Assert.assertEquals(studentName, model.getAttribute("username"));
		Assert.assertEquals("No dashboard exists for the user with given role", model.getAttribute("error"));
	}

	/**
	 * this method tests setModelAttributesForStudentDashboard() of
	 * DashboardServiceImpl.
	 */
	@Test
	public void setModelAttributesForStudentDashboardTest() {
		List<ResearchGroup> researchGroupList = new ArrayList<>();
		researchGroupList.add(new ResearchGroup());
		User user = new User();
		user.setUserId(1);
		user.setUsername(studentName);
		user.setLastLogin(new Date());
		Optional<User> newUser = Optional.ofNullable(user);
		SupervisorUploadedProject supervisorUploadedProject = new SupervisorUploadedProject();
		Optional<SupervisorUploadedProject> supervisorProject =  Optional.ofNullable(supervisorUploadedProject);
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(studentName);
		when(userRepository.findByUsername(studentName)).thenReturn(newUser);
		when(projectRepository.numberOfProjectsFromLastLogin(anyString())).thenReturn(1);
		when(projectRepository.getAvailableProjectsCount()).thenReturn(2);
		when(researchGroupRepository.findAll()).thenReturn(researchGroupList);
		List<Map<String,Integer>> projectsPerModules = new ArrayList<Map<String,Integer>>();
	    when(moduleRepository.findProjectsPerModule()).thenReturn(projectsPerModules);
		List<Integer> savedProjects = new ArrayList<Integer>();
		savedProjects.add(1);
		when(projectRepository.
				findById(1)).thenReturn(supervisorProject);
	    when(projectRepository.getSavedProjectsOfCurrentLoggedInUserLimitFour(1)).thenReturn(savedProjects);
		List<User> userByRole= new ArrayList<User>();
		when(userRepository.findByRoleSupervisor()).thenReturn(userByRole);
		dashboardService.setModelAttributesForStudentDashboard(model);
		Assert.assertEquals(projectsPerModules,model.getAttribute("projectsPerModules"));
		Assert.assertNotNull(model.getAttribute("savedProjects"));
		Assert.assertEquals(userByRole,model.getAttribute("userByRole"));		
		Assert.assertEquals(1,model.getAttribute("projectsFromLastLogin"));
		Assert.assertEquals(2,model.getAttribute("availableProjects"));
		Assert.assertEquals(1,model.getAttribute("researchGroups"));

	}

	/**
	 * this method tests setModelAttributesForSupervisorDashboard() of
	 * DashboardServiceImpl.
	 */
	@Test
	public void setModelAttributesForSupervisorDashboardTest() {
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn("TestUser");
		List<SupervisorUploadedProject> projects = new ArrayList<>();
		List<Map<String,Integer>> projectCountByMonth = new ArrayList<>();
		List<StudentSubmittedProject> studentProposals = new ArrayList<>();
		User user = new User();
		user.setUserId(1);
		user.setUsername(studentName);
		user.setLastLogin(new Date());
		Optional<User> newUser = Optional.ofNullable(user);
		when(userRepository.findByUsername("TestUser")).thenReturn(newUser);
		when(projectRepository.
				findCountByMonth(anyInt(), anyString(),anyInt())).thenReturn(projectCountByMonth);
		when(projectRepository.findByUserIdLimitFive(1)).thenReturn(projects);
		when(studentSubmittedProjectRepository.
				findProjectsBySupervisorId(1)).thenReturn(studentProposals);
		dashboardService.setModelAttributesForSupervisorDashboard(model);
		Assert.assertNotNull(model.getAttribute("projects"));
		Assert.assertNotNull(model.getAttribute("projectCountByMonth"));
		Assert.assertNotNull(model.getAttribute("projectsCount"));
		Assert.assertNotNull(model.getAttribute("studentProposalsCount"));
	}
}

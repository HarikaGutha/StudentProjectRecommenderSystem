package com.prs.service.implementation;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;

import com.prs.model.ResearchGroup;
import com.prs.model.ResearchPublication;
import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;
import com.prs.repositories.ResearchGroupRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;

/**
 * SupervisorResearchAreaServiceImplTest tests
 * SupervisorResearchAreaServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class SupervisorResearchAreaServiceImplTest {
	@InjectMocks
	private SupervisorResearchAreaServiceImpl supervisorResearchAreaServiceImpl;

	@Mock
	private CommonMethodsForController commonMethodsForController;

	@Mock
	private SupervisorUploadedProjectRepository supervisorUploadedProjectRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SupervisorUploadedProjectRepository projectRepository;

	private String currentUserName = "Test";

	private String userImage = "Test.png";

	private Model model;
	@Mock
	private ResearchGroupRepository researchGroupRepository;

	/**
	 * this method sets values to the fields.
	 */
	@Before
	public void setUp() {
		model = new BindingAwareConcurrentModel();
		when(commonMethodsForController.getCurrentLoggedInUser()).thenReturn(currentUserName);
		when(commonMethodsForController.getLoggedInUserImage(currentUserName)).thenReturn(userImage);
	}

	/**
	 * this method tests addModelAttributesForSupervisor() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForSupervisor() {
		Pageable pageable = Mockito.mock(Pageable.class);
		List<ResearchGroup> researchGroupsList = new ArrayList<>();
		ResearchGroup researchGroup = new ResearchGroup();
		researchGroup.setGroupId(1);
		researchGroupsList.add(researchGroup);
		when(researchGroupRepository.findAllResearchGroups()).thenReturn(researchGroupsList);
		User supervisorUser = new User();
		supervisorUser.setUserImage("test.png".getBytes());
		Optional<User> user = Optional.of(supervisorUser );
		when(userRepository.findById(anyInt())).thenReturn(user);
		ArrayList<Integer>  groupList = new ArrayList<>();
		groupList.add(1);
		Page<Integer> usersInResearchGroups = new PageImpl<Integer>(groupList);
		when(researchGroupRepository.findUsersBasedOnResearchGroups(anyInt(), any(Pageable.class))).thenReturn(usersInResearchGroups);
		supervisorResearchAreaServiceImpl.addModelAttributesForSupervisor(pageable,model,new HashMap<>());
		Assert.assertNotNull(model.getAttribute("previouslySelectPages"));
		Assert.assertNotNull(model.getAttribute("researchGroups"));
		Assert.assertNotNull(model.getAttribute("groupPagesMap"));
		Assert.assertNotNull(model.getAttribute("selectedMap"));
		Assert.assertNotNull(model.getAttribute("supervisorImages"));

	}

	/**
	 * this method tests addModelAttributesForSupervisorProfile() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForSupervisorProfile() {
		int id = 1;
		when(commonMethodsForController.getLoggedInUserRole(currentUserName)).thenReturn("testRole");
		User supervisorUser = new User();
		ResearchGroup researchGroup = new ResearchGroup();
		researchGroup.setGroupName("TestGroupName");
		researchGroup.setGroupImage("Test.png".getBytes());
		supervisorUser.setUserImage("userImage".getBytes());
		Set<ResearchGroup> researchGroups = new HashSet<>();
		researchGroups.add(researchGroup);
		supervisorUser.setFirstName("testFirstName");
		supervisorUser.setResearchGroups(researchGroups);
		Optional<User> supervisorDetails = Optional.of(supervisorUser);
		Set<ResearchPublication> researchPublicationSet = new HashSet<>();
		ResearchPublication researchPublication = new ResearchPublication();
		researchPublicationSet.add(researchPublication);
		supervisorUser.setResearchPublication(researchPublicationSet);

		Map<User, String> userMap = new HashMap<User, String>();
		Map<String, String> allResearchGroups = new HashMap<String, String>();
		List<SupervisorUploadedProject> someProjects = new ArrayList<SupervisorUploadedProject>();
		List<Object[]> projectsCountPerTopic = new ArrayList<Object[]>();
		String[]  projectCounts = {"test","test"};
		projectsCountPerTopic.add(projectCounts);
		List<Map<String, Object>> projectsPerTopics = new ArrayList<Map<String, Object>>();
		List<ResearchPublication> allResearchPublications = new ArrayList<ResearchPublication>();
		when(userRepository.findById(id)).thenReturn(supervisorDetails);
		when(projectRepository.getFourProjects(id)).thenReturn(someProjects);
		when(projectRepository.findProjectCountPerTopic(id)).thenReturn(projectsCountPerTopic);
		supervisorResearchAreaServiceImpl.addModelAttributesForSupervisorProfile(id, model);
		Assert.assertNotNull( model.getAttribute("projectsCountPerTopic"));
		Assert.assertNotNull( model.getAttribute("userImage"));
		Assert.assertNotNull( model.getAttribute("allResearchGroups"));
		Assert.assertNotNull(model.getAttribute("someProjects"));
		Assert.assertNotNull( model.getAttribute("allResearchPublications"));
		Assert.assertNotNull(model.getAttribute("publicationsCount"));
	}

	/**
	 * this method tests addModelAttributesForSupervisors() of
	 * StudentNavigationServiceImpl.
	 */
	@Test
	public void testAddModelAttributesForSupervisors() {
		Pageable pageable = Mockito.mock(Pageable.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		when(request.getParameter("previouslySelectPages")).thenReturn("1:1@2:1@3:2@");
		when(request.getParameter("selectedPage")).thenReturn("1");
		when(request.getParameter("selectedGroupId")).thenReturn("1");
		List<ResearchGroup> researchGroupsList = new ArrayList<>();
		ResearchGroup researchGroup = new ResearchGroup();
		researchGroup.setGroupId(1);
		researchGroupsList.add(researchGroup);
		when(researchGroupRepository.findAllResearchGroups()).thenReturn(researchGroupsList);
		User supervisorUser = new User();
		supervisorUser.setUserImage("test.png".getBytes());
		Optional<User> user = Optional.of(supervisorUser );
		when(userRepository.findById(anyInt())).thenReturn(user);
		ArrayList<Integer>  groupList = new ArrayList<>();
		groupList.add(1);
		Page<Integer> usersInResearchGroups = new PageImpl<Integer>(groupList);
		when(researchGroupRepository.findUsersBasedOnResearchGroups(anyInt(), any(Pageable.class))).thenReturn(usersInResearchGroups);
		supervisorResearchAreaServiceImpl.addModelAttributesForSupervisors(pageable,model,request);
		Assert.assertNotNull(model.getAttribute("groupPagesMap"));
		Assert.assertNotNull(model.getAttribute("selectedMap"));
		Assert.assertNotNull(model.getAttribute("supervisorImages"));

	}
}

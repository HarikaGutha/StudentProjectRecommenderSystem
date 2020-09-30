package com.prs.service.implementation;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.prs.model.ResearchGroup;
import com.prs.model.ResearchPublication;
import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;
import com.prs.repositories.ResearchGroupRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;
import com.prs.service.SupervisorResearchAreaService;

/**
 * SupervisorResearchAreaServiceImpl implements SupervisorResearchAreaService.
 * 
 * @author 190026870
 *
 */
@Service
public class SupervisorResearchAreaServiceImpl implements SupervisorResearchAreaService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SupervisorUploadedProjectRepository projectRepository;

	@Autowired
	private CommonMethodsForController commonMethods;

	@Autowired
	private ResearchGroupRepository researchGroupRepository;

	private Map<ResearchGroup, HashMap<User, String>> userGroupsMap;

	/**
	 * addModelAttributesForSupervisor() method used to set model attributes.
	 * 
	 * @param pageable        the Pageable object
	 * @param model           the model attribute
	 * @param selectedPageMap the hashmap
	 */
	@Override
	public void addModelAttributesForSupervisor(Pageable pageable, Model model,
			HashMap<Integer, Integer> selectedPageMap) {
		userGroupsMap = new HashMap<>();
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		HashMap<ResearchGroup, Page<Integer>> researchGroupMap = new LinkedHashMap<>();
		HashMap<User, String> userAndImageMap = new HashMap<User, String>();
		Page<Integer> usersInResearchGroups = null;
		HashMap<Integer, List<Integer>> groupPagesMap = new HashMap<>();
		Map<ResearchGroup, HashMap<User, String>> researchGroupsAndUsersMap = new LinkedHashMap<>();
		List<ResearchGroup> researchGroupsList = researchGroupRepository.findAllResearchGroups();
		StringBuilder selectedPages = new StringBuilder();
		if (selectedPageMap == null || selectedPageMap.isEmpty()) {
			selectedPageMap = new HashMap<>();
			for (ResearchGroup researchGroup : researchGroupsList) {
				selectedPageMap.put(researchGroup.getGroupId(), 1);
				selectedPages.append(researchGroup.getGroupId() + ":" + 1 + "@");
			}
			model.addAttribute("previouslySelectPages", selectedPages.toString());
		}
		int pageId = 0;
		for (ResearchGroup rg : researchGroupsList) {
			pageId = selectedPageMap.get(rg.getGroupId()) != null ? selectedPageMap.get(rg.getGroupId()) - 1 : 0;
			pageable = PageRequest.of(pageId, 6);
			usersInResearchGroups = researchGroupRepository.findUsersBasedOnResearchGroups(rg.getGroupId(), pageable);
			researchGroupMap.put(rg, usersInResearchGroups);
			groupPagesMap.put(rg.getGroupId(), getResearchGroupPages(usersInResearchGroups.getTotalPages()));
		}
		model.addAttribute("researchGroups", usersInResearchGroups);

		for (Entry<ResearchGroup, Page<Integer>> eachEntry : researchGroupMap.entrySet()) {
			Page<Integer> value = eachEntry.getValue();
			userAndImageMap = new HashMap<>();
			for (Object o : value.getContent()) {
				Optional<User> allSupervisors = userRepository.findById((int) o);
				userAndImageMap.put(allSupervisors.get(), new String(allSupervisors.get().getUserImage()));

				userGroupsMap.put(eachEntry.getKey(), userAndImageMap);
			}
			researchGroupsAndUsersMap.put(eachEntry.getKey(), userAndImageMap);
		}
		if (researchGroupsAndUsersMap != null) {
			model.addAttribute("supervisorImages", researchGroupsAndUsersMap);
		}
		if (groupPagesMap != null) {
			model.addAttribute("groupPagesMap", groupPagesMap);
		}
		model.addAttribute("selectedMap", selectedPageMap);

	}

	/**
	 * getResearchGroupPages() gets all pages of the research groups.
	 * 
	 * @param totalPages total pages of the page object
	 * @return page numbers
	 */
	private List<Integer> getResearchGroupPages(int totalPages) {
		List<Integer> pageNumbers = new ArrayList<Integer>();
		if (totalPages > 0) {
			pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

		}
		return pageNumbers;
	}

	/**
	 * addModelAttributesForSupervisorProfile() method used to set model attributes.
	 * 
	 * @param id    supervisor id
	 * @param model the model attribute
	 */
	@Override
	public void addModelAttributesForSupervisorProfile(Integer id, Model model) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		Map<User, String> userMap = new HashMap<User, String>();
		Map<String, String> allResearchGroups = new HashMap<String, String>();
		Optional<User> supervisorDetails = userRepository.findById(id);
		for (ResearchGroup researchGroup : supervisorDetails.get().getResearchGroups()) {
			allResearchGroups.put(researchGroup.getGroupName(), new String(researchGroup.getGroupImage()));
		}
		userMap.put(supervisorDetails.get(), new String(supervisorDetails.get().getUserImage()));
		List<SupervisorUploadedProject> someProjects = projectRepository.getFourProjects(id);
		List<Object[]> projectsCountPerTopic = projectRepository.findProjectCountPerTopic(id);
		List<Map<String, Object>> projectsPerTopics = new ArrayList<Map<String, Object>>();
		for (Object[] projectsCount : projectsCountPerTopic) {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put(String.valueOf(projectsCount[0]), projectsCount[1]);
			projectsPerTopics.add(values);
		}
		List<ResearchPublication> allResearchPublications = new ArrayList<ResearchPublication>();
		for (ResearchPublication researchPublication : supervisorDetails.get().getResearchPublication()) {
			allResearchPublications.add(researchPublication);
		}
		String role = commonMethods.getLoggedInUserRole(currentUserName);
		if (role != null) {
			model.addAttribute("role", role);
		}
		if (projectsPerTopics != null) {
			model.addAttribute("projectsCountPerTopic", projectsPerTopics);
		}
		if (userMap != null) {
			model.addAttribute("userMap", userMap);
		}
		if (allResearchGroups != null) {
			model.addAttribute("allResearchGroups", allResearchGroups);
		}
		if (someProjects != null) {
			model.addAttribute("someProjects", someProjects);
		}
		if (supervisorDetails.get().getFirstName() != null) {
			model.addAttribute("firstName", supervisorDetails.get().getFirstName());
		}
		if (allResearchPublications != null) {
			model.addAttribute("allResearchPublications", allResearchPublications);
			model.addAttribute("publicationsCount", allResearchPublications.size());
		}
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		if(currentUserName != null ) {
			model.addAttribute("currentUserName",currentUserName);
		}

	}

	/**
	 * addModelAttributesForSupervisors() method used to set model attributes.
	 * 
	 * @param pageable the Pageable object
	 * @param model    the model attribute
	 * @param request  the HttpServletRequest
	 */
	@Override
	public void addModelAttributesForSupervisors(Pageable pageable, Model model, HttpServletRequest request) {
		String previouslySelectedPages = request.getParameter("previouslySelectPages");
		String selectedPageId = request.getParameter("selectedPage");
		String selectedGroupId = request.getParameter("selectedGroupId");
		StringTokenizer pageIdAndGroupIdListTokenizer = new StringTokenizer(previouslySelectedPages, "@");
		StringTokenizer pageIdAndGroupIdTokenizer = null;
		String groupIdAndPageIdToken = "";
		HashMap<Integer, Integer> selectedPageMap = new HashMap<>();
		StringBuilder selectedPageMapString = new StringBuilder();
		while (pageIdAndGroupIdListTokenizer.hasMoreTokens()) {
			groupIdAndPageIdToken = pageIdAndGroupIdListTokenizer.nextToken();
			if (!"@".equals(groupIdAndPageIdToken)) {
				pageIdAndGroupIdTokenizer = new StringTokenizer(groupIdAndPageIdToken, ":");
				selectedPageMap.put(Integer.parseInt(pageIdAndGroupIdTokenizer.nextToken()),
						Integer.parseInt(pageIdAndGroupIdTokenizer.nextToken()));
			}
		}
		selectedPageMap.put(Integer.parseInt(selectedGroupId), Integer.parseInt(selectedPageId));
		Set<Integer> groupIdSet = selectedPageMap.keySet();
		groupIdSet.forEach(groupId -> {
			selectedPageMapString.append(groupId + ":" + selectedPageMap.get(groupId)).append("@");
		});
		model.addAttribute("previouslySelectPages", selectedPageMapString.toString());
		addModelAttributesForSupervisor(pageable, model, selectedPageMap);

	}
}

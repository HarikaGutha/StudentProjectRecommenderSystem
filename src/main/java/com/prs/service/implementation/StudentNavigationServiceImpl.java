package com.prs.service.implementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
import com.prs.service.StudentNavigationService;

/**
 * StudentNavigationServiceImpl implements StudentNavigationService.
 * 
 * @author 190026870
 *
 */
@Service
public class StudentNavigationServiceImpl implements StudentNavigationService {

	@Autowired
	private DissertationModuleRepository dissertationModuleRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SupervisorUploadedProjectRepository projectRepository;

	@Autowired
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	@Autowired
	private CommonMethodsForController commonMethods;

	@Autowired
	private StudentSubmittedProjectStateRepository studentSubmittedProjectStateRepository;

	@Autowired
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	/**
	 * addModelAttributesForSavedProjects() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	@Override
	public void addModelAttributesForSavedProjects(Model model) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		List<Integer> savedProjects = projectRepository.getSavedProjectsOfCurrentLoggedInUser(
				userRepository.findByUsername(currentUserName).get().getUserId());
		List<SupervisorUploadedProject> allSavedProjects = new ArrayList<SupervisorUploadedProject>();
		Map<User, String> imageMap = new HashMap<User, String>();
		for (int i = 0; i < savedProjects.size(); i++) {
			Optional<SupervisorUploadedProject> project = projectRepository.findById(savedProjects.get(i));
			allSavedProjects.add(project.get());
			imageMap.put(project.get().getUserId(), new String(project.get().getUserId().getUserImage()));
			if (savedProjects.contains(project.get().getProjectId())) {
				project.get().setSavedProject(true);
			}
		}
		if (allSavedProjects != null) {
			model.addAttribute("allSavedProjects", allSavedProjects);
		}
		if (imageMap != null) {
			model.addAttribute("imageMap", imageMap);
		}

	}

	/**
	 * addModelAttributesForAllProjects() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	@Override
	public void addModelAttributesForAllProjects(Model model) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		List<DissertationModule> dissertationModule = dissertationModuleRepository.findAll();
		List<Module> module = moduleRepository.findAll();
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState = supervisorUploadedProjectStateRepository
				.findAll();
		List<SupervisorUploadedProject> project = projectRepository.findAll();
		Map<User, String> imageMap = new HashMap<User, String>();
		List<Integer> savedProjects = projectRepository.getSavedProjectsOfCurrentLoggedInUser(
				userRepository.findByUsername(currentUserName).get().getUserId());
		for (SupervisorUploadedProject allProjects : project) {
			imageMap.put(allProjects.getUserId(), new String(allProjects.getUserId().getUserImage()));
			if (savedProjects.contains(allProjects.getProjectId())) {
				allProjects.setSavedProject(true);
			}
		}
		List<Object[]> datePostedObject = projectRepository.findByDatePosted();
		List<User> allSupervisors = userRepository.findByRoleSupervisor();
		List<Map<String, Object>> datePosted = new ArrayList<Map<String, Object>>();
		for (Object[] dateObjectArray : datePostedObject) {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put(String.valueOf(dateObjectArray[0]), dateObjectArray[1]);
			datePosted.add(values);
		}
		if (dissertationModule != null) {
			model.addAttribute("dissertationModule", dissertationModule);
		}
		if (module != null) {
			model.addAttribute("module", module);
		}
		if (supervisorUploadedProjectState != null) {
			model.addAttribute("supervisorUploadedProjectState", supervisorUploadedProjectState);
		}
		if (project != null) {
			model.addAttribute("project", project);
		}
		if (datePosted != null) {
			model.addAttribute("datePosted", datePosted);
		}
		if (imageMap != null) {
			model.addAttribute("imageMap", imageMap);
		}

		if (savedProjects != null) {
			model.addAttribute("savedProjects", savedProjects);
		}
		if (allSupervisors != null) {
			model.addAttribute("allSupervisors", allSupervisors);
		}
	}

	/**
	 * addModelAttributesForProjectDescription() method used to set model
	 * attributes.
	 * 
	 * @param id    the project id
	 * @param model the model attribute
	 */
	@Override
	public void addModelAttributesForProjectDescription(Integer id, Model model) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		Optional<SupervisorUploadedProject> project = projectRepository.findById(id);
		if (project.get() != null) {
			model.addAttribute("project", project.get());
		}
		String supervisorImage = commonMethods.getSupervisorImage(project.get().getUserId().getUsername());
		if (supervisorImage != null) {
			model.addAttribute("supervisorImage", supervisorImage);
		}
		Optional<String> date = projectRepository.getDateInYYYYMMMFormatForGivenProject(id);
		if (date != null) {
			model.addAttribute("date", date.get());
		}
		List<Integer> additionalSupervisors = projectRepository.findAdditionalSupervisors(id);
		List<User> allSupervisors = new ArrayList<User>();
		for (Integer i : additionalSupervisors) {
			Optional<User> supervisors = userRepository.findById(i);
			allSupervisors.add(supervisors.get());

		}
		List<Integer> savedProjects = projectRepository.getSavedProjectsOfCurrentLoggedInUser(
				userRepository.findByUsername(currentUserName).get().getUserId());
		if (savedProjects.contains(project.get().getProjectId())) {
			project.get().setSavedProject(true);
		}
		if (allSupervisors != null) {
			model.addAttribute("allSupervisors", allSupervisors);
		}

	}

	/**
	 * addModelAttributesForNextAndPreviousProjectDescription() method used to set
	 * model attributes.
	 * 
	 * @param id         the project id
	 * @param model      the model attribute
	 * @param navigation direction of navigation
	 */
	@Override
	public void addModelAttributesForNextAndPreviousProjectDescription(Integer id, Model model, String navigation) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		List<SupervisorUploadedProject> allProjects = new ArrayList<SupervisorUploadedProject>();
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		if (navigation.equalsIgnoreCase("nextProject")) {
			allProjects = projectRepository.findAllProjectsFromGivenId(id);
		} else if (navigation.equalsIgnoreCase("previousProject")) {
			allProjects = projectRepository.findAllProjectsBeforeGivenId(id);
		}
		Iterator<SupervisorUploadedProject> projects = allProjects.listIterator();
		Optional<SupervisorUploadedProject> project = null;
		if (projects.hasNext() && navigation.equalsIgnoreCase("nextProject")) {
			project = projectRepository.findById(allProjects.get(0).getProjectId());
		} else if (projects.hasNext() && navigation.equalsIgnoreCase("previousProject")) {
			project = projectRepository.findById(allProjects.get(allProjects.size() - 1).getProjectId());
		} else {
			allProjects = projectRepository.findAll();
			projects = allProjects.listIterator();
			if (projects.hasNext() && navigation.equalsIgnoreCase("nextProject")) {
				project = projectRepository.findById(allProjects.get(0).getProjectId());
			} else if (projects.hasNext() && navigation.equalsIgnoreCase("previousProject")) {
				project = projectRepository.findById(allProjects.get(allProjects.size() - 1).getProjectId());
			}
		}
		List<Integer> additionalSupervisors = projectRepository.findAdditionalSupervisors(project.get().getProjectId());
		List<User> allSupervisors = new ArrayList<User>();
		for (Integer i : additionalSupervisors) {
			Optional<User> supervisors = userRepository.findById(i);
			allSupervisors.add(supervisors.get());

		}
		List<Integer> savedProjects = projectRepository.getSavedProjectsOfCurrentLoggedInUser(
				userRepository.findByUsername(currentUserName).get().getUserId());
		if (savedProjects.contains(project.get().getProjectId())) {
			project.get().setSavedProject(true);
		}
		String supervisorImage = commonMethods.getSupervisorImage(project.get().getUserId().getUsername());
		if (supervisorImage != null) {
			model.addAttribute("supervisorImage", supervisorImage);
		}
		if (project != null) {
			model.addAttribute("project", project.get());
		}
		if(allSupervisors != null) {
			model.addAttribute("allSupervisors",allSupervisors);
		}
	}

	/**
	 * addModelAttributesForProjectProposal() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	@Override
	public void addModelAttributesForProjectProposal(Model model) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}
		List<DissertationModule> dissertationModule = dissertationModuleRepository.findAll();
		List<Module> module = moduleRepository.findAll();
		List<User> userByRole = userRepository.findByRoleSupervisor();

		if (dissertationModule != null) {
			model.addAttribute("dissertationModule", dissertationModule);
		}
		if (module != null) {
			model.addAttribute("module", module);
		}
		if (userByRole != null) {
			model.addAttribute("userByRole", userByRole);
		}

		model.addAttribute("project", new StudentSubmittedProject());

	}

	/**
	 * addModelAttributesForUploadProjects() method used to set model attributes.
	 * 
	 * @param project            the project
	 * @param model              the model object
	 * @param redirectAttributes the redirectAttributes
	 */
	@Override
	public void addModelAttributesForUploadProjects(@Valid StudentSubmittedProject project, Model model,
			RedirectAttributes redirectAttributes) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		String userImage = commonMethods.getLoggedInUserImage(currentUserName);
		if (userImage != null) {
			model.addAttribute("userImage", userImage);
		}

		Optional<User> loggedInUser = userRepository.findByUsername(currentUserName);
		if (loggedInUser.get().getUsername().equalsIgnoreCase(currentUserName)) {
			project.setUserId(loggedInUser.get());
		}
		List<StudentSubmittedProjectState> studentSubmittedProjectState = studentSubmittedProjectStateRepository
				.findAll();
		for (StudentSubmittedProjectState state : studentSubmittedProjectState) {
			if (state.getStateId() == 1) {
				project.setProjectState(state);
			}
		}
		List<String> supervisorProjectTitles = projectRepository.findAllTitles(project.getTitle());
		List<String> studentProjectTitles = studentSubmittedProjectRepository.findProjectWithTitle(project.getTitle());
		if (supervisorProjectTitles.size() > 0 || studentProjectTitles.size() > 0) {
			redirectAttributes.addAttribute("message", "Project with the title exists");
		} else {
			studentSubmittedProjectRepository.save(project);
		}

	}

	/**
	 * logout() method destroys sessions and logout the user.
	 * 
	 * @param request the HttpServletRequest
	 */
	@Override
	public void logout(HttpServletRequest request) {
		String currentUserName = commonMethods.getCurrentLoggedInUser();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		int id = userRepository.findByUsername(currentUserName).get().getUserId();
		userRepository.updateLastLogin(timestamp, id);
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

}

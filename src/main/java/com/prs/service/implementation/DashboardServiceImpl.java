package com.prs.service.implementation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.prs.model.StudentSubmittedProject;
import com.prs.model.SupervisorUploadedProject;
import com.prs.model.User;
import com.prs.repositories.ModuleRepository;
import com.prs.repositories.ResearchGroupRepository;
import com.prs.repositories.StudentSubmittedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.UserRepository;
import com.prs.service.DashboardService;

/**
 * DashboardServiceImpl implements DashboardService.
 * 
 * @author 190026870
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private CommonMethodsForController commonMethodsForController;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResearchGroupRepository researchGroupRepository;

	@Autowired
	private SupervisorUploadedProjectRepository projectRepository;

	@Autowired
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	/**
	 * setModelAttributesForDashboard() method used to set model attributes.
	 * 
	 * @param model model attribute
	 */
	@Override
	public void setModelAttributesForDashboard(Model model) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		String loggedInUserImage = commonMethodsForController.getLoggedInUserImage(currentUserName);
		String username = commonMethodsForController.getLoggedInFullUserName(currentUserName);
		String role = commonMethodsForController.getLoggedInUserRole(currentUserName);
		if (loggedInUserImage != null) {
			model.addAttribute("userImage", loggedInUserImage);
		}
		if (username != null) {
			model.addAttribute("username", username);
		}
		if (role != null) {
			if(role.equalsIgnoreCase("Student") || role.equalsIgnoreCase("Supervisor")) {
			model.addAttribute("role", role); 
			} else {
				model.addAttribute("error", "No dashboard exists for the user with given role");
			}
		}
	}

	/**
	 * setModelAttributesForStudentDashboard(Model model) method used to set model
	 * attributes.
	 * 
	 * @param model model attribute
	 */
	@Override
	public void setModelAttributesForStudentDashboard(Model model) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		Date lastLogin = userRepository.findByUsername(currentUserName).get().getLastLogin();
		String lastLoginString = null;
		if(lastLogin!=null) {
		 lastLoginString = lastLogin.toString();
		}
		int projectsFromLastLogin = projectRepository.numberOfProjectsFromLastLogin(lastLoginString);
		int availableProjects = projectRepository.getAvailableProjectsCount();
		int researchGroups = researchGroupRepository.findAll().size();
		List<Map<String, Integer>> projectsPerModules = moduleRepository.findProjectsPerModule();
		if (projectsPerModules != null) {
			model.addAttribute("projectsPerModules", projectsPerModules);
		}
		List<Integer> savedProjects = projectRepository.getSavedProjectsOfCurrentLoggedInUserLimitFour(
				userRepository.findByUsername(currentUserName).get().getUserId());
		List<SupervisorUploadedProject> fourSavedProjects = new ArrayList<SupervisorUploadedProject>();
		for (int i = 0; i < savedProjects.size(); i++) {
			Optional<SupervisorUploadedProject> project = projectRepository.findById(savedProjects.get(i));
			fourSavedProjects.add(project.get());
		}
		List<User> userByRole = userRepository.findByRoleSupervisor();
		if (fourSavedProjects != null) {
			model.addAttribute("savedProjects", fourSavedProjects);
		}
		if (userByRole != null) {
			model.addAttribute("userByRole", userByRole);
		}
		model.addAttribute("projectsFromLastLogin", projectsFromLastLogin);
		model.addAttribute("availableProjects", availableProjects);
		model.addAttribute("researchGroups", researchGroups);
	}

	/**
	 * setModelAttributesForSupervisorDashboard(Model model) method used to set
	 * model attributes.
	 * 
	 * @param model model attribute
	 */
	@Override
	public void setModelAttributesForSupervisorDashboard(Model model) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
		Optional<User> user = userRepository.findByUsername(currentUserName);
		List<SupervisorUploadedProject> projects = projectRepository.findByUserIdLimitFive(user.get().getUserId());
		List<Map<String, Integer>> projectCountByMonth = projectRepository.findCountByMonth(user.get().getUserId(),
				formatNowYear.format(Calendar.getInstance().getTime()), 1);
		int projectsCount = projectRepository.getProjectsCount(userRepository.findByUsername(currentUserName).get());
		List<StudentSubmittedProject> studentProposals = studentSubmittedProjectRepository
				.findProjectsBySupervisorId(userRepository.findByUsername(currentUserName).get().getUserId());
		int studentProposalsCount = studentProposals.size();
		if (projects != null) {
			model.addAttribute("projects", projects);
		}
		if (projectCountByMonth != null) {
			model.addAttribute("projectCountByMonth", projectCountByMonth);
		}
		model.addAttribute("projectsCount", projectsCount);
		model.addAttribute("studentProposalsCount", studentProposalsCount);
	}
}

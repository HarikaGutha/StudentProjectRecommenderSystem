package com.prs.service.implementation;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
import com.prs.service.EditAndUploadProjectService;

/**
 * EditAndUploadProjectServiceImpl implements EditAndUploadProjectService.
 * 
 * @author 190026870
 *
 */
@Service
public class EditAndUploadProjectServiceImpl implements EditAndUploadProjectService {

	@Autowired
	private CommonMethodsForController commonMethodsForController;

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
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	/**
	 * addAttributesToEditProjectModel() method used to set model attributes.
	 * 
	 * @param id    the project id
	 * @param model the model attribute
	 */
	@Override
	public void addAttributesToEditProjectModel(Integer id, Model model) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		String loggedInUserImage = commonMethodsForController.getLoggedInUserImage(currentUserName);
		if (loggedInUserImage != null) {
			model.addAttribute("userImage", loggedInUserImage);
		}
		List<DissertationModule> dissertationModule = dissertationModuleRepository.findAll();
		List<Module> module = moduleRepository.findAll();
		List<User> userByRole = userRepository.findByRoleSupervisor();
		for(User user : userByRole) {
			if(user.getUserId() == userRepository.findByUsername(currentUserName).get().getUserId()) {
				userByRole.remove(user);
				break;
			}
			break;
		}
		if (userByRole != null) {
			model.addAttribute("userByRole", userByRole);
		}
		if (dissertationModule != null) {
			model.addAttribute("dissertationModule", dissertationModule);
		}
		if (module != null) {
			model.addAttribute("module", module);
		}
		Optional<SupervisorUploadedProject> editProject = projectRepository.findById(id);
		model.addAttribute("editProject", editProject.get());
	}

	/**
	 * addAttributesToUploadProjectModel() method used to set model attributes.
	 * 
	 * @param model the model attribute
	 */
	@Override
	public void addAttributesToUploadProjectModel(Model model) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		String loggedInUserImage = commonMethodsForController.getLoggedInUserImage(currentUserName);
		if (loggedInUserImage != null) {
			model.addAttribute("userImage", loggedInUserImage);
		}
		List<DissertationModule> dissertationModule = dissertationModuleRepository.findAll();
		List<Module> module = moduleRepository.findAll();
		List<User> userByRole = userRepository.findByRoleSupervisor();
		for(User user : userByRole) {
			if(user.getUserId() == userRepository.findByUsername(currentUserName).get().getUserId()) {
				userByRole.remove(user);
				break;
			}
			break;
		}
		if (dissertationModule != null) {
			model.addAttribute("dissertationModule", dissertationModule);
		}
		if (module != null) {
			model.addAttribute("module", module);
		}
		if (userByRole != null) {
			model.addAttribute("userByRole", userByRole);
		}

		model.addAttribute("project", new SupervisorUploadedProject());
	}

	/**
	 * saveProjectService() methods used to save uploaded project
	 * 
	 * @param project            the project
	 * @param model              the model attribute
	 * @param redirectAttributes the redirectAttributes
	 */
	@Override
	public void saveProjectService(@Valid SupervisorUploadedProject project, Model model,
			RedirectAttributes redirectAttributes) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		String loggedInUserImage = commonMethodsForController.getLoggedInUserImage(currentUserName);
		if (loggedInUserImage != null) {
			model.addAttribute("userImage", loggedInUserImage);
		}
		Optional<User> loggedInUser = userRepository.findByUsername(currentUserName);
		if (loggedInUser.get().getUsername().equalsIgnoreCase(currentUserName)) {
			project.setUserId(loggedInUser.get());
		}
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState = supervisorUploadedProjectStateRepository
				.findAll();
		for (SupervisorUploadedProjectState state : supervisorUploadedProjectState) {
			if (state.getStateId() == 1) {
				project.setProjectState(state);
			}
		}
		List<String> supervisorProjectTitles = projectRepository.findAllTitles(project.getTitle());
		List<String> studentProjectTitles = studentSubmittedProjectRepository.findProjectWithTitle(project.getTitle());
		if (supervisorProjectTitles.size() > 0 || studentProjectTitles.size() > 0) {
			redirectAttributes.addAttribute("message", "Project with the title exists");
		} else {
			projectRepository.save(project);
		}

	}

	/**
	 * saveEditedProject() methods used to save edited project
	 * 
	 * @param project            the project
	 * @param model              the model attribute
	 * @param redirectAttributes the redirectAttributes
	 */
	@Override
	public void saveEditedProject(Integer id, Model model, RedirectAttributes redirectAttributes,
			@Valid SupervisorUploadedProject project) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		String loggedInUserImage = commonMethodsForController.getLoggedInUserImage(currentUserName);
		if (loggedInUserImage != null) {
			model.addAttribute("userImage", loggedInUserImage);
		}
		Optional<SupervisorUploadedProject> existingProject = projectRepository.findById(id);
		existingProject.get().setAdditionalInformation(project.getAdditionalInformation());
		existingProject.get().setArtefact(project.getArtefact());
		existingProject.get().setBackground(project.getBackground());
		existingProject.get().setDescription(project.getDescription());
		existingProject.get().setDissertationModules(project.getDissertationModules());
		existingProject.get().setExternalCollaboration(project.getExternalCollaboration());
		existingProject.get().setInternalCollaboration(project.getInternalCollaboration());
		existingProject.get().setPreviousModules(project.getPreviousModules());
		existingProject.get().setTitle(project.getTitle());
		existingProject.get().setTopics(project.getTopics());
		projectRepository.save(existingProject.get());
	}
}

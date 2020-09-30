package com.prs.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
import com.prs.service.ProjectManagementService;

/**
 * ProjectManagementServiceImpl implements ProjectManagementService
 * 
 * @author 190026870
 *
 */
@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {

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
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	@Autowired
	private StudentSubmittedProjectStateRepository studentSubmittedProjectSateRepository;

	@Autowired
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	/**
	 * setModelAttributesForProjectManagement() method used to set model attributes.
	 * 
	 * @param model model attribute
	 */
	@Override
	public void setModelAttributesForProjectManagement(Model model) {
		String currentUserName = commonMethodsForController.getCurrentLoggedInUser();
		String loggedInUserImage = commonMethodsForController.getLoggedInUserImage(currentUserName);
		if (loggedInUserImage != null) {
			model.addAttribute("userImage", loggedInUserImage);
		}
		List<DissertationModule> dissertationModule = dissertationModuleRepository.findAll();
		List<Module> module = moduleRepository.findAll();
		List<Integer> studentId = studentSubmittedProjectRepository.findByStudentRole();
		List<User> allStudents = new ArrayList<User>();
		for (Integer i : studentId) {
			allStudents.add(userRepository.findById(i).get());
		}
		List<SupervisorUploadedProjectState> supervisorUploadedProjectState = supervisorUploadedProjectStateRepository
				.findAll();
		List<StudentSubmittedProjectState> studentSubmittedProjectState = studentSubmittedProjectSateRepository
				.findAll();
		List<SupervisorUploadedProject> project = projectRepository
				.findByUserId(userRepository.findByUsername(currentUserName).get());
		List<StudentSubmittedProject> studentProposals = studentSubmittedProjectRepository
				.findProjectsBySupervisorId(userRepository.findByUsername(currentUserName).get().getUserId());
		List<SupervisorUploadedProject> allProjects = projectRepository.findAll();
		List<SupervisorUploadedProject> internalCollaborationProjects = projectRepository
				.findByCollaboration(userRepository.findByUsername(currentUserName).get().getUserId());
		List<Object[]> datePostedObject = projectRepository.findByDatePosted();
		List<Map<String, Object>> datePosted = new ArrayList<Map<String, Object>>();
		for (Object[] dateObjectArray : datePostedObject) {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put(String.valueOf(dateObjectArray[0]), dateObjectArray[1]);
			datePosted.add(values);
		}
		List<Object[]> submittedOnObject = studentSubmittedProjectRepository.findByDatePosted();
		List<Map<String, Object>> submittedOn = new ArrayList<Map<String, Object>>();
		for (Object[] dateObjectArray : submittedOnObject) {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put(String.valueOf(dateObjectArray[0]), dateObjectArray[1]);
			submittedOn.add(values);
		}
		addAttributes(dissertationModule, module, supervisorUploadedProjectState, studentSubmittedProjectState, project,
				datePosted, internalCollaborationProjects, allProjects, studentProposals, allStudents, submittedOn,
				model);
	}

	/**
	 * addAttributes() refactored method to add model attributes.
	 * 
	 * @param dissertationModule             list of dissertationModule
	 * @param module                         list of module
	 * @param supervisorUploadedProjectState list of supervisorUploadedProjectState
	 * @param studentSubmittedProjectState   list of studentSubmittedProjectState
	 * @param project                        list of SupervisorUploadedProjects
	 * @param datePosted                     list of maps of projects and datePosted
	 * @param internalCollaborationProjects  list of internal collaboration
	 *                                       SupervisorUploadedProject
	 * @param allProjects                    list of all SupervisorUploadedProjects
	 * @param studentProposals               list of StudentSubmittedProjects
	 * @param allStudents                    list of allStudents
	 * @param submittedOn                    list of maps of project submissions
	 * @param model                          the model attribute
	 */
	public void addAttributes(List<DissertationModule> dissertationModule, List<Module> module,
			List<SupervisorUploadedProjectState> supervisorUploadedProjectState,
			List<StudentSubmittedProjectState> studentSubmittedProjectState, List<SupervisorUploadedProject> project,
			List<Map<String, Object>> datePosted, List<SupervisorUploadedProject> internalCollaborationProjects,
			List<SupervisorUploadedProject> allProjects, List<StudentSubmittedProject> studentProposals,
			List<User> allStudents, List<Map<String, Object>> submittedOn, Model model) {
		if (dissertationModule != null) {
			model.addAttribute("dissertationModule", dissertationModule);
		}
		if (module != null) {
			model.addAttribute("module", module);
		}
		if (supervisorUploadedProjectState != null) {
			model.addAttribute("supervisorUploadedProjectState", supervisorUploadedProjectState);
		}
		if (studentSubmittedProjectState != null) {
			model.addAttribute("studentSubmittedProjectState", studentSubmittedProjectState);
		}
		if (project != null) {
			model.addAttribute("project", project);
		}
		if (datePosted != null) {
			model.addAttribute("datePosted", datePosted);
		}
		if (internalCollaborationProjects != null) {
			model.addAttribute("internalCollaborationProjects", internalCollaborationProjects);
		}
		if (allProjects != null) {
			model.addAttribute("allProjects", allProjects);
		}
		if (studentProposals != null) {
			model.addAttribute("studentProposals", studentProposals);
		}
		if (allStudents != null) {
			model.addAttribute("allStudents", allStudents);
		}
		if (submittedOn != null) {
			model.addAttribute("submittedOn", submittedOn);
		}

	}

}

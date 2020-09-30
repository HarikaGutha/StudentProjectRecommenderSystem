package com.prs.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.repositories.StudentSubmittedProjectRepository;
import com.prs.repositories.StudentSubmittedProjectStateRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectStateRepository;
import com.prs.service.ChangeProjectStateService;

/**
 * ChangeProjectStateServiceImpl implements ChangeProjectStateService.
 * 
 * @author 190026870
 *
 */
@Service
public class ChangeProjectStateServiceImpl implements ChangeProjectStateService {

	@Autowired
	private SupervisorUploadedProjectRepository projectRepository;

	@Autowired
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	@Autowired
	private StudentSubmittedProjectStateRepository studentSubmittedProjectSateRepository;

	@Autowired
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	/**
	 * changeSupervisorUploadedProjectState() change state of given project
	 * 
	 * @param id    project id
	 * @param state the state
	 */
	@Override
	public void changeSupervisorUploadedProjectState(Integer id, String state) {
		int stateId = supervisorUploadedProjectStateRepository.getState(state);
		projectRepository.updateState(stateId, id);
	}

	/**
	 * changeStudentSubmittedProjectState() change state of given student submitted
	 * project
	 * 
	 * @param id    project id
	 * @param state the state
	 */
	@Override
	public void changeStudentSubmittedProjectState(Integer id, String state) {
		int stateId = studentSubmittedProjectSateRepository.getState(state);
		studentSubmittedProjectRepository.updateState(stateId, id);

	}

}

package com.prs.service;

/**
 * ChangeProjectStateService interface that provides services to change project
 * state.
 * 
 * @author 190026870
 *
 */
public interface ChangeProjectStateService {

	/**
	 * changeSupervisorUploadedProjectState() change state of given project
	 * 
	 * @param id    project id
	 * @param state the state
	 */
	public void changeSupervisorUploadedProjectState(Integer id, String state);

	/**
	 * changeStudentSubmittedProjectState() change state of given student submitted
	 * project
	 * 
	 * @param id    project id
	 * @param state the state
	 */
	public void changeStudentSubmittedProjectState(Integer id, String state);
}

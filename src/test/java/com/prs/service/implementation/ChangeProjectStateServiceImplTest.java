package com.prs.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.prs.repositories.StudentSubmittedProjectRepository;
import com.prs.repositories.StudentSubmittedProjectStateRepository;
import com.prs.repositories.SupervisorUploadedProjectRepository;
import com.prs.repositories.SupervisorUploadedProjectStateRepository;

/**
 * ChangeProjectStateServiceImplTest tests ChangeProjectStateServiceImpl.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class ChangeProjectStateServiceImplTest {

	@InjectMocks
	private ChangeProjectStateServiceImpl ChangeProjectStateServiceImpl;

	@Mock
	private SupervisorUploadedProjectStateRepository supervisorUploadedProjectStateRepository;

	@Mock
	private StudentSubmittedProjectStateRepository studentSubmittedProjectSateRepository;

	@Mock
	private SupervisorUploadedProjectRepository supervisorUploadedProjectRepository;

	@Mock
	private StudentSubmittedProjectRepository studentSubmittedProjectRepository;

	/**
	 * This method test the changeSupervisorUploadedProjectState() of
	 * ChangeProjectStateServiceImpl.
	 */
	@Test
	public void changeSupervisorUploadedProjectStateTest() {
		int stateId = 1;
		int id = 1;
		String state = "success";
		Mockito.when(supervisorUploadedProjectStateRepository.getState(state)).thenReturn(stateId);
		Mockito.doNothing().when(supervisorUploadedProjectRepository).updateState(stateId, id);
		ChangeProjectStateServiceImpl.changeSupervisorUploadedProjectState(id, state);
	}

	/**
	 * This method test the changeStudentSubmittedProjectState() of
	 * ChangeProjectStateServiceImpl.
	 */
	@Test
	public void changeStudentSubmittedProjectStateTest() {
		int stateId = 1;
		int id = 1;
		String state = "success";
		Mockito.when(studentSubmittedProjectSateRepository.getState(state)).thenReturn(stateId);
		Mockito.doNothing().when(studentSubmittedProjectRepository).updateState(stateId, id);
		ChangeProjectStateServiceImpl.changeStudentSubmittedProjectState(id, state);

	}

}

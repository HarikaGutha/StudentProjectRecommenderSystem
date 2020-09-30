package com.prs.rest;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.prs.service.ChangeProjectStateService;

/**
 * ChangeProjectStateRestControllerTest tests ChangeProjectStateRestController.
 * 
 * @author 190026870
 *
 */
@RunWith(MockitoJUnitRunner.class)

public class ChangeProjectStateRestControllerTest {

	@Mock
	private ChangeProjectStateService changeProjectStateService;

	@InjectMocks
	private ChangeProjectStateRestController changeProjectStateRestController;

	/**
	 * This method test the changeProjectState() of
	 * ChangeProjectStateRestController.
	 */
	@Test
	public void testChangeProjectState() {
		int id = 1;
		String state = "state";
		Mockito.doNothing().when(changeProjectStateService).changeSupervisorUploadedProjectState(id, state);
		ResponseEntity<Map<String, String>> responseEntity = changeProjectStateRestController.changeProjectState(id,
				state);
		Assert.assertEquals(200, responseEntity.getStatusCode().value());

	}

	/**
	 * This method test the changeStudentSubmittedProjectState() of
	 * ChangeProjectStateRestController.
	 */
	@Test
	public void testChangeStudentSubmittedProjectState() {
		int id = 1;
		String state = "state";
		Mockito.doNothing().when(changeProjectStateService).changeStudentSubmittedProjectState(id, state);
		ResponseEntity<Map<String, String>> responseEntity = changeProjectStateRestController
				.changeStudentSubmittedProjectState(id, state);
		Assert.assertEquals(200, responseEntity.getStatusCode().value());
	}

}

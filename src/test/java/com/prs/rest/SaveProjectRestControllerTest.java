package com.prs.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.prs.service.SaveProjectService;

/**
 * SaveProjectRestControllerTest tests SaveProjectRestController.
 */
@RunWith(MockitoJUnitRunner.class)

public class SaveProjectRestControllerTest {

	@Mock
	private SaveProjectService saveProjectService;

	@InjectMocks
	private SaveProjectRestController saveProjectRestController;

	/**
	 * This method test the save() of FilterProjectsBySupervisor.
	 */
	@Test
	public void testSaveProject() {
		int id = 1;
		Mockito.doNothing().when(saveProjectService).saveProject(id);
		saveProjectRestController.saveProject(id);
	}

	/**
	 * This method test the unsave() of FilterProjectsBySupervisor.
	 */
	@Test
	public void testUnSaveProject() {
		int id = 1;
		Mockito.doNothing().when(saveProjectService).unsaveProject(id);
		saveProjectRestController.unSaveProject(id);
	}

}

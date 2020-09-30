package com.prs.rest;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.service.ChangeProjectStateService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ChangeProjectStateRestController is the RestController to change project
 * state.
 * 
 * @author 190026870
 *
 */
@RestController
public class ChangeProjectStateRestController {

	@Autowired
	private ChangeProjectStateService changeProjectStateService;

	/**
	 * changeProjectState() method invoked when URL is /changeProjectState.
	 * 
	 * @param id    the project id
	 * @param state state of the project
	 * @return response
	 */
	@RequestMapping("/changeProjectState")
	@ResponseBody
	public ResponseEntity<Map<String, String>> changeProjectState(@PathParam("id") Integer id,
			@PathParam("state") String state) {
		changeProjectStateService.changeSupervisorUploadedProjectState(id, state);
		Map<String, String> response = new HashMap<String, String>();
		response.put("status", "success");
		return ResponseEntity.ok(response);
	}

	/**
	 * changeStudentSubmittedProjectState() method invoked when URL is
	 * /changeStudentSubmittedProjectState.
	 * 
	 * @param id    the project id
	 * @param state state of the project
	 * @return response
	 */
	@RequestMapping("/changeStudentSubmittedProjectState")
	@ResponseBody
	public ResponseEntity<Map<String, String>> changeStudentSubmittedProjectState(@PathParam("id") Integer id,
			@PathParam("state") String state) {
		changeProjectStateService.changeStudentSubmittedProjectState(id, state);
		Map<String, String> response = new HashMap<String, String>();
		response.put("status", "success");
		return ResponseEntity.ok(response);
	}
}

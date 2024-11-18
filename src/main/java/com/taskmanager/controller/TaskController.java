package com.taskmanager.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taskmanager.Entity.Task;
import com.taskmanager.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {
	private static Logger LOGGER = Logger.getLogger(TaskController.class.getName());
	
	@Autowired
	TaskService taskService;
		
	/*
	 * @GetMapping(value="/getAllTask") public ResponseEntity<?> getAllTasks(){
	 * List<Tasks> return ResponseEntity.ok().body("entered add Task"); }
	 */
	@PostMapping(value="/addtask")
	public ResponseEntity<?> getAllTasks(@RequestBody Task task){
		taskService.addTask(task);
		return ResponseEntity.ok().body("entered add Task");
	}
	
	

}

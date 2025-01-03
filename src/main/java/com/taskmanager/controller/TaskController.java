package com.taskmanager.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		taskService.saveTask(task);
		return ResponseEntity.ok().body("Task Added");
	}
	
	@GetMapping(value="/getTasks")
	public ResponseEntity<?> getTasks(){
		List<Task> allTasks = taskService.getAllTasks();
		Map<String, List<Task>> segregatedTasks = allTasks.stream().collect(Collectors.groupingBy(task->task.getStatus().replace(" ", "")));
		return ResponseEntity.ok().body(segregatedTasks);
	}
	
	@DeleteMapping(value="/deleteTask/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable int id){
		taskService.deleteTask(id);
		return ResponseEntity.ok().body("Task Deleted");
	}
	
	@PutMapping(value="/updateTask")
	public ResponseEntity<?> updateTask(@RequestBody Task task){
		taskService.saveTask(task);
		return ResponseEntity.ok().body("Task body is updated");
	}
	
	
}

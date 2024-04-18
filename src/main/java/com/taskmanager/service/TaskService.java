package com.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepo;
	
	public List<Task> getAllTasks(){
		return taskRepo.findAll();
	}
	public void addTask(Task task) {
		taskRepo.save(task);
	}
}

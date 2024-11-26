package com.taskmanager.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.Entity.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.util.TraftConstants;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepo;
	
	public List<Task> getAllTasks(){
		return taskRepo.findAll();
	}
	public void addTask(Task task) {
		String PriorityIndicator = task.getPriority().equalsIgnoreCase(TraftConstants.medium) ? TraftConstants.mediumIndicator 
				: task.getPriority().equalsIgnoreCase(TraftConstants.high)? TraftConstants.highIndicator :TraftConstants.lowIndicator; 
		task.setPriorityIndicator(PriorityIndicator);
		task.setStatus(TraftConstants.default_Status);
		LocalTime time = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TraftConstants.timeFormat);
        String formattedTime = time.format(formatter);
		task.setTime(formattedTime.toUpperCase());
		taskRepo.save(task);
	}
}
	
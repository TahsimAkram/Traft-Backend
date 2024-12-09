package com.taskmanager.util;

import java.time.ZonedDateTime;

import com.taskmanager.Entity.Task;

public class UtilMethods {

	public static void formatDate(Task task) {
		
		 ZonedDateTime zonedDateTime = ZonedDateTime.parse(task.getStartDate());
		 task.setStartDate(zonedDateTime.toLocalDate().toString());
		 
		 zonedDateTime = ZonedDateTime.parse(task.getEndDate());
		 task.setEndDate(zonedDateTime.toLocalDate().toString());
	}
}

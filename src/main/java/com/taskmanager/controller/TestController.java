package com.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.model.MessageResponse;

@RestController
@RequestMapping("api/test")
@EnableMethodSecurity
public class TestController {
	
	@GetMapping("/all")
	public ResponseEntity<?> showAll(){
		return ResponseEntity.ok(new MessageResponse("For All user"));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> showUser(){
		return ResponseEntity.ok(new MessageResponse("For user only"));
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> showadmin(){
		return ResponseEntity.ok(new MessageResponse("For admin only"));
	}

}

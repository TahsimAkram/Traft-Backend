package com.taskmanager.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.jwt.JwtUtils;
import com.taskmanager.model.ERole;
import com.taskmanager.model.JwtResponse;
import com.taskmanager.model.MessageResponse;
import com.taskmanager.model.Role;
import com.taskmanager.model.SignInRequestBody;
import com.taskmanager.model.SignUpRequestBody;
import com.taskmanager.model.User;
import com.taskmanager.repository.RoleRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.UserDetailsImpl;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

	private static Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping(value = "/signin")
	public ResponseEntity<?> verify(@RequestBody SignInRequestBody requestBody) {
		
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();   
			List<String> roles = userDetails.getAuthorities().stream().map(authority->authority.getAuthority()).collect(Collectors.toList());
			
			return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),userDetails.getEmail(),roles));
			
		}catch(BadCredentialsException exception) {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}
	}

	@PostMapping(value = "/signup", produces = "application/json")
	public ResponseEntity<?> register(@RequestBody SignUpRequestBody requestBody) {
		if (userRepository.existsByUsername(requestBody.getUsername())) {
		      return ResponseEntity
		          .status(401)
		          .body(new MessageResponse("Username is already taken!"));
		    }

		    if (userRepository.existsByEmail(requestBody.getEmail())) {
		      return ResponseEntity
		    	  .status(401)
		          .body(new MessageResponse("Email is already in use!"));
		    }

		    // Create new user's account
		    User user = new User(requestBody.getUsername(), 
		    		requestBody.getEmail(),
		               encoder.encode(requestBody.getPassword()),Timestamp.from(Instant.now()));

		    Set<String> strRoles = requestBody.getRoles();
		    Set<Role> roles = new HashSet<>();

		    if (strRoles == null) {
		      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		      roles.add(userRole);
		    } else {
		      strRoles.forEach(role -> {
		        switch (role) {
		        case "admin":
		          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(adminRole);

		          break;
		        case "mod":
		          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(modRole);

		          break;
		        default:
		          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(userRole);
		        }
		      });
		    }

		    user.setRoles(roles);
		    userRepository.save(user);

		    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		  }

}

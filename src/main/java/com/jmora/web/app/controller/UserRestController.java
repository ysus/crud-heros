package com.jmora.web.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmora.web.app.data.models.ApplicationUser;
import com.jmora.web.app.data.payloads.request.ApplicationUserRequest;
import com.jmora.web.app.data.payloads.response.ApplicationUserResponse;
import com.jmora.web.app.service.IUserService;
import com.jmora.web.app.util.JWTUtil;

@Controller
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserService userService;

	@Autowired
	private JWTUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody ApplicationUser user) {
		return userService.findByUsername(user.getUsername())
				.map((u) ->{
					return ResponseEntity.badRequest().body(String.format("username: '%s' already exist", u.getUsername()));
				})
				.orElseGet(() ->{
					Long id = userService.saveUser(user);
					String message = "User with id '" + id + "' saved succssfully!";
					return ResponseEntity.ok(message);
				});
	}

	@PostMapping("/loginUser")
	public ResponseEntity<ApplicationUserResponse> login(@RequestBody ApplicationUserRequest request) {

		// Validate username/password with DB(required in case of Stateless Authentication)
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		String token = util.generateToken(request.getUsername());
		return ResponseEntity.ok(new ApplicationUserResponse(token, "Token generated successfully!"));
	}

	@PostMapping("/getData")
	public ResponseEntity<String> testAfterLogin(Principal p) {
		return ResponseEntity.ok("You are accessing data after a valid Login. You are :" + p.getName());
	}
}
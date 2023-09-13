package com.sopromadze.controller;

import com.sopromadze.model.user.User;
import com.sopromadze.payload.*;
import com.sopromadze.security.CurrentUser;
import com.sopromadze.security.UserPrincipal;
import com.sopromadze.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserSummary> getCurrentUser(
			@CurrentUser UserPrincipal currentUser
	) {
		UserSummary userSummary = userService.getCurrentUser(currentUser);

		return new ResponseEntity< >(userSummary, HttpStatus.OK);
	}

	@GetMapping("/checkUsernameAvailability")
	public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(
			@RequestParam(value = "username") String username
	) {
		UserIdentityAvailability userIdentityAvailability = userService.checkUsernameAvailability(username);

		return new ResponseEntity< >(userIdentityAvailability, HttpStatus.OK);
	}

	@GetMapping("/checkEmailAvailability")
	public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(
			@RequestParam(value = "email") String email
	) {
		UserIdentityAvailability userIdentityAvailability = userService.checkEmailAvailability(email);
		return new ResponseEntity< >(userIdentityAvailability, HttpStatus.OK);
	}

	@GetMapping("/{username}/profile")
	public ResponseEntity<UserProfile> getUSerProfile(
			@PathVariable(value = "username") String username
	) {
		UserProfile userProfile = userService.getUserProfile(username);

		return new ResponseEntity< >(userProfile, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> addUser(
			@Valid @RequestBody User user
	) {
		User newUser = userService.addUser(user);

		return new ResponseEntity< >(newUser, HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> updateUser(
			@Valid @RequestBody User newUser,
			@PathVariable(value = "username") String username,
			@CurrentUser UserPrincipal currentUser
	) {
		User updatedUSer = userService.updateUser(newUser, username, currentUser);

		return new ResponseEntity< >(updatedUSer, HttpStatus.CREATED);
	}

	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(
			@PathVariable(value = "username") String username,
			@CurrentUser UserPrincipal currentUser
	) {
		ApiResponse apiResponse = userService.deleteUser(username, currentUser);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{username}/giveAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
		ApiResponse apiResponse = userService.giveAdmin(username);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/{username}/takeAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") String username) {
		ApiResponse apiResponse = userService.removeAdmin(username);

		return new ResponseEntity< >(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/setOrUpdateInfo")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserProfile> setAddress(
			@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody InfoRequest infoRequest
	) {
		UserProfile userProfile = userService.setOrUpdateInfo(currentUser, infoRequest);

		return new ResponseEntity< >(userProfile, HttpStatus.OK);
	}
}

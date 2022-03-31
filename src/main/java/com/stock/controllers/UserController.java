package com.stock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.dto.RoleToUserForm;
import com.stock.dto.UserDTO;
import com.stock.dto.UserForm;
import com.stock.entities.Role;
import com.stock.services.implementations.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServImpl;

	@PostMapping
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserForm form) {

		UserDTO user = userServImpl.saveUser(form);

		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	@PostMapping(value = "/role")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {

		userServImpl.saveRole(role);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping(value = "/role/toUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userServImpl.addRoleToUser(form.getName(), form.getRoleName());

		return ResponseEntity.ok().build();
	}

	@PutMapping
	public UserDTO updateUser(@PathVariable Long id, @RequestBody UserForm user) {
		return userServImpl.updateUser(id, user);

	}

	@GetMapping
	public ResponseEntity<Page<UserDTO>> listUsers(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

		return ResponseEntity.ok().body(userServImpl.listUsers(pageRequest));

	}

	@GetMapping(value = "/user/name")
	public ResponseEntity<UserDTO> getUser(@RequestParam(required = true) String name) {

		UserDTO user = userServImpl.getUser(name);

		return ResponseEntity.ok().body(user);

	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {

		userServImpl.deleteUser(id);

		return ResponseEntity.noContent().build();

	}

}

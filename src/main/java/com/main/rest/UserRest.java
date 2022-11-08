package com.main.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.AppUser;
import com.main.service.UserService;

@RestController
@RequestMapping("/rest/api/user")
public class UserRest {

	@Autowired
	private UserService service;
	@GetMapping("/checkEmail/{email}")
	public boolean checkIfEmailIsValidToUse(@PathVariable("email") String email,Principal principal) {
		AppUser user = service.findByEmail(email);
		if(user == null) return true;
		if(principal.getName().equals(user.getEmail())) return true;
		return false;
	}
}

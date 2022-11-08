package com.main.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.main.dto.UpdateUserDto;
import com.main.entity.AppUser;
import com.main.service.UserService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping
@Log4j2
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/*
	 * @PostMapping("/validateUser") public String
	 * validateUser(@ModelAttribute("user") AppUser user, BindingResult rs) { return
	 * "redirect:/products/add"; }
	 */
	@GetMapping("/signUp")
	public String signUp(Model model) {
		AppUser user = new AppUser();
		model.addAttribute("user", user);
		return "sign-up";
	}

	@PostMapping("/validate")
	public String processSignUp(@Valid @ModelAttribute("user") AppUser user, BindingResult rs,Model model) {
		if(rs.hasErrors()) {
			
			model.addAttribute("user", user);
			return "sign-up";
		}
		user.setPassword(encoder.encode(user.getPassword()));
		service.insert(user);
		return "redirect-to-login";
	}

	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		
		AppUser user = getUser(principal);
		UpdateUserDto userDto = new UpdateUserDto();
		model.addAttribute("isValidInfo",true);
		model.addAttribute("cart", user.getCart());
		model.addAttribute("user", user);
		model.addAttribute("userDto", userDto);
		return "profile";
	}

	private AppUser getUser(Principal principal) {
		AppUser user = null;
		if (principal != null) {
			user = service.findByEmail(principal.getName());
			System.err.println(user);
		}
		return user;
	}

	@PostMapping("/update")
	public String updateUser(@Valid @ModelAttribute("user") AppUser user, BindingResult rs, Model model,
			Principal principal) {
		model.addAttribute("isValidInfo",true);
		if (rs.hasErrors()) {
			log.error(user);
			UpdateUserDto userDto = new UpdateUserDto();
			AppUser errordUser = getUser(principal);
			model.addAttribute("cart", errordUser.getCart());
			user.setEmail(errordUser.getEmail());
			model.addAttribute("user", user);
			model.addAttribute("userDto", userDto);
			return "profile";
		}

		AppUser newUser = service.update(user);
		UpdateUserDto userDto = new UpdateUserDto();
		model.addAttribute("cart", newUser.getCart());

		model.addAttribute("user", newUser);
		model.addAttribute("userDto", userDto);

		return "profile";
	}

	@PostMapping("/changeInfo")
	public String changeInfo(@Valid @ModelAttribute("userDto") UpdateUserDto userDto, BindingResult rs,
			 Model model, Principal principal) {
		AppUser user = getUser(principal);
		model.addAttribute("cart", user.getCart());
		model.addAttribute("user", user);
		model.addAttribute("userDto", userDto);
		model.addAttribute("isValidInfo",true);
		if(rs.hasErrors()) {
			return "profile";
		}
		boolean isValidInfo = isValidInfo(userDto,user);
		if(!isValidInfo) {
			model.addAttribute("isValidInfo",false);
			return "profile";
		}
		user.setEmail(userDto.getNewEmail());
		user.setPassword(encoder.encode(userDto.getNewPassword()));
		service.updateSecInfo(user);
		SecurityContextHolder.clearContext();
		
		return "/login";
	}

	private boolean isValidInfo(UpdateUserDto userDto,AppUser user) {
		if(userDto.getEmail().equals(user.getEmail())
				&& encoder.matches(userDto.getPassword(),user.getPassword())) {
			return true;
		}
		return false;
	}
	/*
	 * @PostMapping("process-signUp") public String
	 * processSignUp(@ModelAttribute("user") AppUser user) { service.insert(user);
	 * return "login"; }
	 */
}

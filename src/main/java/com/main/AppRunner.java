package com.main;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.main.entity.AppUser;
import com.main.entity.Role;
import com.main.repo.RoleRepo;
import com.main.service.UserService;

@Component
public class AppRunner implements CommandLineRunner {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepo roleRepo;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		if(userService.findAll().size() <= 0) {
			AppUser user1  = new AppUser("mohamed", "moh@gmail.com", encoder.encode("12345"));
			AppUser user2  = new AppUser("ahmed", "ah@gmail.com", encoder.encode("123456"));
			AppUser user3  = new AppUser("mohsen", "mohsen@gmail.com", encoder.encode("123457"));
			AppUser user4  = new AppUser("abdo", "abdo@gmail.com", encoder.encode("123458"));
			
			Role userRole = roleRepo.save(new Role(100,"USER"));
			Role adminRole = roleRepo.save(new Role(101,"ADMIN"));
			Role managerRole =roleRepo.save(new Role(102,"MANAGER"));
			
			
			
			user1.addRole(userRole);
			user1.addRole(adminRole);
			user1.addRole(managerRole);
			user2.addRole(userRole);
			user3.addRole(userRole);
			user4.addRole(userRole);
			
			userService.insert(user1);
			userService.insert(user2);
			userService.insert(user3);
			userService.insert(user4);
		
		}
	}

}

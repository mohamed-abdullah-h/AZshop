package com.main.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.AppUser;

@Repository
public interface UserRepo extends JpaRepository<AppUser,Long>{

	@EntityGraph(attributePaths = {"roles"})
	AppUser findByEmail(String email);
}

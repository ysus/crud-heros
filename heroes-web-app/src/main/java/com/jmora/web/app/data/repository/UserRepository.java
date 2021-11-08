package com.jmora.web.app.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmora.web.app.data.models.ApplicationUser;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
	Optional<ApplicationUser> findByUsername(String username);
	
}

package com.jmora.web.app.service;

import java.util.Optional;

import com.jmora.web.app.data.models.ApplicationUser;

public interface IUserService {

	Integer saveUser(ApplicationUser user);
	
	Optional<ApplicationUser> findByUsername(String username);
}
package com.jmora.web.app.service;

import java.util.Optional;

import com.jmora.web.app.data.models.ApplicationUser;

public interface IUserService {

	/**
	 * @param user user to save
	 * @return  the id of the user saved
	 */
	Long saveUser(ApplicationUser user);
	
	/**
	 * @param username username to find
	 * @return the username found
	 */
	Optional<ApplicationUser> findByUsername(String username);
}
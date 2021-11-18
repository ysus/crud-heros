package com.jmora.web.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jmora.web.app.data.models.ApplicationUser;
import com.jmora.web.app.data.repository.UserRepository;

@SpringBootTest
class UserServiceTest {
	
	@Autowired
	private UserServiceImpl userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	@DisplayName("Test save user")
	void saveUser() {
		
		ApplicationUser user = createUser(1L, "jesus", "password", "moralespanfilo2@gmail.com");
		
		//Setup our mock repository
		when(userRepository.save(any())).thenReturn(user);
		
		//Execute service call
		Long returnedUserId = userService.saveUser(user);
		
		//Assert the response
		Assertions.assertNotNull(returnedUserId,"The saved user should not be null");
		Assertions.assertEquals(1,returnedUserId,  "The ID should be 1");
	}
	
	@Test
	@DisplayName("Test findByUsername")
	void findByUsername() {
		
		//Setup our mock repository
		when(userRepository.findByUsername("jesus"))
			.thenReturn(Optional.of(createUser(1L, "jesus", "password", "moralespanfilo2@gmail.com")));
		
		//Execute service call
		Optional<ApplicationUser> returnedUser = userService.findByUsername("jesus");
		
		//Assert the response
		Assertions.assertTrue(returnedUser.isPresent(), "User by username 'jesus' not found");
		Assertions.assertSame(returnedUser.get().getId(), 1L, "The User returned was not the same as the mock");
	}
	
	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param email
	 * @return ApplicationUser
	 */
	private ApplicationUser createUser(Long id,String username,String password, String email) {
		ApplicationUser user = new ApplicationUser();
		user.setId(1L);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		return user;	
	}
}

package com.jmora.web.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmora.web.app.data.models.ApplicationUser;
import com.jmora.web.app.service.IUserService;
import com.jmora.web.app.util.JWTUtil;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IUserService userService;
	
	@MockBean
	private JWTUtil util;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@Captor
	private ArgumentCaptor<ApplicationUser> argumentCaptor;

	@Test
	@DisplayName("POST /user/saveUser")
	void testSaveUser() throws Exception {
		String username = "jesus";
		ApplicationUser user = createUser(1L, "jesus", "password", "moralespanfilo2@gmail.com");

		// Setup our mocked service
		when(userService
				.findByUsername(username))
				.thenReturn(Optional.empty());
		
		when(userService
				.saveUser(argumentCaptor.capture()))
				.thenReturn(1L);
		
		// Execute the POST request
		this.mockMvc
			.perform(post("/user/saveUser")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(user)))
			
			// Validate the response code
			.andExpect(status().isOk());
		
		// Validate the returned fields
		assertThat(argumentCaptor.getValue().getId(),is(1L));
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
	
    /**
     * @param obj
     * @return a json string
     */
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

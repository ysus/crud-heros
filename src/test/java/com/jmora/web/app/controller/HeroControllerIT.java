package com.jmora.web.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmora.web.app.data.models.ApplicationUser;
import com.jmora.web.app.data.payloads.request.ApplicationUserRequest;
import com.jmora.web.app.data.payloads.response.ApplicationUserResponse;
import com.jmora.web.app.data.payloads.response.HeroResponse;


/**
 * @author Administrador
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerIT {

	@LocalServerPort
	int randomServerPort;

	private TestRestTemplate testRestTemplate;

	@BeforeEach
	public void setUp() {
		this.testRestTemplate = new TestRestTemplate();
	}
	
	@Test
	@DisplayName("Integration Test HeroController")
	void testDeleteHero() throws Exception {
		long heroId = 1;
				
		// create user registration object
		ApplicationUser registrationUser = getRegistrationUser();
		
		// convert the user registration object to JSON
		String registrationBody = asJsonString(registrationUser);
		
		// create headers specifying that it is JSON request
		HttpHeaders registrationHeaders = getHeaders();
		HttpEntity<String> registrationEntity = new HttpEntity<String>(registrationBody, registrationHeaders);
		
		// Register User
		ResponseEntity<String> registrationResponse =  this.testRestTemplate.exchange(createURLWithPort("/user/saveUser"), HttpMethod.POST,
							registrationEntity, String.class);
		
		assertThat(registrationResponse.getStatusCode(),is(HttpStatus.OK));
		
		// create user authentication object
		ApplicationUserRequest authenticationUser = getAuthenticationUser();
		
		// convert the user authentication object to JSON
		String authenticationBody = asJsonString(authenticationUser);
		
		// create headers specifying that it is JSON request
		HttpHeaders authenticationHeaders = getHeaders();
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
				authenticationHeaders);
		
		
		// Authenticate User and get JWT
		ResponseEntity<ApplicationUserResponse> authenticationResponse = this.testRestTemplate.exchange(createURLWithPort("/user/loginUser"),
				HttpMethod.POST, authenticationEntity, ApplicationUserResponse.class);
		
		// Validate the response code
		assertThat(authenticationResponse.getStatusCode(),is(HttpStatus.OK));
		
		// if the authentication is successful		
		String token = authenticationResponse.getBody().getToken();
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token);
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		
		ResponseEntity<HeroResponse> firstResult = this.testRestTemplate.exchange(
				createURLWithPort("/api/heros/"+ heroId), HttpMethod.GET, jwtEntity, HeroResponse.class);
		
		// Validate the response code
		assertThat(firstResult.getStatusCode(),is(HttpStatus.OK));
		
		this.testRestTemplate.exchange(createURLWithPort("/api/heros/"+ heroId),HttpMethod.DELETE, jwtEntity, Void.class);
		
		ResponseEntity<HeroResponse> secondResult = this.testRestTemplate
				.exchange(createURLWithPort("/api/heros/"+ heroId), HttpMethod.GET, jwtEntity, HeroResponse.class);
		
		// Validate the response code
		assertThat(secondResult.getStatusCode(),is(HttpStatus.NOT_FOUND));
	}
	
	/**
	 * @param uri url
	 * @return new url
	 */
	private String createURLWithPort(String uri) {
		return  "http://localhost:" +  randomServerPort + uri;
	}
	
	/**
	 * @return RegistrationUser
	 */
	private ApplicationUser getRegistrationUser() {
		ApplicationUser user = new ApplicationUser();
		user.setUsername("jesus2");
		user.setPassword("morales");
		user.setEmail("moralespanfilo2@gmail.com");
		//user.setRoles(List.of("ADMIN"));
		return user;
	}

	/**
	 * @return AuthenticationUser
	 */
	private ApplicationUserRequest getAuthenticationUser() {
		ApplicationUserRequest user = new ApplicationUserRequest();
		user.setUsername("jesus2");
		user.setPassword("morales");
		return user;
	}

	/**
	 * @return headers
	 */
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
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

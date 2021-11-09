package com.jmora.web.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerIT {

	@LocalServerPort
	int randomServerPort;

	private TestRestTemplate testRestTemplate;

	@BeforeEach
	public void setUp() {
		this.testRestTemplate = new TestRestTemplate();
	}
	
	@Disabled("the testcase is under development") 
	@Test
	@DisplayName("DELETE /api/heros/1")
	void testDeleteHero() {
		long heroId = 1;
		String baseUrl = "http://localhost:" + randomServerPort;
		
		ResponseEntity<JsonNode> firstResult = this.testRestTemplate
				.getForEntity(baseUrl+"/api/heros/"+ heroId, JsonNode.class);
		
		assertThat(firstResult.getStatusCode(),is(HttpStatus.OK));
		
		this.testRestTemplate.delete(baseUrl+"/api/heros/"+ heroId, JsonNode.class);
		
		ResponseEntity<JsonNode> secondResult = this.testRestTemplate
				.getForEntity(baseUrl+"/api/heros/"+ heroId, JsonNode.class);
		
		assertThat(secondResult.getStatusCode(),is(HttpStatus.NOT_FOUND));
		
		
		
		
	}

}

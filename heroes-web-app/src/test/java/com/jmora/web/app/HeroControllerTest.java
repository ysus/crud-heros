/**
 * 
 */
package com.jmora.web.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.exception.HeroNotFoundException;
import com.jmora.web.app.service.IHeroService;


/**
 * @author Administrador
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private	IHeroService heroService;
	
	@Captor
	private ArgumentCaptor<HeroRequest> argumentCaptor;

	@Test
	@DisplayName("POST /api/heros")
	public void testCreateHero() throws Exception{
		
		HeroRequest request = new HeroRequest();
		request.setHeroName("Hulk");
		request.setPower("super fuerza");
		request.setRealName("Robert Bruce Banner");
		
		// Setup our mocked service
		when(heroService.createNewHero(argumentCaptor.capture())).thenReturn(1L);
		
		
		// Execute the POST request
		this.mockMvc.perform(post("/api/heros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
		
		 		// Validate the response code
				.andExpect(status().isCreated())
				
				// Validate headers
				.andExpect(header().exists("Location"))
				.andExpect(header().string("Location", "http://localhost/api/heros/1"));
		
		 // Validate the returned fields
		assertThat(argumentCaptor.getValue().getHeroName(),is("Hulk"));
		assertThat(argumentCaptor.getValue().getPower(),is("super fuerza"));
		assertThat(argumentCaptor.getValue().getRealName(),is("Robert Bruce Banner"));
		
	}
	
	@Test
	@DisplayName("GET /api/heros")
	void testGetHerosSuccess() throws Exception {
		
		// Setup our mocked service
		when(heroService.getAllHeros()).thenReturn(
				List.of(createHero(1L,"iron man", "armadura", "tony stark"),createHero(2L,"capitan america", "super poder", "steve")));
		
		// Execute the GET request
		this.mockMvc.perform(get("/api/heros"))
			
			// Validate the response code
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			 // Validate the returned fields
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id",is(1)))
			.andExpect(jsonPath("$[0].heroName",is("iron man")));
			
	}
	
	
	@Test
	@DisplayName("GET /api/heros/1")
	void testGetHeroById() throws Exception {
		
		// Setup our mocked service
		when(heroService.getHeroById(1L)).thenReturn(Optional.of(createHero(1L,"iron man", "armadura", "tony stark")));
		
		// Execute the GET request
		this.mockMvc.perform(get("/api/heros/1"))
		
		// Validate the response code
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		
		 // Validate the returned fields
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.heroName",is("iron man")));
	}
	
	
	@Test
	@DisplayName("GET /api/heros/100  - Not Found")
	void testGetHeroByIdNotFound() throws Exception {
		
		// Setup our mocked service
		when(heroService.getHeroById(1L)).thenThrow(new HeroNotFoundException("Hero by id '100' not found"));
		
		// Execute the GET request
		this.mockMvc.perform(get("/api/heros/100"))
		
		// Validate the response code
		.andExpect(status().isNotFound());
			
	}
	
	@Test
	@DisplayName("PUT /api/heros/1")
	void testUpdateHero() throws Exception {
		
		HeroRequest request = new HeroRequest();
		request.setHeroName("Hulk");
		request.setPower("super fuerza");
		request.setRealName("Robert Bruce Banner");
		
		when(heroService
				.updateHero(eq(1L),argumentCaptor.capture()))
				.thenReturn(createHero(1L,"Hulk", "super fuerza", "Robert Bruce Banner"));
		
		// Execute the GET request
		this.mockMvc.perform(put("/api/heros/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
		
				// Validate the response code
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
				// Validate the returned fields
				.andExpect(jsonPath("$.id",is(1)))
				.andExpect(jsonPath("$.heroName",is("hulk")));
		
		 // Validate the returned fields
		assertThat(argumentCaptor.getValue().getHeroName(),is("Hulk"));
		assertThat(argumentCaptor.getValue().getPower(),is("super fuerza"));
		assertThat(argumentCaptor.getValue().getRealName(),is("Robert Bruce Banner"));
	}
	
	
	@Test
	@DisplayName("PUT /api/heros/100 -  Not Found")
	void testUpdateHeroNotFound() throws Exception {
		
		HeroRequest request = new HeroRequest();
		request.setHeroName("Hulk");
		request.setPower("super fuerza");
		request.setRealName("Robert Bruce Banner");
		
		// Setup our mocked service
		when(heroService
				.updateHero(eq(100L),argumentCaptor.capture()))
				.thenThrow(new HeroNotFoundException("Hero by id '100' not found"));
		
		// Execute the PUT request
		this.mockMvc.perform(put("/api/heros/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
		
				// Validate the response code
				.andExpect(status().isNotFound());

	}
	
	/**
	 * @param heroName
	 * @param power
	 * @param realName
	 * @return
	 */
	private Hero createHero(Long id,String heroName,String power, String realName) {
		Hero hero = new Hero();
		hero.setId(id);
		hero.setHeroName(heroName);
		hero.setPower(power);
		hero.setRealName(realName);
		
		return hero;	
	}
	
    /**
     * @param obj
     * @return
     */
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

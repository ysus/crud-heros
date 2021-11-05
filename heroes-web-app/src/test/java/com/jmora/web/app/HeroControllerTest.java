/**
 * 
 */
package com.jmora.web.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.jmora.web.app.data.payloads.request.HeroRequest;
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
	public void postingANewHeroShouldCreateANewHeroInDatabase() throws Exception{
		
		HeroRequest request = new HeroRequest();
		request.setHeroName("Hulk");
		request.setPower("super fuerza");
		request.setRealName("Robert Bruce Banner");
		
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
	
	
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

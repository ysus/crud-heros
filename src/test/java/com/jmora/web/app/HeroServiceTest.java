package com.jmora.web.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.payloads.response.HeroResponse;
import com.jmora.web.app.data.repository.HeroRepository;
import com.jmora.web.app.service.HeroServiceImp;


/**
 * @author jmp
 *
 */
@SpringBootTest
class HeroServiceTest {
	
	@Autowired
	private HeroServiceImp heroService;
	
	@MockBean
	private HeroRepository heroRepository;

	@Test
	@DisplayName("Test findById Success")
	void testGetHeroById() {
		
		//Setup our mock repository
		when(heroRepository.findById(1L))
			.thenReturn(Optional.of(createHero(1L,"iron man", "armadura", "tony stark")));
		
		//Execute service call
		Optional<HeroResponse> returnedHero = heroService.getHeroById(1L);
		
		//Assert the response
		Assertions.assertTrue(returnedHero.isPresent(),"Hero by id '1' not found");
		Assertions.assertSame(returnedHero.get().getId(), 1L, "The hero returned was not the same as the mock");
	}
	
	@Test
	@DisplayName("Test findById Not found")
	void testGetHeroByIdNotFound() {
		
		//Setup our mock repository
		when(heroRepository.findById(1L)).thenReturn(Optional.empty());
		
		//Execute service call
		Optional<HeroResponse> returnedHero = heroService.getHeroById(1L);
		
		//Assert the response
		Assertions.assertFalse(returnedHero.isPresent(),"Hero should not be found");
	}
	
	@Test
	@DisplayName("Test findAll")
	void testGetAllHeros() {
		
		//Setup our mock repository
		when(heroRepository.findAll()).thenReturn(
				List.of(
						createHero(1L,"iron man", "armadura", "tony stark"),
						createHero(2L,"capitan america", "super poder", "steve")));
		
		//Execute service call
		Iterable<HeroResponse> heros = heroService.getAllHeros();
		
		List<HeroResponse> heroList = StreamSupport
                .stream(heros.spliterator(), false)
                .collect(Collectors.toList());
		
		//Assert the response
		Assertions.assertEquals(2, heroList.size(),"findAll should return 2 heros");
	}
	
	@Test
	@DisplayName("Test save hero")
	void testCreateNewHero() {
		
		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setHeroName("iron man");
		heroRequest.setPower("armadura");
		heroRequest.setRealName( "tony stark");

		//Setup our mock repository
		when(heroRepository.save(any())).thenReturn(createHero(1L,"iron man", "armadura", "tony stark"));
		
		// Execute the service call
		HeroResponse returnedHero = heroService.createNewHero(heroRequest);
		
		//Assert the response
		Assertions.assertNotNull(returnedHero,"The saved hero should not be null");
		Assertions.assertEquals(1,returnedHero.getId(),  "The ID should be 1");
	}
	
	/**
	 * @param id
	 * @param heroName
	 * @param power
	 * @param realName
	 * @return a new hero 
	 */
	private Hero createHero(Long id,String heroName,String power, String realName) {
		Hero hero = new Hero();
		hero.setId(id);
		hero.setHeroName(heroName);
		hero.setPower(power);
		hero.setRealName(realName);
		hero.setVersion(1L);
		
		return hero;	
	}

}

package com.jmora.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.payloads.response.HeroResponse;

@Component
public interface IHeroService {

	/**
	 * @param request the hero to save
	 * @return the hero saved
	 */
	HeroResponse createNewHero(HeroRequest request);

	/**
	 * @return list of heros
	 */
	List<HeroResponse> getAllHeros();

	/**
	 * @param id hero id
	 * @return a hero 
	 */
	Optional<HeroResponse> getHeroById(Long id);

	/**
	 * @param id hero id
	 * @param request hero to update
	 * @return
	 */
	Optional<HeroResponse> updateHero(Long id, HeroRequest request);

	/**
	 * @param id hero id to delete
	 */
	void deleteHeroById(Long id);
}

package com.jmora.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.payloads.response.HeroResponse;

@Component
public interface IHeroService {

	HeroResponse createNewHero(HeroRequest request);

	List<HeroResponse> getAllHeros();

	Optional<HeroResponse> getHeroById(Long id);

	Optional<HeroResponse> updateHero(Long id, HeroRequest heroRequest);

	void deleteHeroById(Long id);

}

package com.jmora.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;

@Component
public interface IHeroService {

	Long createNewHero(HeroRequest request);

	List<Hero> getAllHeros();

	Optional<Hero> getHeroById(Long id);

}

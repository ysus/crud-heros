package com.jmora.web.app.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;

@Component
public interface IHeroService {

	Long createNewHero(HeroRequest request);

	List<Hero> getAllHeros();

	Hero getHeroById(Long id);

}

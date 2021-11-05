package com.jmora.web.app.service;

import org.springframework.stereotype.Component;

import com.jmora.web.app.data.payloads.request.HeroRequest;

@Component
public interface IHeroService {

	Long createNewHero(HeroRequest request);

}

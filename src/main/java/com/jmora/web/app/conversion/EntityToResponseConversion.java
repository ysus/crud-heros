package com.jmora.web.app.conversion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.payloads.response.HeroResponse;

@Component
public class EntityToResponseConversion {
	
	 @Autowired
	 private ModelMapper modelMapper;
	
	 public HeroResponse HeroToHeroResponse(Hero hero) {
		return modelMapper.map(hero, HeroResponse.class);
	}
	 
	 public Hero HeroRequestToHero(HeroRequest heroRequest) {
		return modelMapper.map(heroRequest, Hero.class);
	}
}

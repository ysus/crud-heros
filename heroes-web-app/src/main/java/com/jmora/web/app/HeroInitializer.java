package com.jmora.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.repository.HeroRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HeroInitializer implements CommandLineRunner {
	@Autowired
	private HeroRepository heroRepository;

	@Override
	public void run(String... args) throws Exception {

		Hero hero = new Hero();
		hero.setHeroName("hulk");
		hero.setPower("fuerza");
		hero.setRealName("bruce warner");

		heroRepository.save(hero);
		log.info("hero saved");

	}
}

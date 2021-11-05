/**
 * 
 */
package com.jmora.web.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.repository.HeroRepository;

/**
 * @author jmp
 *
 */
@Service
public class HeroServiceImp implements IHeroService {

	@Autowired
	private HeroRepository heroRepository;

	@Override
	public Long createNewHero(HeroRequest request) {

		Hero hero = new Hero();
		hero.setHeroName(request.getHeroName());
		hero.setPower(request.getPower());
		hero.setRealName(request.getRealName());

		return heroRepository.save(hero).getId();
	}

	@Override
	public List<Hero> getAllHeros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hero getHeroById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

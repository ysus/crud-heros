/**
 * 
 */
package com.jmora.web.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Hero createNewHero(HeroRequest request) {

		Hero hero = new Hero();
		hero.setHeroName(request.getHeroName());
		hero.setPower(request.getPower());
		hero.setRealName(request.getRealName());

		return heroRepository.save(hero);
	}

	@Override
	public List<Hero> getAllHeros() {
		
		List<Hero> list = new ArrayList<>();
		Iterable<Hero> items = heroRepository.findAll();
		items.forEach(list::add);
		return list;
	}

	@Override
	public Optional<Hero> getHeroById(Long id) {
		return heroRepository.findById(id);
	}

	@Override
	@Transactional
	public Optional<Hero> updateHero(Long id, HeroRequest heroRequest) {
		return heroRepository.findById(id)
				.map(oldHero ->{
					oldHero.setHeroName(heroRequest.getHeroName());
					oldHero.setPower(heroRequest.getPower());
					oldHero.setRealName(heroRequest.getRealName());
					
					return oldHero;
				});
	}

	@Override
	public void deleteHeroById(Long id) {
		heroRepository.deleteById(id);
		
	}

}

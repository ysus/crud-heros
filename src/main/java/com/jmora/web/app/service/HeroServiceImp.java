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

import com.jmora.web.app.convesion.EntityToResponseConversion;
import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.payloads.response.HeroResponse;
import com.jmora.web.app.data.repository.HeroRepository;

/**
 * @author jmp
 *
 */
@Service
public class HeroServiceImp implements IHeroService {

	@Autowired
	private HeroRepository heroRepository;
	
	@Autowired
	private EntityToResponseConversion entityConversion;
 
	@Override
	public HeroResponse createNewHero(HeroRequest request) {

		Hero hero = new Hero();
		hero.setHeroName(request.getHeroName());
		hero.setPower(request.getPower());
		hero.setRealName(request.getRealName());

		return entityConversion.HeroToHeroResponse(heroRepository.save(hero));
	}

	@Override
	public List<HeroResponse> getAllHeros() {
		
		List<HeroResponse> list = new ArrayList<>();
		Iterable<Hero> items = heroRepository.findAll();
		items.forEach(hero->{
			list.add( entityConversion.HeroToHeroResponse(hero));
		});
		
		return list;
	}

	@Override
	public Optional<HeroResponse> getHeroById(Long id) {
		
		Optional<Hero> hero = heroRepository.findById(id);
		return hero.map(h -> entityConversion.HeroToHeroResponse(h));
	}

	@Override
	@Transactional
	public Optional<HeroResponse> updateHero(Long id, HeroRequest heroRequest) {
		
		
		Optional<Hero> hero = heroRepository.findById(id).map(oldHero -> {
			oldHero.setHeroName(heroRequest.getHeroName());
			oldHero.setPower(heroRequest.getPower());
			oldHero.setRealName(heroRequest.getRealName());
			return oldHero;
		});
		
		return hero.map(h -> entityConversion.HeroToHeroResponse(h)); 
		
		
	}

	@Override
	public void deleteHeroById(Long id) {
		heroRepository.deleteById(id);
		
	}

}

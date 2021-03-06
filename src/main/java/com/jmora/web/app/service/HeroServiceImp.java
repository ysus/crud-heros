package com.jmora.web.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmora.web.app.conversion.EntityToResponseConversion;
import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.data.payloads.response.HeroResponse;
import com.jmora.web.app.data.repository.HeroRepository;


@Service
public class HeroServiceImp implements IHeroService {

	@Autowired
	private HeroRepository heroRepository;
	
	@Autowired
	private EntityToResponseConversion entityConversion;
 
	/**
	 * {@inheritDoc}
	 */
	@Caching(evict = {
            @CacheEvict(value="heros", allEntries=true),
            @CacheEvict(value="hero", allEntries=true)})
	@Override
	public HeroResponse createNewHero(HeroRequest request) {
		Hero hero = entityConversion.HeroRequestToHero(request);
		return entityConversion.HeroToHeroResponse(heroRepository.save(hero));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Cacheable("heros")
	@Override
	public List<HeroResponse> getAllHeros() {
		List<HeroResponse> list = new ArrayList<>();
		
		Iterable<Hero> items = heroRepository.findAll();
		items.forEach(hero->{
			list.add( entityConversion.HeroToHeroResponse(hero));
		});
		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Cacheable("hero")
	@Override
	public Optional<HeroResponse> getHeroById(Long id) {
		return heroRepository.findById(id)
				.map(h -> entityConversion.HeroToHeroResponse(h));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Caching(evict = {
            @CacheEvict(value="heros", allEntries=true),
            @CacheEvict(value="hero", allEntries=true)})
	@Override
	@Transactional
	public Optional<HeroResponse> updateHero(Long id, HeroRequest heroRequest) {	
		return heroRepository.findById(id)
				.map(hero ->{ 
					setUpdateValues(hero, heroRequest);
					return hero;
				})
				.map(h -> entityConversion.HeroToHeroResponse(h));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Caching(evict = {
			@CacheEvict(value = "heros", allEntries = true),
            @CacheEvict(value = "hero", key = "#id")})
	//@CacheEvict(value = "hero", key = "#id")
	@Override
	public void deleteHeroById(Long id) {
		heroRepository.deleteById(id);
	}
	
	/**
	 * @param hero hero to update
	 * @param heroRequest new values
	 */
	private void setUpdateValues(Hero hero, HeroRequest heroRequest) {
		hero.setHeroName(heroRequest.getHeroName());
		hero.setPower(heroRequest.getPower());
		hero.setRealName(heroRequest.getRealName());
	}
}

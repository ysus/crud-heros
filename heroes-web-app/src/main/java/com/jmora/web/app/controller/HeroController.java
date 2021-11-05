/**
 * 
 */
package com.jmora.web.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmora.web.app.data.models.Hero;
import com.jmora.web.app.data.payloads.request.HeroRequest;
import com.jmora.web.app.exception.HeroNotFoundException;
import com.jmora.web.app.service.IHeroService;

/**
 * @author jmp
 *
 */
@RestController
@RequestMapping("/api/heros")
public class HeroController {
	
	@Autowired
	private IHeroService heroService;
	
	@PostMapping
	public ResponseEntity<Void> createNewHero(@Valid @RequestBody HeroRequest heroRequest ){
		
		Long id = heroService.createNewHero(heroRequest);
		
		URI location= ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping
	public ResponseEntity<List<Hero>> getAllHeros(){
		return ResponseEntity.ok(heroService.getAllHeros());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Hero> getHeroById(@PathVariable("id") Long id){
		
		return ResponseEntity
				.ok(heroService.getHeroById(id).orElseThrow(() -> 
					new HeroNotFoundException(String.format("Hero with id: '%s' not found", id))));	
	}
	

}

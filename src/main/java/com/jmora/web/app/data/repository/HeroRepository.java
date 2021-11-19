/**
 * 
 */
package com.jmora.web.app.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.jmora.web.app.data.models.Hero;

public interface HeroRepository extends CrudRepository<Hero, Long> {}

/**
 * 
 */
package com.jmora.web.app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmora.web.app.data.models.Hero;

/**
 * @author jmp
 *
 */
public interface HeroRepository extends JpaRepository<Hero, Long> {

}

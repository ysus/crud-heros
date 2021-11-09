package com.jmora.web.app.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jmora.web.app.data.models.ApplicationUser;
import com.jmora.web.app.data.repository.UserRepository;


@Configuration
@EnableJpaAuditing
public class SpringSecurityAuditorAware {
  @Bean
  AuditorAware<ApplicationUser> auditorAware(UserRepository repo) {
    // Lookup ApplicationUser instance corresponding to logged in user
    return () -> Optional.ofNullable(SecurityContextHolder.getContext())
      .map(SecurityContext::getAuthentication)
      .filter(Authentication::isAuthenticated)
      .map(Authentication::getName)
      .map(name ->{
    	  System.out.println("####"+name);
    	  return name;
      })
      .flatMap(repo::findByUsername);
  }
}
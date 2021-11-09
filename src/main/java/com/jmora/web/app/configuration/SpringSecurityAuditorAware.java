package com.jmora.web.app.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jmora.web.app.data.models.Username;


@Configuration
@EnableJpaAuditing
public class SpringSecurityAuditorAware {
  @Bean
  AuditorAware<Username> auditorAware() {
    // Lookup ApplicationUser instance corresponding to logged in user
    return () -> Optional.ofNullable(SecurityContextHolder.getContext())
      .map(SecurityContext::getAuthentication)
      .filter(Authentication::isAuthenticated)
      .map(Authentication::getPrincipal)
     // .map(UserDetails.class::cast)
      .map(u -> new Username(u.toString()));
  }
}
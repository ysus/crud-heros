package com.jmora.web.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";
        
        UserDetails user = userDetailsService.loadUserByUsername(username);
        
        if (!bCryptEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        
        if (!user.isEnabled()) {
            throw new DisabledException("User account is not active");
        }
        
        return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

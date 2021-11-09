package com.jmora.web.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jmora.web.app.filter.SecurityFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private SecurityFilter secFilter;
	
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	//Required in case of Stateless Authentication
	@Override @Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
	
//	 @Bean
//	    UserDetailsService customUserDetailsService(UserRepository users) {
//	        return (username) -> users.findByUsername(username)
//	                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
//	    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable()    //Disabling CSRF as not using form based login
			.authorizeRequests()
			.antMatchers(AUTH_WHITELIST).permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//To Verify user from second request onwards............
			.and()
			.addFilterBefore(secFilter, UsernamePasswordAuthenticationFilter.class)
			.headers().frameOptions().sameOrigin();
			;
	}
	
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/",
            "/swagger-ui/**",
            
            "/user/saveUser","/user/loginUser","/h2-console/**"
    };

}
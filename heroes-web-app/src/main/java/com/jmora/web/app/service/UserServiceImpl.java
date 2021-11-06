package com.jmora.web.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jmora.web.app.data.models.ApplicationUser;
import com.jmora.web.app.data.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
	
	@Autowired
	private UserRepository userRepo; 
	
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;
	
	@Override
	public Integer saveUser(ApplicationUser user) {
		
		//Encode password before saving to DB
		user.setPassword(bCryptEncoder.encode(user.getPassword()));
		return userRepo.save(user).getId();
	}

	//find user by username
	@Override
	public Optional<ApplicationUser> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ApplicationUser> opt = userRepo.findByUsername(username);
		
		User springUser=null;
		
		if(opt.isEmpty()) {
			throw new UsernameNotFoundException("User with username: " +username +" not found");
		}else {
			ApplicationUser user =opt.get();	//retrieving user from DB
			Set<String> roles = user.getRoles();
			Set<GrantedAuthority> ga = new HashSet<>();
			for(String role:roles) {
				ga.add(new SimpleGrantedAuthority(role));
			}
			
			springUser = new User(
							username,
							user.getPassword(),
							ga );
		}
		
		return springUser;
	}

}
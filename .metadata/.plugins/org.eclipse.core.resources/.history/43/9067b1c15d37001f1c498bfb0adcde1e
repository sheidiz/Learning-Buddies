package com.sheiladiz.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Not found!"));

		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

		user.getRoles().forEach(
				role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

		user.getRoles().stream().flatMap(role -> role.getPermissionList().stream())
				.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

		return new User(user.getEmail(), user.getPassword(), user.isEnabled(), user.isAccountNoExpired(),
				user.isCredentialNoExpired(), user.isAccountNoLocked(), authorityList);
	}

}

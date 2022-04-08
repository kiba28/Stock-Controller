package com.stock.user.service.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.stock.user.dto.UserDTO;
import com.stock.user.dto.UserFormDTO;
import com.stock.user.entities.Role;
import com.stock.user.entities.User;
import com.stock.user.exceptions.ResourceNotFoundException;
import com.stock.user.repositories.RoleRepository;
import com.stock.user.repositories.UserRepository;
import com.stock.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModelMapper mapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Optional<User> user1 = userRepo.findByName(name);
		User user = user1.get();
		if (user == null) {
			throw new ResourceNotFoundException("This User doesnt exist in this database");
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
	}

	@Override
	public UserDTO saveUser(@Valid UserFormDTO body) {
		body.setPassword(passwordEncoder.encode(body.getPassword()));
		return mapper.map(userRepo.save(mapper.map(body, User.class)), UserDTO.class);
	}

	@Override
	public UserDTO updateUser(@PathVariable Long id, @Valid UserFormDTO body) {
		User chosenUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));

		BeanUtils.copyProperties(body, chosenUser, "id");

		return mapper.map(userRepo.save(chosenUser), UserDTO.class);
	}

	@Override
	public Role saveRole(@Valid Role role) {
		Role roleSaved = roleRepo.save(role);
		return roleSaved;
	}

	@Override
	public void addRoleToUser(String name, String roleName) {
		Role role = roleRepo.findByName(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role" + roleName + " not found."));
		User user = userRepo.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("User" + name + " not found."));

		user.getRoles().add(role);

		userRepo.save(user);
	}

	@Override
	public UserDTO getUser(String name) {
		User user = userRepo.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("User" + name + " not found."));
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public Page<UserDTO> listUsers(PageRequest pageRequest) {

		Page<User> users = userRepo.findAll(pageRequest);

		List<UserDTO> list = users.getContent().stream().map(User -> mapper.map(User, UserDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<>(list);
	}

	@Override
	public void deleteUser(@PathVariable Long id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));

		userRepo.delete(user);
	}

}

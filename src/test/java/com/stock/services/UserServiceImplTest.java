package com.stock.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.stock.builders.RoleBuilder;
import com.stock.builders.UserBuilder;
import com.stock.userservice.dto.UserDTO;
import com.stock.userservice.dto.UserFormDTO;
import com.stock.userservice.entities.Role;
import com.stock.userservice.entities.User;
import com.stock.userservice.exceptions.ResourceNotFoundException;
import com.stock.userservice.repositories.RoleRepository;
import com.stock.userservice.repositories.UserRepository;
import com.stock.userservice.services.implementations.UserServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

	@Autowired
	private UserServiceImpl service;

	@MockBean
	private UserRepository repository;

	@MockBean
	RoleRepository roleRepository;

	@Test
	public void deveriaInserirUmUsuario() {
		User user = UserBuilder.getUser();

		when(this.repository.save(any(User.class))).thenReturn(user);

		UserDTO userDto = this.service.saveUser(UserBuilder.getUserFormDTO());

		assertThat(userDto.getId()).isNotNull();
		assertThat(userDto.getName()).isEqualTo(user.getName());
		assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	public void deveriaAtualizarUmUsuario() {
		User user = UserBuilder.getUser();
		UserFormDTO userForm = UserBuilder.getUserFormDTO();
		userForm.setName("Mochila");
		userForm.setEmail("mochila@email.com");

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(user));
		when(this.repository.save(any(User.class))).thenReturn(user);

		UserDTO userDto = this.service.updateUser(1L, userForm);

		assertThat(userDto.getId()).isNotNull();
		assertThat(userDto.getName()).isEqualTo(user.getName());
		assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	public void naoDeveriaAtualizarUmUsuario() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.updateUser(1L, UserBuilder.getUserFormDTO()));
	}

	@Test
	public void deveriaInserirUmaFuncao() {
		Role role = RoleBuilder.getRole();

		when(this.roleRepository.save(any(Role.class))).thenReturn(role);

		Role roleSaved = this.service.saveRole(role);

		assertThat(roleSaved.getId()).isNotNull();
		assertThat(roleSaved.getName()).isEqualTo(role.getName());
	}

	@Test
	public void deveriaAdicionarUmaFuncaoAUmUsuario() {
		User user = UserBuilder.getUser();
		Role role = RoleBuilder.getRole();

		when(this.repository.findByName(anyString())).thenReturn(Optional.of(user));
		when(this.roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

		this.service.addRoleToUser(user.getName(), role.getName());

		assertThat(user.getRoles().size()).isGreaterThan(0);
		assertThat(user.getRoles().get(0).getName()).isEqualTo(role.getName());
	}

	@Test
	public void naoDeveriaAdicionarUmaFuncaoAUmUsuarioPoisNaoExisteUsuarioComNomeInformado() {
		when(this.roleRepository.findByName(anyString())).thenReturn(Optional.of(RoleBuilder.getRole()));
		when(this.repository.findByName(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.addRoleToUser("userName", "roleName"));
	}

	@Test
	public void naoDeveriaAdicionarUmaFuncaoAUmUsuarioPoisNaoExisteFuncaoComNomeInformado() {
		when(this.roleRepository.findByName(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class)
				.isThrownBy(() -> this.service.addRoleToUser("userName", "roleName"));
	}

	@Test
	public void deveriaBuscarUmUsuarioPeloNome() {
		User user = UserBuilder.getUser();
		user.setRoles(Arrays.asList(RoleBuilder.getRole()));

		when(this.repository.findByName(anyString())).thenReturn(Optional.of(user));

		UserDTO userDto = this.service.getUser("Jose");

		assertThat(userDto.getId()).isNotNull();
		assertThat(userDto.getName()).isEqualTo(user.getName());
		assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
		assertThat(userDto.getRoles()).isEqualTo(user.getRoles());
	}

	@Test
	public void naoDeveriaBuscarUmUsuarioPoisNaoExisteUsuarioComNomeInformado() {
		when(this.repository.findByName(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.getUser("Mochila"));
	}

	@Test
	public void deveriaBuscarTodosOsUsuariosComPaginacao() {
		User user = UserBuilder.getUser();
		List<User> list = Arrays.asList(user, user);
		Page<User> productAsPage = new PageImpl<>(list);

		when(this.repository.findAll(any(Pageable.class))).thenReturn(productAsPage);

		Page<UserDTO> userDtoAsPage = service.listUsers(PageRequest.of(0, 12, Direction.ASC, "name"));
		UserDTO userDto = userDtoAsPage.getContent().get(0);

		
		assertThat(userDtoAsPage.getContent().size()).isGreaterThan(0);
		assertThat(userDto.getId()).isNotNull();
		assertThat(userDto.getName()).isEqualTo(user.getName());
		assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	public void deveriaDeletarUmUsuarioPeloId() {
		User user = UserBuilder.getUser();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(user));

		this.service.deleteUser(1L);

		verify(this.repository, times(1)).delete(user);
	}
	
	@Test
	public void naoDeveriaDeletarUmUsuarioPoisNaoExisteUsuarioComIdInformado() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> this.service.deleteUser(2L));
	}

//	@Test
//	void testGetUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testListUsers() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteUser() {
//		fail("Not yet implemented");
//	}

}

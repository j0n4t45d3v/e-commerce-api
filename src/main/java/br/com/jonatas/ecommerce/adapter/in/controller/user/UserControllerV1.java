package br.com.jonatas.ecommerce.adapter.in.controller.user;

import br.com.jonatas.ecommerce.core.domain.user.UserDomain;
import br.com.jonatas.ecommerce.gateway.in.user.DeleteUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.EditUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.RegisterUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.SearchUserGateway;
import br.com.jonatas.ecommerce.gateway.in.user.dto.EditUserDTO;
import br.com.jonatas.ecommerce.gateway.in.user.dto.RegisterUserDTO;
import br.com.jonatas.ecommerce.infra.common.http.ResponseV0;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserControllerV1 {

	private final RegisterUserGateway registerUserGateway;
	private final SearchUserGateway searchUserGateway;
	private final EditUserGateway editUserGateway;
	private final DeleteUserGateway deleteUserGateway;

	public UserControllerV1(
		RegisterUserGateway registerUserGateway,
		SearchUserGateway searchUserGateway,
		EditUserGateway editUserGateway,
		DeleteUserGateway deleteUserGateway
	) {
		this.registerUserGateway = registerUserGateway;
		this.searchUserGateway = searchUserGateway;
		this.editUserGateway = editUserGateway;
		this.deleteUserGateway = deleteUserGateway;
	}

	@GetMapping
	public ResponseEntity<ResponseV0<List<UserDomain>>> getAll() {
		var users = this.searchUserGateway.searchAll();
		var response = ResponseV0.of(200, users);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseV0<UserDomain>> getById(@PathVariable("id") Long id) {
		var user = this.searchUserGateway.searchUserById(id);
		var response = ResponseV0.of(200, user);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseV0<String>> register(
		@RequestBody RegisterUserDTO userDTO,
		UriComponentsBuilder uriComponentsBuilder
	) {
		this.registerUserGateway.execute(userDTO);
		var uri = uriComponentsBuilder.path("/v1/users/{id}").buildAndExpand(1L).toUri();
		var response = ResponseV0.of(201, "User created successfully");
		return ResponseEntity.created(uri).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> edit(@PathVariable("id") Long id, @RequestBody EditUserDTO userDTO) {
		this.editUserGateway.execute(id, userDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		this.deleteUserGateway.execute(id);
		return ResponseEntity.noContent().build();
	}
}

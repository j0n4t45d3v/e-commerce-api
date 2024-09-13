package br.com.jonatas.ecommerce.gateway.in.user.dto;

public record EditUserDTO(
		String username,
		String password,
		String email
) {
}

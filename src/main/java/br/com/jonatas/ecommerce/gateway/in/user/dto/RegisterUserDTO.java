package br.com.jonatas.ecommerce.gateway.in.user.dto;

import br.com.jonatas.ecommerce.core.domain.user.enums.Role;

import java.time.LocalDate;

public record RegisterUserDTO(
        String username,
        String password,
        String email,
        LocalDate birthdate,
        Role role
) {
}

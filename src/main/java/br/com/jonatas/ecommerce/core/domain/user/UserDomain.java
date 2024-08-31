package br.com.jonatas.ecommerce.core.domain.user;

import br.com.jonatas.ecommerce.core.domain.user.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDomain {
	private Long id;
	private String username;
	private String password;
	private String email;
	private Role role;
	private LocalDate birthday;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public UserDomain(String username, String password, String email, Role role, LocalDate birthday) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.birthday = birthday;
	}

	public UserDomain() {
	}

	public Long getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}

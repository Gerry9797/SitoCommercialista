package com.commercialista.backend.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.commercialista.backend.models.Role;

public class UserDTO {

	private UUID id;

	private String username;

	private String email;

	private Set<Role> roles = new HashSet<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}

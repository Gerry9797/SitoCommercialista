package com.commercialista.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class MessageRequest {
	
	private String nome;
	
	private String cognome;
	
	private String telefono;

	@NotBlank
	private String email;

	@NotBlank
	private String message;
	
	private boolean permessoTrattamentoDati;
	
	private boolean permessoNewsletter;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isPermessoTrattamentoDati() {
		return permessoTrattamentoDati;
	}

	public void setPermessoTrattamentoDati(boolean permessoTrattamentoDati) {
		this.permessoTrattamentoDati = permessoTrattamentoDati;
	}

	public boolean isPermessoNewsletter() {
		return permessoNewsletter;
	}

	public void setPermessoNewsletter(boolean permessoNewsletter) {
		this.permessoNewsletter = permessoNewsletter;
	}	

}

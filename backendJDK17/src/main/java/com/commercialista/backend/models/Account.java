package com.commercialista.backend.models;

import java.util.Date;

import com.commercialista.backend.enums.StatiAccountEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(	name = "accounts")
public class Account {
	
	@Id
	@Column(name = "id_user", nullable = false)
	private Long idUser;
	
	@Column(name = "verification_code", nullable = true, length = 255)
	private String verificationCode;

	@NotBlank
	@Size(max = 50)
	@Column(length = 50)
	@Enumerated(EnumType.STRING)
	private StatiAccountEnum status;
	
	@Column(name = "data_pubb", nullable = false)
	private Date dataPubb;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public StatiAccountEnum getStatus() {
		return status;
	}

	public void setStatus(StatiAccountEnum status) {
		this.status = status;
	}

	public Date getDataPubb() {
		return dataPubb;
	}

	public void setDataPubb(Date dataPubb) {
		this.dataPubb = dataPubb;
	}
	
	


}

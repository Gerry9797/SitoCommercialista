package com.commercialista.backend.models;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

import com.commercialista.backend.enums.StatiAccountEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(	name = "accounts")
public class Account {
	
	@Id
	@Column(name = "id_user", nullable = false)
	@JdbcTypeCode(Types.VARCHAR)
	private UUID idUser;
	
	@Column(name = "verification_code")
	private String verificationCode;

	@Column(length = 50)
	@Enumerated(EnumType.STRING)
	private StatiAccountEnum status;
	
	@Column(name = "data_pubb", nullable = false)
	private Date dataPubb;

	public UUID getIdUser() {
		return idUser;
	}

	public void setIdUser(UUID idUser) {
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

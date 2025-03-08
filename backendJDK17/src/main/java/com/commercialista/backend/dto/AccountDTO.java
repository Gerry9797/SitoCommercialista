package com.commercialista.backend.dto;

import java.util.Date;
import java.util.UUID;

import com.commercialista.backend.enums.StatiAccountEnum;

public class AccountDTO {

	private UUID idUser;

	private StatiAccountEnum status;
	
	private Date dataPubb;

	public UUID getIdUser() {
		return idUser;
	}

	public void setIdUser(UUID idUser) {
		this.idUser = idUser;
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

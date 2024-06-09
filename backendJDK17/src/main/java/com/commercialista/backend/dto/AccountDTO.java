package com.commercialista.backend.dto;

import java.util.Date;

import com.commercialista.backend.enums.StatiAccountEnum;

public class AccountDTO {

	private Long idUser;

	private StatiAccountEnum status;
	
	private Date dataPubb;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
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

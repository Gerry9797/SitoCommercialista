package com.commercialista.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {

	private String oldPassword;

	@NotBlank
	private String newPassword;
	
	private String verificationCode;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	
}

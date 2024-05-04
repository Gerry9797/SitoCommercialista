package com.commercialista.backend.errors;

import org.springframework.http.HttpStatusCode;

public class ErrorEntry {

    private String code;
    private String title;
    private String description;
    private HttpStatusCode statusCode;

    public ErrorEntry(String code, String title, String description, HttpStatusCode statusCode) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.statusCode = statusCode;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HttpStatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatusCode statusCode) {
		this.statusCode = statusCode;
	}
	
	

    
}


package com.commercialista.backend.errors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ErrorCodeLoader {

    @Value("classpath:config.errors")
    private Resource errorResource;

    private Map<String, ErrorEntry> errorMap;

    @PostConstruct
    public void init() {
        errorMap = new HashMap<>();
        try {
            InputStream inputStream = errorResource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
            	
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String keyword = parts[0].trim();
                    String code = parts[1].trim();
                    String title = parts[2].trim();
                    String description = parts[3].trim();
                    int statusCodeInt = Integer.valueOf(parts[4].trim());
                    HttpStatusCode statusCode = HttpStatusCode.valueOf(statusCodeInt);
                    
                    errorMap.put(keyword, new ErrorEntry(code, title, description, statusCode));
                }
            }
        } catch (IOException e) {
            // Gestione dell'errore nella lettura del file
            e.printStackTrace();
        }
    }

    public ErrorEntry getErrorEntry(String keyword) {
        return errorMap.get(keyword);
    }
}


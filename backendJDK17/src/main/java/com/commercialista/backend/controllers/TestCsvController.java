package com.commercialista.backend.controllers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercialista.backend.dto.test.CsvChildElementTest;
import com.commercialista.backend.dto.test.CsvElementTest;
import com.commercialista.backend.helper.csv.CsvWriter;
import com.commercialista.backend.payload.request.test.RicercaElementTestCsvRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test-csv")
public class TestCsvController {
	

	@GetMapping("/getCsvFromDto")
	public ResponseEntity<byte[]> getCsvFromDto(@ParameterObject RicercaElementTestCsvRequest request) {
		List<String> visibleFields = request.getVisibleFields(); // se è vuoto non filtra ma li mostra tutti i campi
		List<CsvElementTest> rows = getRowsHardcoded();
		// se dovrebbe ottenere questi elementi da una qualche logica di ricerca sulla base del parametro dato, ma qui li mettiamo hardcoded
		
		
        String csvAsString = new CsvWriter().getCsvString(rows, CsvElementTest.class, request.getVisibleFields());

        byte[] csvBytes = csvAsString.getBytes(StandardCharsets.UTF_8);
        String fileName = "export_" + LocalDateTime.now() + ".csv";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/csv"));
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
	}

	private List<CsvElementTest> getRowsHardcoded() {
		CsvChildElementTest childElement1 = new CsvChildElementTest("child1", "nome child 1", "icona child 1", "colore child 1");
		CsvChildElementTest childElement2 = new CsvChildElementTest("child2", "nome child 2", "icona child 2", "colore child 2");
		
		CsvElementTest element1 = new CsvElementTest("element1", childElement1, "111", "111", "oggetto 1", "idFase1", "nomeFase1", true, 11, "02/06/2024");
		CsvElementTest element2 = new CsvElementTest("element2", childElement2, "222", "222", "oggetto 2", "idFase2", "nomeFase2", false, 22, "02/07/2024");
		
		return List.of(element1, element2);
	}

}

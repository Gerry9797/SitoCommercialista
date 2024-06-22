package com.commercialista.backend.controllers;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.commercialista.backend.services.PdfService;
import com.commercialista.backend.utils.FormateManager;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test-pdf")
public class TestPdfController {
	
	@Autowired
	PdfService pdfService;

	@GetMapping("/getPdfAccessibleFromTemplateReadingValuesFromXmlString")
	public ResponseEntity<byte[]> getPdfFromTemplateReadingValuesFromXmlString() throws Exception {
		String RT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<RT xmlns=\"http://www.digitpa.gov.it/schemas/2011/Pagamenti/\">\n"
				+ "    <versioneOggetto>1.0</versioneOggetto>\n"
				+ "    <dominio>\n"
				+ "        <identificativoDominio>80062590379</identificativoDominio>\n"
				+ "        <identificativoStazioneRichiedente>02770891204_02</identificativoStazioneRichiedente>\n"
				+ "    </dominio>\n"
				+ "    <identificativoMessaggioRicevuta>m3_18f71c0af516450fb5d7d341d30767cd</identificativoMessaggioRicevuta>\n"
				+ "    <dataOraMessaggioRicevuta>2024-04-04T09:23:27+02:00</dataOraMessaggioRicevuta>\n"
				+ "    <riferimentoMessaggioRichiesta>m3_18f71c0af516450fb5d7d341d30767cd</riferimentoMessaggioRichiesta>\n"
				+ "    <riferimentoDataRichiesta>2024-04-04</riferimentoDataRichiesta>\n"
				+ "    <istitutoAttestante>\n"
				+ "        <identificativoUnivocoAttestante>\n"
				+ "            <tipoIdentificativoUnivoco>G</tipoIdentificativoUnivoco>\n"
				+ "            <codiceIdentificativoUnivoco>BCITITMM</codiceIdentificativoUnivoco>\n"
				+ "        </identificativoUnivocoAttestante>\n"
				+ "        <denominazioneAttestante>Intesa Sanpaolo S.p.A</denominazioneAttestante>\n"
				+ "    </istitutoAttestante>\n"
				+ "    <enteBeneficiario>\n"
				+ "        <identificativoUnivocoBeneficiario>\n"
				+ "            <tipoIdentificativoUnivoco>G</tipoIdentificativoUnivoco>\n"
				+ "            <codiceIdentificativoUnivoco>80062590379</codiceIdentificativoUnivoco>\n"
				+ "        </identificativoUnivocoBeneficiario>\n"
				+ "        <denominazioneBeneficiario>Regione Emilia-Romagna</denominazioneBeneficiario>\n"
				+ "    </enteBeneficiario>\n"
				+ "    <soggettoVersante>\n"
				+ "        <identificativoUnivocoVersante>\n"
				+ "            <tipoIdentificativoUnivoco>F</tipoIdentificativoUnivoco>\n"
				+ "            <codiceIdentificativoUnivoco>BSSMRA97A01A944J</codiceIdentificativoUnivoco>\n"
				+ "        </identificativoUnivocoVersante>\n"
				+ "        <anagraficaVersante>MARIO BASSI</anagraficaVersante>\n"
				+ "    </soggettoVersante>\n"
				+ "    <soggettoPagatore>\n"
				+ "        <identificativoUnivocoPagatore>\n"
				+ "            <tipoIdentificativoUnivoco>F</tipoIdentificativoUnivoco>\n"
				+ "            <codiceIdentificativoUnivoco>BSSMRA97A01A944J</codiceIdentificativoUnivoco>\n"
				+ "        </identificativoUnivocoPagatore>\n"
				+ "        <anagraficaPagatore>MARIO BASSI</anagraficaPagatore>\n"
				+ "    </soggettoPagatore>\n"
				+ "    <datiPagamento>\n"
				+ "        <codiceEsitoPagamento>0</codiceEsitoPagamento>\n"
				+ "        <importoTotalePagato>10.0</importoTotalePagato>\n"
				+ "        <identificativoUnivocoVersamento>01000000119926169</identificativoUnivocoVersamento>\n"
				+ "        <CodiceContestoPagamento>18f71c0af516450fb5d7d341d30767cd</CodiceContestoPagamento>\n"
				+ "        <datiSingoloPagamento>\n"
				+ "            <singoloImportoPagato>10.00</singoloImportoPagato>\n"
				+ "            <esitoSingoloPagamento>PAGATO</esitoSingoloPagamento>\n"
				+ "            <dataEsitoSingoloPagamento>2024-04-04</dataEsitoSingoloPagamento>\n"
				+ "            <identificativoUnivocoRiscossione>18f71c0af516450fb5d7d341d30767cd</identificativoUnivocoRiscossione>\n"
				+ "            <causaleVersamento>Pagamento ddc180e8-5bd5-4c56-9007-86b32e493c14 per la richiesta c6525b81-f3ed-4083-9e25-5ab3ef588026</causaleVersamento>\n"
				+ "            <datiSpecificiRiscossione>9/0301135TS/</datiSpecificiRiscossione>\n"
				+ "            <commissioniApplicatePSP>0.50</commissioniApplicatePSP>\n"
				+ "        </datiSingoloPagamento>\n"
				+ "    </datiPagamento>\n"
				+ "</RT>\n"
				+ "";
		// Converte la stringa XML in un oggetto Document
		InputSource inputSource = new InputSource(new java.io.StringReader(RT));
		Document document = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(inputSource);
		// Crea un'istanza di XPath
		XPath xPath = XPathFactory.newInstance().newXPath();
		String cfEnteCreditore = (String) (xPath.compile("/RT/enteBeneficiario/identificativoUnivocoBeneficiario/codiceIdentificativoUnivoco")
				.evaluate(document, XPathConstants.STRING));
		String denominazioneEnteCreditore = (String) (xPath.compile("/RT/enteBeneficiario/denominazioneBeneficiario")
				.evaluate(document, XPathConstants.STRING));
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataOraTransazioneRaw = (String) (xPath.compile("/RT/dataOraMessaggioRicevuta")
				.evaluate(document, XPathConstants.STRING));
		String dataOraTransazione = outputDateFormat.format(inputDateFormat.parse(dataOraTransazioneRaw));
//		String dataApplicativa = outputDateFormat.format(inputDateFormat.parse(esitoMBD.getDataOraAcquisto()));
		// mappa con valori da utilizzare nel template .vm
		Map parameters = new HashMap<>();
		parameters.put("dataOraOperazione", dataOraTransazione);
//		parameters.put("dataApplicativa", dataApplicativa);
		parameters.put("cfEnteCreditore", cfEnteCreditore);
		parameters.put("denominazioneEnteCreditore", denominazioneEnteCreditore);
		parameters.put("iuv", "12345678");
		parameters.put("iubd", "12345678");// Identificativo Univoco Bollo Digitale
		parameters.put("idPagamento", "11111111");
		parameters.put("ragioneSocialePSP", "Instesa San Paolo s.p.a.");
		parameters.put("cfPSP", "8989698766");
		parameters.put("paymentToken", "hvhvbkandab");
		parameters.put("importoOperazione",
				FormateManager.formattaImportoString(Double.valueOf(12.2d)) + " €");
		parameters.put("causale", "causale prova");
		byte[] bytePdf = pdfService.generateAccessiblePdf("pdf/modelloRicevutaPagamento", parameters, "titolo esempio", "it-IT");
		String filename = "ricevuta-pagamento.pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);headers.setContentDispositionFormData("attachment", filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");return new ResponseEntity<>(bytePdf, headers, HttpStatus.OK);
	}
	
	@GetMapping("/getPdfAccessibleFromTemplate")
	public ResponseEntity<byte[]> getAccessiblePdfFromTemplateReadingValuesFromXmlString() throws Exception {
		// mappa con valori da utilizzare nel template .vm
		Map parameters = new HashMap<>();
		byte[] bytePdf = pdfService.generateAccessiblePdf("pdf/modelloEsempioAccessibile", parameters, "titolo esempio", "it-IT");
		String filename = "esempio-accessibile.pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);headers.setContentDispositionFormData("attachment", filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");return new ResponseEntity<>(bytePdf, headers, HttpStatus.OK);
	}

}

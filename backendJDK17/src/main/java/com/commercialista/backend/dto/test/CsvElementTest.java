package com.commercialista.backend.dto.test;

import com.commercialista.backend.helper.csv.CsvField;
import com.commercialista.backend.helper.csv.CsvObject;

public class CsvElementTest {
	
    @CsvField(header = "id", order = 1)
    private String id;

    @CsvObject(prefix = "tipologia procedimento ", order = 2)
    private CsvChildElementTest tipologiaProcedimento;

    @CsvField(header = "idIterCfg", order = 3)
    private String idIterCfg;

    @CsvField(header = "numero", order = 4)
    private String numero;
    @CsvField(header = "oggetto", order = 5)
    private String oggetto;
    @CsvField(header = "id fase corrente", order = 6)

    private String idFaseCorrente;
    @CsvField(header = "nome fase corrente", order = 7)

    private String nomeFaseCorrente;
    private boolean faseFinale;
    @CsvField(header = "id ufficio avvio", order = 8)

    private Integer registrazioneNumero;
    @CsvField(header = "data registrazione", order = 18)

    private String registrazioneData;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CsvChildElementTest getTipologiaProcedimento() {
		return tipologiaProcedimento;
	}
	public void setTipologiaProcedimento(CsvChildElementTest tipologiaProcedimento) {
		this.tipologiaProcedimento = tipologiaProcedimento;
	}
	public String getIdIterCfg() {
		return idIterCfg;
	}
	public void setIdIterCfg(String idIterCfg) {
		this.idIterCfg = idIterCfg;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getIdFaseCorrente() {
		return idFaseCorrente;
	}
	public void setIdFaseCorrente(String idFaseCorrente) {
		this.idFaseCorrente = idFaseCorrente;
	}
	public String getNomeFaseCorrente() {
		return nomeFaseCorrente;
	}
	public void setNomeFaseCorrente(String nomeFaseCorrente) {
		this.nomeFaseCorrente = nomeFaseCorrente;
	}
	public boolean isFaseFinale() {
		return faseFinale;
	}
	public void setFaseFinale(boolean faseFinale) {
		this.faseFinale = faseFinale;
	}
	public Integer getRegistrazioneNumero() {
		return registrazioneNumero;
	}
	public void setRegistrazioneNumero(Integer registrazioneNumero) {
		this.registrazioneNumero = registrazioneNumero;
	}
	public String getRegistrazioneData() {
		return registrazioneData;
	}
	public void setRegistrazioneData(String registrazioneData) {
		this.registrazioneData = registrazioneData;
	}
	
	
	
	public CsvElementTest() {
		super();
	}
	public CsvElementTest(String id, CsvChildElementTest tipologiaProcedimento, String idIterCfg, String numero,
			String oggetto, String idFaseCorrente, String nomeFaseCorrente, boolean faseFinale,
			Integer registrazioneNumero, String registrazioneData) {
		super();
		this.id = id;
		this.tipologiaProcedimento = tipologiaProcedimento;
		this.idIterCfg = idIterCfg;
		this.numero = numero;
		this.oggetto = oggetto;
		this.idFaseCorrente = idFaseCorrente;
		this.nomeFaseCorrente = nomeFaseCorrente;
		this.faseFinale = faseFinale;
		this.registrazioneNumero = registrazioneNumero;
		this.registrazioneData = registrazioneData;
	}
	

	
	

    
}

package com.commercialista.backend.payload.request.test;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.commercialista.backend.consts.Consts;
import com.commercialista.backend.enums.SortDirection;


public class RicercaElementTestRequest {
    private String numero;
    private String tipo;
    private String stato;
    
    @DateTimeFormat(pattern = Consts.DATETIME_FORMAT)
    private Date dataAvvioDal;
    
    @DateTimeFormat(pattern = Consts.DATETIME_FORMAT)
    private Date dataAvvioAl;
    
    private String idUfficioAvvio;
    private String ufficioAvvio;
    private String responsabile;
    private String idUfficioResponsabile;
    private String ufficioResponsabile;
    private String oggetto;
    private Integer page;
    private Integer pageSize;
    private SortField sortBy;
    private SortDirection sortDirection;
    private String idIstanzaProcedimento;
    private String valoriRicercaEstesa;
    private String nomeTitolare;
    private String cognomeTitolare;

    private Integer progressivoAnno;

    private Integer progressivoId;

    private String progressivo;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Date getDataAvvioDal() {
        return dataAvvioDal;
    }

    public void setDataAvvioDal(Date dataAvvioDal) {
        this.dataAvvioDal = dataAvvioDal;
    }

    public Date getDataAvvioAl() {
        return dataAvvioAl;
    }

    public void setDataAvvioAl(Date dataAvvioAl) {
        this.dataAvvioAl = dataAvvioAl;
    }

    public String getUfficioAvvio() {
        return ufficioAvvio;
    }

    public void setUfficioAvvio(String ufficioAvvio) {
        this.ufficioAvvio = ufficioAvvio;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getUfficioResponsabile() {
        return ufficioResponsabile;
    }

    public void setUfficioResponsabile(String ufficioResponsabile) {
        this.ufficioResponsabile = ufficioResponsabile;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public SortField getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortField sortBy) {
        this.sortBy = sortBy;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getIdUfficioAvvio() {
		return idUfficioAvvio;
	}

	public void setIdUfficioAvvio(String idUfficioAvvio) {
		this.idUfficioAvvio = idUfficioAvvio;
	}

	public String getIdUfficioResponsabile() {
		return idUfficioResponsabile;
	}

	public void setIdUfficioResponsabile(String idUfficioResponsabile) {
		this.idUfficioResponsabile = idUfficioResponsabile;
	}

    public String getIdIstanzaProcedimento() {
        return idIstanzaProcedimento;
    }

    public void setIdIstanzaProcedimento(String idIstanzaProcedimento) {
        this.idIstanzaProcedimento = idIstanzaProcedimento;
    }

    public String getValoriRicercaEstesa() {
        return valoriRicercaEstesa;
    }

    public void setValoriRicercaEstesa(String valoriRicercaEstesa) {
        this.valoriRicercaEstesa = valoriRicercaEstesa;
    }

    public String getNomeTitolare() {
        return nomeTitolare;
    }

    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    public String getCognomeTitolare() {
        return cognomeTitolare;
    }

    public void setCognomeTitolare(String cognomeTitolare) {
        this.cognomeTitolare = cognomeTitolare;
    }

    public Integer getProgressivoAnno() {

        return this.progressivoAnno;
    }

    public void setProgressivoAnno(Integer progressivoAnno) {

        this.progressivoAnno = progressivoAnno;
    }

    public Integer getProgressivoId() {

        return this.progressivoId;
    }

    public void setProgressivoId(Integer progressivoId) {

        this.progressivoId = progressivoId;
    }

    public String getProgressivo() {

        return this.progressivo;
    }

    public void setProgressivo(String progressivo) {

        this.progressivo = progressivo;
    }

    public enum SortField {
        PROGRESSIVO_ANNO,
        PROGRESSIVO_ID,
        PROGRESSIVO,
        NUMERO,
        OGGETTO,
        DATA_ULTIMA_MODIFICA,
        NOME_TIPO_PROCEDIMENTO,
        NOME_FASE
    }
}

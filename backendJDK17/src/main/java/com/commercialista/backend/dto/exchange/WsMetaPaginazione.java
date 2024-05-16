package com.commercialista.backend.dto.exchange;

import org.springframework.data.domain.Page;

/**
 * Metadati della risposta paginata
 * 
 */
public class WsMetaPaginazione {
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public static WsMetaPaginazione of(Page<?> page) {
		return new WsMetaPaginazione(page.getNumber(), page.getSize(), page.getTotalElements());
	}
	
	/**
	 * 
	 * @param pagina
	 * @param limite
	 * @param totale
	 * @return
	 */
	public static WsMetaPaginazione of(Integer pagina, Integer limite, Long totale) {
		return new WsMetaPaginazione(pagina, limite, totale);
	}
	
	private Integer pagina;
	private Integer limite;
	private Long totale;
	
	/**
	 * 
	 */
	public WsMetaPaginazione() {
	}

	/**
	 * 
	 * @param pagina
	 * @param limite
	 * @param totale
	 */
	public WsMetaPaginazione(Integer pagina, Integer limite, Long totale) {
		super();
		this.pagina = pagina;
		this.limite = limite;
		this.totale = totale;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getLimite() {
		return limite;
	}

	public void setLimite(Integer limite) {
		this.limite = limite;
	}

	public Long getTotale() {
		return totale;
	}

	public void setTotale(Long totale) {
		this.totale = totale;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WsMetaPaginazione [pagina=");
		builder.append(pagina);
		builder.append(", limite=");
		builder.append(limite);
		builder.append(", totale=");
		builder.append(totale);
		builder.append("]");
		return builder.toString();
	}
	
}

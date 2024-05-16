package com.commercialista.backend.dto.exchange;

import java.io.Serializable;

import org.springframework.data.domain.Page;

/**
 * 
 * 
 */
public class WsRispostaPaginata<D> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param <D>
	 * @param data
	 * @param pagina
	 * @param limite
	 * @param totale
	 * @return
	 */
	public static <D> WsRispostaPaginata<D> of(D data, Integer pagina, Integer limite, Long totale) {
		WsRispostaPaginata<D> response = new WsRispostaPaginata<>();
		
		response.setDati(data);
		response.setMeta(WsMetaPaginazione.of(pagina, limite, totale));
		
		return response;
	}
	
	/**
	 * 
	 * @param <D>
	 * @param data
	 * @param meta
	 * @return
	 */
	public static <D> WsRispostaPaginata<D> of(D data, WsMetaPaginazione meta) {
		WsRispostaPaginata<D> response = new WsRispostaPaginata<>();

		response.setDati(data);
		response.setMeta(meta);
		
		return response;
	}
	
	/**
	 * 
	 * @param <D>
	 * @param data
	 * @param page
	 * @return
	 */
	public static <D> WsRispostaPaginata<D> of(D data, Page<?> page) {
		return of(data, WsMetaPaginazione.of(page));
	}

    private WsMetaPaginazione meta;
    private D dati;
    
    /**
     * 
     */
    public WsRispostaPaginata() {
	}

    /**
     * 
     * @param dati
     */
    public WsRispostaPaginata(D dati) {
		super();
		setDati(dati);
	}

    public WsMetaPaginazione getMeta() {
		return meta;
	}

	public void setMeta(WsMetaPaginazione meta) {
		this.meta = meta;
	}

	public D getDati() {
        return dati;
    }

    public void setDati(D dati) {
        this.dati = dati;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WsRispostaPaginata [meta=");
		builder.append(meta);
		builder.append(", dati=");
		builder.append(dati);
		builder.append("]");
		return builder.toString();
	}
}

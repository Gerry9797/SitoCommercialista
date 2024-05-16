package com.commercialista.backend.dto.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.commercialista.backend.exception.OrdinamentoNonValidoOriginalException;

/**
 * Parametri per la request paginata
 * 
 */
public class WsParamPaginazione {
	
	/**
	 * Converte i parametri della paginazione in una PagedRequest adatta al JpaRepository
	 * @param params
	 * @return
	 */
	public static PageRequest toPagedRequest(WsParamPaginazione params){
		return PageRequest.of(params.getPagina(), params.getLimite(), toSorters(params.getOrdinamenti()));
	}
	
	/**
	 * Converte i parametri della paginazione in una PagedRequest adatta al JpaRepository
	 * @param params
	 * @param fieldMap mappa che permette di tradurre i nomi dei campi del dto in nomi dei campi dell'entity. 
	 * La mappa ha in chiave i nomi dei campi del dto ed in value i nomi dei campi dell'entity
	 * @return
	 */
	public static PageRequest toPagedRequest(WsParamPaginazione params, Map<String,String> fieldMap){
		return PageRequest.of(params.getPagina(), params.getLimite(), toSorters(params.getOrdinamenti(), fieldMap));
	}
	
	/**
	 * Restituisce un oggetto Sort adatto all'ordinamento dei dati.
	 * @param fields i nomi dei campi del dto in base a cui ordinare
	 * @return
	 */
	public static Sort toSorters(List<String> fields){
		if (fields == null || fields.isEmpty()) {
			return Sort.unsorted();
		}

		List<Order> orders = fields.stream()
				.filter(param -> param != null && param.length() > 0)
				.map(WsParamPaginazione::toOrder).collect(Collectors.toList());
		
		return Sort.by(orders);
	}
	
	/**
	 * Restituisce un oggetto Sort adatto all'ordinamento dei dati.
	 * @param fields i nomi dei campi del dto in base a cui ordinare
	 * @param fieldMap mappa che permette di tradurre i nomi dei campi del dto in nomi dei campi dell'entity. 
	 * La mappa ha in chiave i nomi dei campi del dto ed in value i nomi dei campi dell'entity
	 * @return
	 */
	public static Sort toSorters(List<String> fields, Map<String,String> fieldMap){
		if (fields == null || fields.isEmpty()) {
			return Sort.unsorted();
		}

		List<Order> orders = new ArrayList<>();
		for (String field : fields) {
			if (field != null && field.length() > 0) {
				orders.add(WsParamPaginazione.toOrder(field, fieldMap));
			}
		}

		return Sort.by(orders);
	}
	
	/**
	 * Restituisce un oggetto Order adatto all'ordinamento dei dati.
	 * @param field il nome del campo del dto in base a cui ordinare.
	 * Se il campo ha un "-" iniziale, l'ordinamento viene considerato decrescente.
	 * @return
	 */
	public static Order toOrder(String field){
		Direction direction = null;
		if(field.startsWith("-")) {
			field = field.substring(1, field.length());
			direction = Direction.DESC;
		}
		else {
			direction = Direction.ASC;
		}
		
		return new Order(direction, field);
	}
	
	/**
	 * Restituisce un oggetto Order adatto all'ordinamento dei dati.
	 * @param field il nome del campo del dto in base a cui ordinare.
	 * Se il campo ha un "-" iniziale, l'ordinamento viene considerato decrescente.
	 * @param fieldMap mappa che permette di tradurre i nomi dei campi del dto in nomi dei campi dell'entity. 
	 * La mappa ha in chiave i nomi dei campi del dto ed in value i nomi dei campi dell'entity.
	 * @return
	 */
	public static Order toOrder(String field, Map<String,String> fieldMap) {
		boolean caseInsensitive = field.startsWith("#");
		if (caseInsensitive) {
			field = field.substring(1);
		}

		Direction direction = Direction.ASC;
		if (field.startsWith("-")) {
			field = field.substring(1);
			direction = Direction.DESC;
		}

		String entityField = fieldMap.get(field);
		Order order = null;
		if (entityField != null) {
			order = new Order(direction, entityField);
			if (caseInsensitive) {
				order = order.ignoreCase();
			}
		} else {
			throw new OrdinamentoNonValidoOriginalException(field);
		}
		return order;
	}
	
	public static final int LIMITE_DEF = Integer.MAX_VALUE;
	
	private int pagina = 0;
	private int limite = LIMITE_DEF;
	private List<String> ordinamenti;
	
	/**
	 * 
	 */
	public WsParamPaginazione() {
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public List<String> getOrdinamenti() {
		if(ordinamenti == null)
			ordinamenti = new ArrayList<>();
		
		return ordinamenti;
	}

	public void setOrdinamenti(List<String> ordinamenti) {
		this.ordinamenti = ordinamenti;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WsParamPaginazione [pagina=");
		builder.append(pagina);
		builder.append(", limite=");
		builder.append(limite);
		builder.append(", ordinamenti=");
		builder.append(ordinamenti);
		builder.append("]");
		return builder.toString();
	}
	
}

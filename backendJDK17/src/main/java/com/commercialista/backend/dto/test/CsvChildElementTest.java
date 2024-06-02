package com.commercialista.backend.dto.test;

import java.io.Serializable;
import java.util.Objects;

import com.commercialista.backend.helper.csv.CsvField;

public class CsvChildElementTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6850849442264649131L;


	public CsvChildElementTest(String id, String nomeTipo, String iconaRendering, String coloreRendering) {
		this.id = id;
		this.nomeTipo = nomeTipo;
		this.iconaRendering = iconaRendering;
		this.coloreRendering = coloreRendering;
	}

	public CsvChildElementTest() {
	}

	@CsvField(header = "id", order = 1)
	private String id;
	@CsvField(header = "nome tipo", order = 2)
	private String nomeTipo;

	@CsvField(header = "colore rendering", order = 3)
	private String coloreRendering;
	@CsvField(header = "icona rendering", order = 4)
	private String iconaRendering;
	

	public String getColoreRendering() {
		return coloreRendering;
	}

	public void setColoreRendering(String coloreRendering) {
		this.coloreRendering = coloreRendering;
	}

	public String getIconaRendering() {
		return iconaRendering;
	}

	public void setIconaRendering(String iconaRendering) {
		this.iconaRendering = iconaRendering;
	}

	public String getNomeTipo() {
		return nomeTipo;
	}

	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CsvChildElementTest that = (CsvChildElementTest) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

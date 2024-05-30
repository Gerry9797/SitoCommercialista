package com.commercialista.backend.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormateManager {
	
	public static String formattaImportoString(Double importo) {    // Creare un oggetto DecimalFormatSymbols con il separatore delle migliaia e delle decine desiderate
	    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
	    symbols.setGroupingSeparator('.');
	    symbols.setDecimalSeparator(',');
	    // Creare un oggetto DecimalFormat con il formato desiderato e i simboli personalizzati
	    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
	    // Ottenere la rappresentazione formattata come stringa
	    return decimalFormat.format(importo);}

}
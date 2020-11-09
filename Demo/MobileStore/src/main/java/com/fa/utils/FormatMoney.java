package com.fa.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatMoney {
	private static final Locale US_LOCALE = new Locale("en", "US");
	private static DecimalFormat df = new DecimalFormat("0.00");

	public static String formartToUS(float value) {
		return NumberFormat.getCurrencyInstance(US_LOCALE).format(value).substring(1);
	}

	
	public static float round(float price) {
		return Float.parseFloat(df.format(price));
	}

}

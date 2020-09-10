package com.fa.utils;

public class Word {
	/**
	 * get nWords from a String
	 * 
	 * @param origin
	 * @param nWords
	 * @return
	 */
	public static String getWords(String origin, int nWords) {
		if (origin == null || origin.equalsIgnoreCase("")) {
			return "";
		}

		String tmp = origin.trim();

		while (tmp.indexOf("  ") != -1) {
			tmp = tmp.replace("  ", " ");
		}
		int count = 0;
		int i;
		for (i = 0; i < tmp.length(); i++) {
			if (tmp.charAt(i) == ' ') {
				if (++count > nWords - 1) {
					break;
				}
			}
		}

		if (count < nWords) {
			return tmp;
		} else {
			return tmp.substring(0, i) + "...";
		}
	}
	
	public static String getChars(String origin, int nChar) {
		if (origin == null || origin.equalsIgnoreCase("")) {
			return "";
		}

		if(origin.length()<=nChar) {
			return origin;
		}
		

		return origin.substring(0, nChar) + "...";
	}
	
}

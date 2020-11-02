package com.fa.utils;

import java.util.Date;

public class TimeUtils {
	public static Date getPreTime() {
		return new Date(new Date().getTime() - 2592000000l);
	}
}

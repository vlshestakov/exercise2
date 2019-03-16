package ru.vlshestakov.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Utils {

	public static Date parseDate(String sdate) {
		DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(sdate);
		} catch (ParseException e) {
			fail("Ошибка чтения даты");
		}
		return date;
	}

	public static List<String> decodeList(List<String> list) {
		return list.stream().map(a -> {
			try {
				return java.net.URLDecoder.decode(a, StandardCharsets.UTF_8.name());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}



}

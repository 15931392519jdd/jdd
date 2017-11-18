package cn.smbms.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringDateFormat implements Converter<String, Date> {

	private String datePattern;

	public StringDateFormat(String date) {
		datePattern = date;
	}

	@Override
	public Date convert(String arg0) {
		System.err.println("参数:" + arg0);
		Date d = null;
		try {
			d = new SimpleDateFormat(datePattern).parse(arg0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

}

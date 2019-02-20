package com.it.zzg.common.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * 重写spring日期转换器，自定义日期转换器
 * 
 * @author Robert_Zhan
 */
public class MyCustomDateEditor extends PropertyEditorSupport {

	@Override
	@SuppressWarnings("static-access")
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			if (!StringUtils.hasText(text)) {
				setValue(null);
			} else {
				setValue(this.dateAdapter(text));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (value != null ? dateFormat.format(value) : "");
	}

	/**
	 * 字符串转日期适配方法
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static Date dateAdapter(String str) throws ParseException {
		Date date = null;

		if (StringUtils.isEmpty(str)) {
			// 判断是不是日期字符串，如Wed May 28 08:00:00 CST 2014
			if (str.contains("CST")) {
				date = new Date(str);
			} else {
				str = str.replace("年", "-").replace("月", "-").replace("日", "").replaceAll("/", "-")
						.replaceAll("\\.", "-").trim();
				String format = "";
				// 日期格式匹配
				if (Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*").matcher(str).matches()) {
					format = "yyyy-MM-dd";
				} else if (Pattern.compile("^[0-9]{4}-[0-9]{1}-[0-9]+.*||^[0-9]{4}-[0-9]+-[0-9]{1}.*").matcher(str)
						.matches()) {
					format = "yyyy-M-d";
				} else if (Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{2}.*").matcher(str).matches()) {
					format = "yy-MM-dd";
				} else if (Pattern.compile("^[0-9]{2}-[0-9]{1}-[0-9]+.*||^[0-9]{2}-[0-9]+-[0-9]{1}.*").matcher(str)
						.matches()) {
					format = "yy-M-d";
				}

				// 时间格式匹配
				if (Pattern.compile(".*[ ][0-9]{2}").matcher(str).matches()) {
					format += " HH";
				} else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}").matcher(str).matches()) {
					format += " HH:mm";
				} else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}").matcher(str).matches()) {
					format += " HH:mm:ss";
				} else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{0,3}").matcher(str).matches()) {
					format += " HH:mm:ss:sss";
				}

				// 判断当前格式化内容是否为空
				if (!StringUtils.isEmpty(format)) {
					date = new SimpleDateFormat(format).parse(str);
				} else {
					throw new RuntimeException("参数字符串" + str + ",格式存在问题，无法进行日期转换！");
				}
			}
			return date;
		} else {
			throw new RuntimeException("参数字符串" + str + "无法被转换为日期格式！");
		}
	}
}

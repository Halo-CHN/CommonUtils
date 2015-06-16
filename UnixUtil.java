package com.credithc.gedaiacquisition.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class UnixUtil {
	/**
	 * unix时间戳转换为dateFormat
	 * 
	 * @param beginDate
	 * @return
	 */
	public static String timestampToDate(String beginDate) {
		String flag = "";
		if (null != beginDate && !beginDate.equals("")) {
			if (!beginDate.equals("0")) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				flag = sdf.format(new Date(Long.parseLong(beginDate) * 1000));
			}
		}
		return flag;
	}

	/**
	 * 自定义格式时间戳转换
	 * 
	 * @param beginDate
	 * @return
	 */
	public static String timestampToDate(String beginDate, String format) {
		String flag = "";
		if (null != beginDate && !beginDate.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			flag = sdf.format(new Date(Long.parseLong(beginDate)));
		}
		return flag;
	}

	/**
	 * 将字符串转为时间戳
	 * 
	 * @param user_time
	 * @return
	 */
	public static String dateToTimestamp(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d;
		try {
			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			if (str.length() > 10)
				re_time = str.substring(0, 10);
			else {
				re_time = str;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return re_time;
	}
}

package com.credithc.gedaiacquisition.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringUtils {

	private StringUtils() {
		throw new AssertionError();
	}

	/**
	 * is null or its length is 0 or it is made by space
	 * 
	 * <pre>
	 * isBlank(null) = true;
	 * isBlank(&quot;&quot;) = true;
	 * isBlank(&quot;  &quot;) = true;
	 * isBlank(&quot;a&quot;) = false;
	 * isBlank(&quot;a &quot;) = false;
	 * isBlank(&quot; a&quot;) = false;
	 * isBlank(&quot;a b&quot;) = false;
	 * </pre>
	 * 
	 * @param str
	 * @return if string is null or its size is 0 or it is made by space, return
	 *         true, else return false.
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * is null or its length is 0
	 * 
	 * <pre>
	 * isEmpty(null) = true;
	 * isEmpty(&quot;&quot;) = true;
	 * isEmpty(&quot;  &quot;) = false;
	 * </pre>
	 * 
	 * @param str
	 * @return if string is null or its size is 0, return true, else return
	 *         false.
	 */
	public static boolean isEmpty(CharSequence str) {
		return (str == null || str.length() == 0);
	}

	/**
	 * compare two string
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 * @see ObjectUtils#isEquals(Object, Object)
	 */
	public static boolean isEquals(String actual, String expected) {
		return ObjectUtils.isEquals(actual, expected);
	}

	/**
	 * get length of CharSequence
	 * 
	 * <pre>
	 * length(null) = 0;
	 * length(\"\") = 0;
	 * length(\"abc\") = 3;
	 * </pre>
	 * 
	 * @param str
	 * @return if str is null or empty, return 0, else return
	 *         {@link CharSequence#length()}.
	 */
	public static int length(CharSequence str) {
		return str == null ? 0 : str.length();
	}

	/**
	 * null Object to empty string
	 * 
	 * <pre>
	 * nullStrToEmpty(null) = &quot;&quot;;
	 * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
	 * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStrToEmpty(Object str) {
		return (str == null ? "" : (str instanceof String ? (String) str : str
				.toString()));
	}

	/**
	 * capitalize first letter
	 * 
	 * <pre>
	 * capitalizeFirstLetter(null)     =   null;
	 * capitalizeFirstLetter("")       =   "";
	 * capitalizeFirstLetter("2ab")    =   "2ab"
	 * capitalizeFirstLetter("a")      =   "A"
	 * capitalizeFirstLetter("ab")     =   "Ab"
	 * capitalizeFirstLetter("Abc")    =   "Abc"
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalizeFirstLetter(String str) {
		if (isEmpty(str)) {
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
				: new StringBuilder(str.length())
						.append(Character.toUpperCase(c))
						.append(str.substring(1)).toString();
	}

	/**
	 * encoded in utf-8
	 * 
	 * <pre>
	 * utf8Encode(null)        =   null
	 * utf8Encode("")          =   "";
	 * utf8Encode("aa")        =   "aa";
	 * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
	 * </pre>
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 *             if an error occurs
	 */
	public static String utf8Encode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * encoded in utf-8, if exception, return defultReturn
	 * 
	 * @param str
	 * @param defultReturn
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return defultReturn;
			}
		}
		return str;
	}

	/**
	 * get innerHtml from href
	 * 
	 * <pre>
	 * getHrefInnerHtml(null)                                  = ""
	 * getHrefInnerHtml("")                                    = ""
	 * getHrefInnerHtml("mp3")                                 = "mp3";
	 * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
	 * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
	 * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
	 * </pre>
	 * 
	 * @param href
	 * @return <ul>
	 *         <li>if href is null, return ""</li>
	 *         <li>if not match regx, return source</li>
	 *         <li>return the last string that match regx</li>
	 *         </ul>
	 */
	public static String getHrefInnerHtml(String href) {
		if (isEmpty(href)) {
			return "";
		}

		String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
		Pattern hrefPattern = Pattern
				.compile(hrefReg, Pattern.CASE_INSENSITIVE);
		Matcher hrefMatcher = hrefPattern.matcher(href);
		if (hrefMatcher.matches()) {
			return hrefMatcher.group(1);
		}
		return href;
	}

/**
     * process special char in html
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
	public static String htmlEscapeCharsToString(String source) {
		return StringUtils.isEmpty(source) ? source : source
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
	}

	/**
	 * transform half width char to full width char
	 * 
	 * <pre>
	 * fullWidthToHalfWidth(null) = null;
	 * fullWidthToHalfWidth("") = "";
	 * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
	 * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String fullWidthToHalfWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == 12288) {
				source[i] = ' ';
				// } else if (source[i] == 12290) {
				// source[i] = '.';
			} else if (source[i] >= 65281 && source[i] <= 65374) {
				source[i] = (char) (source[i] - 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * transform full width char to half width char
	 * 
	 * <pre>
	 * halfWidthToFullWidth(null) = null;
	 * halfWidthToFullWidth("") = "";
	 * halfWidthToFullWidth(" ") = new String(new char[] {12288});
	 * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String halfWidthToFullWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == ' ') {
				source[i] = (char) 12288;
				// } else if (source[i] == '.') {
				// source[i] = (char)12290;
			} else if (source[i] >= 33 && source[i] <= 126) {
				source[i] = (char) (source[i] + 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * 字符串超过一定长度加省略号
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String spilt(String str, int size) {
		if (str != null && !str.equals("")) {
			if (str.length() > size) {
				str = str.substring(0, size) + " ...";
			}
			return str;
		}
		return str;
	}

	/**
	 * 金额格式化
	 * 
	 * @param s
	 *            要格式化的金额
	 * @param len
	 *            保留的小数位
	 * @return
	 */
	public static String spiltAmt(String s, int len) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		double num = Double.parseDouble(s);
		if (len == 0) {
			formater = new DecimalFormat("###,###");
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,##0.");
			for (int i = 0; i < len; i++) {
				buff.append("0");
			}
			formater = new DecimalFormat(buff.toString());
		}

		return formater.format(num);

	}

	/**
	 * 金额格式化
	 * 
	 * @param s
	 * @param len
	 * @return
	 */
	public static String spiltAmt_edit(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		double num = Double.parseDouble(s);

		StringBuffer buff = new StringBuffer();
		buff.append("###,##0.##");
		formater = new DecimalFormat(buff.toString());
		return formater.format(num);

	}

	/**
	 * 金额去格式化
	 * 
	 * @param s
	 * @return
	 */
	public static String delComma(String s) {
		String formatString = "";
		if (s != null && s.length() >= 1) {
			formatString = s.replaceAll(",", "");
		}

		return formatString;
	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 过滤url特殊字符
	 * 
	 * @param url
	 * @return
	 */
	public static String StringFilter(String url) {
		String regEx = "[`~!@#$%^&*()+=|{}'',\\[\\].<>/?~@#￥%……&*——+|{}”“'/]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(url);
		return m.replaceAll("").trim();
	}

	/**
	 * 字符串为空时的处理
	 * 
	 * @Description:
	 * @param str
	 */
	public static String getString(String str) {
		if (str == null || str.equals("null")) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 判断用户名的长度是否为最小6个字符,最大为16个字符 只能输入下划线,英文字符 不能输入中文
	 * 
	 * @param string
	 * @return
	 */
	public static Boolean isSixF(String string) {
		String str = "^[\\|\\-\\_a-zA-Z0-9]+$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(string);
		return m.matches();
	}

	/**
	 * 密码验证 密码为 6 到 16 英文字母，数字、符号； ^[A-Za-z0-9_?!@#$%]{6,16}$
	 * 
	 * @param string
	 * @return ^(?!(\d+|[a-zA-Z]+|[?!@#$%&]+)$)[A-Za-z0-9]{6,16}$
	 */
	public static Boolean isPsRight(String string) {
		String str = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![-+_!@#$%^&*()+|{}:<>?,.]+$)[0-9A-Za-z-+_!@#$%^&*()+|{}:<>?,.]{6,16}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(string);
		return m.matches();
	}

	/**
	 * 判断是否为空
	 * 
	 * @Description:
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 现在号码段分配如下：
	 * 
	 * 移动： 139 138 137 136 135 134 147 150 151 152 157 158 159 182 183 187 188
	 * 
	 * 联通： 130 131 132 155 156 185 186 145
	 * 
	 * 电信： 133 153 180 181 189 177
	 * 
	 * 参考：http://www.jihaoba.com/tools/?com=haoduan
	 * 
	 * @Description:
	 * @param number
	 * @return
	 */
	public static boolean isPhoneNumberFormat(String number) {
		// ^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[^4,\\D]))\\d{8}$
		Pattern p = Pattern
				.compile("^0?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 组装url请求参数
	 * 
	 * @param parameterMap
	 * @return
	 */
	public static String assembleUrlParameter(Map<String, String> parameterMap) {
		StringBuffer buffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator<Entry<String, String>> iter = parameterMap.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				buffer.append("&");
				buffer.append(entry.getKey().toString());
				buffer.append("=");
				try {
					buffer.append(URLEncoder.encode(
							entry.getValue().toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}
		}
		return buffer.toString();
	}

	/**
	 * 通过{n},格式化.
	 * 
	 * @param src
	 * @param objects
	 * @return
	 */
	public static String format(String src, Object... objects) {
		int k = 0;
		for (Object obj : objects) {
			src = src.replace("{" + k + "}", obj.toString());
			k++;
		}
		return src;
	}

	public static boolean isChinese(String src) {
		String regx = "[\u4e00-\u9fa5]";
		return src.matches(regx);
	}

	/**
	 * 是否不为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

	/**
	 * 是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}

	/**
	 * 身份证号验证
	 */
	public static boolean personIdValidation(String text) {
		String regx = "[0-9]{17}X";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex);
	}

	/**
	 * 过滤Editable空格
	 * 
	 * @param edit
	 */
	public static void inputFilterSpace(final Editable edit) {
		edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16),
				new InputFilter() {

					private char results[];

					@Override
					public CharSequence filter(CharSequence source, int start,
							int end, Spanned dest, int dstart, int dend) {
						if (source.length() < 1) {
							return null;
						} else {
							char temp[] = (source.toString()).toCharArray();
							results = new char[temp.length];
							for (int i = 0, j = 0; i < temp.length; i++) {
								if (temp[i] == ' ') {
									continue;
								} else {
									results[j++] = temp[i];
								}
							}
						}
						return String.valueOf(results).trim();
					}

				} });
	}
}

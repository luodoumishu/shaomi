package com.xjj.cms.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.xjj.cms.base.VO.CmsCC;

/**
 * @Description: 工具类
 * @author fengjunkong
 * @created 2014-9-4 下午5:23:41
 */
public class StringTools {

	/**
	 * 在参数后增加一些空格后返回
	 * 
	 * @param str
	 * @return
	 */
	public static String addSomeBlank(String str) {
		return str + "  ";
	}

	public static Integer[] changeStr2Int(String[] strArray) {
		if (strArray != null && strArray.length > 0) {
			int length = strArray.length;
			Integer[] intRtn = new Integer[length];
			for (int i = 0; i < length; i++) {
				intRtn[i] = Integer.valueOf(strArray[i]);
			}
			return intRtn;
		} else {
			return null;
		}
	}

	public static Integer formatStr2Int(String str) {
		Integer rtn;
		try {
			rtn = Integer.valueOf(str);
		} catch (Exception e) {
			rtn = 0;
		}
		return rtn;
	}

	public static Double formatStr2Dbl(String str) {
		Double rtn;
		try {
			rtn = Double.valueOf(str);
		} catch (Exception e) {
			rtn = 0.0;
		}
		return rtn;
	}

	public static Float formatStr2Float(String str) {
		Float rtn;
		try {
			rtn = Float.valueOf(str);
		} catch (Exception e) {
			rtn = 0.0f;
		}
		return rtn;
	}

	public static void strArrayCompare(String[] a, String[] b) {
		if (a != null && b != null) {
			for (int i = 0; i < a.length; i++) {
				String strA = a[i];
				for (int j = 0; j < b.length; j++) {
					String strB = b[j];
					if (strA.equals(strB)) {
						a[i] = "0";
						b[j] = "0";
						break;
					}
				}
			}
		}
	}

	public static String formatNull2Blank(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	public static int compareStrInStrArray(String source, String[] array) {
		int rtn = -1;
		for (int i = 0; i < array.length; i++) {
			String str = array[i];
			if (source.equals(str)) {
				rtn = i;
				break;
			}
		}
		return rtn;
	}

	/**
	 * 将str作为源字符串，使用map里的键值对对str里的相应的键进行值的替换
	 * 
	 * @param str
	 * @param params
	 */
	public static String setParameters(String str, Map params) {
		Set keys = params.keySet();
		StringTools st = new StringTools();
		for (Object key : keys) {
			String regx = "\\x24\\x7b" + (String) key + "\\x7d";
			String value = (String) params.get(key);
			str = st.strMatcher(str, regx, value);
		}
		return str;
	}

	/**
	 * 将str作为源字符串，对key value进行替换
	 * 
	 * @param str
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setParameters(String str, String key, String value) {
		Map params = new HashMap();
		params.put(key, value);
		return setParameters(str, params);
	}

	/**
	 * 去掉给定字符串末尾的指定的字符串
	 * 
	 * @param sourceStr
	 * @param removeStr
	 */
	public static String removeEndCharBy(String sourceStr, String removeStr) {
		if (removeStr.length() > sourceStr.length()) {
			return sourceStr;
		} else {
			String endStr = sourceStr
					.substring(sourceStr.length() - removeStr.length(),
							sourceStr.length());
			if (endStr.equals(removeStr)) {
				return sourceStr.substring(0,
						sourceStr.length() - removeStr.length());
			} else {
				return sourceStr;
			}
		}
	}

	private String strMatcher(String str, String regx, String value) {
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(str);
		return m.replaceAll(value);
	}

	public static String replaceBlankByAdd(String str) {
		String[] temp = str.replaceAll(" +", " ").split(" ");
		int length = temp.length;
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < length - 1; i++) {
			if (StringUtils.isNotBlank(temp[i])) {
				strbuf.append(temp[i]).append("+");
			}
		}
		strbuf.append(temp[length - 1]);
		return strbuf.toString();
	}

	public static String htmlToText(String content) {
		Pattern p = null;
		Matcher m = null;
		String value = null;// 去掉<>标签
		p = Pattern.compile("(<[^>]*>)");
		m = p.matcher(content);
		String temp = content;
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, "");
		}
		// 去掉换行或回车符号
		p = Pattern.compile("(\r+|\n+)");
		m = p.matcher(temp);
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, "");
		}
		return temp.replaceAll("&nbsp;", "");
	}

	public final static String clob2String(Clob content) {

		try {
			InputStream input = content.getAsciiStream();
			int len = (int) content.length();
			byte[] by = new byte[len];
			int i;
			while (-1 != (i = input.read(by, 0, by.length))) {
				input.read(by, 0, i);
			}
			String rtn = new String(by, "utf-8");
			rtn = content.getSubString((long) 1, (int) content.length());
			return rtn;
		} catch (Exception e) {
			return "";
		}

	}

	public static boolean StringFilter(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException,
			ServletException {

		boolean keyWordBlankhasKey = false;
		boolean keyWordNoBlankhasKey = false;

		int keyWordNoBlank_length = CmsCC.keyWordNoBlank.length;
		int keyWordBlank_length = CmsCC.keyWordBlank.length;

		Map<String, String[]> paramMap = httpRequest.getParameterMap();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			int values_length = values.length;
			for (int k = 0; k < values_length; k++) {
				String value = values[k].toLowerCase().trim();
				if (!key.equals("method")) {
					for (int j = 0; j < keyWordNoBlank_length; j++) {
						if (value.indexOf(CmsCC.keyWordNoBlank[j]) > -1) {
							keyWordNoBlankhasKey = true;
							break;
						}
					}
					if (keyWordNoBlankhasKey == false) {
						String[] v = value.replaceAll("%20", " ").split("\\s+");
						int v_length = v.length;
						for (int i = 0; i < v_length; i++) {
							for (int j = 0; j < keyWordBlank_length; j++) {
								if (v[i].equals(CmsCC.keyWordBlank[j])) {
									keyWordBlankhasKey = true;
									break;
								}
							}
						}
					}
				}
			}
			if (keyWordBlankhasKey || keyWordNoBlankhasKey) {
				break;
			}
		}

		if (keyWordBlankhasKey || keyWordNoBlankhasKey) {
			return false;
		}
		return false;

	}

	public static void main(String[] args) throws Exception {

		String a = "abcd1234efgh1234aBCd";
		int size = a.toLowerCase().indexOf("abcd");
		while (size > -1) {
			String b = a.substring(size, size + 4);
			a = a.replaceAll(b, "");
			System.out.println(a);
			size = a.toLowerCase().indexOf("abcd");

		}

	}
}

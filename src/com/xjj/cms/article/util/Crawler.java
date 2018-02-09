package com.xjj.cms.article.util;

/**
 * Crawler.java
 * @author yeyunfeng 2012-11-13 上午9:33:56 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.Assert;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @author yeyunfeng
 * 
 */
public class Crawler {

	/**
	 * 根据标签获取NodeList
	 * 
	 * @author yeyunfeng 2012-11-13 下午12:12:05
	 * @param pageUrl
	 *            网址
	 * @param strTag
	 *            多个垂直标签
	 * @return
	 * @throws ParserException
	 * @throws Exception
	 * 
	 */
	public static NodeList getNodeList(String pageContents, String strTag,String charset)
			throws Exception {
		NodeList nodelist = null;
		try {
			if (pageContents != null) {
				pageContents = removeRemark(pageContents);
				Parser parser = Parser.createParser(pageContents, charset);
				NodeFilter filter = new TagNameFilter(strTag);
				nodelist = parser.parse(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("解析异常" + e);
		}
		return nodelist;
	}

	/**
	 * 从NodeList根据标签名称过滤
	 * 
	 * @author yeyunfeng 2012-11-15 上午10:06:30
	 * @param nodeList
	 * @param tagName
	 * @return
	 * @throws Exception
	 * 
	 */
	public static NodeList getByTagName(NodeList nodeList, String tagName)
			throws Exception {
		NodeFilter tagFilter = new TagNameFilter(tagName);
		return nodeList.extractAllNodesThatMatch(tagFilter, true);
	}

	
	public static NodeList getByTagName(Node node,String tabName,String charset)
			throws Exception {
		Assert.assertNotNull(node);
		Parser parser = Parser.createParser(node.toHtml(), charset);
		NodeFilter filter = new TagNameFilter(tabName);
		return parser.parse(filter);
	}
	
		
	/**
	 * 获取html页面的编码
	 * 
	 * @author yeyunfeng 2012-11-14 上午9:39:39
	 * @param content
	 * @return
	 * 
	 */
	protected static String getCharset(String content) {
		final String CHARSET_STRING = "charset";
		int index;
		String ret = "gb2312";
		if (null != content) {
			index = content.indexOf(CHARSET_STRING);
			if (index != -1) {
				content = content.substring(index + CHARSET_STRING.length())
						.trim();
				if (content.startsWith("=")) {
					content = content.substring(1).trim();
					index = content.indexOf("\"");
					if (index != -1)
						content = content.substring(0, index);
					// 去掉双引号
					if (content.startsWith("\"") && content.endsWith("\"")
							&& (1 < content.length()))
						content = content.substring(1, content.length() - 1);

					// 去掉单引号
					if (content.startsWith("'") && content.endsWith("'")
							&& (1 < content.length()))
						content = content.substring(1, content.length() - 1);

					ret = findCharset(content, ret);
				}
			}
		}
		return ret;
	}

	protected static String findCharset(String name, String fallback) {
		String ret;
		try {
			Class cls;
			Method method;
			Object object;
			cls = Class.forName("java.nio.charset.Charset");
			method = cls.getMethod("forName", new Class[] { String.class });
			object = method.invoke(null, new Object[] { name });
			method = cls.getMethod("name", new Class[] {});
			object = method.invoke(object, new Object[] {});
			ret = (String) object;
		} catch (ClassNotFoundException cnfe) {
			ret = name;
		} catch (NoSuchMethodException nsme) {
			ret = name;
		} catch (IllegalAccessException ia) {
			ret = name;
		} catch (InvocationTargetException ita) {
			ret = fallback;
			System.out
					.println("unable to determine cannonical charset name for "
							+ name + " - using " + fallback);
		}
		return ret;
	}

	/**
	 * 移除注释
	 * 
	 * @author yeyunfeng 2012-11-14 下午4:05:02
	 * @param pageContent
	 * @return
	 * @throws Exception
	 * 
	 */
	protected static String removeRemark(String pageContent) throws Exception {
		Assert.assertNotNull(pageContent);
		String ramarkStart = "<!--";
		String ramarkEnd = "-->";
		int startIndex = pageContent.indexOf(ramarkStart);
		int endIndex = pageContent.indexOf(ramarkEnd, startIndex);
		if (-1 != startIndex && -1 != endIndex) {
			pageContent = pageContent.substring(0, startIndex)
					+ pageContent.substring(endIndex + ramarkEnd.length(),
							pageContent.length());
			pageContent = removeRemark(pageContent);
		}
		return pageContent;
	}

	/**
	 * 统计某个字符串出现的次数
	 * 
	 * @author yeyunfeng 2012-11-16 下午4:54:12
	 * @param sourceStr
	 * @param argetStr
	 * @param count
	 * @return
	 * @throws Exception
	 * 
	 */
	public static int countStr(String sourceStr, String argetStr, int count)
			throws Exception {
		int index = sourceStr.indexOf(argetStr);
		if (-1 != index) {
			sourceStr = sourceStr.substring(index + argetStr.length());
			count++;
			return countStr(sourceStr, argetStr, count);
		}
		return count;
	}

	/**
	 * 
	 * @author yeyunfeng 2012-11-24 上午10:32:53
	 * @param urlStr
	 * @param charset 字符编码 默认gb2312
	 * @return
	 * @throws Exception
	 * 
	 */
	public static String connect(String urlStr,String charset) throws Exception {
		if (null == charset) {
			charset = "gb2312";
		}
		BufferedReader br = null;
		String response = "", brLine = "";
		HttpURLConnection urlconn = null;
		URL url = null;
		try {
			url = new URL(urlStr);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setRequestMethod("GET");
			// urlconn.setUseCaches(false); // Post can not user cache
			// urlconn.setDoOutput(true); // set output from urlconn
			// urlconn.setDoInput(true); // set input from urlconn

			urlconn.setConnectTimeout(120000);

			br = new BufferedReader(new InputStreamReader(
					urlconn.getInputStream(), charset));
			while ((brLine = br.readLine()) != null) {
				response = (new StringBuilder(String.valueOf(response)))
						.append(brLine).toString();
			}
			final String MSG_400 = "访问的网页不存在或者已经被删除";
			if (response.indexOf(MSG_400) >= 0) {
				return null;
			}
		} catch (ConnectException e) {
			System.out.println(("请求" + urlStr + "异常   " + e.getMessage()));
			throw e;
		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				throw e;
			}
			urlconn.disconnect();
		}
		return response;
	}
}

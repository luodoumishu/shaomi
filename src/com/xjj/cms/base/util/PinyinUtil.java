package com.xjj.cms.base.util;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * <p>
 * 拼音帮助类，提供由汉字转化为拼音的方法
 * </p>
 * @author yeyunfeng 2011-7-13 下午04:08:36
 * @version V1.0
 */
public class PinyinUtil {
	
	private HanyuPinyinOutputFormat outputFormat;
	
	public PinyinUtil() {
		outputFormat = new HanyuPinyinOutputFormat();
		// 默认输出字符为小写
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 默认无注音
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// 默认当碰到'ü'时以'v'代替
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	}
	
	public PinyinUtil(HanyuPinyinOutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}
	
	/**
	 * 单个汉字转换成拼音（忽略多音字)
	 * @author yeyunfeng 2015年5月22日  下午2:42:55
	 * @param ch
	 * @return
	 *
	 */
	public String char2Pinyin(char ch) {
		return char2Pinyin(ch, false, null);
	}
	
	/**
	 * 将单个汉字转换成拼音
	 * @author yeyunfeng 2015年5月22日  下午2:43:13
	 * @param ch
	 * @param isPolyphone
	 * @param separator
	 * @return
	 *
	 */
	public String char2Pinyin(char ch, boolean isPolyphone, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		
		// 如果c是中文
		if (ch > 128) {
			try {
				String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch, outputFormat);
				if (null == pinyinArray || pinyinArray.length <= 0) {
					return stringBuffer.toString();
				}
				// 如果需要返回多音字的所有拼音
				if (isPolyphone) {
					for (int i = 0; i < pinyinArray.length; i++) {
						stringBuffer.append(pinyinArray[i]);
						if (pinyinArray.length != i + 1 && null != separator) {
							stringBuffer.append(separator);
						}
					}
				} else {
					stringBuffer.append(pinyinArray[0]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			stringBuffer.append(ch);
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 多汉字转换为拼音首字母
	 * @author yeyunfeng 2015年5月22日  下午2:44:11
	 * @param str
	 * @return
	 *
	 */
	public String string2Initial(String str) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strChs = str.toCharArray();
		for(int i = 0; i < strChs.length; i++) {
			String pinyin = char2Pinyin(strChs[i]);
			if (null != pinyin && !pinyin.isEmpty()) {
				stringBuffer.append(pinyin.charAt(0));
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 多汉字转为拼音
	 * @author yeyunfeng 2015年5月22日  下午2:58:49
	 * @param str
	 * @return
	 *
	 */
	public String string2Pinyin(String str) {
		return string2Pinyin(str, null);
	}
	
	/**
	 * 多汉字转为拼音
	 * @author yeyunfeng 2015年5月22日  下午2:59:00
	 * @param str
	 * @param separator
	 * @return
	 *
	 */
	public String string2Pinyin(String str, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strChs = str.toCharArray();
		for(int i = 0; i < strChs.length; i++) {
			stringBuffer.append(char2Pinyin(strChs[i]));
			if(strChs.length != i + 1 && null != separator) {
				stringBuffer.append(separator);
			}
		}
		return stringBuffer.toString();
	}
	
	private static PinyinUtil pinyinUtil;
	
	public static PinyinUtil getInstance() {
		if (pinyinUtil == null) {
			pinyinUtil = new PinyinUtil();
		}
		return pinyinUtil;
	}
}

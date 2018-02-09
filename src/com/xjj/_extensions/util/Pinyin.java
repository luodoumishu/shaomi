package com.xjj._extensions.util;

import com.xjj.jdk17.utils.StringUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin {
	
    /**
    * 汉字转拼音的方法
    * @param name 汉字
    * @return 拼音
    */
	public static String getHeadLetters(String name){
    	String pinyinName = "";
    	String headPinYin = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                    headPinYin += pinyinName.substring(0,1).toUpperCase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        }
        return headPinYin;
    }
	
	public static String getPinYin(String src) { 
        char[] t1 = null; 
        t1 = src.toCharArray();  
        // System.out.println(t1.length); 
        String[] t2 = new String[t1.length]; 
        // System.out.println(t2.length); 
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length; 
        try {  
            for (int i =0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // System.out.println(t1[i]); 
               if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) { 
                   t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
                    t4 += t2[0]+" ";// 取出该汉字全拼的第一种读音并连接到字符串t4后 
               } else { 
                   // 如果不是汉字字符，间接取出字符并连接到字符串t4后 
                    t4 += Character.toString(t1[i]);  
                }  
            }  
       } catch (BadHanyuPinyinOutputFormatCombination e) { 
           e.printStackTrace();  
        }  
       return t4;  
    } 
	
	public static boolean isEnglishLetter(char c) {
			
			int i = (int) c;
			if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
				return true;
			} else {
				return false;
			}
	}

	public static String getEngHeadChat(String word)
    {
		char[] chatArray = word!=null?word.toCharArray():null; 
		
		if(chatArray!=null)
		{
			StringBuffer sb = new StringBuffer();
			for(char c:chatArray)
			{
				if(isEnglishLetter(c))
					sb.append(c);
			}
			return StringUtil.isEmpty(sb.toString())?null:sb.toString().toUpperCase();
		}
		return null;
    }
	
	  
	public static void main(String[] args) {
		System.out.println("=============="+getHeadLetters("陈石伟"));
		System.out.println("=============="+getPinYin("陈石伟"));
		System.out.println("=============="+getEngHeadChat("d陈石伟c"));
		
		System.out.println("=============="+getHighLightCharsByLetter("陈石伟","W"));
	}
	
	
	public static String getHighLightCharsByLetter(String name,String letters)
	{
		String pinyinOfName = Pinyin.getHeadLetters(name);
		int beg = pinyinOfName.indexOf(letters);
		if(beg!=-1)
		{
			return name.substring(beg, (beg+letters.length()));
		}
		return null;
	}
	
	

}

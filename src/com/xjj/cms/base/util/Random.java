package com.xjj.cms.base.util;


import java.math.BigDecimal;

public class Random {
	
	/**
	 * 根据参数获取与参数位数相同的随机数
	 * 确保了5位数的随机数
	 * @param number
	 * @return
	 */
	public static BigDecimal getIntRandom(int num){
		BigDecimal minBd = new BigDecimal(0.1);
		BigDecimal bd = null;
		while(bd == null || bd.compareTo(minBd) == -1){
			bd = new BigDecimal(Math.random());
		}
		return bd.movePointRight(num);
	}
	
	/**
	 * 根据参数获取与参数位数相同的随机数
	 * 确保了5位数的随机数
	 * @param number
	 * @return
	 */
	public static String getStrRandom(int num){
		return Random.getIntRandom(num).toBigInteger().toString();
	}
	
	public static void main(String[] args){
		System.out.println(Random.getStrRandom(22));
		
	}

}

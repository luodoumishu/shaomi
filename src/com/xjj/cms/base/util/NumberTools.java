package com.xjj.cms.base.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;


/**
 * 数字计算公共类
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 * @author fengjunkong
 *
 */
public class NumberTools {

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 15;
    //这个类不能实例化
    private NumberTools(){
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    } 
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后15位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    
    /**
     * 2个bigDecimal相除
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(BigDecimal v1,BigDecimal v2){
        return v1.divide(v2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
    public static String formatInt2StrDefaltBlank(Object obj){
    	String strRtn = "";
    	if(obj != null){
    		try{
    			strRtn = ((Integer)obj).toString();
    		}catch(Exception e){
    			
    		}
    	}
    	return strRtn;
    }
    
    
    public static String formatInt2StrDefaultZero(Object obj){
    	String strRtn = "0";
    	if(obj != null){
    		try{
    			strRtn = ((Integer)obj).toString();
    		}catch(Exception e){
    			
    		}
    	}
    	return strRtn;
    }
    
    /**
     * 判断2个整数是否在5的同一范围内，即：1，2处于0-4范围内，
     * 范围定义：与5的商相同的则为同一范围。例如：0-4，5-9，10-14
     * @param num1
     * @param num2
     * @return
     */
    public static boolean judge2IntIn5Round(int num1 , int num2){
    	if(num1/5 == num2/5){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 返回一个数组，此数组为与5的商相同的5个数的最小值和最大值
     * 例如：number=1，返回0，4
     * number=12，返回10，14
     * @param number
     * @return
     */
    public static Integer[] arrayIn5ByArg(Integer number){
    	if(number < 0){
    		return null;
    	}else{
    		Integer[] rtnInt = new Integer[2];
    		int shang = number / 5;
    		rtnInt[0] = shang * 5;
    		rtnInt[1] = shang * 5 + 4;
    		return rtnInt;
    	}
    }

    public static Integer formatStr2IntDefaultNull(String number){
		try{
			return Integer.valueOf(number);
		}catch(Exception e){
			return null;
		}
    }
	
    public static Integer formatStr2IntDefaultZero(String number){
		try{
			return Integer.valueOf(number);
		}catch(Exception e){
			return 0;
		}
    }
	
    public static Integer formatObject2IntDefaultZeroNoExp(Object number){
		return formatObject2IntDefaultParamNoExp(number , 0);
    }
    
    
    public static Integer formatObject2IntDefaultParamNoExp(Object number , Integer param){
    	Integer returnInt = param;
		try{
			if(number != null){
				if(number instanceof Integer){
					returnInt = (Integer)number;
				}else if(number instanceof String){
					returnInt = Integer.valueOf((String)number);
				}
			}
		}catch(Exception e){
			
		}
		return returnInt;
    }
    
    
    public static String formatInt2StrDefaultBlank(Object obj){
    	String strRtn = "";
    	if(obj != null){
    		try{
    			strRtn = ((Integer)obj).toString();
    		}catch(Exception e){
    			
    		}
    	}
    	return strRtn;
    }
    
    
    /**
     * 判断传入的参数是否为数字
     * @param obj
     * @return
     */
    public static boolean isNumber(String str){
    	try{
    		Integer.valueOf(str);
    		return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
    
    public static boolean isNotNumber(String str){
    	return !isNumber(str);
    }
    
	public static void main(String[] args){
		NumberTools nt = new NumberTools();
        //直接使用浮点数进行计算，得到的结果是有问题的
        //System.out.println(0.01+0.05);
        //使用了BigDecimal类进行计算后，可以做到精确计算
        //System.out.println(nt.add(0.05, 0.01));
		
		//System.out.println(nt.formatObject2IntDefaultZeroNoExp("1"));
		System.out.println(nt.judge2IntIn5Round(1,6));
		
	}
}

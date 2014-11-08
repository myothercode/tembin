/** Created by flym at 13-3-1 */
package com.base.utils.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额工具类，用于封装金额处理
 *
 */
public class MyNumberUtils {
	private static final RoundingMode roundingMode_halfUp = RoundingMode.HALF_UP;
	public static final int SCALE_2 = 2;
	public static final int SCALE_4 = 4;
	public static final int SCALE_10 = 10;

	/** 判断金额是否输入或者为0 */
	public static boolean isNullOrZero(BigDecimal bigDecimal) {
		if(bigDecimal == null)
			return true;
		String str = bigDecimal.toString();
		int length = str.length();
		for(int i = 0;i < length;i++) {
			char c = str.charAt(i);
			if(c != '0' && c != '.')
				return false;
		}
		return true;
	}

	/** 判断金额是否和指定int值相等 */
	public static boolean equals(BigDecimal bigDecimal, int value) {
		return bigDecimal.doubleValue() == (double) value;
	}

	/** 判断金额是否和指定long值相等 */
	public static boolean equals(BigDecimal bigDecimal, long value) {
		return bigDecimal.doubleValue() == (double) value;
	}

	/** 判断两个bigdecimal是否为0 */
	public static boolean equals(BigDecimal bigDecimalA, BigDecimal bigDecimalB) {
		return bigDecimalA.compareTo(bigDecimalB) == 0;
	}

	/** 判断两个bigdecimal在保留2位小数时是否相等 */
	public static boolean equalsScale2(BigDecimal bigDecimalA, BigDecimal bigDecimalB) {
		return toBigDecimal(bigDecimalA, 2).compareTo(toBigDecimal(bigDecimalB, 2)) == 0;
	}

	/** 判断两个bigdecimal在保留4位小数时是否相等 */
	public static boolean equalsScale4(BigDecimal bigDecimalA, BigDecimal bigDecimalB) {
		return toBigDecimal(bigDecimalA, 4).compareTo(toBigDecimal(bigDecimalB, 4)) == 0;
	}

	/** 金额相加 */
	public static BigDecimal add(BigDecimal bigDecimal, int value) {
		return bigDecimal.add(BigDecimal.valueOf(value));
	}

	/** 金额相加 */
	public static BigDecimal add(BigDecimal bigDecimal, long value) {
		return bigDecimal.add(BigDecimal.valueOf(value));
	}

	/** 金额相加 */
	public static BigDecimal add(BigDecimal bigDecimal, BigDecimal value, BigDecimal... others) {
		java.math.BigDecimal returnValue = bigDecimal.add(value);
		for(java.math.BigDecimal v : others) {
			returnValue = returnValue.add(v);
		}
		return returnValue;
	}

	/** 金额相减 */
	public static BigDecimal minus(BigDecimal bigDecimal, int value) {
		return bigDecimal.subtract(BigDecimal.valueOf(value));
	}

	/** 金额相减 */
	public static BigDecimal minus(BigDecimal bigDecimal, long value) {
		return bigDecimal.subtract(BigDecimal.valueOf(value));
	}

	/** 金额相减 */
	public static BigDecimal minus(BigDecimal bigDecimal, BigDecimal value, BigDecimal... others) {
		BigDecimal returnValue = bigDecimal.subtract(value);
		for(BigDecimal v : others) {
			returnValue = returnValue.subtract(v);
		}
		return returnValue;
	}

	/** 金额乘法 */
	public static BigDecimal multiple(BigDecimal bigDecimal, int value) {
		return bigDecimal.multiply(BigDecimal.valueOf(value));
	}

	/** 金额乘法 */
	public static BigDecimal multiple(BigDecimal bigDecimal, long value) {
		return bigDecimal.multiply(BigDecimal.valueOf(value));
	}

	/** 金额乘法 */
	public static BigDecimal multiple(BigDecimal bigDecimal, BigDecimal other) {
		return bigDecimal.multiply(other);
	}

	/** 金额除法,标量为10，舍入为四舍五入 */
	public static BigDecimal divide(BigDecimal bigDecimal, int value) {
		return bigDecimal.divide(BigDecimal.valueOf(value), SCALE_10, roundingMode_halfUp);
	}

	/** 金额除法,标量为10，舍入为四舍五入 */
	public static BigDecimal divide(BigDecimal bigDecimal, long value) {
		return bigDecimal.divide(BigDecimal.valueOf(value), SCALE_10, roundingMode_halfUp);
	}

	/** 金额除法,标量为10，舍入为四舍五入 */
	public static BigDecimal divide(BigDecimal bigDecimal, BigDecimal other) {
		return bigDecimal.divide(other, SCALE_10, roundingMode_halfUp);
	}

	/** 将数值转成指定标度的数据,并以四舍五入的模式进行舍入 */
	public static BigDecimal toBigDecimal(BigDecimal bigDecimal, int maxScale) {
		return bigDecimal.setScale(maxScale, roundingMode_halfUp);
	}

	/** 将数值的末尾去0 */
	public static BigDecimal toBigDecimalTrim(BigDecimal bigDecimal) {
		String str = toString(bigDecimal, null);
		if(str == null)
			return BigDecimal.ZERO;
		if(str.lastIndexOf('.') == -1)
			return bigDecimal;
		char[] chars = str.toCharArray();
		int i = chars.length - 1;
		char c = '1';
		while(i > 0) {
			c = chars[i];
			if(c != '0')
				break;
			i--;
		}
		if(c == '.')
			i--;
		//这里的i表示第一个不为0的位置，因此需要将i+1表示相应的长度
		return new BigDecimal(chars, 0, i + 1);
	}

	/** 将金额转换为显示值 */
	public static String toString(BigDecimal bigDecimal) {
		return bigDecimal.toString();
	}

	/** 将金额转换为显示值,并以2位小数进行显示 */
	public static String toStringScale2(BigDecimal bigDecimal) {
		return toString(toBigDecimal(bigDecimal, SCALE_2));
	}

	/** 将金额转换为显示值,最多以2位小数进行显示 */
	public static String toStringScale2Trim(BigDecimal bigDecimal) {
		return toString(toBigDecimalTrim(toBigDecimal(bigDecimal, SCALE_2)));
	}

	/** 将金额转换为显示值,并以4位小数进行显示 */
	public static String toStringScale4(BigDecimal bigDecimal) {
		return toString(toBigDecimal(bigDecimal, SCALE_4));
	}

	/** 将金额转换为显示值,最多以4位小数进行显示 */
	public static String toStringScale4Trim(BigDecimal bigDecimal) {
		return toString(toBigDecimalTrim(toBigDecimal(bigDecimal, SCALE_4)));
	}

	/** 将金额转换为显示值,如果为0，则按照指定值显示 */
	public static String toString(BigDecimal bigDecimal, String zeroValue) {
		boolean isZero = isNullOrZero(bigDecimal);
		return isZero ? zeroValue : bigDecimal.toString();
	}

	/** 将int值转换为bigDecimal */
	public static BigDecimal toBigDecimal(int value) {
		return BigDecimal.valueOf(value);
	}

	/** 将long值转换为bigDecimal */
	public static BigDecimal toBigDecimal(long value) {
		return BigDecimal.valueOf(value);
	}


    /**String转为double，并只保留两位小数*/
    public static Double str2DoubleAndSCALE2(String num){
        BigDecimal bigDecimal=new BigDecimal(num);
        Double d=bigDecimal.setScale(SCALE_2,roundingMode_halfUp).doubleValue();
        return d;
    }

}

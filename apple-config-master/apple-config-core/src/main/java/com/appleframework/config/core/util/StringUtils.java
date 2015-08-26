package com.appleframework.config.core.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static boolean isNullOrEmpty(String s) {
		if( s == null || s.trim().length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isMac(String mac) {
		String regex = "[a-f\\d]{2}[a-f\\d]{2}[a-f\\d]{2}[a-f\\d]{2}[a-f\\d]{2}[a-f\\d]{2}";
		if(mac != null && mac.toLowerCase().matches(regex) ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 按默认样式(yyyy-MM-dd)格式化日期
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		if (date!=null) {
			DateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sf.format(date);
		}else {
			return "";
		}
		
	}
	
	/**
	 * 格式化日期
	 * @param date
	  * @param style 显示的样式 如:yyyy-MM-dd HH:mm
	 * @return
	 */
	public static String dateFormat(Date date,String style) {
		if (date!=null) {
			DateFormat sf=new SimpleDateFormat(style);
			return sf.format(date);
		}else {
			return "";
		}
	}
	
	/**
	 * 去除java字符串里面的特殊字符
	 * @param 
	  * @param 
	 * @return
	 */
	public String StringFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 判断指定字符串是否全部为数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDigit(String s) {
		if (isNullOrEmpty(s)) {
			return false;
		}

		for (int i = s.length() - 1; i >= 0; i--) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	// Trim
    //-----------------------------------------------------------------------
    /**
     * <p>Removes control characters (char &lt;= 32) from both
     * ends of this String, handling {@code null} by returning
     * {@code null}.</p>
     *
     * <p>The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #strip(String)}.</p>
     *
     * <p>To trim your choice of characters, use the
     * {@link #strip(String, String)} methods.</p>
     *
     * <pre>
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str  the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
    
    
 // 判断一个字符串是否null或""
 	public static boolean isEmpty(String str) {
 		if (str == null)
 			return true;
 		else if (str.length() == 0)
 			return true;
 		else if ("null".equals(str))
 			return true;
 		else if ("0".equals(str))
 			return true;
 		return false;
 	}
 	
 	public static boolean isEmptyString(String str) {
 		return (str == null) || (str.trim().length() == 0);
 	}

 	public static String replaceAll(String s, String oss, String nss) {
 		if (null == s || oss == null || nss == null) {
 			return s;
 		}
 		String rlt = s;
 		StringBuffer sb = new StringBuffer();
 		while (true) {
 			int idx = rlt.indexOf(oss);
 			if (idx < 0)
 				break;
 			sb.delete(0, sb.length());
 			if (idx > 0) {
 				sb.append(rlt.substring(0, idx));
 			}
 			sb.append(nss);
 			sb.append(rlt.substring(idx + oss.length()));
 			rlt = sb.toString();
 		}
 		return rlt;
 	}

 	/**
 	 * 字符串通过分隔符增加名值对
 	 * 
 	 * @param string
 	 * @param name
 	 * @param value
 	 * @param valueSeparate
 	 * @param paramSeparate
 	 * @return param
 	 */
 	public static String appendParam(String string, String name, String value,
 			String valueSeparate, String paramSeparate) {
 		StringBuffer sb = new StringBuffer();
 		if (null == string || "".equals(string)) {
 			sb.append(name);
 			sb.append(valueSeparate);
 			sb.append(value);
 		} else {
 			sb.append(string);
 			sb.append(paramSeparate);
 			sb.append(name);
 			sb.append(valueSeparate);
 			sb.append(value);
 		}
 		return sb.toString();
 	}

 	/**
 	 * 字符串通过分隔符及参数名查找参数值
 	 * 
 	 * @param string
 	 *            原字符串
 	 * @param name
 	 *            分隔名
 	 * @param valueSeparate
 	 *            值分隔符
 	 * @param paramSeparate
 	 *            参数分隔符
 	 * @return 从字符串里取出被分隔的串
 	 */
 	public static String getValueFromString(String string, String name,
 			String valueSeparate, String paramSeparate) {
 		String[] params = string.split(paramSeparate);
 		for (int i = 0; i < params.length; i++) {
 			String[] param = params[i].split(valueSeparate);
 			if (param != null && param.length > 0 && param[0].equals(name)) {
 				if (param.length > 1)
 					return param[1];
 				else
 					return null;
 			}
 		}
 		return null;
 	}

 	/**
 	 * 获取唯一的标识值
 	 * 
 	 * @return
 	 */
 	public static String getUUID() {
 		return UUID.randomUUID().toString();
 	}

 	/**
 	 * 根据用","隔开的字符Id转换成list
 	 * 
 	 * @param id
 	 * @return
 	 */
 	public static List<Integer> getListId(String id) {
 		String str[] = id.split(",");
 		List<Integer> list = new ArrayList<Integer>();
 		for (int i = 0; i < str.length; i++) {
 			list.add(Integer.parseInt(str[i]));
 		}
 		return list;
 	}

 	/**
 	 * 判断两个字符串是否相等
 	 * 
 	 * @param arg1
 	 * @param arg2
 	 * @return
 	 */
 	public static Boolean isEqualString(String arg1, String arg2) {
 		return arg1.equals(arg2);
 	}

 	/**
 	 * 格式化字符串
 	 * 
 	 * @param arg
 	 * @param objects
 	 * @return
 	 */
 	public static String formatterString(String arg, Object... objects) {
 		return MessageFormat.format(arg, objects);
 	}

		
}

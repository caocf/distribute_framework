package com.appleframework.oss.boss.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringBase {

	/**
	 * 判断变量是否为NULL或空
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-03
	 * @company: GLSX.
	 * @return
	 */
	public static boolean CheckStr(String str) {
		if (null != str) {
			if (!"".equals(str.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断数字是否为NULL及是否大于0
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-30
	 * @company: GLSX.
	 * @param ints
	 *            需要检测比较的数字
	 * @return
	 */
	public static boolean CheckInt(Integer ints) {
		if (null != ints) {
			if (0 < ints) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断数字是否为NULL及是否大于0
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-30
	 * @company: GLSX.
	 * @param ints
	 *            需要检测比较的数字
	 * @return
	 */
	public static boolean CheckDouble(Double dbles) {
		if (null != dbles) {
			if (0 < dbles) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断变量是否为NULL及
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-03
	 * @company: GLSX.
	 * @param str
	 *            需要检测比较的字符串
	 * @param value
	 *            用作比较的值
	 * @return
	 */
	public static boolean CheckStr(String str, String value) {
		if (null != str) {
			if (value.equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断变量是否为NULL或空
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-03
	 * @company: GLSX.
	 * @param array
	 *            用作比较的值
	 * @return
	 */
	public static boolean CheckArray(List<?> array) {
		if (null != array && 0 < array.size()) {
			return true;
		}
		return false;
	}

	public static boolean CheckStrArray(Object[] str) {
		if (null != str && 0 < str.length) {
			return true;
		}
		return false;
	}

	/**
	 * 根据查询标识返回一组开始，结束的时间日期，格式为：xxxx-xx-xx
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-07
	 * @company: GLSX.
	 * @param selValue
	 *            查询标识
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String[] getCombinationDate(String selValue) {
		// toDay 今天
		// yesterDay 昨天
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strdate;
		String[] strdates = new String[2];

		try {
			if (StringBase.CheckStr(selValue)) {

				if ("toDay".equals(selValue)) {// 当天时间
					calendar.setTime(date);
					calendar.add(calendar.DATE, 0);// 把日期往后增加一天.整数往后推,负数往前移动
					date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
					strdate = formatter.format(date);

					strdates[0] = strdate;
					strdates[1] = strdate;
				} else if ("yesterDay".equals(selValue)) {// 昨天时间
					calendar.setTime(date);
					calendar.add(calendar.DATE, -1);
					date = calendar.getTime();
					strdate = formatter.format(date);

					strdates[0] = strdate;
					strdates[1] = strdate;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return strdates;
		}

		return strdates;
	}

	/**
	 * 根据查询标识组合查询时间段
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-07
	 * @company: GLSX.
	 * @param field
	 *            组合内容
	 * @param selValue
	 *            查询标识
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String combinationDate(String field, String selValue) {
		// toDay 今天
		// yesterDay 昨天
		// sevenDays 最近7天
		// aMonth 最近一个月
		// month 本月

		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strdate;

		try {
			if (StringBase.CheckStr(selValue)) {// 当天时间
				if ("toDay".equals(selValue)) {
					calendar.setTime(date);
					calendar.add(calendar.DATE, 0);// 把日期往后增加一天.整数往后推,负数往前移动
					date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
					strdate = formatter.format(date);
					return field + " BETWEEN '" + strdate + " 00:00:00 ' AND '" + strdate + " 23:59:59' ";

				} else if ("yesterDay".equals(selValue)) {// 昨天时间
					calendar.setTime(date);
					calendar.add(calendar.DATE, -1);
					date = calendar.getTime();
					strdate = formatter.format(date);
					return field + " BETWEEN '" + strdate + " 00:00:00 ' AND '" + strdate + " 23:59:59' ";
				} else if ("sevenDays".equals(selValue)) {// 最近7天
					calendar.setTime(date);
					calendar.add(calendar.DATE, -6);
					date = calendar.getTime();
					strdate = formatter.format(date);

					date = new Date();
					Calendar bincalendar = new GregorianCalendar();
					bincalendar.setTime(date);
					bincalendar.add(bincalendar.DATE, 0);
					date = bincalendar.getTime();
					String endstrdate = formatter.format(date);

					return field + " BETWEEN '" + strdate + " 00:00:00 ' AND '" + endstrdate + " 23:59:59' ";
				} else if ("twoWeek".equals(selValue)) {// 最近两周
					calendar.setTime(date);
					calendar.add(calendar.DATE, -13);
					date = calendar.getTime();
					strdate = formatter.format(date);

					date = new Date();
					Calendar bincalendar = new GregorianCalendar();
					bincalendar.setTime(date);
					bincalendar.add(bincalendar.DATE, 0);
					date = bincalendar.getTime();
					String endstrdate = formatter.format(date);

					return field + " BETWEEN '" + strdate + " 00:00:00 ' AND '" + endstrdate + " 23:59:59' ";
				} else if ("aMonth".equals(selValue)) {// 最近一个月
					calendar.setTime(date);
					calendar.add(calendar.DATE, -29);
					date = calendar.getTime();
					strdate = formatter.format(date);

					date = new Date();
					Calendar bincalendar = new GregorianCalendar();
					bincalendar.setTime(date);
					bincalendar.add(bincalendar.DATE, 0);
					date = bincalendar.getTime();
					String endstrdate = formatter.format(date);

					return field + " BETWEEN '" + strdate + " 00:00:00 ' AND '" + endstrdate + " 23:59:59' ";
				} else if ("month".equals(selValue)) {// 本月
					calendar.setTime(date);
					calendar.add(calendar.DATE, 0);
					date = calendar.getTime();
					strdate = formatter.format(date);
					String binstrdate = strdate.substring(0, 8);
					binstrdate = binstrdate + "01";

					return field + " BETWEEN '" + binstrdate + " 00:00:00 ' AND '" + strdate + " 23:59:59' ";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return "";
	}

	/**
	 * KB单位转为MB或G
	 * 
	 * @author 产研部-许智皓
	 * @date 2013-01-07
	 * @company: GLSX.
	 * @param mapcount
	 *            地图数据大小 单位/KB
	 * @return 一个字符串的数据大小单位
	 */
	public static String kbToMbRoGb(long mapcounts) {

		DecimalFormat fnum = new DecimalFormat("##0.00 "); // 两位小数
		DecimalFormat fnum2 = new DecimalFormat("##0 "); // 没有小数

		float mapcount = mapcounts;
		if (0 < mapcount) {
			// 先转为MB
			mapcount = mapcount / 1024;
			if (mapcount > 1024) {
				// 转为GB
				mapcount = mapcount / 1024;
				if (mapcount > 1024) {
					if (mapcount > 100) {
						return fnum2.format(mapcount) + "GB";
					}
					return fnum.format(mapcount) + "GB";
				} else {
					if (mapcount > 100) {
						return fnum2.format(mapcount) + "GB";
					}
					return fnum.format(mapcount) + "GB";
				}
			} else {
				if (mapcount > 100) {
					return fnum2.format(mapcount) + "MB";
				}
				return fnum.format(mapcount) + "MB";
			}
		}

		return "0KB";

	}

	/**
	 * BASE64加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encodePasswordByBASE64(String str) {
		BASE64Encoder b = new BASE64Encoder();
		String res = b.encode(str.getBytes());
		return res;
	}

	/**
	 * BASE64解密
	 * 
	 * @param str
	 * @return
	 */
	public static String decodePasswordByBASE64(String str) {
		BASE64Decoder bd = new BASE64Decoder();
		byte[] bt = null;
		try {
			bt = bd.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String res = new String(bt);
		return res;

	}

	/**
	 * 输入：用“；”分隔的非空字符串 输出：返回封装“String”的List;
	 * 
	 * @param str
	 * @return
	 * @吴金玉
	 */
	public static List<String> StringToList(String str) {
		String[] strArray = str.split(";");
		List<String> list = Arrays.asList(strArray);
		return list;
	}

}

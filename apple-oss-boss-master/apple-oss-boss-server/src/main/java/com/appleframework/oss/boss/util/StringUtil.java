package com.appleframework.oss.boss.util;

public class StringUtil {

	public static String convert(String[] msg) {
		if(msg.length == 0) {
			return null;
		}
		else {
			String reValue = "";
			for (int i = 0; i < msg.length; i++) {
				reValue += msg[i];
				if(i < msg.length -1)
					reValue += ",";
			}
			return reValue;
		}
	}
}

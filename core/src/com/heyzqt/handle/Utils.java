package com.heyzqt.handle;

/**
 * Created by heyzqt on 2017/2/18.
 *
 * 操作类
 */
public class Utils {

	public static float time;

	public static void setTime(float time) {
		Utils.time = time;
	}

	public static String changeSecondToHMS(float time) {
		String hour, minute, second;
		int result = (int)time;
		hour = result / 3600 + "时";
		minute = result / 60 % 60 + "分";
		second = result % 60 + "秒";
		return hour + minute + second;
	}
}

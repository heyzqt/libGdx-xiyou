package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.Random;

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

	/**
	 * 将秒转换为 时分秒格式
	 */
	public static String changeSecondToHMS(float time) {
		String hour, minute, second;
		int result = (int) time;
		hour = result / 3600 + "时";
		minute = result / 60 % 60 + "分";
		second = result % 60 + "秒";
		return hour + minute + second;
	}

	/**
	 * 判断两个物体是否接触
	 *
	 * @param fixtureA 夹具A
	 * @param fixtureB 夹具B
	 * @param strA     A物体名称
	 * @param strB     B物体名称
	 * @return 接触返回true 未接触返回false
	 */
	public static boolean isContacted(Fixture fixtureA, Fixture fixtureB, String strA, String strB) {
		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals(strA) &&
				fixtureB.getUserData() != null && fixtureB.getUserData().equals(strB)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建对象判断
	 *
	 * @param max 设置出现概率
	 */
	public static boolean isCreated(int max) {
		Random random = new Random();
		int result = random.nextInt(max);
		if (result == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断用户名是否合乎规范
	 *
	 * @return 0 符合要求
	 * 1 字符串为null
	 * 2 字符串超过10个字符或小于等于0个字符
	 * 3 字符串仅能由英文字母和数字组成
	 */
	public static int isNameFormatted(String name) {
		if (name == null) {
			return 1;
		}

		if (name.length() > 10 || name.length() <= 0) {
			return 2;
		}

		char[] cha = name.toCharArray();
		for (int i = 0; i < cha.length; i++) {
			if ((cha[i] <= 'z' && cha[i] >= 'a') || (cha[i] <= 'Z' && cha[i] >= 'A')
					|| (cha[i] <= '9' && cha[i] >= '0')) {
				continue;
			}
			return 3;
		}
		return 0;
	}
}

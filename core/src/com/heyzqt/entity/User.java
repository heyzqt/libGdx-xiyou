package com.heyzqt.entity;

/**
 * Created by heyzqt on 2017/4/5.
 *
 * 用户类
 */
public class User {

	/**
	 * 用户名
	 */
	public String name;

	/**
	 * 血值
	 */
	public int HP;

	/**
	 * MP值
	 */
	public int MP;

	/**
	 * 当前通关位置
	 */
	public int curLevel;

	/**
	 * 第一关通关分数和时间
	 */
	public int firstSco;
	public int firstTime;

	/**
	 * 第二关通关分数和时间
	 */
	public int secondSco;
	public int secondTime;

	/**
	 * 第三关通关分数和时间
	 */
	public int thirdSco;
	public int thirdTime;

	/**
	 * 第四关通关分数和时间
	 */
	public int forthSco;
	public int forthTime;

	/**
	 * 第五关通关分数和时间
	 */
	public int fifthSco;
	public int fifthTime;

	/**
	 * 通关总分数和总时间
	 */
	public int scores;
	public int times;

	/**
	 * 通关状态
	 */
	public boolean isCompleted;

	public static User user;

	public User() {
	}

	public static User getInstance() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public void saveUserData() {

	}
}

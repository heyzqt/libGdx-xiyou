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
	 * HP
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
	 * 当前总分数
	 */
	public int score;

	/**
	 * 当前总时间
	 */
	public int time;

	public User() {
		name = "";
		HP = 8;
		MP = 8;
		curLevel = 0;
		score = 0;
		time = 0;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", HP=" + HP +
				", MP=" + MP +
				", curLevel=" + curLevel +
				", score=" + score +
				", time=" + time +
				'}';
	}
}

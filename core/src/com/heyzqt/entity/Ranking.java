package com.heyzqt.entity;

/**
 * Created by heyzqt on 2017/4/6.
 *
 * 排行榜类
 */
public class Ranking {

	public String name;

	public int score;

	public int time;

	public double weight;

	public Ranking() {
		name = "";
		score = 0;
		time = 0;
		weight = 0.0;
	}

	@Override
	public String toString() {
		return "Ranking{" +
				"name='" + name + '\'' +
				", score=" + score +
				", time=" + time +
				", weight=" + weight +
				'}';
	}
}

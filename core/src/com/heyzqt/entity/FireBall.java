package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by heyzqt on 2017/3/19.
 *
 * 孙悟空技能：光球
 */
public class FireBall extends Image {

	private Vector2 target;
	private int power;

	public FireBall(TextureRegion region) {
		super(region);
		power = 1; //默认杀伤力为1
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}

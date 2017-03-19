package com.heyzqt.handle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.heyzqt.entity.FireBall;


/**
 * Created by heyzqt on 2017/3/19.
 *
 * 光球管理类
 */
public class FireBallController extends Group {

	private TextureRegion region;
	private Sound bang;

	//更新火球逻辑
	public void update(Stage stage) {
	}

	public FireBallController() {
		region = new TextureRegion(new Texture("widget/fireball.png"));
	}

	public void AddFireBalls(FireBall ball) {
		if (this.getChildren().size >= 3) { // 如果火球数量大于等于3个就结束
			return;
		}
		ball.addAction(Actions.moveTo(ball.getX() + 1100, ball.getY(), 2f)); // 设置火球的移动
		this.addActor(ball);
	}

	//攻击敌人
	public Boolean attackEnemy(Actor target, Actor ball) {
		return false;
	}

	//敌人是否存活
	public Boolean checkAlive(Actor projectile) {
		return false;
	}

	public FireBall createFireBall() {
		return new FireBall(region);
	}
}

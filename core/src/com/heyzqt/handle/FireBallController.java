package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.entity.FireBall;


/**
 * Created by heyzqt on 2017/3/19.
 *
 * 光球管理类
 */
public class FireBallController {

	public Array<FireBall> balls;

	//更新火球逻辑
	public void update(World world) {
		for (int i = 0; i < balls.size; i++) {
			FireBall ball = balls.get(i);
			if (!checkAlive(ball)) {
				balls.removeValue(ball, true);
				world.destroyBody(ball.getBody());
			}
		}
	}

	public FireBallController() {
		balls = new Array<FireBall>();
	}

	public void addFireBalls(FireBall ball) {
		if (balls.size >= 3) { // 如果火球数量大于等于3个就结束
			return;
		}
		// 设置火球移动速度
		if (ball.STATE == State.STATE_RIGHT) {
			ball.getBody().setLinearVelocity(1f, 0f);
		} else {
			ball.getBody().setLinearVelocity(-1f, 0f);
		}
		balls.add(ball);
	}

	//攻击敌人
	public Boolean attackEnemy(Actor target, FireBall ball) {
		return false;
	}

	/**
	 * 检查火球是否到达目的地
	 * 火球到达目的地之前所含动作数量为1
	 * 到达后动作数量为0
	 *
	 * @return 到达返回true 否则返回false
	 */
	public Boolean checkAlive(FireBall ball) {
		float result = ball.getBody().getPosition().x * Constant.RATE -
				ball.startPosition;
		if (result >= 400 || (-result) >= 400) {
			return false;
		}
		return true;
	}

	public FireBall createFireBall(Body body, int state) {
		return new FireBall(body, state);
	}
}

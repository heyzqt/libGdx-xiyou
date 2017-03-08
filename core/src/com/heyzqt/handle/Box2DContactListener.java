package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.entity.EnemyDao;
import com.heyzqt.entity.Monkey;
import com.heyzqt.state.Play;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 接触监听类
 */
public class Box2DContactListener implements ContactListener {

	//记录要移除的天兵
	private Array<Body> removeEnemies;

	public Box2DContactListener() {
		removeEnemies = new Array<Body>();
	}

	@Override
	public void beginContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();

		//孙悟空攻击持刀天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemyDao", "stick")) {
			EnemyDao enemyDao = (EnemyDao) fixtureA.getBody().getUserData();
			//天兵被攻击次数+1
			enemyDao.attacks++;
			//天兵被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					enemyDao.getBody().getPosition().x;
			if (x < 0) {
				enemyDao.getBody().setLinearVelocity(1f, 0);
				enemyDao.STATE = State.STATE_LEFT_HITED;
			} else {
				enemyDao.getBody().setLinearVelocity(-1f, 0);
				enemyDao.STATE = State.STATE_RIGHT_HITED;
			}
			//天兵被攻击2次后死亡
			if (enemyDao.attacks == 2) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//持刀天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "dao", "monkey")) {
			EnemyDao enemy = (EnemyDao) fixtureA.getBody().getUserData();
			//孙悟空被攻击次数+1
			Play.mMonkey.attacks++;
			//孙悟空被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					enemy.getBody().getPosition().x;
			if (x > 0) {
				enemy.isHittedSun = true;
				Monkey.STATE = State.STATE_LEFT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(1f, 0);
			} else {
				enemy.isHittedSun = true;
				Monkey.STATE = State.STATE_RIGHT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(-1f, 0);
			}

			//攻击结束标志
			enemy.isAttacked = true;
		}

		//持刀天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "monkey", "dao")) {
			EnemyDao enemy = (EnemyDao) fixtureB.getBody().getUserData();
			//孙悟空被攻击次数+1
			Play.mMonkey.attacks++;
			//孙悟空被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					enemy.getBody().getPosition().x;
			if (x > 0) {
				enemy.isHittedSun = true;
				Monkey.STATE = State.STATE_LEFT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(1f, 0);
			} else {
				enemy.isHittedSun = true;
				Monkey.STATE = State.STATE_RIGHT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(-1f, 0);
			}

			//攻击结束标志
			enemy.isAttacked = true;
		}
	}

	@Override
	public void endContact(Contact contact) {
		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	public Array<Body> getRemoveEnemies() {
		return removeEnemies;
	}
}

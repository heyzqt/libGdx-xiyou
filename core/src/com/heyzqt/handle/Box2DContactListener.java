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

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 接触监听类
 */
public class Box2DContactListener implements ContactListener {

	private Array<Body> removeEnemies;

	public Box2DContactListener() {
		removeEnemies = new Array<Body>();
	}

	private Fixture enemyFixture;

	@Override
	public void beginContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();

		//孙悟空攻击持刀天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemyDao", "stick")) {
			EnemyDao enemyDao = (EnemyDao) fixtureA.getBody().getUserData();
			enemyDao.attacks++;
			//天兵被击飞
			if (Monkey.STATE == Monkey.STATE_RIGHT || Monkey.STATE == Monkey.STATE_RITHT_ATTACK
					|| Monkey.STATE == Monkey.STATE_IDEL_RIGHT) {
				enemyDao.getBody().setLinearVelocity(1f, 0);
			} else if (Monkey.STATE == Monkey.STATE_LEFT || Monkey.STATE == Monkey.STATE_LEFT_ATTACK
					|| Monkey.STATE == Monkey.STATE_IDEL_LEFT) {
				enemyDao.getBody().setLinearVelocity(-1f, 0);
			}
			if (enemyDao.attacks == 2) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//持刀天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "enemyDao", "monkey")) {
			//检测孙悟空的X坐标来设置天兵的位置
			Monkey sun = (Monkey) fixtureB.getBody().getUserData();
			EnemyDao enemy = (EnemyDao) fixtureA.getBody().getUserData();
			//sun.attacks++;

			enemy.setContacted(true);
			//根据孙悟空的位置来设置天兵的方向
			if (sun.getBody().getPosition().x < enemy.getBody().getPosition().x) {
				enemy.STATE = EnemyDao.STATE_LEFT;
			} else if (sun.getBody().getPosition().x > enemy.getBody().getPosition().x) {
				enemy.STATE = EnemyDao.STATE_RIGHT;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();

		//持刀天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "enemyDao", "monkey")) {
			EnemyDao enemy = (EnemyDao) fixtureA.getBody().getUserData();
			enemy.setContacted(false);
		}
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

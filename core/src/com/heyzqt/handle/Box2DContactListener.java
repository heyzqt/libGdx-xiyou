package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.entity.Boss;
import com.heyzqt.entity.Enemy;
import com.heyzqt.entity.Monkey;
import com.heyzqt.state.Play;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 接触监听类
 */
public class Box2DContactListener implements ContactListener {

	//记录要移除的天兵
	private Array<Body> removeEnemies;

	//记录要移除的Boss
	private Body removeBoss;

	public Box2DContactListener() {
		removeEnemies = new Array<Body>();
		removeBoss = null;
	}

	@Override
	public void beginContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();

		//孙悟空攻击持刀天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemy", "stick")) {
			Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
			//天兵被攻击次数+1
			enemy.attacks++;
			//播放音乐
			MyGdxGame.mAssetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					enemy.getBody().getPosition().x;
			if (x < 0) {
				enemy.getBody().setLinearVelocity(1f, 0);
				enemy.STATE = State.STATE_LEFT_HITED;
			} else {
				enemy.getBody().setLinearVelocity(-1f, 0);
				enemy.STATE = State.STATE_RIGHT_HITED;
			}
			//天兵被攻击2次后死亡
			if (enemy.attacks == 2) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//孙悟空攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "boss", "stick")) {
			Boss boss = (Boss) fixtureA.getBody().getUserData();
			//Boss被攻击次数+1
			boss.attacks++;
			//播放音乐
			MyGdxGame.mAssetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					boss.getBody().getPosition().x;
			if (x < 0) {
				boss.getBody().setLinearVelocity(1f, 0);
				boss.STATE = State.STATE_LEFT_HITED;
			} else {
				boss.getBody().setLinearVelocity(-1f, 0);
				boss.STATE = State.STATE_RIGHT_HITED;
			}
			//boss被攻击4次后死亡
			if (boss.attacks == 4) {
				removeBoss = fixtureA.getBody();
			}
		}

		//持刀天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "dao", "monkey")) {
			Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
			//孙悟空被攻击次数+1
			Play.mMonkey.attacks++;
			//播放音乐
			MyGdxGame.mAssetManager.getSound(Constant.SOUND_SUN_GET_HURT).play();
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
			Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
			//孙悟空被攻击次数+1
			Play.mMonkey.attacks++;
			//播放音乐
			MyGdxGame.mAssetManager.getSound(Constant.SOUND_SUN_GET_HURT).play();
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

		//Boss攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "weapon", "monkey")) {
			Boss boss = (Boss) fixtureA.getBody().getUserData();
			//孙悟空被攻击次数+1
			Play.mMonkey.attacks++;
			//播放音乐
			MyGdxGame.mAssetManager.getSound(Constant.SOUND_SUN_GET_HURT).play();
			//孙悟空被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					boss.getBody().getPosition().x;
			if (x > 0) {
				boss.isHittedSun = true;
				Monkey.STATE = State.STATE_LEFT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(1f, 0);
			} else {
				boss.isHittedSun = true;
				Monkey.STATE = State.STATE_RIGHT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(-1f, 0);
			}

			//攻击结束标志
			boss.isAttacked = true;
		}

		//Boss攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "monkey", "weapon")) {
			Boss boss = (Boss) fixtureB.getBody().getUserData();
			//孙悟空被攻击次数+1
			Play.mMonkey.attacks++;
			//播放音乐
			MyGdxGame.mAssetManager.getSound(Constant.SOUND_SUN_GET_HURT).play();
			//孙悟空被击飞
			float x = Play.mMonkey.getBody().getPosition().x -
					boss.getBody().getPosition().x;
			if (x > 0) {
				boss.isHittedSun = true;
				Monkey.STATE = State.STATE_LEFT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(1f, 0);
			} else {
				boss.isHittedSun = true;
				Monkey.STATE = State.STATE_RIGHT_HITED;
				Play.mMonkey.getBody().setLinearVelocity(-1f, 0);
			}

			//攻击结束标志
			boss.isAttacked = true;
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

	public Body getRemoveBoss() {
		return removeBoss;
	}

	public void setRemoveBoss(Body removeBoss) {
		this.removeBoss = removeBoss;
	}
}

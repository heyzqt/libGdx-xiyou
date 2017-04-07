package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.entity.BaseSprite;
import com.heyzqt.entity.Boss;
import com.heyzqt.entity.Enemy;
import com.heyzqt.entity.Monkey;
import com.heyzqt.state.Play;
import com.heyzqt.util.Utils;
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

	//记录要移除的Tao
	private Array<Body> removeTaos;

	//记录要移除的Blue
	private Array<Body> removeBlues;

	public Box2DContactListener() {
		removeEnemies = new Array<Body>();
		removeBoss = null;
		removeTaos = new Array<Body>();
		removeBlues = new Array<Body>();
	}

	@Override
	public void beginContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();

		//孙悟空攻击天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemy", "stick")) {
			Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
			//血量-1
			enemy.HP--;
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			checkHitted(enemy, "enemy");
			//血量为0死亡
			if (enemy.HP <= 0) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//孙悟空攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "boss", "stick")) {
			Boss boss = (Boss) fixtureA.getBody().getUserData();
			//Boss血量-1
			boss.HP--;
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			checkHitted(boss, "boss");
			//血量为0死亡
			if (boss.HP <= 0) {
				removeBoss = fixtureA.getBody();
			}
		}

		//天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "dao", "monkey")) {
			enemyAttackSun(fixtureA, fixtureB);
		}

		//天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "monkey", "dao")) {
			enemyAttackSun(fixtureB, fixtureA);
		}

		//Boss攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "weapon", "monkey")) {
			bossAttackSun(fixtureA, fixtureB);
		}

		//Boss攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "monkey", "weapon")) {
			bossAttackSun(fixtureB, fixtureA);
		}

		//技能攻击天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemy", "ball")) {
			Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
			//天兵血量-2
			enemy.HP -= 2;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			checkHitted(enemy, "enemy");
			//血量为0死亡
			if (enemy.HP <= 0) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//技能攻击天兵
		if (Utils.isContacted(fixtureA, fixtureB, "ball", "enemy")) {
			Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
			//天兵血量-2
			enemy.HP -= 2;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			checkHitted(enemy, "enemy");
			//血量为0死亡
			if (enemy.HP <= 0) {
				removeEnemies.add(fixtureB.getBody());
			}
		}

		//技能攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "boss", "ball")) {
			Boss boss = (Boss) fixtureA.getBody().getUserData();
			//血量-2
			boss.HP -= 2;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			checkHitted(boss, "boss");
			if (boss.HP <= 0) {
				removeBoss = fixtureA.getBody();
			}
		}

		//技能攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "ball", "boss")) {
			Boss boss = (Boss) fixtureB.getBody().getUserData();
			//血量-2
			boss.HP -= 2;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			checkHitted(boss, "boss");
			//血量为0死亡
			if (boss.HP <= 0) {
				removeBoss = fixtureB.getBody();
			}
		}

		// 孙悟空碰到桃子
		if (Utils.isContacted(fixtureA, fixtureB, "monkey", "tao")) {
			removeTaos.add(fixtureB.getBody());
		}

		// 孙悟空碰到桃子
		if (Utils.isContacted(fixtureA, fixtureB, "tao", "monkey")) {
			removeTaos.add(fixtureA.getBody());
		}

		// 孙悟空碰到瓶子
		if (Utils.isContacted(fixtureA, fixtureB, "monkey", "blue")) {
			removeBlues.add(fixtureB.getBody());
		}

		// 孙悟空碰到瓶子
		if (Utils.isContacted(fixtureA, fixtureB, "blue", "monkey")) {
			removeBlues.add(fixtureA.getBody());
		}
	}

	//检查精灵被击飞状态
	private void checkHitted(BaseSprite sprite, String name) {
		if (name.equals("boss")) {
			//检查Boss
			Boss boss = (Boss) sprite;
			float x = Play.mMonkey.getBody().getPosition().x -
					boss.getBody().getPosition().x;
			if (x < 0) {
				boss.getBody().setLinearVelocity(1f, 0);
				boss.STATE = State.STATE_LEFT_HITED;
			} else {
				boss.getBody().setLinearVelocity(-1f, 0);
				boss.STATE = State.STATE_RIGHT_HITED;
			}
		} else if (name.equals("enemy")) {
			Enemy enemy = (Enemy) sprite;
			float x = Play.mMonkey.getBody().getPosition().x -
					enemy.getBody().getPosition().x;
			if (x < 0) {
				enemy.getBody().setLinearVelocity(1f, 0);
				enemy.STATE = State.STATE_LEFT_HITED;
			} else {
				enemy.getBody().setLinearVelocity(-1f, 0);
				enemy.STATE = State.STATE_RIGHT_HITED;
			}
		} else if (name.equals("monkey")) {
			float x = Play.mMonkey.getBody().getPosition().x -
					sprite.getBody().getPosition().x;
			if (x > 0) {
				Monkey.STATE = State.STATE_LEFT_HITED;
				Play.mMonkey.monkeytime = 0;
				Play.mMonkey.getBody().setLinearVelocity(1f, 0);
			} else {
				Monkey.STATE = State.STATE_RIGHT_HITED;
				Play.mMonkey.monkeytime = 0;
				Play.mMonkey.getBody().setLinearVelocity(-1f, 0);
			}
		}
	}

	//天兵攻击孙悟空
	private void enemyAttackSun(Fixture enemyFix, Fixture sunFix) {
		Enemy enemy = (Enemy) enemyFix.getBody().getUserData();
		//孙悟空血量减1
		Monkey.HP--;
		//播放音乐
		MyGdxGame.assetManager.getSound(Constant.SOUND_SUN_GET_HURT).play();
		//孙悟空被击飞
		checkHitted(enemy, "monkey");

		//天兵击中孙悟空
		enemy.isHittedSun = true;

		//攻击结束标志
		enemy.isAttacked = true;
	}

	//Boss攻击孙悟空
	private void bossAttackSun(Fixture bossFix, Fixture sunFix) {
		Boss boss = (Boss) bossFix.getBody().getUserData();
		//孙悟空血量减1
		Monkey.HP--;
		//播放音乐
		MyGdxGame.assetManager.getSound(Constant.SOUND_SUN_GET_HURT).play();
		//孙悟空被击飞
		checkHitted(boss, "monkey");

		//Boss击中孙悟空
		boss.isHittedSun = true;

		//攻击结束标志
		boss.isAttacked = true;
	}

	@Override
	public void endContact(Contact contact) {
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

	public Array<Body> getRemoveTaos() {
		return removeTaos;
	}

	public Array<Body> getRemoveBlues() {
		return removeBlues;
	}
}

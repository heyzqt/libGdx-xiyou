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

		//孙悟空攻击持刀天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemy", "stick")) {
			Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
			//天兵被攻击次数+1
			enemy.attacks++;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			checkHitted(enemy, "enemy");
			//天兵被攻击次数等于生命值后死亡
			if (enemy.attacks == Enemy.LIVE) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//孙悟空攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "boss", "stick")) {
			Boss boss = (Boss) fixtureA.getBody().getUserData();
			//Boss被攻击次数+1
			boss.attacks++;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			checkHitted(boss, "boss");
			//boss被攻击次数等于生命值后死亡
			if (boss.attacks == Boss.LIVE) {
				removeBoss = fixtureA.getBody();
			}
		}

		//持刀天兵攻击孙悟空
		if (Utils.isContacted(fixtureA, fixtureB, "dao", "monkey")) {
			enemyAttackSun(fixtureA, fixtureB);
		}

		//持刀天兵攻击孙悟空
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

		//火球攻击天兵
		if (Utils.isContacted(fixtureA, fixtureB, "enemy", "ball"))

		{
			Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
			//天兵被攻击次数+1
			enemy.attacks++;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			checkHitted(enemy, "enemy");
			//天兵被攻击次数等于生命值后死亡
			if (enemy.attacks == Enemy.LIVE) {
				removeEnemies.add(fixtureA.getBody());
			}
		}

		//火球攻击天兵
		if (Utils.isContacted(fixtureA, fixtureB, "ball", "enemy")) {
			Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
			//天兵被攻击次数+1
			enemy.attacks++;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//天兵被击飞
			checkHitted(enemy, "enemy");
			//天兵被攻击次数等于生命值后死亡
			if (enemy.attacks == Enemy.LIVE) {
				removeEnemies.add(fixtureB.getBody());
			}
		}

		//火球攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "boss", "ball")) {
			Boss boss = (Boss) fixtureA.getBody().getUserData();
			//Boss被攻击次数+1
			boss.attacks++;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			checkHitted(boss, "boss");
			//boss被攻击次数等于生命值后死亡
			if (boss.attacks == Boss.LIVE) {
				removeBoss = fixtureA.getBody();
			}
		}

		//火球攻击Boss
		if (Utils.isContacted(fixtureA, fixtureB, "ball", "boss")) {
			Boss boss = (Boss) fixtureB.getBody().getUserData();
			//Boss被攻击次数+1
			boss.attacks++;
			//播放音乐
			MyGdxGame.assetManager.getSound(Constant.SOUND_ENEMY_GET_HURT).play();
			//Boss被击飞
			checkHitted(boss, "boss");
			//boss被攻击次数等于生命值后死亡
			if (boss.attacks == Boss.LIVE) {
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

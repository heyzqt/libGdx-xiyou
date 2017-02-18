package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

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

	@Override
	public void beginContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();

		if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("enemyDao")) {
			removeEnemies.add(fixtureA.getBody());
		}

		if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("enemyDao")) {
			removeEnemies.add(fixtureB.getBody());
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

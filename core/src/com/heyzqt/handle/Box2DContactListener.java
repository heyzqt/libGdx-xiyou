package com.heyzqt.handle;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 接触监听类
 */
public class Box2DContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {

		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();
		//打印刚体夹具用户数据
		System.out.println("hello begin : " + fixtureA.getUserData() + "-->" + fixtureB.getUserData());
	}

	@Override
	public void endContact(Contact contact) {
		//获取刚体碰撞夹具A
		Fixture fixtureA = contact.getFixtureA();
		//获取刚体碰撞夹具B
		Fixture fixtureB = contact.getFixtureB();
		//打印刚体夹具用户数据
		System.out.println("hello end : " + fixtureA.getUserData() + "-->" + fixtureB.getUserData());
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}

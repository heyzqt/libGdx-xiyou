package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/3/20.
 */
public class Blue {

	private World mWorld;

	private float x;

	private float y;

	private TextureAtlas.AtlasRegion mRegion;

	private Body mBody;

	private BodyDef mBodyDef;

	private FixtureDef mFixtureDef;

	public Blue(World world, float x, float y) {
		this.mWorld = world;
		this.x = x;
		this.y = y - 0.4f;
		init();
	}

	private void init() {
		mRegion = MyGdxGame.assetManager.getTextureAtlas(Constant.PLAY_BLOOD).findRegion("mp");

		mBodyDef = new BodyDef();
		mFixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		//初始化刚体形状
		mBodyDef.type = BodyDef.BodyType.StaticBody;
		mBodyDef.position.set(x, y);

		shape.setAsBox(30 / Constant.RATE, 30 / Constant.RATE);
		mFixtureDef.shape = shape;
		mFixtureDef.isSensor = true;
		mFixtureDef.filter.categoryBits = Constant.BLUE;
		mFixtureDef.filter.maskBits = Constant.PLAYER;

		mBody = mWorld.createBody(mBodyDef);
		mBody.createFixture(mFixtureDef).setUserData("blue");
	}

	public Body getBody() {
		return mBody;
	}

	public void update(float delta) {
	}

	public void render(SpriteBatch batch, float delta) {
		update(delta);

		batch.begin();
		batch.draw(mRegion, x * Constant.RATE - mRegion.getRegionWidth() / 2
				, y * Constant.RATE - mRegion.getRegionHeight() / 2);
		batch.end();
	}
}

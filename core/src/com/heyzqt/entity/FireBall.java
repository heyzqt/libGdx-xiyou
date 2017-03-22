package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/3/19.
 *
 * 孙悟空技能：光球
 */
public class FireBall extends BaseSprite {

	//光球当前状态
	public int STATE;
	public final static int LEFT = 0;
	public final static int RIGHT = 1;

	//光球动画
	private TextureAtlas mAtlas;
	private TextureAtlas.AtlasRegion[] mLeftState;
	private TextureAtlas.AtlasRegion[] mRightState;

	//火球初始化的位置
	public float startPosition;

	public FireBall(Body body, int state) {
		super(body);
		this.STATE = state;
		this.startPosition = body.getPosition().x * Constant.RATE;
		init();
	}

	private void init() {
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.FIREBALL_WIDGET);

		mLeftState = new TextureAtlas.AtlasRegion[2];
		mLeftState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("leftfireball"));
		mLeftState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("leftfireball"));

		mRightState = new TextureAtlas.AtlasRegion[2];
		mRightState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("rightfireball"));
		mRightState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("rightfireball"));
	}

	@Override
	public void update(float delta) {
		switch (STATE) {
			case LEFT:
				setAnimation(mLeftState, 1 / 12f);
				break;
			case RIGHT:
				setAnimation(mRightState, 1 / 12f);
				break;
		}
	}
}

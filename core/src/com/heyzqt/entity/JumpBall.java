package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.State;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/3/23.
 *
 * 孙悟空技能：升龙斩
 */
public class JumpBall {

	private TextureAtlas mSkillAtlas;

	private float mWidth;

	private float mHeight;

	//升龙斩攻击
	private TextureAtlas.AtlasRegion[] mRJumpAtkRegions;
	private TextureAtlas.AtlasRegion[] mLJumpAtkRegions;
	private Animation mRJumpAtkAni;
	private Animation mLJumpAtkAni;

	public int STATE;

	private Monkey mMonkey;

	public JumpBall(Monkey monkey) {
		this.mMonkey = monkey;
		init();
	}

	private void init() {
		mSkillAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.FIREBALL_WIDGET);

		//升龙斩动画初始化
		mRJumpAtkRegions = new TextureAtlas.AtlasRegion[4];
		for (int i = 0; i < mRJumpAtkRegions.length - 1; i++) {
			mRJumpAtkRegions[i] = new TextureAtlas.AtlasRegion(mSkillAtlas.findRegion("jumpHit" + (i + 1)));
		}
		mRJumpAtkRegions[3] = new TextureAtlas.AtlasRegion(mSkillAtlas.findRegion("jumpHit3"));
		mLJumpAtkRegions = new TextureAtlas.AtlasRegion[4];
		for (int i = 0; i < mLJumpAtkRegions.length - 1; i++) {
			mLJumpAtkRegions[i] = new TextureAtlas.AtlasRegion(mSkillAtlas.findRegion("jumpHit" + (i + 1)));
			mLJumpAtkRegions[i].flip(true, false);
		}
		mLJumpAtkRegions[3] = mLJumpAtkRegions[2];
		mRJumpAtkAni = new Animation(1 / 12f, mRJumpAtkRegions);
		mLJumpAtkAni = new Animation(1 / 12f, mLJumpAtkRegions);
	}

	private void setFrame(SpriteBatch batch, Animation animation, TextureRegion[] regions, float delta) {
		mWidth = regions[0].getRegionWidth();
		mHeight = regions[0].getRegionHeight();
		if (STATE == State.STATE_RIGHT) {
			batch.draw(animation.getKeyFrame(delta, true),
					mMonkey.getBody().getPosition().x * Constant.RATE,
					mMonkey.getBody().getPosition().y * Constant.RATE - mHeight / 2);
		} else {
			batch.draw(animation.getKeyFrame(delta, true),
					mMonkey.getBody().getPosition().x * Constant.RATE - mWidth,
					mMonkey.getBody().getPosition().y * Constant.RATE - mHeight / 2);
		}
	}

	public void render(SpriteBatch batch, float delta) {
		switch (STATE) {
			case State.STATE_RIGHT:
				setFrame(batch, mRJumpAtkAni, mRJumpAtkRegions, delta);
				break;
			case State.STATE_LEFT:
				setFrame(batch, mLJumpAtkAni, mLJumpAtkRegions, delta);
				break;
		}
	}
}

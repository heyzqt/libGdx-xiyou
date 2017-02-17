package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 迟到天兵精灵类
 */
public class EnemyDao extends BaseSprite {

	private TextureAtlas mAtlas;

	//右走状态图片集合
	private TextureAtlas.AtlasRegion[] mRightState;
	//左走状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftState;

	//天兵2种状态
	public static int STATE;
	public static int STATE_LEFT = 1;
	public static int STATE_RIGHT = 2;


	public EnemyDao(Body body) {

		super(body);

		STATE = STATE_LEFT;

		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.ROLE);

		//初始化右走图片
		mRightState = new TextureAtlas.AtlasRegion[3];
		mRightState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoRightStand"));
		mRightState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoRightW1"));
		mRightState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoRightW2"));

		//初始化左走图片
		mLeftState = new TextureAtlas.AtlasRegion[3];
		mLeftState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoLeftStand"));
		mLeftState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoLeftW1"));
		mLeftState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoLeftW2"));
	}

	@Override
	public void update(float delta) {
		if (STATE == STATE_LEFT) {
			setAnimation(mLeftState, 1 / 12f);
		} else {
			setAnimation(mRightState, 1 / 12f);
		}
	}
}

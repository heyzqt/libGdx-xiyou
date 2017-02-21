package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 持刀天兵精灵类
 */
public class EnemyDao extends BaseSprite implements Runnable {

	//天兵图片集合
	private TextureAtlas mAtlas;

	//右走状态图片集合
	private TextureAtlas.AtlasRegion[] mRightState;
	//左走状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftState;

	//天兵2种状态
	public static int STATE;
	public static int STATE_LEFT = 1;
	public static int STATE_RIGHT = 2;

	//天兵是否存活
	private boolean isLive = true;

	//记录天兵被攻击的次数 攻击两次天兵死亡
	public int attacks = 0;

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
	}


	@Override
	public void run() {

		while (true) {

			//判断刀兵是否存活
			if (!isLive) {
				return;
			}

			if (STATE == STATE_LEFT) {
				setAnimation(mLeftState, 1 / 12f);
				//屏幕左边缘
				mBody.setLinearVelocity(-0.2f, 0);
			} else {
				setAnimation(mRightState, 1 / 12f);
				//屏幕右边缘
				mBody.setLinearVelocity(0.2f, 0);
			}

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//给刀兵随机产生一个往左或往右的方向 产生随机数[1,2]
			STATE = (int) (Math.random() * 2) + 1;
		}
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean live) {
		isLive = live;
	}
}
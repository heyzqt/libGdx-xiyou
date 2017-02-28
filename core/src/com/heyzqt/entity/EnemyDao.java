package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.heyzqt.handle.Constant;
import com.heyzqt.state.Play;
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
	//左攻击图片集合
	private TextureAtlas.AtlasRegion[] mLeftAttackState;
	//右攻击图片集合
	private TextureAtlas.AtlasRegion[] mRightAttackState;

	//天兵3种状态
	public int STATE;
	public static int STATE_LEFT = 1;    //左行走
	public static int STATE_RIGHT = 2;   //右行走
	public static int STATE_LEFT_ATTACK = 3;    //左攻击
	public static int STATE_RIGHT_ATTACK = 4;    //右攻击

	//天兵是否存活
	private boolean isLive = true;

	//天兵与孙悟空是否接触
	private boolean isContacted = false;

	//攻击是否完成
	public boolean isAttacked = false;

	//记录天兵被攻击的次数 攻击两次天兵死亡
	public int attacks = 0;

	//天兵左右攻击夹具
	public Fixture mLeftFixture;
	public Fixture mRightFixture;
	public FixtureDef mLeftFixDef;
	public FixtureDef mRightFixDef;

	//记录update()上一次时间
	private int preTime;

	public EnemyDao(Body body) {

		super(body);

		STATE = STATE_LEFT;

		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.ENEMY_DAO_ROLE);

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

		//初始化左攻击图片
		mLeftAttackState = new TextureAtlas.AtlasRegion[3];
		mLeftAttackState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoLeftAttack1"));
		mLeftAttackState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoLeftW1"));
		mLeftAttackState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoLeftW2"));

		//初始化右攻击图片
		mRightAttackState = new TextureAtlas.AtlasRegion[3];
		mRightAttackState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoRightAttack1"));
		mRightAttackState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoRightW1"));
		mRightAttackState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("enemyDaoRightW2"));

		//左传感器 dao
		PolygonShape shapeLeft = new PolygonShape();
		shapeLeft.setAsBox(30 / Constant.RATE, 5 / Constant.RATE
				, new Vector2(-60 / Constant.RATE, 0), 0);
		mLeftFixDef = new FixtureDef();
		mLeftFixDef.shape = shapeLeft;
		mLeftFixDef.isSensor = true;
		mLeftFixDef.filter.categoryBits = Constant.ENEMY_DAO;
		mLeftFixDef.filter.maskBits = Constant.PLAYER;

		//右传感器 dao
		PolygonShape shapeRight = new PolygonShape();
		mRightFixDef = new FixtureDef();
		shapeRight.setAsBox(30 / Constant.RATE, 5 / Constant.RATE
				, new Vector2(60 / Constant.RATE, 0), 0);
		mRightFixDef.shape = shapeRight;
		mRightFixDef.isSensor = true;
		mRightFixDef.filter.categoryBits = Constant.ENEMY_DAO;
		mRightFixDef.filter.maskBits = Constant.PLAYER;

		//设置状态动画
		setStateAnimation();
	}

	@Override
	public void update(float delta) {

		/**
		 * 根据当前天兵与主角位置关系
		 * 设置天兵攻击状态
		 */
		if (Play.mMonkey != null) {
			//天兵与孙悟空x差值
			float x = Play.mMonkey.getBody().getPosition().x - mBody.getPosition().x;
			int time = (int) delta;
			if (x < 100 / Constant.RATE && x > 0) {    //孙悟空在天兵右边1米
				isContacted = true;
				//设置状态动画
				STATE = STATE_RIGHT_ATTACK;
				mBody.setLinearVelocity(0, 0);
				setAnimation(mRightAttackState, 1 / 12f);
				//每隔2秒攻击一次
				if (time > preTime && time % 2 == 0 && mRightFixture == null && !isAttacked) {
					mRightFixture = mBody.createFixture(mRightFixDef);
					mRightFixture.setUserData("dao");
					preTime = time;
				} else {
					//如果右武器不为空且攻击结束则清理
					if (mRightFixture != null && isAttacked) {
						mBody.destroyFixture(mRightFixture);
						mRightFixture = null;
						isAttacked = false;
					}
				}
			} else if ((-x) < 100 / Constant.RATE && (-x) > 0) {  //孙悟空在天兵左边1米
				isContacted = true;
				//设置状态动画
				STATE = STATE_LEFT_ATTACK;
				mBody.setLinearVelocity(0, 0);
				setAnimation(mLeftAttackState, 1 / 12f);
				//每隔2秒攻击一次
				if (time > preTime && time % 2 == 0 && mLeftFixture == null && !isAttacked) {
					mLeftFixture = mBody.createFixture(mLeftFixDef);
					mLeftFixture.setUserData("dao");
					preTime = time;
				} else {
					//如果左武器不为空且攻击结束则清理
					if (mLeftFixture != null && isAttacked) {
						mBody.destroyFixture(mLeftFixture);
						mLeftFixture = null;
						isAttacked = false;
					}
				}
			} else {
				isContacted = false;
				if (mLeftFixture != null && isAttacked) {
					mBody.destroyFixture(mLeftFixture);
					mLeftFixture = null;
					isAttacked = false;
				}
				if (mRightFixture != null && isAttacked) {
					mBody.destroyFixture(mRightFixture);
					mRightFixture = null;
					isAttacked = false;
				}
			}
		}
	}


	@Override
	public void run() {

		while (true) {
			//判断刀兵是否存活
			if (!isLive) {
				return;
			}

			//刀兵孙悟空接触时不执行下面的代码
			if (isContacted) {
				continue;
			}

			setStateAnimation();

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!isContacted) {
				//给刀兵随机产生一个往左或往右的方向 产生随机数[1,2]
				STATE = (int) (Math.random() * 2) + 1;
			}
		}
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean live) {
		isLive = live;
	}

	public boolean isContacted() {
		return isContacted;
	}

	synchronized public void setContacted(boolean contacted) {
		isContacted = contacted;
	}

	/**
	 * 设置状态动画
	 */
	private void setStateAnimation() {
		if (STATE == STATE_RIGHT_ATTACK) {
			mBody.setLinearVelocity(0, 0);
			setAnimation(mRightAttackState, 1 / 12f);
		} else if (STATE == STATE_LEFT_ATTACK) {
			mBody.setLinearVelocity(0, 0);
			setAnimation(mLeftAttackState, 1 / 12f);
		} else if (STATE == STATE_LEFT) {
			setAnimation(mLeftState, 1 / 12f);
			mBody.setLinearVelocity(-0.2f, 0);
		} else if (STATE == STATE_RIGHT) {
			setAnimation(mRightState, 1 / 12f);
			mBody.setLinearVelocity(0.2f, 0);
		}
	}
}
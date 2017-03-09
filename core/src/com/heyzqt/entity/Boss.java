package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.State;
import com.heyzqt.state.Play;

/**
 * Created by heyzqt on 2017/2/7.
 */
public class Boss extends BaseSprite implements Runnable {

	//Boss图片集合
	private TextureAtlas mAtlas;

	//左停止状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftStandState;
	//右停止状态图片集合
	private TextureAtlas.AtlasRegion[] mRightStandState;
	//右走状态图片集合
	private TextureAtlas.AtlasRegion[] mRightState;
	//左走状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftState;
	//左攻击图片集合
	private TextureAtlas.AtlasRegion[] mLeftAttackState;
	//右攻击图片集合
	private TextureAtlas.AtlasRegion[] mRightAttackState;
	//左边被击飞动画
	private TextureAtlas.AtlasRegion[] mLeftHittedState;
	//右边被击飞动画
	private TextureAtlas.AtlasRegion[] mRightHittedState;

	//Boss状态
	public int STATE;

	//Boss是否存活
	private boolean isLive = true;

	//Boss与孙悟空是否接触
	private boolean isContacted = false;

	//是否击中孙悟空
	public boolean isHittedSun = false;

	//攻击是否完成
	public boolean isAttacked = false;

	//记录天兵被攻击的次数 攻击4次Boss死亡
	public int attacks = 0;

	//Boss左右攻击夹具
	public Fixture mLeftFixture;
	public Fixture mRightFixture;
	public FixtureDef mLeftFixDef;
	public FixtureDef mRightFixDef;

	//记录update()上一次时间
	private int preTime;

	public Boss(Body body, TextureAtlas atlas) {
		super(body);
		this.mAtlas = atlas;

		STATE = State.STATE_IDEL_RIGHT;

		//初始化静止图片
		mLeftStandState = new TextureAtlas.AtlasRegion[2];
		mLeftStandState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftWalk1"));
		mLeftStandState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftWalk1"));

		mRightStandState = new TextureAtlas.AtlasRegion[2];
		mRightStandState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightWalk1"));
		mRightStandState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightWalk1"));

		//初始化右走图片
		mRightState = new TextureAtlas.AtlasRegion[3];
		mRightState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightWalk1"));
		mRightState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightWalk2"));
		mRightState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightWalk3"));

		//初始化左走图片
		mLeftState = new TextureAtlas.AtlasRegion[3];
		mLeftState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftWalk1"));
		mLeftState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftWalk2"));
		mLeftState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftWalk3"));

		//初始化左攻击图像
		mLeftAttackState = new TextureAtlas.AtlasRegion[4];
		mLeftAttackState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftAttack1"));
		mLeftAttackState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftAttack2"));
		mLeftAttackState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftAttack3"));
		mLeftAttackState[3] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftAttack4"));

		//初始化右攻击图像
		mRightAttackState = new TextureAtlas.AtlasRegion[4];
		mRightAttackState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightAttack1"));
		mRightAttackState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightAttack2"));
		mRightAttackState[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightAttack3"));
		mRightAttackState[3] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightAttack4"));

		//左边被击飞动画
		mLeftHittedState = new TextureAtlas.AtlasRegion[2];
		mLeftHittedState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftHitted"));
		mLeftHittedState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossLeftHitted"));

		//右边被击飞动画
		mRightHittedState = new TextureAtlas.AtlasRegion[2];
		mRightHittedState[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightHitted"));
		mRightHittedState[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("julingBossRightHitted"));

		//左传感器Fixture chui
		PolygonShape shapeLeft = new PolygonShape();
		shapeLeft.setAsBox(30 / Constant.RATE, 5 / Constant.RATE
				, new Vector2(-60 / Constant.RATE, 0), 0);
		mLeftFixDef = new FixtureDef();
		mLeftFixDef.shape = shapeLeft;
		mLeftFixDef.isSensor = true;
		mLeftFixDef.filter.categoryBits = Constant.ENEMY_DAO;
		mLeftFixDef.filter.maskBits = Constant.PLAYER;

		//右传感器Fixture chui
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

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean live) {
		isLive = live;
	}

	public boolean isContacted() {
		return isContacted;
	}

	public void setContacted(boolean contacted) {
		isContacted = contacted;
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
				mBody.setLinearVelocity(0, 0);
				//每隔2秒攻击一次
				if (time > preTime && time % 2 == 0 && mRightFixture == null && !isAttacked) {
					mRightFixture = mBody.createFixture(mRightFixDef);
					mRightFixture.setUserData("chui");
					preTime = time;
					//设置状态动画
					STATE = State.STATE_RIGHT_ATTACK;
					setAnimation(mRightAttackState, 0.2f);
				} else {
					//如果右武器不为空且攻击结束则清理
					if (mRightFixture != null && isAttacked) {
						mBody.destroyFixture(mRightFixture);
						mRightFixture = null;
						isAttacked = false;
						//设置状态动画
						STATE = State.STATE_IDEL_RIGHT;
						setAnimation(mRightStandState, 1 / 12f);
					}
				}
			} else if ((-x) < 100 / Constant.RATE && (-x) > 0) {  //孙悟空在天兵左边1米
				isContacted = true;
				mBody.setLinearVelocity(0, 0);
				//每隔2秒攻击一次
				if (time > preTime && time % 2 == 0 && mLeftFixture == null && !isAttacked) {
					mLeftFixture = mBody.createFixture(mLeftFixDef);
					mLeftFixture.setUserData("chui");
					preTime = time;
					//设置状态动画
					STATE = State.STATE_LEFT_ATTACK;
					setAnimation(mLeftAttackState, 0.2f);
				} else {
					//如果左武器不为空且攻击结束则清理
					if (mLeftFixture != null && isAttacked) {
						mBody.destroyFixture(mLeftFixture);
						mLeftFixture = null;
						isAttacked = false;
						//设置状态动画
						STATE = State.STATE_IDEL_LEFT;
						setAnimation(mLeftStandState, 1 / 12f);
					}
				}
			} else {
				isContacted = false;
				if (mLeftFixture != null) {
					mBody.destroyFixture(mLeftFixture);
					mLeftFixture = null;
					isAttacked = false;
				}
				if (mRightFixture != null) {
					mBody.destroyFixture(mRightFixture);
					mRightFixture = null;
					isAttacked = false;
				}
			}

			//设置天兵被击中状态
			if (STATE == State.STATE_LEFT_HITED) {
				setAnimation(mLeftHittedState, 1 / 12f);
			} else if (STATE == State.STATE_RIGHT_HITED) {
				setAnimation(mRightHittedState, 1 / 12f);
			}

			//孙悟空被击飞过程检测孙悟空位置 修改孙悟空状态
			if (Monkey.STATE == State.STATE_LEFT_HITED) {
				if (isHittedSun && x >= 300 / Constant.RATE) {
					Monkey.STATE = State.STATE_IDEL_LEFT;
					Play.mMonkey.getBody().setLinearVelocity(0, 0);
					isHittedSun = false;
				}
			} else if (Monkey.STATE == State.STATE_RIGHT_HITED) {
				if (isHittedSun && (-x) >= 300 / Constant.RATE) {
					Monkey.STATE = State.STATE_IDEL_RIGHT;
					Play.mMonkey.getBody().setLinearVelocity(0, 0);
					isHittedSun = false;
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
				//给刀兵随机产生一个往左或往右的方向 产生随机数[3,4]
				STATE = (int) (Math.random() * 2) + 3;
			}
		}
	}


	/**
	 * 设置状态动画
	 */
	private void setStateAnimation() {

		if (STATE == State.STATE_RIGHT_ATTACK) {
			mBody.setLinearVelocity(0, 0);
			setAnimation(mRightAttackState, 1 / 12f);
		} else if (STATE == State.STATE_LEFT_ATTACK) {
			mBody.setLinearVelocity(0, 0);
			setAnimation(mLeftAttackState, 1 / 12f);
		} else if (STATE == State.STATE_LEFT) {
			setAnimation(mLeftState, 1 / 12f);
			mBody.setLinearVelocity(-0.2f, 0);
		} else if (STATE == State.STATE_RIGHT) {
			setAnimation(mRightState, 1 / 12f);
			mBody.setLinearVelocity(0.2f, 0);
		} else if (STATE == State.STATE_IDEL_LEFT) {
			setAnimation(mLeftStandState, 1 / 12f);
			mBody.setLinearVelocity(0f, 0);
		} else if (STATE == State.STATE_IDEL_RIGHT) {
			setAnimation(mRightStandState, 1 / 12f);
			mBody.setLinearVelocity(0f, 0);
		} else if (STATE == State.STATE_LEFT_HITED) {
			setAnimation(mLeftHittedState, 1 / 12f);
		} else if (STATE == State.STATE_LEFT_HITED) {
			setAnimation(mRightHittedState, 1 / 12f);
		}
	}
}

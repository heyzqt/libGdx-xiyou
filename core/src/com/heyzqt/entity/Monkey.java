package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.State;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 主角:孙悟空
 */
public class Monkey extends BaseSprite {

	private TextureAtlas mMonkeyAtlas;

	//左停止状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftStandState;
	//右停止状态图片集合
	private TextureAtlas.AtlasRegion[] mRightStandState;
	//右走状态图片集合
	private TextureAtlas.AtlasRegion[] mRightState;
	//左走状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftState;
	//左攻击状态图片集合
	private TextureAtlas.AtlasRegion[] mLeftAttackState;
	//右攻击状态图片集合
	private TextureAtlas.AtlasRegion[] mRightAttackState;
	//左边被击飞动画
	private TextureAtlas.AtlasRegion[] mLeftHittedState;
	//右边被击飞动画
	private TextureAtlas.AtlasRegion[] mRightHittedState;

	//孙悟空状态
	public static int STATE;

	//击败敌人数目
	private int enemyCount;

	//敌人总数
	private int allEnemiesCount;
	public int level_1_enemies = 6;        //第一关敌人总数
	public int level_2_enemies = 12;        //第二关敌人总数
	public int level_3_enemies = 14;        //第三关敌人总数
	public int level_4_enemies = 14;        //第四关敌人总数
	public int level_5_enemies = 18;        //第五关敌人总数

	//被攻击次数
	public int attacks = 0;

	//孙悟空的总血量
	public final static int BLOOD = 8;
	public static int HP = BLOOD;

	//孙悟空的MP
	public final static int MP_VALUE = 8;
	public static int MP = MP_VALUE;

	//攻击动画总时间
	public final static float ATTACKTIME = 2f;

	public Monkey(Body body) {
		super(body);

		STATE = State.STATE_IDEL_RIGHT;

		mMonkeyAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.SUN);

		//初始化静止图片
		mLeftStandState = new TextureAtlas.AtlasRegion[2];
		mLeftStandState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftStand"));
		mLeftStandState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftStand"));

		mRightStandState = new TextureAtlas.AtlasRegion[2];
		mRightStandState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));
		mRightStandState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));

		//初始化右走图片
		mRightState = new TextureAtlas.AtlasRegion[3];
		mRightState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW1"));
		mRightState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW2"));
		mRightState[2] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW3"));

		//初始化左走图片
		mLeftState = new TextureAtlas.AtlasRegion[3];
		mLeftState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftW1"));
		mLeftState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftW2"));
		mLeftState[2] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftW3"));

		//初始化左攻击图像
		mLeftAttackState = new TextureAtlas.AtlasRegion[2];
		mLeftAttackState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftAttack1"));
		mLeftAttackState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftAttack2"));

		//初始化右攻击图像
		mRightAttackState = new TextureAtlas.AtlasRegion[2];
		mRightAttackState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightAttack1"));
		mRightAttackState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightAttack2"));

		//左边被击飞动画
		mLeftHittedState = new TextureAtlas.AtlasRegion[2];
		mLeftHittedState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftHited"));
		mLeftHittedState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunLeftHited"));

		//右边被击飞动画
		mRightHittedState = new TextureAtlas.AtlasRegion[2];
		mRightHittedState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightHited"));
		mRightHittedState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightHited"));
	}

	@Override
	public void update(float delta) {

		if (STATE == State.STATE_IDEL_RIGHT || STATE == State.STATE_IDEL_LEFT) {
			if (STATE == State.STATE_IDEL_RIGHT) {
				setAnimation(mRightStandState, 1 / 12f);
			} else {
				setAnimation(mLeftStandState, 1 / 12f);
			}
		} else if (STATE == State.STATE_LEFT) {
			setAnimation(mLeftState, 1 / 12f);
		} else if (STATE == State.STATE_RIGHT) {
			setAnimation(mRightState, 1 / 12f);
		} else if (STATE == State.STATE_LEFT_ATTACK) {
			setAnimation(mLeftAttackState, 1f);
		} else if (STATE == State.STATE_RIGHT_ATTACK) {
			setAnimation(mRightAttackState, 1f);
		} else if (STATE == State.STATE_LEFT_HITED) {
			setAnimation(mLeftHittedState, 1 / 12f);
		} else if (STATE == State.STATE_RIGHT_HITED) {
			setAnimation(mRightHittedState, 1 / 12f);
		}

		//设置孙悟空一直是苏醒状态
		mBody.setAwake(true);
	}

	//打败敌人分数加1
	public void beatEnemy() {
		enemyCount++;
	}

	//打败Boss分数加2
	public void beatBoss() {
		enemyCount += 2;
	}

	//获得打败敌人数目
	public int getEnemyCount() {
		return enemyCount;
	}

	//获得敌人总数
	public int getEnemiesCount() {
		return allEnemiesCount;
	}

	//设置敌人数目
	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	//设置敌人总数
	public void setAllEnemiesCount(int allEnemiesCount) {
		this.allEnemiesCount = allEnemiesCount;
	}

	//孙悟空血量加1
	public void addBlood() {
		if (HP == BLOOD) {
			return;
		}
		HP++;
	}

	//孙悟空MP加1
	public void addMP() {
		if (MP == MP_VALUE) {
			return;
		}
		MP++;
	}
}

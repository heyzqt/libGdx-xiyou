package com.heyzqt.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.FireBallController;
import com.heyzqt.handle.State;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 主角:孙悟空
 */
public class Monkey extends BaseSprite {

	private World mWorld;

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
	//左 火球动画
	private TextureAtlas.AtlasRegion[] mLeftFireballState;
	//右 火球动画
	private TextureAtlas.AtlasRegion[] mRightFireballState;
	//右 跳跃动画
	private TextureAtlas.AtlasRegion[] mRightJumpAtkState;
	//左 跳跃动画
	private TextureAtlas.AtlasRegion[] mLeftJumpAtkState;

	//动画
	private Animation mRightStandAni;
	private Animation mLeftStandAni;
	private Animation mRightAni;
	private Animation mLeftAni;
	private Animation mLeftAttackAni;
	private Animation mRightAttackAni;
	private Animation mLeftHittedAni;
	private Animation mRightHittedAni;
	private Animation mLeftFireballAni;
	private Animation mRightFireballAni;
	private Animation mLeftJumpHitAni;
	private Animation mRightJumpHitAni;

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

	//孙悟空的总血量
	public final static int BLOOD = 8;
	public static int HP = BLOOD;

	//孙悟空的MP
	public final static int MP_VALUE = 8;
	public static int MP = MP_VALUE;

	//火球Controller
	public FireBallController mBallController;

	//升龙斩
	public JumpBall mJumpBall;

	//是否攻击
	public boolean isAttacked = false;

	//是否跳跃
	public boolean isJump = false;

	//攻击夹具
	private Fixture mAttackFixture;

	//升龙斩光波夹具
	private Fixture mJumpAtkFix;

	//计算动画时间
	public float monkeytime = 0;

	public Monkey(Body body) {
		super(body);

		mWorld = body.getWorld();

		STATE = State.STATE_IDEL_RIGHT;

		mMonkeyAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.SUN);

		//初始化静止图片
		mRightStandState = new TextureAtlas.AtlasRegion[2];
		mRightStandState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));
		mRightStandState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));

		mLeftStandState = new TextureAtlas.AtlasRegion[2];
		mLeftStandState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));
		mLeftStandState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));
		mLeftStandState[0].flip(true, false);
		mLeftStandState[1].flip(true, false);

		//初始化右走图片
		mRightState = new TextureAtlas.AtlasRegion[3];
		mRightState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW1"));
		mRightState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW2"));
		mRightState[2] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW3"));

		//初始化左走图片
		mLeftState = new TextureAtlas.AtlasRegion[3];
		mLeftState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW1"));
		mLeftState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW2"));
		mLeftState[2] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW3"));
		for (int i = 0; i < mLeftState.length; i++) {
			mLeftState[i].flip(true, false);
		}

		//右边被击飞动画
		mRightHittedState = new TextureAtlas.AtlasRegion[2];
		mRightHittedState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightHited"));
		mRightHittedState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightHited"));

		//左边被击飞动画
		mLeftHittedState = new TextureAtlas.AtlasRegion[2];
		mLeftHittedState[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightHited"));
		mLeftHittedState[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightHited"));
		for (int i = 0; i < mLeftHittedState.length; i++) {
			mLeftHittedState[i].flip(true, false);
		}

		//初始化右攻击图像
		mRightAttackState = new TextureAtlas.AtlasRegion[6];
		for (int i = 0; i < mRightAttackState.length; i++) {
			mRightAttackState[i] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightAttack" + (i + 1)));
		}

		//初始化左攻击图像
		mLeftAttackState = new TextureAtlas.AtlasRegion[6];
		for (int i = 0; i < mLeftAttackState.length; i++) {
			mLeftAttackState[i] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightAttack" + (i + 1)));
			mLeftAttackState[i].flip(true, false);
		}

		//右 火球攻击
		mRightFireballState = new TextureAtlas.AtlasRegion[5];
		for (int i = 0; i < mRightFireballState.length; i++) {
			mRightFireballState[i] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightFireAttack" + (i + 1)));
		}

		//左 火球攻击
		mLeftFireballState = new TextureAtlas.AtlasRegion[5];
		for (int i = 0; i < mLeftFireballState.length; i++) {
			mLeftFireballState[i] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightFireAttack" + (i + 1)));
			mLeftFireballState[i].flip(true, false);
		}

		//右 升龙斩攻击
		mRightJumpAtkState = new TextureAtlas.AtlasRegion[10];
		for (int i = 0; i < 8; i++) {
			mRightJumpAtkState[i] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightJumAttack" + (i + 1)));
		}
		mRightJumpAtkState[8] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightJumAttack8"));
		mRightJumpAtkState[9] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightJumAttack8"));
		//左 升龙斩攻击
		mLeftJumpAtkState = new TextureAtlas.AtlasRegion[10];
		for (int i = 0; i < 8; i++) {
			mLeftJumpAtkState[i] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightJumAttack" + (i + 1)));
			mLeftJumpAtkState[i].flip(true, false);
		}
		mLeftJumpAtkState[8] = mLeftJumpAtkState[7];
		mLeftJumpAtkState[9] = mLeftJumpAtkState[7];

		//初始化所有动画
		mRightStandAni = new Animation(1 / 12f, mRightStandState);
		mLeftStandAni = new Animation(1 / 12f, mLeftStandState);
		mRightAni = new Animation(1 / 12f, mRightState);
		mLeftAni = new Animation(1 / 12f, mLeftState);
		mLeftAttackAni = new Animation(1 / 18f, mLeftAttackState);
		mRightAttackAni = new Animation(1 / 18f, mRightAttackState);
		mLeftHittedAni = new Animation(1 / 12f, mLeftHittedState);
		mRightHittedAni = new Animation(1 / 12f, mRightHittedState);
		mLeftFireballAni = new Animation(1 / 15f, mLeftFireballState);
		mRightFireballAni = new Animation(1 / 15f, mRightFireballState);
		mRightJumpHitAni = new Animation(1 / 12f, mRightJumpAtkState);
		mLeftJumpHitAni = new Animation(1 / 12f, mLeftJumpAtkState);

		//初始化火球
		mBallController = new FireBallController();

		//初始化升龙斩
		mJumpBall = new JumpBall(this);
	}

	@Override
	public void update(float delta) {

		monkeytime += Gdx.graphics.getDeltaTime();

		//设置孙悟空一直是苏醒状态
		mBody.setAwake(true);

		//一组普通攻击动画完成
		if (STATE == State.STATE_RIGHT_ATTACK && mRightAttackAni.isAnimationFinished(delta)) {
			STATE = State.STATE_IDEL_RIGHT;
			monkeytime = 0;
		} else if (STATE == State.STATE_LEFT_ATTACK && mLeftAttackAni.isAnimationFinished(delta)) {
			STATE = State.STATE_IDEL_LEFT;
			monkeytime = 0;
		}
		//在攻击动画最后一帧创建攻击夹具
		int frameNumber1 = (int) (monkeytime / mRightAttackAni.getFrameDuration());
		if (STATE == State.STATE_RIGHT_ATTACK && isAttacked && frameNumber1 == mRightAttackState.length - 1) {
			createStick();
			isAttacked = false;
		} else if (STATE == State.STATE_LEFT_ATTACK && isAttacked && frameNumber1 == mLeftAttackState.length - 1) {
			createStick();
			isAttacked = false;
		}
		//销毁攻击夹具
		if (STATE != State.STATE_LEFT_ATTACK && STATE != State.STATE_RIGHT_ATTACK) {
			destroyStick();
		}

		//一组火球攻击动画完成
		if (STATE == State.STATE_RIGHT_FIREBALL && mRightFireballAni.isAnimationFinished(delta)) {
			STATE = State.STATE_IDEL_RIGHT;
			monkeytime = 0;
		} else if (STATE == State.STATE_LEFT_FIREBALL && mLeftFireballAni.isAnimationFinished(delta)) {
			STATE = State.STATE_IDEL_LEFT;
			monkeytime = 0;
		}
		//在火球攻击动画最后一帧创建火球
		int frameNumber2 = (int) (monkeytime / mRightFireballAni.getFrameDuration());
		if (STATE == State.STATE_RIGHT_FIREBALL && isAttacked && frameNumber2 == mRightFireballState.length - 1) {
			createFireBall();
			MyGdxGame.assetManager.getSound(Constant.FIREBALL_SOUND).play();
			isAttacked = false;
		} else if (STATE == State.STATE_LEFT_FIREBALL && isAttacked && frameNumber2 == mLeftFireballState.length - 1) {
			createFireBall();
			MyGdxGame.assetManager.getSound(Constant.FIREBALL_SOUND).play();
			isAttacked = false;
		}

		//升龙斩攻击
		if (STATE == State.STATE_RIGHT_JUMP_ATTACK && mRightJumpHitAni.isAnimationFinished(delta)) {
			STATE = State.STATE_IDEL_RIGHT;
			monkeytime = 0;
		} else if (STATE == State.STATE_LEFT_JUMP_ATTACK && mLeftJumpHitAni.isAnimationFinished(delta)) {
			STATE = State.STATE_IDEL_LEFT;
			monkeytime = 0;
		}
		int frameNumber3 = (int) (monkeytime / mRightJumpHitAni.getFrameDuration());
		//跳跃
		if (STATE == State.STATE_RIGHT_JUMP_ATTACK && isJump && frameNumber3 == mRightJumpAtkState.length - 4) {
			createJumpStick();
			mBody.applyForceToCenter(0, 200, true);
			mJumpBall.STATE = State.STATE_RIGHT;
			isJump = false;
		} else if (STATE == State.STATE_LEFT_JUMP_ATTACK && isJump && frameNumber3 == mLeftJumpAtkState.length - 4) {
			createJumpStick();
			mBody.applyForceToCenter(0, 200, true);
			mJumpBall.STATE = State.STATE_LEFT;
			isJump = false;
		}
		//攻击完毕
		if (STATE == State.STATE_RIGHT_JUMP_ATTACK && isAttacked && frameNumber3 == mRightJumpAtkState.length - 1) {
			isAttacked = false;
		} else if (STATE == State.STATE_LEFT_JUMP_ATTACK && isAttacked && frameNumber3 == mLeftJumpAtkState.length - 1) {
			isAttacked = false;
		}
		//销毁攻击夹具
		if (STATE != State.STATE_RIGHT_JUMP_ATTACK && STATE != State.STATE_LEFT_JUMP_ATTACK) {
			destroyJumpStick();
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		update(delta);

		batch.begin();
		switch (STATE) {
			case State.STATE_IDEL_RIGHT:
				setFrame(batch, mRightStandAni, mRightStandState, delta);
				break;
			case State.STATE_IDEL_LEFT:
				setFrame(batch, mLeftStandAni, mLeftStandState, delta);
				break;
			case State.STATE_RIGHT:
				setFrame(batch, mRightAni, mRightState, delta);
				break;
			case State.STATE_LEFT:
				setFrame(batch, mLeftAni, mLeftState, delta);
				break;
			case State.STATE_RIGHT_HITED:
				setFrame(batch, mRightHittedAni, mRightHittedState, delta);
				break;
			case State.STATE_LEFT_HITED:
				setFrame(batch, mLeftHittedAni, mLeftHittedState, delta);
				break;
			case State.STATE_RIGHT_ATTACK:
				setFrame(batch, mRightAttackAni, mRightAttackState, delta);
				break;
			case State.STATE_LEFT_ATTACK:
				setFrame(batch, mLeftAttackAni, mLeftAttackState, delta);
				break;
			case State.STATE_RIGHT_FIREBALL:
				setFrame(batch, mRightFireballAni, mRightFireballState, delta);
				break;
			case State.STATE_LEFT_FIREBALL:
				setFrame(batch, mLeftFireballAni, mLeftFireballState, delta);
				break;
			case State.STATE_RIGHT_JUMP_ATTACK:
				setFrame(batch, mRightJumpHitAni, mRightJumpAtkState, delta);
				//升龙斩攻击光球
				int frame = (int) (monkeytime / mRightJumpHitAni.getFrameDuration());
				if (frame == mRightJumpAtkState.length - 3) {
					mJumpBall.render(batch, delta);
				}
				break;
			case State.STATE_LEFT_JUMP_ATTACK:
				setFrame(batch, mLeftJumpHitAni, mLeftJumpAtkState, delta);
				//升龙斩攻击光球
				int frame1 = (int) (monkeytime / mLeftJumpHitAni.getFrameDuration());
				if (frame1 == mLeftJumpAtkState.length - 3) {
					mJumpBall.render(batch, delta);
				}
				break;
		}
		batch.end();
	}

	private void setFrame(SpriteBatch batch, Animation animation, TextureRegion[] regions, float delta) {
		mWidth = regions[0].getRegionWidth();
		mHeight = regions[0].getRegionHeight();
		batch.draw(animation.getKeyFrame(delta, true),
				mBody.getPosition().x * Constant.RATE - mWidth / 2,
				mBody.getPosition().y * Constant.RATE - mHeight / 2);
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

	/**
	 * 创建攻击夹具
	 */
	public void createStick() {
		//创建攻击传感器 stick
		PolygonShape shape = new PolygonShape();
		if (STATE == State.STATE_RIGHT_ATTACK) {
			shape.setAsBox(45 / Constant.RATE, 5 / Constant.RATE
					, new Vector2(60 / Constant.RATE, 0), 0);
		} else {
			shape.setAsBox(45 / Constant.RATE, 5 / Constant.RATE
					, new Vector2(-60 / Constant.RATE, 0), 0);
		}
		FixtureDef attackFixDef = new FixtureDef();
		attackFixDef.shape = shape;
		attackFixDef.filter.categoryBits = Constant.PLAYER;
		attackFixDef.filter.maskBits = Constant.ENEMY_DAO | Constant.BOSS;
		attackFixDef.isSensor = true;
		mAttackFixture = mBody.createFixture(attackFixDef);
		mAttackFixture.setUserData("stick");
	}

	//销毁攻击夹具
	public void destroyStick() {
		if (mAttackFixture != null) {
			mBody.destroyFixture(mAttackFixture);
			mAttackFixture = null;
		}
	}

	/**
	 * 创建火球刚体
	 */
	private Body createFireBallBody(World world) {
		BodyDef ballDef = new BodyDef();
		Body ballBody;
		FixtureDef ballFixDef = new FixtureDef();
		Fixture ballFix;
		PolygonShape ballShape = new PolygonShape();

		//设置形状
		ballDef.type = BodyDef.BodyType.KinematicBody;
		//position是刚体中心点的位置
		if (STATE == State.STATE_RIGHT_FIREBALL) {
			ballDef.position.set(mBody.getPosition().x + 0.5f, mBody.getPosition().y);
		} else {
			ballDef.position.set(mBody.getPosition().x - 0.5f, mBody.getPosition().y);
		}

		ballBody = world.createBody(ballDef);
		ballShape.setAsBox(50 / Constant.RATE, 20 / Constant.RATE);
		ballFixDef.shape = ballShape;
		ballFixDef.filter.categoryBits = Constant.FIREBALL;
		ballFixDef.filter.maskBits = Constant.ENEMY_DAO | Constant.BOSS;
		ballFixDef.isSensor = true;
		ballFix = ballBody.createFixture(ballFixDef);
		ballFix.setUserData("ball");
		return ballBody;
	}

	//创建火球
	public void createFireBall() {
		//创建刚体
		Body ballBody = createFireBallBody(mWorld);
		//设置火球出现位置
		FireBall ball;
		if (STATE == State.STATE_RIGHT_FIREBALL) {
			ball = mBallController.createFireBall(ballBody, State.STATE_RIGHT);
		} else {
			ball = mBallController.createFireBall(ballBody, State.STATE_LEFT);
		}
		ballBody.setUserData(ball);
		mBallController.addFireBalls(ball);
	}

	/**
	 * 创建升龙斩攻击夹具
	 */
	public void createJumpStick() {
		//创建攻击传感器 stick
		PolygonShape shape = new PolygonShape();
		if (STATE == State.STATE_RIGHT_JUMP_ATTACK) {
			shape.setAsBox(5 / Constant.RATE, 40 / Constant.RATE
					, new Vector2(100 / Constant.RATE, 20 / Constant.RATE), 0);
		} else {
			shape.setAsBox(5 / Constant.RATE, 45 / Constant.RATE
					, new Vector2(-100 / Constant.RATE, 20 / Constant.RATE), 0);
		}
		FixtureDef attackFixDef = new FixtureDef();
		attackFixDef.shape = shape;
		attackFixDef.filter.categoryBits = Constant.PLAYER;
		attackFixDef.filter.maskBits = Constant.ENEMY_DAO | Constant.BOSS;
		attackFixDef.isSensor = true;
		mJumpAtkFix = mBody.createFixture(attackFixDef);
		mJumpAtkFix.setUserData("ball");
	}

	//销毁升龙斩攻击夹具
	public void destroyJumpStick() {
		if (mJumpAtkFix != null) {
			mBody.destroyFixture(mJumpAtkFix);
			mJumpAtkFix = null;
		}
	}

	/**
	 * @return true右边 false 左边
	 */
	public boolean isRight() {
		if (STATE == State.STATE_IDEL_RIGHT || STATE == State.STATE_RIGHT ||
				STATE == State.STATE_RIGHT_ATTACK || STATE == State.STATE_RIGHT_HITED
				|| STATE == State.STATE_RIGHT_FIREBALL || STATE == State.STATE_RIGHT_JUMP_ATTACK) {
			return true;
		}
		return false;
	}
}

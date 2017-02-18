package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;
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

	//孙悟空4种状态
	public static int STATE;
	public static int STATE_IDEL_LEFT = 1;
	public static int STATE_IDEL_RIGHT = 2;
	public static int STATE_LEFT = 3;
	public static int STATE_RIGHT = 4;

	//击败敌人数目
	private int enemyCount;

	//敌人总数
	private int allEnemiesCount;

	public Monkey(Body body) {
		super(body);

		STATE = STATE_IDEL_RIGHT;

		mMonkeyAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.ROLE);

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
	}

	@Override
	public void update(float delta) {
		if (STATE == STATE_IDEL_RIGHT || STATE == STATE_IDEL_LEFT) {
			if (STATE == STATE_IDEL_RIGHT) {
				setAnimation(mRightStandState, 1 / 12f);
			} else {
				setAnimation(mLeftStandState, 1 / 12f);
			}
		} else if (STATE == STATE_LEFT) {
			setAnimation(mLeftState, 1 / 12f);
		} else if (STATE == STATE_RIGHT) {
			setAnimation(mRightState, 1 / 12f);
		}
	}

	//打败敌人
	public void beatEnemy(){
		enemyCount++;
	}

	//获得打败敌人数目
	public int getEnemyCount(){
		return enemyCount;
	}

	//获得敌人总数
	public int getEnemiesCount(){
		return allEnemiesCount;
	}

	//设置敌人数目
	public void setEnemyCount(int enemyCount){
		this.enemyCount = enemyCount;
	}

	//设置敌人总数
	public void setAllEnemiesCount(int allEnemiesCount){
		this.allEnemiesCount = allEnemiesCount;
	}

}

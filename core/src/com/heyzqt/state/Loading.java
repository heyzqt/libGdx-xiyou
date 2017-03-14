package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/3/13.
 *
 * 加载界面
 */
public class Loading extends GameState {

	//所有控件素材
	private TextureAtlas mAtlas;

	//背景
	private Texture mBackground;

	//人物
	private TextureAtlas.AtlasRegion mRole;

	//进度条控件
	private TextureAtlas.AtlasRegion mBarBg;
	private TextureAtlas.AtlasRegion mBeforeBg;
	private TextureAtlas.AtlasRegion mAfterBg;
	private TextureAtlas.AtlasRegion mFlag;

	//时间
	private float preTime = 0;
	private float stateTime = 0;
	private int ENDTIME = 5;

	//进度条进度
	private int mProgress = 0;

	//判断更新
	private boolean isFirstUpdated = true;
	//判断跳转
	private boolean isJump = true;

	//预加载Play界面
	private Play mPlay;

	public Loading(GameStateManager manager) {
		super(manager);
		init();
	}

	public void init() {
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.LOADING_WIDGET);

		//背景音乐
		MyGdxGame.mAssetManager.getMusic(Constant.START_BGM).play();

		//背景
		mBackground = MyGdxGame.mAssetManager.getTexture(Constant.LOADING_BG);

		//加载人物资源
		switch (Play.level) {
			case 0:
				mRole = mAtlas.findRegion("sun");
				break;
			case 1:
				mRole = mAtlas.findRegion("tang");
				break;
			case 2:
				mRole = mAtlas.findRegion("sha");
				break;
			case 3:
				mRole = mAtlas.findRegion("ba");
				break;
			case 4:
				mRole = mAtlas.findRegion("sun");
				break;
		}

		//加载进度条资源
		mBarBg = mAtlas.findRegion("frame");
		mBeforeBg = mAtlas.findRegion("before");
		mAfterBg = mAtlas.findRegion("after");
		mFlag = mAtlas.findRegion("flag");

		//预加载Play界面
		mPlay = new Play(mGameStateManager);
	}

	@Override
	public void update(float delta) {
		if (isFirstUpdated) {
			preTime = delta;
			isFirstUpdated = false;
		}
	}

	@Override
	public void render() {
		//清屏
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += Gdx.graphics.getDeltaTime();
		mProgress = (int) (stateTime - preTime);

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		//背景
		mBatch.draw(mBackground, 0, 0);
		//人物
		mBatch.draw(mRole, MyGdxGame.VIEW_WIDTH / 2 - mRole.getRegionWidth() / 2 - 30,
				MyGdxGame.VIEW_HEIGHT / 2 - mRole.getRegionHeight() / 2 + 110);

		//进度条
		mBatch.draw(mAfterBg, 145, 91);
		//before总长度660 分5次加载 每次132px
		if (mProgress <= 5) {
			mBatch.draw(mBeforeBg, 150, 90, 132 * mProgress, 30);
		} else {
			mBatch.draw(mBeforeBg, 150, 90, 660, 30);
		}
		mBatch.draw(mBarBg, 125, 80);
		//flag
		if (mProgress <= 5) {
			mBatch.draw(mFlag, 150 + 132 * mProgress - mFlag.getRegionWidth() / 2, 90, 70, 97);
		} else {
			mBatch.draw(mFlag, 810 - mFlag.getRegionWidth() / 2, 90, 70, 97);
		}
		mBatch.end();

		//5秒后跳转到游戏界面
		if (mProgress > ENDTIME && isJump) {
			//出栈 加载界面
			mGameStateManager.popState();
			mGameStateManager.pushState(mPlay);
			isJump = false;
		}
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {
		//关闭背景音乐
		MyGdxGame.mAssetManager.getMusic(Constant.START_BGM).stop();

		//游戏界面音乐播放
		mPlay.initMusic();

		mBackground = null;
		mAtlas = null;
	}
}

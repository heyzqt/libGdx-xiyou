package com.heyzqt.xiyou;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.heyzqt.handle.AssetManager;
import com.heyzqt.handle.Constant;
import com.heyzqt.state.GameStateManager;

public class MyGdxGame extends ApplicationAdapter {

	private SpriteBatch mBatch;
	private Stage mStage;
	private OrthographicCamera mCamera;
	private OrthographicCamera mUICamera;

	public final static int VIEW_WIDTH = 960;
	public final static int VIEW_HEIGHT = 640;

	private GameStateManager mManager;

	public static AssetManager mAssetManager;

	@Override
	public void create() {

		loadAssets();

		mBatch = new SpriteBatch();
		mStage = new Stage();
		Gdx.input.setInputProcessor(mStage);

		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);
		mUICamera = new OrthographicCamera();
		mUICamera.setToOrtho(false, VIEW_WIDTH, VIEW_HEIGHT);

		mManager = new GameStateManager(this);
	}

	private void loadAssets() {
		mAssetManager = new AssetManager();

		//加载字体
		mAssetManager.loadFont();

		//加载按钮音效
		mAssetManager.loadSound("sound/btn_common.wav", Constant.BTN_COMMON_SOUND);
		mAssetManager.loadSound("sound/btn_select.wav", Constant.BTN_SELECT_SOUND);
		//加载第一关背景音乐
		mAssetManager.loadMusic("sound/bgm_level_0.mp3", Constant.LEVEL_0_BGM);

		//加载开始界面Start.java资源
		mAssetManager.loadTexture("background/start_bg.png", Constant.START_BG);
		mAssetManager.loadTextureAtlas("widget/start_setting.atlas", Constant.START_SETTING);
		mAssetManager.loadMusic("sound/bgm_main.mp3", Constant.START_BGM);

		//加载选关界面Select.java资源
		mAssetManager.loadTexture("background/world_map_bg.jpg", Constant.SELECT_BG);
		mAssetManager.loadTextureAtlas("widget/select.atlas", Constant.SELECT_WIDGET);

		//加载失败界面Failure.java资源
		mAssetManager.loadSound("sound/bgm_lose.mp3", Constant.FAILURE_BGM);
		mAssetManager.loadTexture("background/failure_bg.png", Constant.FAILURE_BG);
		mAssetManager.loadTextureAtlas("widget/failure.atlas", Constant.FAILURE_WIDGET);

		//加载成功界面Success.java资源
		mAssetManager.loadSound("sound/bgm_win.mp3", Constant.SUCCESS_BGM);
		mAssetManager.loadTexture("background/success_bg.png", Constant.SUCCESS_BG);
		mAssetManager.loadTextureAtlas("widget/success.atlas", Constant.SUCCESS_WIDGET);

		//加载游戏界面Play.java资源
		mAssetManager.loadNumFont();
		mAssetManager.loadTextureAtlas("widget/playBlood.atlas", Constant.PLAY_BLOOD);
		mAssetManager.loadTexture("map/level_static_2_bg.png", Constant.PLAY_BG);
		//加载孙悟空人物素材
		mAssetManager.loadTextureAtlas("role/sun.atlas", Constant.SUN);
		//加载持刀天兵人物素材
		mAssetManager.loadTextureAtlas("role/enemyDao.atlas", Constant.ENEMY_DAO_ROLE);
		mAssetManager.loadTextureAtlas("widget/playWidget.atlas", Constant.PLAY_WIDGET);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mManager.update(Gdx.graphics.getDeltaTime());
		mManager.render();
	}

	@Override
	public void dispose() {
	}

	public Stage getStage() {
		return mStage;
	}

	public SpriteBatch getSpriteBatch() {
		return mBatch;
	}

	public OrthographicCamera getCamera() {
		return mCamera;
	}

	public OrthographicCamera getUICamera() {
		return mUICamera;
	}

}

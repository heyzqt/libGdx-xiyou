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

	//舞台宽高
	public final static int VIEW_WIDTH = 960;
	public final static int VIEW_HEIGHT = 640;

	//屏幕大小
	public final static int SCREEN_WIDTH = 1280;
	public final static int SCREEN_HEIGHT = 720;

	private GameStateManager mManager;

	public static AssetManager assetManager;
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
		assetManager = AssetManager.getInstance();

		//加载字体
		assetManager.loadFont();

		//加载按钮音效
		assetManager.loadSound("sound/btn_common.wav", Constant.BTN_COMMON_SOUND);
		assetManager.loadSound("sound/btn_select.wav", Constant.BTN_SELECT_SOUND);
		//加载第一关背景音乐
		assetManager.loadMusic("sound/bgm_level_0.mp3", Constant.LEVEL_0_BGM);
		//加载第二、三、四关背景音乐
		assetManager.loadMusic("sound/bgm_level_1.mp3", Constant.LEVEL_1_BGM);
		//加载第五关背景音乐
		assetManager.loadMusic("sound/bgm_level_2.mp3", Constant.LEVEL_2_BGM);
		//加载击打音效
		assetManager.loadSound("sound/sfx_enemy_get_hurt.mp3", Constant.SOUND_ENEMY_GET_HURT);
		assetManager.loadSound("sound/sfx_role_get_hurt_1.mp3", Constant.SOUND_SUN_GET_HURT);

		//加载开始界面Start.java资源
		assetManager.loadTexture("background/start_bg.png", Constant.START_BG);
		assetManager.loadTextureAtlas("widget/start_setting.atlas", Constant.START_SETTING);
		assetManager.loadMusic("sound/bgm_main.mp3", Constant.START_BGM);

		//加载设置界面对话框
		assetManager.loadTexture("background/aboutDialog.png", Constant.SETTING_DIALOG);

		//加载选关界面Select.java资源
		assetManager.loadTexture("background/world_map_bg.jpg", Constant.SELECT_BG);
		assetManager.loadTextureAtlas("widget/select.atlas", Constant.SELECT_WIDGET);

		//加载失败界面Failure.java资源
		assetManager.loadSound("sound/bgm_lose.mp3", Constant.FAILURE_BGM);
		assetManager.loadTexture("background/failure_bg.png", Constant.FAILURE_BG);
		assetManager.loadTextureAtlas("widget/failure.atlas", Constant.FAILURE_WIDGET);

		//加载成功界面Success.java资源
		assetManager.loadSound("sound/bgm_win.mp3", Constant.SUCCESS_BGM);
		assetManager.loadTexture("background/success_bg.png", Constant.SUCCESS_BG);
		assetManager.loadTextureAtlas("widget/success.atlas", Constant.SUCCESS_WIDGET);

		//加载游戏界面Play.java资源
		assetManager.loadNumFont();
		assetManager.loadTextureAtlas("widget/playBlood.atlas", Constant.PLAY_BLOOD);
		assetManager.loadTexture("map/level_0_bg.png", Constant.FIRST_GAME_BG);
		assetManager.loadTexture("map/level_1_bg.png", Constant.SECOND_GAME_BG);
		//加载孙悟空人物素材
		assetManager.loadTextureAtlas("role/sun.atlas", Constant.SUN);
		//加载天兵人物素材
		assetManager.loadTextureAtlas("role/enemyDao.atlas", Constant.ENEMY_DAO_ROLE);
		assetManager.loadTextureAtlas("role/enemyFu.atlas", Constant.ENEMY_FU_ROLE);
		assetManager.loadTextureAtlas("role/enemyQiang.atlas", Constant.ENEMY_QIANG_ROLE);
		//加载Boss人物素材
		assetManager.loadTextureAtlas("role/julingBoss.atlas", Constant.BOSS_JULING_ROLE);
		assetManager.loadTextureAtlas("role/zengBoss.atlas", Constant.BOSS_ZENGZHANG_ROLE);
		assetManager.loadTextureAtlas("role/guangBoss.atlas", Constant.BOSS_GUANGMU_ROLE);
		assetManager.loadTextureAtlas("role/duoBoss.atlas", Constant.BOSS_DUOWEN_ROLE);
		assetManager.loadTextureAtlas("role/erlangBoss.atlas", Constant.BOSS_ERLANG_ROLE);
		//加载游戏摇杆素材
		assetManager.loadTextureAtlas("widget/playWidget.atlas", Constant.PLAY_WIDGET);
		//加载火球素材
		assetManager.loadTextureAtlas("widget/fireball.atlas", Constant.FIREBALL_WIDGET);
		assetManager.loadSound("sound/fireball.mp3", Constant.FIREBALL_SOUND);
		//加载攻击音效
		assetManager.loadSound("sound/attack.mp3", Constant.ATTACK_SOUND);
		//加载升龙斩音效
		assetManager.loadSound("sound/jumpball.mp3", Constant.JUMPBALL_SOUND);

		//加载排行榜素材
		assetManager.loadTextureAtlas("widget/range.atlas", Constant.RANGE_WIDGET);
		assetManager.loadTexture("background/range_bg.png", Constant.RANGE_BG);

		//加载输入输入姓名对话框背景
		assetManager.loadTexture("background/inputname_bg.png", Constant.INPUT_NAME_BG);

		//加载界面背景和游戏素材
		assetManager.loadTexture("background/loading_bg.png", Constant.LOADING_BG);
		assetManager.loadTextureAtlas("widget/loading.atlas", Constant.LOADING_WIDGET);
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

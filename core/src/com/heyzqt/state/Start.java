package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.widget.SettingDialog;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 * 开始设置界面
 */
public class Start extends GameState {

	//开始界面+设置界面 控件图片集
	private TextureAtlas mAtlas;

	//开始界面按钮
	private ImageButton mStartBtn;
	private ImageButton mSettingBtn;

	//设置界面
	//是否打开音乐
	private CheckBox mCheckBox;
	public static boolean isPlay = true;
	//关于我们
	private ImageButton mAboutBtn;
	private SettingDialog mSettingDialog;
	//返回主界面按钮
	private ImageButton mBackButton;

	/**
	 * 判断当前界面 true 开始界面 false 设置界面
	 */
	public static boolean isStart = true;

	public static boolean isShowDialog = false;

	public Start(GameStateManager gsm) {
		super(gsm);
		init();
	}

	private void init() {

		//开始界面控件初始化
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.START_SETTING);
		mStartBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("startBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("startBtnDown")));
		mSettingBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("settingBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("settingBtnDown")));
		mStartBtn.setSize(280, 100);
		mSettingBtn.setSize(280, 100);
		mStartBtn.setPosition(mGame.VIEW_WIDTH / 2 + 20, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 100);
		mSettingBtn.setPosition(mGame.VIEW_WIDTH / 2 + 26, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 180);

		//设置界面初始化 - 是否打开音效
		Drawable checkOn = new TextureRegionDrawable(mAtlas.findRegion("musicBtnOn"));
		Drawable checkOff = new TextureRegionDrawable(mAtlas.findRegion("musicBtnOff"));
		CheckBox.CheckBoxStyle boxStyle = new CheckBox.CheckBoxStyle(checkOff, checkOn,
				MyGdxGame.mAssetManager.getFont(), Color.BLUE);
		mCheckBox = new CheckBox("", boxStyle);
		mCheckBox.setSize(255, 100);
		mCheckBox.setPosition(mGame.VIEW_WIDTH / 2 + 38, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 40);
		if (isPlay) {
			mCheckBox.setChecked(true);
		} else {
			mCheckBox.setChecked(false);
		}

		//设置界面初始化 - 关于我们
		mAboutBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("aboutBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("aboutBtnDown")));
		mAboutBtn.setSize(280, 100);
		mAboutBtn.setPosition(mGame.VIEW_WIDTH / 2 + 26, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 110);
		mSettingDialog = new SettingDialog(MyGdxGame.VIEW_WIDTH / 2, MyGdxGame.VIEW_HEIGHT / 2);

		//设置界面初始化 - 返回按钮
		mBackButton = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("backStartBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("backStartBtnDown")));
		mBackButton.setSize(280, 100);
		mBackButton.setPosition(mGame.VIEW_WIDTH / 2 + 26, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 180);

		//背景音乐
		Music music = MyGdxGame.mAssetManager.getMusic(Constant.START_BGM);
		music.setLooping(true);
		music.play();

		//初始化监听
		initListener();
	}

	private void initListener() {
		//开始游戏按钮
		mStartBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MyGdxGame.mAssetManager.getSound(Constant.BTN_COMMON_SOUND);
				sound.play();
				mGameStateManager.setState(GameStateManager.SELECT);
			}
		});

		//设置按钮
		mSettingBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				isStart = false;
				Sound sound = MyGdxGame.mAssetManager.getSound(Constant.BTN_COMMON_SOUND);
				sound.play();
			}
		});

		//关于按钮
		mAboutBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				isShowDialog = true;
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//这里必须要touchDown()方法返回true 否则对话框无法显示
				return true;
			}
		});

		//是否打开音乐
		mCheckBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (isPlay) {
					isPlay = false;
				} else {
					isPlay = true;
				}
			}
		});

		//返回开始界面按钮
		mBackButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				isStart = true;
				Sound sound = MyGdxGame.mAssetManager.getSound(Constant.BTN_COMMON_SOUND);
				sound.play();
			}
		});
	}

	@Override
	public void update(float delta) {

		//清空舞台
		mStage.getActors().clear();

		//开始界面
		if (isStart) {
			mStage.addActor(mStartBtn);
			mStage.addActor(mSettingBtn);
		} else {    //设置界面
			mStage.addActor(mCheckBox);
			mStage.addActor(mAboutBtn);
			mStage.addActor(mBackButton);
			if (isShowDialog) {
				mStage.addActor(mSettingDialog.mWindow);
				mStage.addActor(mSettingDialog.mBackBtn);
			}
		}

		//更新音乐状态
		if (!isPlay) {
			MyGdxGame.mAssetManager.getMusic(Constant.START_BGM).pause();
		} else {
			MyGdxGame.mAssetManager.getMusic(Constant.START_BGM).play();
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(MyGdxGame.mAssetManager.getTexture(Constant.START_BG), 0, 0);
		mBatch.end();

		update(Gdx.graphics.getDeltaTime());

		mStage.act();
		mStage.draw();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {

		//清空演员
		mStage.getActors().clear();
		//清空舞台
		mStage.clear();
	}
}

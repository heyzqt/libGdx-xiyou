package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 * 开始界面
 */
public class Start extends GameState {

	//开始界面+设置界面 控件图片集
	private TextureAtlas mAtlas;

	//开始界面按钮
	private ImageButton mStartBtn;
	private ImageButton mSettingBtn;

	//设置界面
	//是否打开音乐
	private Label mSoundLabel;
	private CheckBox mCheckBox;
	public static boolean isPlay = true;
	//关于我们
	private Label mAboutUSLab;
	private Label mAboutCont;
	//返回主界面按钮
	private ImageButton mBackButton;

	/**
	 * 判断当前界面 true 开始界面 false 设置界面
	 */
	public static boolean isStart = true;

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
		Label.LabelStyle style = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont()
				, Constant.MAIN_COLOR);
		mSoundLabel = new Label("是否打开音乐:", style);
		mSoundLabel.setPosition(400, 220);
		Drawable checkOn = new TextureRegionDrawable(mAtlas.findRegion("checkOn"));
		Drawable checkOff = new TextureRegionDrawable(mAtlas.findRegion("checkOff"));
		CheckBox.CheckBoxStyle boxStyle = new CheckBox.CheckBoxStyle(checkOn, checkOff,
				MyGdxGame.mAssetManager.getFont(), Color.BLUE);
		mCheckBox = new CheckBox("", boxStyle);
		mCheckBox.setPosition(720, 220);
		//设置界面初始化 - 关于我们
		mAboutUSLab = new Label("关于游戏：", style);
		mAboutCont = new Label("游戏名：大话西游之大闹天宫; 版本号 1.0; 开发者：张晴", style);
		mAboutCont.setFontScale(0.6f);
		mAboutUSLab.setPosition(400, 145);
		mAboutCont.setPosition(460, 100);
		//设置界面初始化 - 返回按钮
		mBackButton = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("backStartBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("backStartBtnDown")));
		mBackButton.setPosition(500, 40);

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
			}
		});

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
			mStage.addActor(mSoundLabel);
			mStage.addActor(mCheckBox);
			mStage.addActor(mAboutUSLab);
			mStage.addActor(mAboutCont);
			mStage.addActor(mBackButton);
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

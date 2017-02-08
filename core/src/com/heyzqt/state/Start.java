package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;

/**
 * Created by heyzqt on 2017/2/7.
 * 开始界面
 */
public class Start extends GameState {

	//开始界面图片
	private Texture mTexture;
	private ImageButton mStartBtn;
	private ImageButton mSettingBtn;
	private TextureAtlas mAtlas;
	private TextureRegion mTitleA;
	private TextureRegion mTitleB;

	//设置界面
	private CheckBox mCheckBox;
	private Label mSoundLabel;
	private Label mAboutUSLab;
	private Label mAboutCont;

	/**
	 * 判断当前界面 true 开始界面 false 设置界面
	 */
	public static boolean isStart = true;

	public Start(GameStateManager gsm) {
		super(gsm);
		init();
	}

	private void init() {

		//初始化背景 标题
		mTexture = new Texture("background/bg_start.png");
		mAtlas = new TextureAtlas("widget/title.pack");
		mTitleA = mAtlas.findRegion("textA");
		mTitleB = mAtlas.findRegion("textB");

		//开始界面控件初始化
		mStartBtn = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(new Texture("widget/startBtn.jpg"))));
		mSettingBtn = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(new Texture("widget/settingBtn.jpg"))));
		mStartBtn.setPosition(mGame.VIEW_WIDTH / 2 -
				mStartBtn.getWidth() / 2, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2);
		mSettingBtn.setPosition(mGame.VIEW_WIDTH / 2 - mSettingBtn.getWidth() / 2
				, mGame.VIEW_HEIGHT / 2 - mSettingBtn.getHeight() / 2 - 100);

		//设置界面初始化 - 是否打开音效
		BitmapFont font = new BitmapFont(Gdx.files.internal("widget/xiyou.fnt"));
		Label.LabelStyle style = new Label.LabelStyle(font, Constant.MAIN_COLOR);
		mSoundLabel = new Label("关于我们:", style);
		mSoundLabel.setPosition(515, 400);
		Drawable checkOn = new TextureRegionDrawable(new TextureRegion(new Texture("widget/checkboxOn.png")));
		Drawable checkOff = new TextureRegionDrawable(new TextureRegion(new Texture("widget/checkboxOff.png")));
		CheckBox.CheckBoxStyle boxStyle = new CheckBox.CheckBoxStyle(checkOff, checkOn, font, Color.BLUE);
		mCheckBox = new CheckBox("", boxStyle);
		mCheckBox.setPosition(700, 400);
		//设置界面初始化 - 关于我们
		mAboutUSLab = new Label("关于我们：",style);
		mAboutCont = new Label("游戏名：大话西游之大闹天宫；版本号 1.0；开发者：张晴",style);
		mAboutUSLab.setPosition(515,300);
		mAboutCont.setPosition(515,250);


		//初始化监听
		initListener();
	}

	private void initListener() {
		mStartBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello 游戏开始");
				return true;
			}
		});

		mSettingBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				isStart = false;
				return true;
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
		}
	}

	@Override
	public void render() {

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(mTexture, 0, 0);
		mBatch.draw(mTitleA, mGame.VIEW_WIDTH / 2 - 350, mGame.VIEW_HEIGHT - 100);
		mBatch.draw(mTitleB, mGame.VIEW_WIDTH / 2 - 350, mGame.VIEW_HEIGHT - 150);
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
		System.out.println("hello dispose()");

		//清空舞台
		mStage.clear();
		//销毁舞台
		mStage.dispose();
	}
}

package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.heyzqt.handle.Constant;

/**
 * Created by heyzqt on 2017/2/7.
 * 开始界面
 */
public class Start extends GameState {

	//开始界面图片
	private Texture mTexture;
	//开始界面按钮图片
	private TextureAtlas mBtnAtlas;
	private Array<TextureAtlas.AtlasRegion> mBtnRegions;
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
	private TextureRegion[] mBackRegions;
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

		//初始化背景
		mTexture = new Texture("background/start_bg.png");

		//开始界面控件初始化
		mBtnAtlas = new TextureAtlas("widget/startBtn.atlas");
		mBtnRegions = new Array<TextureAtlas.AtlasRegion>();
		mBtnRegions = mBtnAtlas.findRegions("startBtn");
		mStartBtn = new ImageButton(new TextureRegionDrawable(mBtnRegions.get(2)),
				new TextureRegionDrawable(mBtnRegions.get(3)));
		mSettingBtn = new ImageButton(new TextureRegionDrawable(mBtnRegions.get(1)),
				new TextureRegionDrawable(mBtnRegions.get(0)));
		mStartBtn.setSize(280, 100);
		mSettingBtn.setSize(280, 100);
		mStartBtn.setPosition(mGame.VIEW_WIDTH / 2 +20, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 100);
		mSettingBtn.setPosition(mGame.VIEW_WIDTH / 2 + 26, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 180);

		//设置界面初始化 - 是否打开音效
		BitmapFont font = new BitmapFont(Gdx.files.internal("font/text48.fnt"));
		Label.LabelStyle style = new Label.LabelStyle(font, Constant.MAIN_COLOR);
		mSoundLabel = new Label("是否打开音乐:", style);
		mSoundLabel.setPosition(400, 220);
		Drawable checkOn = new TextureRegionDrawable(new TextureRegion(new Texture("widget/checkOn.png")));
		Drawable checkOff = new TextureRegionDrawable(new TextureRegion(new Texture("widget/checkOff.png")));
		CheckBox.CheckBoxStyle boxStyle = new CheckBox.CheckBoxStyle(checkOn, checkOff, font, Color.BLUE);
		mCheckBox = new CheckBox("", boxStyle);
		mCheckBox.setPosition(720, 220);
		//设置界面初始化 - 关于我们
		mAboutUSLab = new Label("关于游戏：", style);
		mAboutCont = new Label("游戏名：大话西游之大闹天宫; 版本号 1.0; 开发者：张晴", style);
		mAboutCont.setFontScale(0.6f);
		mAboutUSLab.setPosition(400, 145);
		mAboutCont.setPosition(460, 100);
		//设置界面初始化 - 返回按钮
		mBackRegions = new TextureRegion[2];
		mBackRegions[0] = new TextureRegion(new Texture("widget/backStartBtnUp.png"));
		mBackRegions[1] = new TextureRegion(new Texture("widget/backStartBtnDown.png"));
		mBackButton = new ImageButton(new TextureRegionDrawable(mBackRegions[0]),
				new TextureRegionDrawable(mBackRegions[1]));
		mBackButton.setPosition(500, 40);

		//初始化监听
		initListener();
	}

	private void initListener() {
		//开始游戏按钮
		mStartBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello 游戏开始");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				isStart = false;
			}
		});

		//设置按钮
		mSettingBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello 设置");
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
				if(isPlay){
					isPlay = false;
				}else{
					isPlay = true;
				}
				System.out.println("hello : isPlay = "+isPlay);
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
		mBatch.draw(mTexture, 0, 0);
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
	}
}

package com.heyzqt.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by heyzqt on 2017/2/7.
 * 开始界面
 */
public class Start extends GameState {

	Texture mTexture;
	ImageButton mStartBtn;
	ImageButton mSettingBtn;
	TextureAtlas mAtlas;
	TextureRegion mTitleA;
	TextureRegion mTitleB;

	public Start(GameStateManager gsm) {
		super(gsm);
		init();
	}

	private void init() {

		mTexture = new Texture("background/bg_start.png");
		mAtlas = new TextureAtlas("widget/title.pack");
		mTitleA = mAtlas.findRegion("textA");
		mTitleB = mAtlas.findRegion("textB");
		mStartBtn = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(new Texture("widget/startBtn.jpg"))));
		mSettingBtn = new ImageButton(new TextureRegionDrawable(
				new TextureRegion(new Texture("widget/settingBtn.jpg"))));
		mStartBtn.setPosition(mGame.VIEW_WIDTH / 2 -
				mStartBtn.getWidth() / 2, mGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2);
		mSettingBtn.setPosition(mGame.VIEW_WIDTH / 2 - mSettingBtn.getWidth() / 2
				, mGame.VIEW_HEIGHT / 2 - mSettingBtn.getHeight() / 2 - 100);

		initListener();

		mStage.addActor(mStartBtn);
		mStage.addActor(mSettingBtn);
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
				System.out.println("hello 设置");
				return true;
			}
		});
	}

	@Override
	public void update(float delta) {
		handleInput();
	}

	@Override
	public void render() {

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(mTexture, 0, 0);
		mBatch.draw(mTitleA, mGame.VIEW_WIDTH / 2 - 350, mGame.VIEW_HEIGHT - 100);
		mBatch.draw(mTitleB, mGame.VIEW_WIDTH / 2 - 350, mGame.VIEW_HEIGHT - 150);
		mBatch.draw(new Texture("role/role2.png"),200,200);
		mBatch.end();

		mStage.act();
		mStage.draw();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {

	}
}

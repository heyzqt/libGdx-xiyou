package com.heyzqt.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 选关界面
 */
public class Select extends GameState {

	//背景
	private Texture mBackground;

	//四个关卡素材
	private TextureAtlas mAtlas;

	//四个关卡对应四个按钮
	private ImageButton mSouthDoorBtn;
	private ImageButton mSouthPlaBtn;
	private ImageButton mNorthPlaBtn;
	private ImageButton mWestPlaBtn;

	public Select(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init() {

		//初始化选关素材
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.SELECT_WIDGET);
		mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForth")));
		mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThird")));
		mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecond")));
		mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectImgFirst")));
		mSouthDoorBtn.setPosition(mGame.VIEW_WIDTH / 2 - 200, mGame.VIEW_HEIGHT / 2 + 100);
		mSouthPlaBtn.setPosition(mGame.VIEW_WIDTH / 2 + 300, mGame.VIEW_HEIGHT / 2 + 70);
		mWestPlaBtn.setPosition(mGame.VIEW_WIDTH / 2 - 200, mGame.VIEW_HEIGHT / 2 - 300);
		mNorthPlaBtn.setPosition(mGame.VIEW_WIDTH / 2 + 250, mGame.VIEW_HEIGHT / 2 - 290);

		mStage.addActor(mSouthDoorBtn);
		mStage.addActor(mSouthPlaBtn);
		mStage.addActor(mWestPlaBtn);
		mStage.addActor(mNorthPlaBtn);

		initListener();
	}

	private void initListener() {

		//南天门按钮
		mSouthDoorBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				//进入第一关
				Play.level = 0;
				mGameStateManager.setState(GameStateManager.PLAY);
				return true;
			}
		});

		//南天王殿按钮
		mSouthPlaBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		//西天门按钮
		mWestPlaBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		//北天门按钮
		mNorthPlaBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render() {

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.end();

		mStage.act();
		mStage.draw();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {
		//销毁背景图片
		//mBackground.dispose();

		//清空所有演员
		mStage.getActors().clear();
		//清空舞台
		mStage.clear();
	}
}

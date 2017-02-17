package com.heyzqt.state;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 挑战成功界面
 */
public class Success extends GameState {

	//标题
	private Label mTitleLab;
	//时间
	private Label mTimeLab;

	//五角星 按钮控件
	private TextureAtlas mAtlas;
	private TextureAtlas.AtlasRegion[] mAtlasRegions;

	//返回地图按钮
	private ImageButton mBackBtn;
	//下一关按钮
	private ImageButton mNextBtn;

	public Success(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init() {

		//初始化标题
		Label.LabelStyle style = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont(), Constant.PLACE_COLOR);
		mTitleLab = new Label("大闹天", style);
		mTitleLab.setFontScale(1.5f);
		mTitleLab.setPosition(mGame.VIEW_WIDTH / 2 + 60, mGame.VIEW_HEIGHT - 50);

		//初始化时间
		style = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont(), Constant.TIME_COLOR);
		mTimeLab = new Label("0时1天1宫", style);
		mTimeLab.setPosition(480, 370);

		//初始化五角星和按钮
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.SUCCESS_WIDGET);
		mAtlasRegions = new TextureAtlas.AtlasRegion[6];
		mAtlasRegions[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("starOff"));
		mAtlasRegions[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("starOn"));
		mAtlasRegions[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("nextlevelBtnUp"));
		mAtlasRegions[3] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("nextlevelBtnDown"));
		mAtlasRegions[4] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("backBtnUp"));
		mAtlasRegions[5] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("backBtnDown"));

		//初始返回地图按钮
		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[4]),
				new TextureRegionDrawable(mAtlasRegions[5]));
		mNextBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[2]),
				new TextureRegionDrawable(mAtlasRegions[3]));
		mBackBtn.setPosition(450, 80);
		mBackBtn.setSize(200, 90);
		mNextBtn.setPosition(660, 80);
		mNextBtn.setSize(200, 90);

		mStage.addActor(mTitleLab);
		mStage.addActor(mTimeLab);
		mStage.addActor(mBackBtn);
		mStage.addActor(mNextBtn);

		initListener();
	}

	private void initListener() {
		//返回地图按钮
		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello 返回选关界面");
				mGameStateManager.setState(GameStateManager.START);
			}
		});

		//下一关
		mNextBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("hello 下一关");
			}
		});
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render() {

		//重新设置相机锚点
		mCamera.setToOrtho(false, MyGdxGame.VIEW_WIDTH, MyGdxGame.VIEW_HEIGHT);
		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(MyGdxGame.mAssetManager.getTexture(Constant.SUCCESS_BG), 0, 0);
		mBatch.draw(mAtlasRegions[1], 180, 213, 50, 60);
		mBatch.draw(mAtlasRegions[1], 240, 213, 50, 60);
		mBatch.draw(mAtlasRegions[0], 300, 213, 50, 60);
		mBatch.draw(mAtlasRegions[0], 360, 213, 50, 60);
		mBatch.draw(mAtlasRegions[0], 420, 213, 50, 60);
		mBatch.end();

		mStage.act();
		mStage.draw();
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {

		//清空所有演员
		mStage.getActors().clear();
		//清空舞台
		mStage.clear();
	}
}

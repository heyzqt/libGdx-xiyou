package com.heyzqt.state;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.handle.Utils;
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

	//标题
	private String titleStr;

	//时间
	private String timeStr;

	//星级
	public static int winGrades = 0;

	//画星级起始x坐标
	public int distance = 180;

	public Success(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init() {

		//初始化标题
		switch (Play.level) {
			case 0:
				titleStr = "南天门";
				break;
			case 1:
				titleStr = "南天王殿";
				break;
			case 2:
				titleStr = "西天王殿";
				break;
			case 3:
				titleStr = "北天王殿";
				break;
			case 4:
				titleStr = "凌霄宝殿";
				break;
		}

		//初始化时间
		timeStr = Utils.changeSecondToHMS(Utils.time);


		//初始化标题
		Label.LabelStyle style = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont(), Constant.PLACE_COLOR);
		mTitleLab = new Label(titleStr, style);
		mTitleLab.setFontScale(1.5f);
		mTitleLab.setPosition(mGame.VIEW_WIDTH / 2 + 60, mGame.VIEW_HEIGHT - 50);

		//初始化时间
		style = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont(), Constant.TIME_COLOR);
		mTimeLab = new Label(timeStr, style);
		mTimeLab.setPosition(480, 370);

		//初始化五角星和下一关按钮素材
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.SUCCESS_WIDGET);
		mAtlasRegions = new TextureAtlas.AtlasRegion[6];
		mAtlasRegions[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("starOff"));
		mAtlasRegions[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("starOn"));
		mAtlasRegions[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("nextlevelBtnUp"));
		mAtlasRegions[3] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("nextlevelBtnDown"));
		mAtlasRegions[4] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("backBtnUp"));
		mAtlasRegions[5] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("backBtnDown"));

		//游戏通关
		if (Play.level != 4) {
			//初始返回地图按钮和下一关按钮
			mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[4]),
					new TextureRegionDrawable(mAtlasRegions[5]));
			mNextBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[2]),
					new TextureRegionDrawable(mAtlasRegions[3]));
			mBackBtn.setPosition(450, 80);
			mBackBtn.setSize(200, 90);
			mNextBtn.setPosition(660, 80);
			mNextBtn.setSize(200, 90);
			mStage.addActor(mNextBtn);
		} else {
			mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[4]),
					new TextureRegionDrawable(mAtlasRegions[5]));
			mBackBtn.setPosition(550, 80);
			mBackBtn.setSize(200, 90);
		}

		//初始化背景音乐
		MyGdxGame.mAssetManager.getSound(Constant.SUCCESS_BGM).play();

		mStage.addActor(mTitleLab);
		mStage.addActor(mTimeLab);
		mStage.addActor(mBackBtn);

		initListener();
	}

	private void initListener() {
		//返回开始界面按钮
		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.mAssetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				mGameStateManager.setState(GameStateManager.START);
			}
		});

		if (Play.level != 4) {
			//下一关
			mNextBtn.addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					MyGdxGame.mAssetManager.getSound(Constant.BTN_COMMON_SOUND).play();
					//进入下一关
					Play.level++;
					mGameStateManager.setState(GameStateManager.LOADING);
				}
			});
		}
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
		//画出亮的星星
		int i = 0;
		for (; i < winGrades; i++) {
			mBatch.draw(mAtlasRegions[1], distance + i * 60, 213, 50, 60);
		}
		//画出暗的星星
		for (; i < 5; i++) {
			mBatch.draw(mAtlasRegions[0], distance + i * 60, 213, 50, 60);
		}
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

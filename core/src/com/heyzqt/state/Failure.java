package com.heyzqt.state;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
 * 游戏失败界面
 */
public class Failure extends GameState {

	//关卡名称 过关时间
	private Label mPlaceLab;
	private Label mTimeLab;

	//所有控件图片
	private TextureAtlas mAtlas;
	private TextureAtlas.AtlasRegion[] mAtlasRegions;
	//亮五角星
	private Image mStarOnImg;
	//暗五角星
	private Image mStarOffImg;
	//返回按钮
	private ImageButton mBackBtn;
	//重新开始按钮
	private ImageButton mRestartBtn;

	//标题
	private String titleStr;

	//时间
	private String timeStr;

	public Failure(GameStateManager manager) {
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

		//地点样式
		Label.LabelStyle placeStyle = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont(), Constant.PLACE_COLOR);
		mPlaceLab = new Label(titleStr, placeStyle);
		mPlaceLab.setFontScale(1.3f);
		mPlaceLab.setPosition(390, 425);

		//时间样式
		Label.LabelStyle timeStyle = new Label.LabelStyle(MyGdxGame.mAssetManager.getFont(), Constant.TIME_COLOR);
		mTimeLab = new Label(timeStr, timeStyle);
		mTimeLab.setPosition(480, 360);

		//五角星与按钮
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.FAILURE_WIDGET);
		mAtlasRegions = new TextureAtlas.AtlasRegion[6];
		mAtlasRegions[0] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("starOff"));
		mAtlasRegions[1] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("starOn"));
		mAtlasRegions[2] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("restartBtnUp"));
		mAtlasRegions[3] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("restartBtnDown"));
		mAtlasRegions[4] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("backBtnUp"));
		mAtlasRegions[5] = new TextureAtlas.AtlasRegion(mAtlas.findRegion("backBtnDown"));
		//初始化五角星
		mStarOnImg = new Image(mAtlasRegions[1]);
		mStarOffImg = new Image(mAtlasRegions[0]);
//		mStarOnImg.setPosition(300,240);
//		mStarOnImg.setScale(0.7f);
//		mStarOffImg.setPosition(375,240);
//		mStarOffImg.setScale(0.7f);

		//初始化返回控件
		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[4]),
				new TextureRegionDrawable(mAtlasRegions[5]));
		mBackBtn.setSize(200, 90);
		mBackBtn.setPosition(450, 100);
		//初始化重新挑战控件
		mRestartBtn = new ImageButton(new TextureRegionDrawable(mAtlasRegions[2]),
				new TextureRegionDrawable(mAtlasRegions[3]));
		mRestartBtn.setSize(200, 90);
		mRestartBtn.setPosition(660, 100);

		mStage.addActor(mPlaceLab);
		mStage.addActor(mTimeLab);
//		mStage.addActor(mStarOnImg);
//		mStage.addActor(mStarOffImg);
		mStage.addActor(mBackBtn);
		mStage.addActor(mRestartBtn);

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
				mGameStateManager.setState(GameStateManager.START);
			}
		});

		//重新挑战
		mRestartBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				mGameStateManager.setState(GameStateManager.PLAY);
			}
		});
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render() {

		mCamera.setToOrtho(false, MyGdxGame.VIEW_WIDTH, MyGdxGame.VIEW_HEIGHT);
		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(MyGdxGame.mAssetManager.getTexture(Constant.FAILURE_BG), 0, 0);
		mBatch.draw(mAtlasRegions[1], 225, 213, 50, 60);
		mBatch.draw(mAtlasRegions[1], 285, 213, 50, 60);
		mBatch.draw(mAtlasRegions[0], 345, 213, 50, 60);
		mBatch.draw(mAtlasRegions[0], 405, 213, 50, 60);
		mBatch.draw(mAtlasRegions[0], 465, 213, 50, 60);
		mBatch.end();

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

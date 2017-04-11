package com.heyzqt.state;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.entity.Monkey;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 选关界面
 */
public class Select extends GameState {

	//四个关卡素材
	private TextureAtlas mAtlas;

	//四个关卡对应四个按钮
	private ImageButton mSouthDoorBtn;
	private ImageButton mSouthPlaBtn;
	private ImageButton mNorthPlaBtn;
	private ImageButton mWestPlaBtn;
	private ImageButton mPalaceBtn;

	private Texture mBg;
	private Image mBackground;

	public Select(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init() {

		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.SELECT_WIDGET);
		mBg = MyGdxGame.assetManager.getTexture(Constant.SELECT_BG);

		//重新设置相机锚点
		mCamera.viewportHeight = MyGdxGame.SCREEN_HEIGHT;
		mCamera.position.set(MyGdxGame.VIEW_WIDTH / 2, MyGdxGame.SCREEN_HEIGHT / 2, 0);

		mBackground = new Image();
		mBackground.setSize(MyGdxGame.SCREEN_WIDTH - 10, mBg.getHeight() - 20);
		mBackground.setPosition(10, 10);
		mStage.addActor(mBackground);

		//限制玩家只能在当前关卡游戏
		switch (MyGdxGame.user.curLevel) {
			case 0:
				mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFirst")));
				mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecond")));
				mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThird")));
				mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForth")));
				mPalaceBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFifth")));
				mSouthDoorBtn.setTouchable(Touchable.enabled);
				mSouthPlaBtn.setTouchable(Touchable.disabled);
				mWestPlaBtn.setTouchable(Touchable.disabled);
				mNorthPlaBtn.setTouchable(Touchable.disabled);
				mPalaceBtn.setTouchable(Touchable.disabled);
				break;
			case 1:
				mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFirstFinished")));
				mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecond")));
				mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThird")));
				mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForth")));
				mPalaceBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFifth")));
				mSouthDoorBtn.setTouchable(Touchable.disabled);
				mSouthPlaBtn.setTouchable(Touchable.enabled);
				mWestPlaBtn.setTouchable(Touchable.disabled);
				mNorthPlaBtn.setTouchable(Touchable.disabled);
				mPalaceBtn.setTouchable(Touchable.disabled);
				break;
			case 2:
				mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFirstFinished")));
				mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecondFinished")));
				mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThird")));
				mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForth")));
				mPalaceBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFifth")));
				mSouthDoorBtn.setTouchable(Touchable.disabled);
				mSouthPlaBtn.setTouchable(Touchable.disabled);
				mWestPlaBtn.setTouchable(Touchable.enabled);
				mNorthPlaBtn.setTouchable(Touchable.disabled);
				mPalaceBtn.setTouchable(Touchable.disabled);
				break;
			case 3:
				mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFirstFinished")));
				mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecondFinished")));
				mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThirdFinished")));
				mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForth")));
				mPalaceBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFifth")));
				mSouthDoorBtn.setTouchable(Touchable.disabled);
				mSouthPlaBtn.setTouchable(Touchable.disabled);
				mWestPlaBtn.setTouchable(Touchable.disabled);
				mNorthPlaBtn.setTouchable(Touchable.enabled);
				mPalaceBtn.setTouchable(Touchable.disabled);
				break;
			case 4:
				mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFirstFinished")));
				mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecondFinished")));
				mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThirdFinished")));
				mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForthFinished")));
				mPalaceBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFifth")));
				mSouthDoorBtn.setTouchable(Touchable.disabled);
				mSouthPlaBtn.setTouchable(Touchable.disabled);
				mWestPlaBtn.setTouchable(Touchable.disabled);
				mNorthPlaBtn.setTouchable(Touchable.disabled);
				mPalaceBtn.setTouchable(Touchable.enabled);
				break;
			default:
				mSouthDoorBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFirst")));
				mSouthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectSecond")));
				mWestPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectThird")));
				mNorthPlaBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectForth")));
				mPalaceBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("selectFifth")));
				break;
		}


		//初始化选关素材
		mSouthDoorBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4 * 3 - 200, MyGdxGame.SCREEN_HEIGHT / 4);
		mSouthPlaBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4 * 3 - 60, MyGdxGame.SCREEN_HEIGHT / 2 + 130);
		mWestPlaBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 3 - 60, MyGdxGame.SCREEN_HEIGHT / 2 + 120);
		mNorthPlaBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 20 - 40, MyGdxGame.SCREEN_HEIGHT / 4 * 3 + 190);
		mPalaceBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 2 - mPalaceBtn.getWidth() / 2
				, mBg.getHeight() - mPalaceBtn.getHeight() - 80);

		mStage.addActor(mSouthDoorBtn);
		mStage.addActor(mSouthPlaBtn);
		mStage.addActor(mWestPlaBtn);
		mStage.addActor(mNorthPlaBtn);
		mStage.addActor(mPalaceBtn);

		initListener();
	}

	private void initListener() {

		//南天门按钮
		mSouthDoorBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MyGdxGame.assetManager.getSound(Constant.BTN_SELECT_SOUND);
				sound.play();
				Monkey.HP = Monkey.BLOOD;
				Monkey.MP = Monkey.MP_VALUE;
				//进入第一关
				Play.level = 0;
				mGameStateManager.setState(GameStateManager.LOADING);
				return true;
			}
		});

		//南天王殿按钮
		mSouthPlaBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MyGdxGame.assetManager.getSound(Constant.BTN_SELECT_SOUND);
				sound.play();
				Monkey.HP = Monkey.BLOOD;
				Monkey.MP = Monkey.MP_VALUE;
				//进入第二关
				Play.level = 1;
				mGameStateManager.setState(GameStateManager.LOADING);
				return true;
			}
		});

		//西天门按钮
		mWestPlaBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MyGdxGame.assetManager.getSound(Constant.BTN_SELECT_SOUND);
				sound.play();
				Monkey.HP = Monkey.BLOOD;
				Monkey.MP = Monkey.MP_VALUE;
				//进入第三关
				Play.level = 2;
				mGameStateManager.setState(GameStateManager.LOADING);
				return true;
			}
		});

		//北天门按钮
		mNorthPlaBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Sound sound = MyGdxGame.assetManager.getSound(Constant.BTN_SELECT_SOUND);
				sound.play();
				Monkey.HP = Monkey.BLOOD;
				Monkey.MP = Monkey.MP_VALUE;
				//进入第四关
				Play.level = 3;
				mGameStateManager.setState(GameStateManager.LOADING);
				return true;
			}
		});

		//凌霄宝殿按钮
		mPalaceBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_SELECT_SOUND).play();
				Monkey.HP = Monkey.BLOOD;
				Monkey.MP = Monkey.MP_VALUE;
				//进入第四关
				Play.level = 4;
				mGameStateManager.setState(GameStateManager.LOADING);
				return true;
			}
		});

		//滑动屏幕事件监听
		mBackground.addListener(new InputListener() {
			float preY;

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				preY = y;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				float result;
				if (preY < y) {
					result = y - preY;
					mCamera.position.set(MyGdxGame.VIEW_WIDTH / 2.0f, mCamera.position.y + result, 0);
					mStage.getCamera().position.set(MyGdxGame.SCREEN_WIDTH / 2.0f,
							mStage.getCamera().position.y + result, 0);
				} else {
					result = preY - y;
					mCamera.position.set(MyGdxGame.VIEW_WIDTH / 2.0f, mCamera.position.y - result, 0);
					mStage.getCamera().position.set(MyGdxGame.SCREEN_WIDTH / 2.0f,
							mStage.getCamera().position.y - result, 0);
				}
				adjustCamera();
				super.touchDragged(event, x, y, pointer);
			}
		});
	}

	private void adjustCamera() {
		//当相机锚点大于地图高度时，不再移动相机
		if (mCamera.position.y > mBg.getHeight() - MyGdxGame.SCREEN_HEIGHT / 2) {
			mCamera.position.y = mBg.getHeight() - MyGdxGame.SCREEN_HEIGHT / 2;
		}
		if (mStage.getCamera().position.y > mBg.getHeight() - MyGdxGame.SCREEN_HEIGHT / 2) {
			mStage.getCamera().position.y = mBg.getHeight() - MyGdxGame.SCREEN_HEIGHT / 2;
		}

		//当相机锚点小于地图高度时，不再移动相机
		if (mCamera.position.y < MyGdxGame.SCREEN_HEIGHT / 2) {
			mCamera.position.y = MyGdxGame.SCREEN_HEIGHT / 2;
		}
		if (mStage.getCamera().position.y < MyGdxGame.SCREEN_HEIGHT / 2) {
			mStage.getCamera().position.y = MyGdxGame.SCREEN_HEIGHT / 2;
		}
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render() {

		mCamera.update();
		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(mBg, 0, 0);
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

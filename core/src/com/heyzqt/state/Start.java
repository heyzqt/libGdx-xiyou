package com.heyzqt.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.widget.AboutGameDialog;
import com.heyzqt.widget.InputnameDialog;
import com.heyzqt.widget.NameWarningDialog;
import com.heyzqt.widget.RankingDialog;
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
	//输入姓名对话框
	private InputnameDialog mInputnameDialog;
	//排行榜
	private TextureAtlas mRangeAtlas;
	private ImageButton mRangeBtn;
	private RankingDialog mRangeDialog;
	//警告对话框
	private NameWarningDialog mWarningDialog;

	//设置界面
	//是否打开音乐
	private CheckBox mCheckBox;
	public static boolean isPlay = true;
	//关于我们
	private ImageButton mAboutBtn;
	private AboutGameDialog mAboutGameDialog;
	//返回主界面按钮
	private ImageButton mBackButton;

	/**
	 * 判断当前界面 true 开始界面 false 设置界面
	 */
	public static boolean isStart = true;

	//是否跳转到选关界面
	public static boolean isChangeToSelect = false;

	//是否显示关于我们对话框
	public static boolean isShowAboutDialog = false;

	//是否显示排行榜对话框
	public static boolean isShowRangeDialog = false;

	//是否显示输入名字对话框
	public static boolean isShowInputNameDialog = false;

	//是否显示名字警告对话框
	public static boolean isShowNameWarningDialog = false;

	public Start(GameStateManager gsm) {
		super(gsm);
		init();
	}

	private void init() {
		//开始界面控件初始化
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.START_SETTING);
		mRangeAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.RANGE_WIDGET);
		mStartBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("startBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("startBtnDown")));

		mSettingBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("settingBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("settingBtnDown")));
		mStartBtn.setSize(280, 100);
		mSettingBtn.setSize(280, 100);
		//输入姓名对话框
		mInputnameDialog = new InputnameDialog(MyGdxGame.VIEW_WIDTH / 2, MyGdxGame.VIEW_HEIGHT / 2);
		//警告对话框
		mWarningDialog = new NameWarningDialog(MyGdxGame.SCREEN_WIDTH / 2, MyGdxGame.SCREEN_HEIGHT / 2);

		//开始界面 - 排行榜
		mRangeBtn = new ImageButton(new TextureRegionDrawable(mRangeAtlas.findRegion("rangeBtnUp")),
				new TextureRegionDrawable(mRangeAtlas.findRegion("rangeBtnDown")));
		mRangeBtn.setSize(280, 100);
		//初始化排行榜对话框
		mRangeDialog = new RankingDialog(MyGdxGame.SCREEN_WIDTH / 2, MyGdxGame.SCREEN_HEIGHT / 2);

		mStartBtn.setPosition(MyGdxGame.VIEW_WIDTH / 2 + 20, MyGdxGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 20);
		mRangeBtn.setPosition(MyGdxGame.VIEW_WIDTH / 2 + 26, MyGdxGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 100);
		mSettingBtn.setPosition(MyGdxGame.VIEW_WIDTH / 2 + 26, MyGdxGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 180);

		//设置界面初始化 - 是否打开音效
		Drawable checkOn = new TextureRegionDrawable(mAtlas.findRegion("musicBtnOn"));
		Drawable checkOff = new TextureRegionDrawable(mAtlas.findRegion("musicBtnOff"));
		CheckBox.CheckBoxStyle boxStyle = new CheckBox.CheckBoxStyle(checkOff, checkOn,
				MyGdxGame.assetManager.getFont(), Color.BLUE);
		mCheckBox = new CheckBox("", boxStyle);
		mCheckBox.setSize(255, 100);
		mCheckBox.setPosition(MyGdxGame.VIEW_WIDTH / 2 + 38, MyGdxGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 40);
		if (isPlay) {
			mCheckBox.setChecked(true);
		} else {
			mCheckBox.setChecked(false);
		}

		//设置界面初始化 - 关于我们
		mAboutBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("aboutBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("aboutBtnDown")));
		mAboutBtn.setSize(280, 100);
		mAboutBtn.setPosition(MyGdxGame.VIEW_WIDTH / 2 + 26, MyGdxGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 110);
		mAboutGameDialog = new AboutGameDialog(MyGdxGame.SCREEN_WIDTH / 2, MyGdxGame.SCREEN_HEIGHT / 2);

		//设置界面初始化 - 返回按钮
		mBackButton = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("backStartBtnUp")),
				new TextureRegionDrawable(mAtlas.findRegion("backStartBtnDown")));
		mBackButton.setSize(280, 100);
		mBackButton.setPosition(MyGdxGame.VIEW_WIDTH / 2 + 26, MyGdxGame.VIEW_HEIGHT / 2 - mStartBtn.getHeight() / 2 - 180);

		//背景音乐
		Music music = MyGdxGame.assetManager.getMusic(Constant.START_BGM);
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
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				if (MyGdxGame.user.name == null || MyGdxGame.user.name.equals("")) {
					isShowInputNameDialog = true;
					setBtnDisabled(true);
				} else {
					//在用户已登录情况下，直接跳转到选关界面
					mGameStateManager.setState(GameStateManager.SELECT);
				}
			}
		});

		//排行榜按钮
		mRangeBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				isShowRangeDialog = true;
				setBtnDisabled(true);
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
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
			}
		});

		//关于按钮
		mAboutBtn.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				isShowAboutDialog = true;
				setBtnDisabled(true);
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
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				isStart = true;
			}
		});
	}

	/**
	 * 设置当前按钮的点击状态
	 *
	 * @param disabled true 不能点击 false 可以点击
	 */
	private void setBtnDisabled(boolean disabled) {
		if (isStart) {
			if (disabled) {
				mStartBtn.setTouchable(Touchable.disabled);
				mRangeBtn.setTouchable(Touchable.disabled);
				mSettingBtn.setTouchable(Touchable.disabled);
			} else {
				mStartBtn.setTouchable(Touchable.enabled);
				mRangeBtn.setTouchable(Touchable.enabled);
				mSettingBtn.setTouchable(Touchable.enabled);
			}
		} else {
			if (disabled) {
				mCheckBox.setTouchable(Touchable.disabled);
				mAboutBtn.setTouchable(Touchable.disabled);
				mBackButton.setTouchable(Touchable.disabled);
			} else {
				mCheckBox.setTouchable(Touchable.enabled);
				mAboutBtn.setTouchable(Touchable.enabled);
				mBackButton.setTouchable(Touchable.enabled);
			}
		}
	}

	@Override
	public void update(float delta) {

		//清空舞台
		mStage.getActors().clear();

		//开始界面
		if (isStart) {
			mStage.addActor(mStartBtn);
			mStage.addActor(mRangeBtn);
			mStage.addActor(mSettingBtn);
			//是否显示排行榜对话框
			if (isShowRangeDialog) {
				mRangeDialog.addDialog(mStage);
			} else if (isShowInputNameDialog) {        //是否显示输入名字对话框
				mInputnameDialog.addDialog(mStage);
				mStage.addActor(mInputnameDialog.mInputBg);
				mStage.addActor(mInputnameDialog.mNameField);
				if (isShowNameWarningDialog) {
					mWarningDialog.addDialog(mStage);
					mStage.addActor(mWarningDialog.mBackBtn);
				}
			} else {
				setBtnDisabled(false);
			}
		} else {    //设置界面
			mStage.addActor(mCheckBox);
			mStage.addActor(mAboutBtn);
			mStage.addActor(mBackButton);
			//是否显示关于游戏对话框
			if (isShowAboutDialog) {
				mAboutGameDialog.addDialog(mStage);
			} else {
				setBtnDisabled(false);
			}
		}

		//更新音乐状态
		if (!isPlay) {
			MyGdxGame.assetManager.getMusic(Constant.START_BGM).pause();
		} else {
			MyGdxGame.assetManager.getMusic(Constant.START_BGM).play();
		}

		//是否跳转到选关界面
		if (isChangeToSelect) {
			mGameStateManager.setState(GameStateManager.SELECT);
			isChangeToSelect = false;
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mBatch.draw(MyGdxGame.assetManager.getTexture(Constant.START_BG), 0, 0);
		mBatch.end();

		update(Gdx.graphics.getDeltaTime());

		mStage.act();
		mStage.draw();

		if (isShowRangeDialog) {
			mRangeDialog.render(mBatch);
		}
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

package com.heyzqt.widget;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.state.Start;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/3/2.
 *
 * 设置界面关于游戏对话框
 */
public class SettingDialog {

	private float x;
	private float y;

	//对话框
	public Image mWindow;

	//返回按钮
	public ImageButton mBackBtn;

	private TextureAtlas mAtlas;

	public SettingDialog(float x, float y) {
		this.x = x;
		this.y = y;
		init();
	}

	private void init() {
		//获取资源
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.START_SETTING);

		//初始化对话框
		mWindow = new Image(new TextureRegion(MyGdxGame.assetManager.getTexture(Constant.SETTING_DIALOG)));
		mWindow.setPosition(MyGdxGame.VIEW_WIDTH / 2 - 25, MyGdxGame.VIEW_HEIGHT / 2 - 130);

		//初始化返回按钮
		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("close")));
		mBackBtn.setPosition(785, 480);

		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Start.isShowDialog = false;
				return true;
			}
		});
	}
}

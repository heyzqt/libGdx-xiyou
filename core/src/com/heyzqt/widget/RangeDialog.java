package com.heyzqt.widget;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.heyzqt.handle.Constant;
import com.heyzqt.state.Start;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/4/4.
 *
 * 排行榜对话框
 */
public class RangeDialog extends BaseDialog {

	public RangeDialog(float x, float y) {
		super(x, y);
		init();
	}

	private void init() {
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.START_SETTING);
		mBackground = MyGdxGame.assetManager.getTexture(Constant.RANGE_BG);

		//初始化对话框
		mWindow = new Image(mBackground);
		mWindow.setPosition(x, y);

		//初始化返回按钮
		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("close")));
		mBackBtn.setPosition(785, 480);

		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				Start.isShowRangeDialog = false;
				return true;
			}
		});
	}
}

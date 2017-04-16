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
 * Created by heyzqt on 2017/4/11.
 *
 * 用户名警告框
 */
public class NameWarningDialog extends BaseDialog {

	public NameWarningDialog(float x, float y) {
		super(x, y);
		init();
	}

	private void init() {
		mBackground = MyGdxGame.assetManager.getTexture(Constant.NAMEWARNING_BG);
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.COMMON_COMPONENTS);

		mWindow = new Image(mBackground);
		mWindow.setPosition(x - mWindow.getWidth() / 2, y - mWindow.getHeight() / 2 + 240);

		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("ok")));
		mBackBtn.setPosition(x + mWindow.getWidth() / 3, y - mBackBtn.getHeight() / 2 + 225);

		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Start.isShowNameWarningDialog = false;
				return true;
			}
		});
	}
}

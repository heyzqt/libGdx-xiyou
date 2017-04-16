package com.heyzqt.widget;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
public class RankingDialog extends BaseDialog {

	private BitmapFont mFont;

	public RankingDialog(float x, float y) {
		super(x, y);
		init();
		mFont = MyGdxGame.assetManager.getFont32();
		mFont.setColor(Constant.FONT_COLOR);
	}

	private void init() {
		mAtlas = MyGdxGame.assetManager.getTextureAtlas(Constant.COMMON_COMPONENTS);
		mBackground = MyGdxGame.assetManager.getTexture(Constant.RANGE_BG);

		//初始化对话框
		mWindow = new Image(mBackground);
		mWindow.setPosition(x - mBackground.getWidth() / 2, y - mBackground.getHeight() / 2);

		//初始化返回按钮
		mBackBtn = new ImageButton(new TextureRegionDrawable(mAtlas.findRegion("close64")));
		mBackBtn.setPosition(x + mBackground.getWidth() / 2 - 75, y + mBackground.getHeight() / 2 - 70);

		mBackBtn.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				MyGdxGame.assetManager.getSound(Constant.BTN_COMMON_SOUND).play();
				Start.isShowRangeDialog = false;
				return true;
			}
		});
	}

	public void render(SpriteBatch batch) {
		if (MyGdxGame.rankings == null || MyGdxGame.rankings.size() == 0) {
			return;
		}
		batch.begin();
		for (int i = 0; i < MyGdxGame.rankings.size(); i++) {
			mFont.draw(batch, (i + 1) + ".", x * MyGdxGame.WEIGHT_RATE - 175, y * MyGdxGame.HEIGHT_RATE + 50 - i * 80);
			mFont.draw(batch, MyGdxGame.rankings.get(i).name, x * MyGdxGame.WEIGHT_RATE - 95, y * MyGdxGame.HEIGHT_RATE + 50 - i * 80);
			mFont.draw(batch, MyGdxGame.rankings.get(i).score + "", x * MyGdxGame.WEIGHT_RATE - 95 + 140, y * MyGdxGame.HEIGHT_RATE + 50 - i * 80);
			mFont.draw(batch, MyGdxGame.rankings.get(i).time + "秒", x * MyGdxGame.WEIGHT_RATE - 95 + 235, y * MyGdxGame.HEIGHT_RATE + 50 - i * 80);
		}
		batch.end();
	}
}

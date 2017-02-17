package com.heyzqt.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/16.
 *
 * 背景精灵
 */
public class Background {

	private Texture mTexture;

	public Background(String key) {

		mTexture = MyGdxGame.mAssetManager.getTexture(key);
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(mTexture, 0, 0, 200, 0, 1360, 640);
		batch.end();
	}
}

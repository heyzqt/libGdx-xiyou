package com.heyzqt.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 主角:孙悟空
 */
public class Monkey extends BaseSprite {

	private TextureAtlas mMonkeyAtlas;

	public Monkey(Body body) {
		super(body);

		mMonkeyAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.MONKEY);

		TextureAtlas.AtlasRegion[] regions = new TextureAtlas.AtlasRegion[3];
		regions[0] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightStand"));
		regions[1] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW1"));
		regions[2] = new TextureAtlas.AtlasRegion(mMonkeyAtlas.findRegion("sunRightW2"));

		//初始化主角动画
		setAnimation(regions, 1/12f);
	}
}

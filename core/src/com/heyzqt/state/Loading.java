package com.heyzqt.state;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.heyzqt.handle.Constant;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/3/13.
 *
 * 加载界面
 */
public class Loading extends GameState{

	//所有控件素材
	private TextureAtlas mAtlas;

	public Loading(GameStateManager manager) {
		super(manager);
		init();
	}

	public void init(){
		mAtlas = MyGdxGame.mAssetManager.getTextureAtlas(Constant.LOADING_WIDGET);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render() {

	}

	@Override
	public void handleInput() {

	}

	@Override
	public void dispose() {

	}
}

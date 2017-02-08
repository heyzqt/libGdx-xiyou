package com.heyzqt.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.heyzqt.xiyou.MyGdxGame;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 游戏状态基类
 */
public abstract class GameState {

	protected MyGdxGame mGame;
	protected GameStateManager mGameStateManager;

	protected SpriteBatch mBatch;
	protected OrthographicCamera mCamera;
	protected OrthographicCamera mUICamera;
	protected Stage mStage;

	public GameState(GameStateManager manager){
		this.mGameStateManager = manager;
		mGame = mGameStateManager.getGame();
		mBatch = mGame.getSpriteBatch();
		mCamera = mGame.getCamera();
		mUICamera = mGame.getUICamera();
		mStage = mGame.getStage();
	}

	public abstract void update(float delta);
	public abstract void render();
	public abstract void handleInput();
	public abstract void dispose();

}

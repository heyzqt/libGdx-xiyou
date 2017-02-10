package com.heyzqt.xiyou;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.heyzqt.state.GameStateManager;

public class MyGdxGame extends ApplicationAdapter {

	private SpriteBatch mBatch;
	private Stage mStage;
	private OrthographicCamera mCamera;
	private OrthographicCamera mUICamera;

	public final int VIEW_WIDTH = 960;
	public final int VIEW_HEIGHT = 640;

	private GameStateManager mManager;

	@Override
	public void create() {
		mBatch = new SpriteBatch();
		mStage = new Stage();
		Gdx.input.setInputProcessor(mStage);

		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false,VIEW_WIDTH,VIEW_HEIGHT);
		mUICamera = new OrthographicCamera();
		mUICamera.setToOrtho(false,VIEW_WIDTH,VIEW_HEIGHT);

		mManager = new GameStateManager(this);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mManager.update(Gdx.graphics.getDeltaTime());
		mManager.render();
	}

	@Override
	public void dispose() {
	}

	public Stage getStage(){ return mStage; }

	public SpriteBatch getSpriteBatch() {
		return mBatch;
	}

	public OrthographicCamera getCamera() { return mCamera; }

	public OrthographicCamera getUICamera() {
		return mUICamera;
	}

}

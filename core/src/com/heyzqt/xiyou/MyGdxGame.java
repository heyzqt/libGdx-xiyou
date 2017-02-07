package com.heyzqt.xiyou;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch mBatch;
	OrthographicCamera mCamera;
	OrthographicCamera mUICamera;

	@Override
	public void create() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public SpriteBatch getSpriteBatch() {
		return mBatch;
	}

	public OrthographicCamera getCamera() {return mCamera;}

	public OrthographicCamera getUICamera() {
		return mUICamera;
	}

}

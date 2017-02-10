package com.heyzqt.state;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 游戏界面
 */
public class Play extends GameState{

	private TiledMap mMap;
	private OrthoCachedTiledMapRenderer mMapRenderer;
	private OrthogonalTiledMapRenderer mOrthogonalTiledMapRenderer;

	public Play(GameStateManager manager) {
		super(manager);
		init();
	}

	private void init(){
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

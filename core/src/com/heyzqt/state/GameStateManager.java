package com.heyzqt.state;

import com.heyzqt.xiyou.MyGdxGame;

import java.util.Stack;

/**
 * Created by heyzqt on 2017/2/7.
 */
public class GameStateManager {

	private MyGdxGame mGame;

	/**
	 * 游戏状态管理栈
	 */
	private Stack<GameState> mGameStates;

	//开始界面
	public final static int START = 0;

	public GameStateManager(MyGdxGame game) {
		this.mGame = game;
		mGameStates = new Stack<GameState>();
	}

	public MyGdxGame getGame(){
		return mGame;
	}
}

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
	protected Stack<GameState> mGameStates;

	public final static int START = 0;   //开始设置界面
	public final static int PLAY = 1;    //游戏界面
	public final static int FAILURE = 2;  //失败界面
	public final static int SUCCESS = 3;  //成功界面
	public final static int SELECT = 4;    //选关界面
	public final static int LOADING = 5;    //加载界面

	public GameStateManager(MyGdxGame game) {
		this.mGame = game;
		mGameStates = new Stack<GameState>();
		pushState(START);
	}

	public MyGdxGame getGame() {
		return mGame;
	}

	public void update(float delta) {
		mGameStates.peek().update(delta);
	}

	public void render() {
		mGameStates.peek().render();
	}

	public GameState getState(int state) {
		if (state == START) return new Start(this);
		if (state == PLAY) return new Play(this);
		if (state == FAILURE) return new Failure(this);
		if (state == SUCCESS) return new Success(this);
		if (state == SELECT) return new Select(this);
		if (state == LOADING) return new Loading(this);
		return null;
	}

	//入栈
	public void pushState(int state) {
		mGameStates.push(getState(state));
	}

	//入栈
	public void pushState(Play play) {
		mGameStates.push(play);
	}

	//出栈
	public void popState() {
		GameState state = mGameStates.pop();
		state.dispose();
	}

	//设置当前游戏状态
	public void setState(int state) {
		popState();
		pushState(state);
	}
}

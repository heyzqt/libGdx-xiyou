package com.heyzqt.handle;


import com.badlogic.gdx.graphics.Color;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 常量池类
 */
public class Constant {

	//开始 设置界面字体颜色
	public final static Color MAIN_COLOR = new Color(1f, 0.8f, 0, 1);
	//挑战失败 挑战成功界面地名字体颜色
	public final static Color PLACE_COLOR = new Color(0.2f, 0.1f, 0.02f, 1);
	//挑战失败 挑战成功界面地名 时间字体颜色
	public final static Color TIME_COLOR = new Color(1f, 0.26f, 0, 1);


	/**
	 * 刚体世界常量
	 */
	//刚体世界与像素世界单位换算 单位：100像素/米
	public final static float RATE = 100;
	//主角刚体碰撞属性
	public static final short PLAYER = 2;
	//砖块碰撞属性
	public static final short BLOCK = 4;
	//持刀天兵碰撞属性
	public static final short ENEMY_DAO = 8;
	//持枪天兵碰撞属性
	public static final short ENEMY_QIANG = 16;
	//持弓碰撞属性
	public static final short ENEMY_GONG = 32;

	/**
	 * 资源名
	 */
	//失败界面
	public static final String FAILURE_BG = "failure_bg";
	public static final String FAILURE_WIDGET = "failure_widget";
	//游戏界面
	public static final String MONKEY = "monkey";
	public static final String PLAY_BG = "play_bg";
	public static final String PLAY_WIDGET = "play_widget";

}

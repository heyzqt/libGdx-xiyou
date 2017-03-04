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
	//按钮音效
	public static final String BTN_COMMON_SOUND = "btn_common_sound";
	public static final String BTN_SELECT_SOUND = "btn_select_sound";

	//第一关至第五关卡背景音效
	public static final String LEVEL_0_BGM = "bgm_level_0";
	public static final String LEVEL_1_BGM = "bgm_level_1";
	public static final String LEVEL_2_BGM = "bgm_level_2";
	public static final String LEVEL_3_BGM = "bgm_level_3";
	public static final String LEVEL_4_BGM = "bgm_level_4";

	//开始界面 设置界面
	public static final String START_BG = "start_bg";
	public static final String START_SETTING = "start_setting";
	public static final String START_BGM = "start_bgm";
	public static final String SETTING_DIALOG = "setting_dialog";

	//选关界面
	public static final String SELECT_WIDGET = "select_widget";
	public static final String SELECT_BG = "select_bg";

	//成功界面
	public static final String SUCCESS_BG = "success_bg";
	public static final String SUCCESS_WIDGET = "success_widget";
	public static final String SUCCESS_BGM = "success_bgm";

	//失败界面
	public static final String FAILURE_BG = "failure_bg";
	public static final String FAILURE_WIDGET = "failure_widget";
	public static final String FAILURE_BGM = "failure_bgm";

	//游戏界面
	public static final String SUN = "sun";
	public static final String ENEMY_DAO_ROLE = "enemy_dao";
	public static final String PLAY_BG = "play_bg";
	public static final String PLAY_WIDGET = "play_widget";
	public static final String PLAY_BLOOD = "play_blood";

}

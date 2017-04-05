package com.heyzqt.handle;


import com.badlogic.gdx.graphics.Color;

/**
 * Created by heyzqt on 2017/2/7.
 *
 * 常量池类
 */
public class Constant {

	/**
	 * Preferences
	 */
	public final static String PREFERENCES_NAME = "xiyou";
	public final static String PREFERENCES_USERNAME = "username";
	public final static String PREFERENCES_HP = "hp";
	public final static String PREFERENCES_MP = "mp";
	public final static String PREFERENCES_CUR_LEVEL = "cur_level";
	public final static String PREFERENCES_CUR_SCORE = "cur_scorce";
	public final static String PREFERENCES_CUR_TIME = "cur_time";
	public final static String PREFERENCES_RANGE_FIRST_NAME = "range_first_name";
	public final static String PREFERENCES_RANGE_FIRST_SCORE = "range_first_score";
	public final static String PREFERENCES_RANGE_FIRST_TIME = "range_first_time";
	public final static String PREFERENCES_RANGE_SECOND_NAME = "range_second_name";
	public final static String PREFERENCES_RANGE_SECOND_SCORE = "range_second_score";
	public final static String PREFERENCES_RANGE_SECOND_TIME = "range_second_time";
	public final static String PREFERENCES_RANGE_THIRD_NAME = "range_third_name";
	public final static String PREFERENCES_RANGE_THIRD_SCORE = "range_third_score";
	public final static String PREFERENCES_RANGE_THIRD_TIME = "range_third_time";

	//开始 设置界面字体颜色
	public final static Color MAIN_COLOR = new Color(1f, 0.8f, 0, 1);
	//挑战失败 挑战成功界面地名字体颜色
	public final static Color PLACE_COLOR = new Color(0.2f, 0.1f, 0.02f, 1);
	//挑战失败 挑战成功界面时间字体颜色
	public final static Color TIME_COLOR = new Color(1f, 0.26f, 0, 1);
	//常用字体颜色
	public final static Color FONT_COLOR = new Color(0.392f, 0.2745f, 0.145f, 1);


	/**
	 * 刚体世界常量
	 */
	//刚体世界与像素世界单位换算 单位：100像素/米
	public final static float RATE = 100;
	//主角刚体碰撞属性
	public static final short PLAYER = 2;
	//火球碰撞属性
	public static final short FIREBALL = 2;
	//桃子碰撞属性
	public static final short TAO = 8;
	//蓝瓶碰撞属性
	public static final short BLUE = 8;
	//砖块碰撞属性
	public static final short BLOCK = 4;
	//持刀天兵碰撞属性
	public static final short ENEMY_DAO = 8;
	//持枪天兵碰撞属性
	public static final short ENEMY_QIANG = 16;
	//持弓碰撞属性
	public static final short ENEMY_GONG = 32;
	//BOSS碰撞属性
	public static final short BOSS = 8;

	/**
	 * 资源名
	 */
	//按钮音效
	public static final String BTN_COMMON_SOUND = "btn_common_sound";
	public static final String BTN_SELECT_SOUND = "btn_select_sound";

	//孙悟空被击打音效
	public static final String SOUND_SUN_GET_HURT = "sfx_role_get_hurt_1";
	//天兵被击打音效
	public static final String SOUND_ENEMY_GET_HURT = "sfx_enemy_get_hurt";

	//第一关至第五关卡背景音乐
	public static final String LEVEL_0_BGM = "bgm_level_0";
	public static final String LEVEL_1_BGM = "bgm_level_1";
	public static final String LEVEL_2_BGM = "bgm_level_2";

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
	public static final String FIRST_GAME_BG = "first_play_bg";
	public static final String SECOND_GAME_BG = "second_game_bg";
	public static final String THIRD_GAME_BG = "third_game_bg";
	public static final String FORTH_GAME_BG = "forth_game_bg";
	public static final String FIFTH_GAME_BG = "fifth_game_bg";
	public static final String PLAY_WIDGET = "play_widget";
	public static final String PLAY_BLOOD = "play_blood";
	public static final String FIREBALL_WIDGET = "fireball_widget";
	public static final String FIREBALL_SOUND = "fireball_sound";
	public static final String ATTACK_SOUND = "attack_sound";
	public static final String JUMPBALL_SOUND = "jumpball_sound";

	//排行榜素材
	public static final String RANGE_BG = "range_bg";
	public static final String RANGE_WIDGET = "range_widget";

	//输入姓名对话框
	public static final String INPUT_NAME_BG = "input_name_bg";

	//加载界面
	public static final String LOADING_BG = "loading_bg";
	public static final String LOADING_WIDGET = "loading_widget";

	//天兵素材
	public static final String ENEMY_DAO_ROLE = "enemy_dao";
	public static final String ENEMY_FU_ROLE = "enemy_fu";
	public static final String ENEMY_QIANG_ROLE = "enemy_qiang";
	//Boss素材
	public static final String BOSS_JULING_ROLE = "juling_boss";
	public static final String BOSS_ZENGZHANG_ROLE = "zengzhang_boss";
	public static final String BOSS_GUANGMU_ROLE = "guangmu_boss";
	public static final String BOSS_DUOWEN_ROLE = "duowen_boss";
	public static final String BOSS_ERLANG_ROLE = "erlang_boss";

}

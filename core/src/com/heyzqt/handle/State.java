package com.heyzqt.handle;

/**
 * Created by heyzqt on 2017/2/24.
 *
 * 状态管理类
 */
public class State {

	//状态
	public static int STATE_IDEL_LEFT = 1;        //左边站立
	public static int STATE_IDEL_RIGHT = 2;        //右边站立
	public static int STATE_LEFT = 3;            //左边行走
	public static int STATE_RIGHT = 4;            //右边行走
	public static int STATE_LEFT_ATTACK = 5;            //左攻击
	public static int STATE_RIGHT_ATTACK = 6;            //右攻击
	public static int STATE_LEFT_HITED = 7;            //往左被击飞
	public static int STATE_RIGHT_HITED = 8;            //往右被击飞
}

package com.heyzqt.handle;

/**
 * Created by heyzqt on 2017/2/24.
 *
 * 状态管理类
 */
public class State {

	//状态
	public final static int STATE_IDEL_LEFT = 1;        //左边站立
	public final static int STATE_IDEL_RIGHT = 2;        //右边站立
	public final static int STATE_LEFT = 3;            //左边行走
	public final static int STATE_RIGHT = 4;            //右边行走
	public final static int STATE_LEFT_ATTACK = 5;            //左攻击
	public final static int STATE_RIGHT_ATTACK = 6;            //右攻击
	public final static int STATE_LEFT_HITED = 7;            //往左被击飞
	public final static int STATE_RIGHT_HITED = 8;            //往右被击飞

	//孙悟空
	public final static int STATE_RIGHT_FIREBALL = 9;            //右 火球攻击
	public final static int STATE_LEFT_FIREBALL = 10;            //左 火球攻击
	public final static int STATE_RIGHT_JUMP_ATTACK = 11;            //右 升龙斩攻击
	public final static int STATE_LEFT_JUMP_ATTACK = 12;            //左 升龙斩攻击
}

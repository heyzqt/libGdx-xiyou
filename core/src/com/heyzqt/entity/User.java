package com.heyzqt.entity;

import com.heyzqt.handle.Constant;
import com.heyzqt.handle.DataSaveSecurity;

/**
 * Created by heyzqt on 2017/4/5.
 *
 * 用户类
 */
public class User {

	/**
	 * 用户名
	 */
	public String name;

	/**
	 * HP
	 */
	public int HP;

	/**
	 * MP值
	 */
	public int MP;

	/**
	 * 当前通关位置
	 */
	public int curLevel;

	/**
	 * 当前总分数和总时间
	 */
	public int scores;
	public int times;

	private static User mUser = null;

	private User() {
		mUser = getUser();
	}

	public static User getInstance() {
		if (mUser == null) {
			mUser = new User();
		}
		return mUser;
	}

	public User getUser() {
		User user = getUserData();
		if (user.name == null || user.name.equals("")) {
			return null;
		}
		return user;
	}

	public User getUserData() {
		User user = new User();
		user.name = DataSaveSecurity.getInstance().loadDataValue(Constant.PREFERENCES_USERNAME, String.class);
		user.HP = Integer.parseInt(DataSaveSecurity.getInstance().loadDataValue(Constant.PREFERENCES_HP, String.class));
		user.MP = Integer.parseInt(DataSaveSecurity.getInstance().loadDataValue(Constant.PREFERENCES_MP, String.class));
		user.curLevel = Integer.parseInt(DataSaveSecurity.getInstance().loadDataValue(Constant.PREFERENCES_CUR_LEVEL, String.class));
		user.scores = Integer.parseInt(DataSaveSecurity.getInstance().loadDataValue(Constant.PREFERENCES_CUR_SCORE, String.class));
		user.times = Integer.parseInt(DataSaveSecurity.getInstance().loadDataValue(Constant.PREFERENCES_CUR_TIME, String.class));
		return user;
	}

	public void saveUserData(User user) {
		if (user == null) {
			return;
		}
		DataSaveSecurity.getInstance().saveDataToEncode(Constant.PREFERENCES_USERNAME, user.name);
		DataSaveSecurity.getInstance().saveDataToEncode(Constant.PREFERENCES_HP, user.HP + "");
		DataSaveSecurity.getInstance().saveDataToEncode(Constant.PREFERENCES_MP, user.MP + "");
		DataSaveSecurity.getInstance().saveDataToEncode(Constant.PREFERENCES_CUR_LEVEL, user.curLevel + "");
		DataSaveSecurity.getInstance().saveDataToEncode(Constant.PREFERENCES_CUR_SCORE, user.scores + "");
		DataSaveSecurity.getInstance().saveDataToEncode(Constant.PREFERENCES_CUR_TIME, user.times + "");
	}
}

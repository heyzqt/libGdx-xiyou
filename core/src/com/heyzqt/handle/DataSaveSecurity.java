package com.heyzqt.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by heyzqt on 2017/4/5.
 *
 * 对数据进行加密解密处理(Base64Coder)
 */
public class DataSaveSecurity {

	private Save mSave;

	private Preferences mPreferences;

	private static DataSaveSecurity dataSavaSecurity = null;

	private DataSaveSecurity() {
		mPreferences = Gdx.app.getPreferences(Constant.PREFERENCES_NAME);
		mSave = getSave();
	}

	public static DataSaveSecurity getInstance() {
		if (dataSavaSecurity == null) {
			dataSavaSecurity = new DataSaveSecurity();
		}
		return dataSavaSecurity;
	}

	public Save getSave() {
		Save save = new Save();

		String name = mPreferences.getString(Constant.PREFERENCES_USERNAME);
		//游戏初始化
		if (name == null || name.equals("")) {
			//个人数据
			mPreferences.putString(Constant.PREFERENCES_USERNAME, Base64Coder.encodeString(""));
			mPreferences.putString(Constant.PREFERENCES_HP, Base64Coder.encodeString("8"));
			mPreferences.putString(Constant.PREFERENCES_MP, Base64Coder.encodeString("8"));
			mPreferences.putString(Constant.PREFERENCES_CUR_LEVEL, Base64Coder.encodeString("1"));
			mPreferences.putString(Constant.PREFERENCES_CUR_TIME, Base64Coder.encodeString("0"));
			mPreferences.putString(Constant.PREFERENCES_CUR_SCORE, Base64Coder.encodeString("0"));

			String rangeNameFirst = Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_RANGE_FIRST_NAME));
			//排行榜数据
			if (rangeNameFirst == null || rangeNameFirst.equals("")) {
				mPreferences.putString(Constant.PREFERENCES_RANGE_FIRST_NAME, Base64Coder.encodeString(""));
				mPreferences.putString(Constant.PREFERENCES_RANGE_FIRST_SCORE, Base64Coder.encodeString("-1"));
				mPreferences.putString(Constant.PREFERENCES_RANGE_FIRST_TIME, Base64Coder.encodeString("-1"));

				mPreferences.putString(Constant.PREFERENCES_RANGE_SECOND_NAME, Base64Coder.encodeString(""));
				mPreferences.putString(Constant.PREFERENCES_RANGE_SECOND_SCORE, Base64Coder.encodeString("-1"));
				mPreferences.putString(Constant.PREFERENCES_RANGE_SECOND_TIME, Base64Coder.encodeString("-1"));

				mPreferences.putString(Constant.PREFERENCES_RANGE_THIRD_NAME, Base64Coder.encodeString(""));
				mPreferences.putString(Constant.PREFERENCES_RANGE_THIRD_TIME, Base64Coder.encodeString("-1"));
				mPreferences.putString(Constant.PREFERENCES_RANGE_THIRD_SCORE, Base64Coder.encodeString("-1"));
			}
			mPreferences.flush();
		}

		//初始化
		//个人数据
		save.datas.put(Constant.PREFERENCES_USERNAME, Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_USERNAME, "")));
		save.datas.put(Constant.PREFERENCES_HP, Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_HP, "-1")));
		save.datas.put(Constant.PREFERENCES_MP, Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_MP, "-1")));
		save.datas.put(Constant.PREFERENCES_CUR_LEVEL, Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_CUR_LEVEL, "-1")));
		save.datas.put(Constant.PREFERENCES_CUR_TIME, Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_CUR_TIME, "-1")));
		save.datas.put(Constant.PREFERENCES_CUR_SCORE, Base64Coder.decodeString(mPreferences.getString(Constant.PREFERENCES_CUR_SCORE, "-1")));

		//排行榜数据
		save.datas.put(Constant.PREFERENCES_RANGE_FIRST_NAME, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_FIRST_NAME, "")));
		save.datas.put(Constant.PREFERENCES_RANGE_FIRST_SCORE, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_FIRST_SCORE, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_FIRST_TIME, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_FIRST_TIME, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_SECOND_NAME, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_SECOND_NAME, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_SECOND_SCORE, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_SECOND_SCORE, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_SECOND_TIME, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_SECOND_TIME, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_THIRD_NAME, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_THIRD_NAME, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_THIRD_TIME, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_THIRD_TIME, "-1")));
		save.datas.put(Constant.PREFERENCES_RANGE_THIRD_SCORE, Base64Coder.decodeString(
				mPreferences.getString(Constant.PREFERENCES_RANGE_THIRD_SCORE, "-1")));

		return save;
	}

	/**
	 * Base64Coder加密保存数据
	 */
	public void saveDataToEncode(String key, String content) {
		mSave.datas.put(key, content);
		String result = Base64Coder.encodeString(content);
		mPreferences.putString(key, result);
		mPreferences.flush();
	}

	/**
	 * Base64Coder解密获取数据
	 */
	public String loadDataValue(String key, Class<String> type) {
		if (mSave.datas.containsKey(key)) {
			return (String) mSave.datas.get(key);
		}
		return "-1";
	}

	private static class Save {
		public ObjectMap<String, Object> datas = new ObjectMap<String, Object>();
	}
}

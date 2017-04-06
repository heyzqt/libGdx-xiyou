package com.heyzqt.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by heyzqt on 2017/4/5.
 *
 * 对数据进行保存加密解密处理(Base64Coder)
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

	private Save getSave() {
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
	 * 保存数据
	 */
	public void saveData(String key, String content) {
		mSave.datas.put(key, content);
	}

	/**
	 * 获取数据
	 */
	public String loadDataValue(String key, Class<String> type) {
		if (mSave.datas.containsKey(key)) {
			return (String) mSave.datas.get(key);
		}
		return "-1";
	}


	/**
	 * Base64Coder加密保存单个数据
	 */
	public void saveDataToEncode(String key, String content) {
		String result = Base64Coder.encodeString(content);
		mPreferences.putString(key, result);
		mPreferences.flush();
	}

	/**
	 * Base64Coder加密保存所有数据
	 */
	public void saveAllDataToEncode() {
		saveDataToEncode(Constant.PREFERENCES_USERNAME, (String) mSave.datas.get(Constant.PREFERENCES_USERNAME));
		saveDataToEncode(Constant.PREFERENCES_HP, (String) mSave.datas.get(Constant.PREFERENCES_HP));
		saveDataToEncode(Constant.PREFERENCES_MP, (String) mSave.datas.get(Constant.PREFERENCES_MP));
		saveDataToEncode(Constant.PREFERENCES_CUR_TIME, (String) mSave.datas.get(Constant.PREFERENCES_CUR_TIME));
		saveDataToEncode(Constant.PREFERENCES_CUR_SCORE, (String) mSave.datas.get(Constant.PREFERENCES_CUR_SCORE));
		saveDataToEncode(Constant.PREFERENCES_CUR_LEVEL, (String) mSave.datas.get(Constant.PREFERENCES_CUR_LEVEL));

		saveDataToEncode(Constant.PREFERENCES_RANGE_FIRST_NAME, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_FIRST_NAME));
		saveDataToEncode(Constant.PREFERENCES_RANGE_FIRST_SCORE, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_FIRST_SCORE));
		saveDataToEncode(Constant.PREFERENCES_RANGE_FIRST_TIME, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_FIRST_TIME));

		saveDataToEncode(Constant.PREFERENCES_RANGE_SECOND_NAME, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_SECOND_NAME));
		saveDataToEncode(Constant.PREFERENCES_RANGE_SECOND_SCORE, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_SECOND_SCORE));
		saveDataToEncode(Constant.PREFERENCES_RANGE_SECOND_TIME, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_SECOND_TIME));

		saveDataToEncode(Constant.PREFERENCES_RANGE_THIRD_NAME, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_THIRD_NAME));
		saveDataToEncode(Constant.PREFERENCES_RANGE_THIRD_SCORE, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_THIRD_SCORE));
		saveDataToEncode(Constant.PREFERENCES_RANGE_THIRD_TIME, (String) mSave.datas.get(Constant.PREFERENCES_RANGE_THIRD_TIME));
	}

	/**
	 * Base64Coder解密获取单个数据
	 */
	private String getDataFromDecode(String key) {
		String result = Base64Coder.decodeString(mPreferences.getString(key));
		return result;
	}

	/**
	 * Base64Coder解密获取所有数据
	 */
	private String[] getAllDataFromDecode() {
		String[] result = new String[15];

		//个人数据
		result[0] = getDataFromDecode(Constant.PREFERENCES_USERNAME);
		result[1] = getDataFromDecode(Constant.PREFERENCES_HP);
		result[2] = getDataFromDecode(Constant.PREFERENCES_MP);
		result[3] = getDataFromDecode(Constant.PREFERENCES_CUR_SCORE);
		result[4] = getDataFromDecode(Constant.PREFERENCES_CUR_TIME);
		result[5] = getDataFromDecode(Constant.PREFERENCES_CUR_LEVEL);

		//排行榜数据
		result[6] = getDataFromDecode(Constant.PREFERENCES_RANGE_FIRST_NAME);
		result[7] = getDataFromDecode(Constant.PREFERENCES_RANGE_FIRST_SCORE);
		result[8] = getDataFromDecode(Constant.PREFERENCES_RANGE_FIRST_TIME);

		result[9] = getDataFromDecode(Constant.PREFERENCES_RANGE_SECOND_NAME);
		result[10] = getDataFromDecode(Constant.PREFERENCES_RANGE_SECOND_SCORE);
		result[11] = getDataFromDecode(Constant.PREFERENCES_RANGE_SECOND_TIME);

		result[12] = getDataFromDecode(Constant.PREFERENCES_RANGE_THIRD_NAME);
		result[13] = getDataFromDecode(Constant.PREFERENCES_RANGE_THIRD_SCORE);
		result[14] = getDataFromDecode(Constant.PREFERENCES_RANGE_THIRD_TIME);

		return result;
	}

	private static class Save {
		public ObjectMap<String, Object> datas = new ObjectMap<String, Object>();
	}
}

package com.heyzqt.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.ObjectMap;
import com.heyzqt.entity.User;

/**
 * Created by heyzqt on 2017/4/5.
 *
 * 对数据进行加密解密处理(Base64Coder)
 */
public class DataSaveSecurity {

	private Save mSave;

	private Preferences mPreferences;

	public static User mUser;

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

		//个人数据
		save.datas.get(Constant.PREFERENCES_USERNAME, "");
		save.datas.get(Constant.PREFERENCES_HP, "");
		save.datas.get(Constant.PREFERENCES_MP, "");
		save.datas.get(Constant.PREFERENCES_CUR_SCORE, "");
		save.datas.get(Constant.PREFERENCES_CUR_TIME, "");
		save.datas.get(Constant.PREFERENCES_CUR_LEVEL, "");

		//排行榜数据
		save.datas.get(Constant.PREFERENCES_RANGE_FIRST_NAME, "");
		save.datas.get(Constant.PREFERENCES_RANGE_FIRST_SCORE, "");
		save.datas.get(Constant.PREFERENCES_RANGE_FIRST_TIME, "");
		save.datas.get(Constant.PREFERENCES_RANGE_SECOND_NAME, "");
		save.datas.get(Constant.PREFERENCES_RANGE_SECOND_SCORE, "");
		save.datas.get(Constant.PREFERENCES_RANGE_SECOND_TIME, "");
		save.datas.get(Constant.PREFERENCES_RANGE_THIRD_NAME, "");
		save.datas.get(Constant.PREFERENCES_RANGE_THIRD_SCORE, "");
		save.datas.get(Constant.PREFERENCES_RANGE_THIRD_TIME, "");

		return save;
	}

	/**
	 * Base64Coder加密保存数据
	 */
	public void saveDataToEncode(String key, String content) {
		String result = Base64Coder.encodeString(content);
		mSave.datas.put(key, result);
	}

	/**
	 * Base64Coder解密获取数据
	 */
	public String loadDataValue(String key, Class<String> type) {
		if (mSave.datas.containsKey(key)) {
			return Base64Coder.decodeString((String) mSave.datas.get(key));
		}
		return null;
	}

	private static class Save {
		public ObjectMap<String, Object> datas = new ObjectMap<String, Object>();
	}
}

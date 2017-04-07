package com.heyzqt.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.heyzqt.entity.Ranking;
import com.heyzqt.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyzqt on 2017/4/5.
 *
 * 对数据进行保存(Preferences)加密解密(Base64Coder)处理
 */
public class DataUtils {

	private Preferences mPreferences;

	private static DataUtils dataSavaSecurity = null;

	private DataUtils() {
		mPreferences = Gdx.app.getPreferences(Constant.PREFERENCES_NAME);
	}

	public static DataUtils getInstance() {
		if (dataSavaSecurity == null) {
			dataSavaSecurity = new DataUtils();
		}
		return dataSavaSecurity;
	}

	public void initPreferences() {
		User user = new User();
		List<Ranking> rankings = new ArrayList<Ranking>();
		Ranking ranking = new Ranking();
		rankings.add(ranking);
		rankings.add(ranking);
		rankings.add(ranking);
		saveUserDataToEncode(user);
		saveRankingDataToEncode(rankings);
	}

	/**
	 * Base64Coder解密获取单个数据
	 */
	public String getDataFromDecode(String key) {
		String result = Base64Coder.decodeString(mPreferences.getString(key));
		return result;
	}

	/**
	 * 获取当前用户数据
	 */
	public User getUserData() {
		User user = new User();
		user.name = getDataFromDecode(Constant.PREFERENCES_USERNAME);
		user.HP = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_HP));
		user.MP = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_MP));
		user.score = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_CUR_SCORE));
		user.time = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_CUR_TIME));
		user.curLevel = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_CUR_LEVEL));
		return user;
	}

	/**
	 * 获取排行榜单数据
	 */
	public List<Ranking> getRankings() {
		List<Ranking> lists = new ArrayList<Ranking>();
		Ranking ranking = new Ranking();

		for (int i = 0; i < 3; i++) {
			ranking.name = getDataFromDecode(Constant.PREFERENCES_RANGE_NAME + i);
			ranking.score = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_RANGE_SCORE + i));
			ranking.time = Integer.parseInt(getDataFromDecode(Constant.PREFERENCES_RANGE_TIME + i));
			ranking.weight = Double.parseDouble(getDataFromDecode(Constant.PREFERENCES_RANGE_WEIGHT + i));
			lists.add(ranking);
		}

		return lists;
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
	 * Base64Coder加密保存用户相关数据
	 */
	public void saveUserDataToEncode(User user) {
		saveDataToEncode(Constant.PREFERENCES_USERNAME, user.name);
		saveDataToEncode(Constant.PREFERENCES_HP, user.HP + "");
		saveDataToEncode(Constant.PREFERENCES_MP, user.MP + "");
		saveDataToEncode(Constant.PREFERENCES_CUR_TIME, user.time + "");
		saveDataToEncode(Constant.PREFERENCES_CUR_SCORE, user.score + "");
		saveDataToEncode(Constant.PREFERENCES_CUR_LEVEL, user.curLevel + "");
	}

	/**
	 * Base64Coder加密保存单个用户信息到排行榜
	 */
	public void saveSingleRankingDataToEncode(int i, Ranking ranking) {
		saveDataToEncode(Constant.PREFERENCES_RANGE_NAME + i, ranking.name);
		saveDataToEncode(Constant.PREFERENCES_RANGE_SCORE + i, ranking.score + "");
		saveDataToEncode(Constant.PREFERENCES_RANGE_TIME + i, ranking.time + "");
		saveDataToEncode(Constant.PREFERENCES_RANGE_WEIGHT + i, ranking.weight + "");
	}

	/**
	 * Base64Coder加密保存排行榜相关数据
	 */
	public void saveRankingDataToEncode(List<Ranking> rankings) {
		for (int i = 0; i < rankings.size(); i++) {
			saveDataToEncode(Constant.PREFERENCES_RANGE_NAME + i, rankings.get(i).name);
			saveDataToEncode(Constant.PREFERENCES_RANGE_SCORE + i, rankings.get(i).score + "");
			saveDataToEncode(Constant.PREFERENCES_RANGE_TIME + i, rankings.get(i).time + "");
			saveDataToEncode(Constant.PREFERENCES_RANGE_WEIGHT + i, rankings.get(i).weight + "");
		}
	}

	/**
	 * Base64Coder加密保存所有数据
	 */
	public void saveAllDataToEncode(User user, List<Ranking> rankings) {
		saveUserDataToEncode(user);
		saveRankingDataToEncode(rankings);
	}
}

package com.heyzqt.util;

import com.heyzqt.entity.Ranking;
import com.heyzqt.entity.User;
import com.heyzqt.handle.DataUtils;
import com.heyzqt.xiyou.MyGdxGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by heyzqt on 2017/4/6.
 *
 * 排行榜数据操作类
 */
public class RankingUtils {

	/**
	 * 排行榜是否为空
	 */
	public static boolean isRankingEmpty() {
		String result = MyGdxGame.rankings.get(0).name;
		if (result.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 返回当前排行榜上榜人数
	 */
	public static int getRankingSize() {
		int count = 1;
		if (isRankingEmpty()) {
			return 0;
		}

		String name2 = MyGdxGame.rankings.get(1).name;
		String name3 = MyGdxGame.rankings.get(2).name;

		if (!name2.equals("")) {
			count++;
		}

		if (!name3.equals("")) {
			count++;
		}

		return count;
	}

	/**
	 * weight计算 = 分数*60%+时间*40%
	 */
	public static double getWeight(int score, int time) {
		return score * 0.6 + time * 0.4;
	}

	/**
	 * 保存用户信息到排行榜
	 */
	public static void saveUserToRanking(User user) {
		Ranking ranking = Utils.changeUserToRanking(user);
		switch (getRankingSize()) {
			case 0:
				MyGdxGame.rankings.add(ranking);
				DataUtils.getInstance().saveSingleRankingDataToEncode(0, ranking);
				break;
			case 1:
			case 2:
			case 3:
				MyGdxGame.rankings.add(ranking);
				sortRankingList(MyGdxGame.rankings);
				DataUtils.getInstance().saveRankingDataToEncode(MyGdxGame.rankings);
				break;
			default:
				break;
		}
	}

	/**
	 * 比较排行榜A和B的综合成绩,并返回较大对象
	 */
	public static Ranking compareAwithB(Ranking rankingA, Ranking rankingB) {
		if (rankingA.weight > rankingB.weight) {
			return rankingA;
		} else {
			return rankingB;
		}
	}

	/**
	 * 按照排行榜数据大小重新排序
	 */
	public static void sortRankingList(ArrayList<Ranking> rankings) {
		Collections.sort(rankings, new SortRankingByWeight());

		if (rankings.size() == 4) {
			rankings.remove(3);
		}
	}
}

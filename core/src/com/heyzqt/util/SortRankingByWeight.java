package com.heyzqt.util;

import com.heyzqt.entity.Ranking;

import java.util.Comparator;

/**
 * Created by heyzqt on 2017/4/7.
 *
 * 设置Ranking的排序规则
 */
public class SortRankingByWeight implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		Ranking rankingA = (Ranking) o1;
		Ranking rankingB = (Ranking) o2;
		if (rankingA.weight <= rankingB.weight) {        //降序
			return 1;
		}
		return -1;
	}
}

package com.yunfu.leaf.algorithm.common;

public class TestMaxDiffStr {

	public static void main(String[] args) {
		String str = "asdfdewfdsafa";
		System.out.println(lengthOfLongestSubstring(str));
	}

	public static int lengthOfLongestSubstring(String s) {
		int ALPHA_MAX = 26;
		// 记录字符上次出现过的位置
		int last[] = new int[ALPHA_MAX];
		// 记录当前子串的起始位置
		int start = 0;
		int maxLen = 0;

		// 初始化last数组
		for (int i = 0; i < ALPHA_MAX; i++) {
			last[i] = -1;
		}

		for (int i = 0; i < s.length(); i++) {
			// 当前元素重复
			if (last[s.charAt(i) - 'a'] >= start) {
				maxLen = maxLen > (i - start) ? maxLen : (i - start);

				// 移动当前位置到上一个重复元素的下一位
				start = last[s.charAt(i) - 'a'] + 1;
			}

			// 记录当前字符的位置
			last[s.charAt(i) - 'a'] = i;
		}

		// 最后一次别忘了
		return maxLen > (s.length() - start) ? maxLen : (s.length() - start);
	};
}

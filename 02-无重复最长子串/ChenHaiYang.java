/**
 * @author haiyang.chen
 */
public class Question20190514 {
	public static void main(String[] args) {
		System.out.println(getNoRepeatSubStringMaxLength("abcabcbb"));
		System.out.println(getNoRepeatSubStringMaxLength("bbbbb"));
		System.out.println(getNoRepeatSubStringMaxLength("pwwkew"));
	}

	private static int getNoRepeatSubStringMaxLength(String string) {
		if (string == null || "".equals(string)) {
			return 0;
		}

		int maxNotRepeatLength = 0;

		int startIndex = 0;
		int endIndex = 0;

		final char[] chars = string.toCharArray();

		for (char currentChar : chars) {
			if ((endIndex - startIndex) > maxNotRepeatLength) {
				maxNotRepeatLength = endIndex - startIndex;
			}

			if (arrayContains(chars, startIndex, endIndex, currentChar)) {
				startIndex++;
			} else {
				endIndex++;
			}

		}

		return maxNotRepeatLength;

	}

	private static boolean arrayContains(char[] chars, int start, int end, char aChar) {
		if (start == end) {
			return false;
		}

		for (int i = start; i < end; i++) {
			if (chars[i] == aChar) {
				return true;
			}
		}

		return false;
	}
}

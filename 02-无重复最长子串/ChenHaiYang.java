/**
 * @author haiyang.chen
 */
public class Question20190514 {
	public static void main(String[] args) {
		System.out.println(getNoRepeatSubStringMaxLength("abcabcbb"));
		System.out.println(getNoRepeatSubStringMaxLength("bbbbb"));
		System.out.println(getNoRepeatSubStringMaxLength("pwwkew"));
		System.out.println(getNoRepeatSubStringMaxLength("pwkeZwycndyu"));

	}

	private static int getNoRepeatSubStringMaxLength(String string) {
		if (string == null || "".equals(string)) {
			return 0;
		}

		int maxNotRepeatLength = 0;

		int startIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < string.length(); i++) {
			if ((endIndex - startIndex) > maxNotRepeatLength) {
				maxNotRepeatLength = endIndex - startIndex;
			}

			if (contains(string, startIndex, endIndex, string.charAt(i))) {
				startIndex++;
			} else {
				endIndex++;
			}

		}

		return maxNotRepeatLength;

	}

	private static boolean contains(String string, int start, int end, char aChar) {
		if (start == end) {
			return false;
		}

		for (int i = start; i < end; i++) {
			if (string.charAt(i) == aChar) {
				return true;
			}
		}

		return false;
	}
}

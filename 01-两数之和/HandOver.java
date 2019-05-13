import java.util.HashMap;

/*
 * by wh
 * 
 * find the indicies of the two digits with the sum is the required number.
 */

public class HandOver {

	public static void main(String args[]) {
		System.out.println("Hello My HomeWork");
		Integer[] interger = {1,2,3,4,5,6,7,8,9,0};
		System.out.println(findIndicies(interger, 10));
	}
	
	// 思考：如果有两个数据的值是一样的会发生什么事情？ MAP不允许存在两个Key一样的元素。
	public static String findIndicies(Integer inputNumbers[], int nSum) {

		String strResult = "";

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < inputNumbers.length; i++) {
			map.put(inputNumbers[i], i);
		}

		for (int i = 0; i < inputNumbers.length; i++) {
			if (map.containsKey(nSum - inputNumbers[i])) {
				strResult += "下标是 : " + i + " 和 " + map.get(nSum - inputNumbers[i]) + " 的元素的和是: " + nSum + "\r\n";
				map.remove(inputNumbers[i]);
				map.remove(nSum - inputNumbers[i]);
			}
		}
		return strResult;
	}

}

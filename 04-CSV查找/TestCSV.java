package com.yunfu.leaf.algorithm.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestCSV {

	public static String[] title = null;
	public static Integer rowNum = null;
	public static String[][] data = null;

	public static void main(String[] args) throws IOException {
		loadCSV("d:\\data\\a.csv");
		Integer[][] values = find("age","12");
		for (Integer[] value : values) {
            System.out.println(Arrays.toString(value));
        }
	}

	public static void loadCSV(String path) throws IOException {
		FileReader in = new FileReader("d:\\data\\a.csv");
		BufferedReader br = new BufferedReader(in);
		String rec = null;
		String[] argsArr = null;
		int row = 0;
		while ((rec = br.readLine()) != null) {
			argsArr = rec.split(",");
			if (row == 0) {
				title = argsArr;
				data = new String[65535][title.length];
			} else {
				for (int i = 0; i < title.length; i++) {
					data[row][i] = argsArr[i];
				}
			}
			++row;
		}
		rowNum = row;
	}

	public static Integer[][] find(String key, String value) {
		int colNum = 0;
		for (int i = 0; i < title.length; i++) {
			if (key.equals(title[i])) {
				colNum = i;
				break;
			}
		}
		ArrayList<Integer[]> hasData = new ArrayList<Integer[]>();
		for (int j = 1; j < rowNum; j++) {
			if (value.equals(data[j][colNum])) {
				hasData.add(new Integer[]{j,colNum});
			}
		}
		Integer[][] dataPoint = new Integer[][]{};
		dataPoint = hasData.toArray(dataPoint);
        return dataPoint;
	}

}
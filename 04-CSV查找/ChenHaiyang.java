import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 实现一个csv文件简单操作类，实现两个方法:
 * load() - 加载数据，请不要用第三方csv解析类
 * find() - 按照字段名=字段值 条件查询并返回结果集。
 * 内存不限，要求find方法尽量快。
 *
 * @author haiyang.chen
 * @since 0.0.1
 */
public class ChenHaiyang {

    public static void main(String[] args) throws IOException {

        CvsReader cvsReader = new CvsReaderImpl();
        cvsReader.load("H:\\IdeaProjects\\algorithm\\04-CSV查找\\sample.csv");
        print(cvsReader.find("distinct_id", "000A1968-0B49-4094-9F0B-5001EDCB5342"));
        System.out.println("****************");
        print(cvsReader.find("Y400", null));
        System.out.println("****************");
        print(cvsReader.find("date", "2018-01-23"));

    }

    private static void print(String[][] array) {
        if (array == null) {
            return;
        }
        for (String[] tmp : array) {
            for (String str : tmp) {
                System.out.print(str);
                System.out.print(",");
            }
            System.out.println();
        }
    }

}

interface CvsReader {
    /**
     * 加载
     *
     * @param path 文件路径
     */
    void load(String path) throws IOException;

    /**
     * 查找字段名为{@code fieldName}且值为 {@code fieldValue} 的记录
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return 如果不存在, 则返回null, 否则返回符合条件的集合
     */
    String[][] find(String fieldName, String fieldValue);
}

class CvsReaderImpl implements CvsReader {

    public static final int NOT_EXISTS = -1;
    String[] columnTiles = null;
    String[][] content = new String[][]{};

    @Override
    public void load(String path) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));

        String line = bufferedReader.readLine();

        if (line == null) {
            return;
        }

        initColumnTitle(line);

        List<String[]> array = new LinkedList<String[]>();

        while ((line = bufferedReader.readLine()) != null) {
            array.add(split(line));
        }
        content = array.toArray(content);
    }

    private void initColumnTitle(String line) {
        columnTiles = split(line);
    }

    private String[] split(String line) {
        final String[] split = line.split(",");
        for (int i = 0; i < split.length; i++) {
            if ("NULL".equals(split[i])) {
                split[i] = null;
            }
        }
        return split;
    }

    @Override
    public String[][] find(String fieldName, String fieldValue) {

        if (isEmpty(fieldName)) {
            return null;
        }

        int columnIndex = getIndex(fieldName);

        if (columnIndex == NOT_EXISTS) {
            return null;
        }

        return search(fieldValue, columnIndex);
    }

    private String[][] search(String fieldValue, int columnIndex) {
        List<String[]> result = new LinkedList<String[]>();

        for (int i = 0; i < content.length; i++) {
            if (Objects.equals(fieldValue, content[i][columnIndex])) {
                result.add(content[i]);
            }
        }

        final String[][] array = {};
        return result.toArray(array);
    }

    private int getIndex(String fieldName) {
        int columnIndex = NOT_EXISTS;
        for (int i = 0; i < columnTiles.length; i++) {
            if (columnTiles[i].equals(fieldName)) {
                columnIndex = i;
                break;
            }
        }
        return columnIndex;
    }

    private boolean isEmpty(String fieldName) {
        return fieldName == null || "".equals(fieldName);
    }
}



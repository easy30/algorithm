package com.cehome.csv;

import java.io.*;
import java.util.*;

import static com.cehome.csv.Tag.Type.*;

/**
 *  实现一个csv文件简单操作类，实现两个方法:
 *  load() - 加载数据，请不要用第三方csv解析类
 *  find() - 按照字段名=字段值 条件查询并返回结果集。
 *  内存不限，要求find方法尽量快。
 */
public class CSVFind {

    static int lastChar = -2;

    //换行
    static final char LF = '\n';

    static final char CR = '\r';

    static final char TAB = '\t';

    static final char BACKSPACE = '\b';

    //换页
    static final char FF = '\f';

    static final char COMMA = ',';

    static final int END_OF_STREAM = -1;

    //定义反斜线作为转译字符
    private static final char DISABLED = '\\';

    static final Character DOUBLE_QUOTE_CHAR = Character.valueOf('"');

    private final BufferedReader reader;

    /**
     * 可以重复使用tag
     */
    private final Tag reusableTag;

    private static List<String[]> list = new ArrayList<>();

    public CSVFind(BufferedReader reader, Tag tag) {
        this.reusableTag = tag;
        this.reader = reader;
    }

    public static void main(String[] args) throws Exception {
        String path = "/work/github/cehome-com/algorithm/04-CSV查找/sample.csv";
        loadCSV(path);
        String[][] values = find("date","2018-01-23");
        for (String[] value : values) {
            System.out.println(Arrays.toString(value));
        }
    }

    public static String[][] find(String fieldName, String fieldValue) {
        int index = -1;
        String[] heads = list.get(0);
        for(int j=0; j<heads.length; j++) {
            String value = heads[j];
            if (fieldName.equals(value)) {
                index = j;
                break;
            }
        }
        List<String[]> dataList = new ArrayList<>();
        for (int i=1; i<list.size(); i++) {
            String[] values = list.get(i);
            if (values[index].equals(fieldValue)) {
                dataList.add(values);
            }
        }
        String[][] content = new String[][]{};
        content = dataList.toArray(content);
        return content;
    }


    public static void loadCSV(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        CSVFind find = new CSVFind(reader, new Tag());
        String[] values;
        while ((values = find.nextLine()) != null && values.length > 0) {
            list.add(values);
        }
    }

    //下一行
    String[] nextLine() throws IOException {
        List<String> values = new ArrayList<>();
        do {
            this.reusableTag.reset();
            this.next(this.reusableTag);
            switch (this.reusableTag.type) {
                case TOKEN:
                    values.add(this.reusableTag.content.toString());
                    break;
                case EORECORD:
                    values.add(this.reusableTag.content.toString());
                    break;
                case EOF:
                    if (this.reusableTag.isReady) {
                        values.add(this.reusableTag.content.toString());
                    }
                    break;
                default:
                    throw new IllegalStateException("无效的标记类型: " + this.reusableTag.type);
            }
        } while (this.reusableTag.type == TOKEN);

        return values.toArray(new String[values.size()]);
    }

    //下一个标记
    Tag next(final Tag tag) throws IOException {
        int ch = reader.read();
        while (true) {
            if (readEndOfLine(ch)) {
                tag.type = EORECORD;
                break;
            } else if (isEndOfFile(ch)) {
                tag.type = EOF;
                break;
            } else if (isDelimiter(ch)) {
                tag.type = TOKEN;
                break;
            } else if (isEscape(ch)) {
                final int unescaped = readEscape(reader);
                if (unescaped == END_OF_STREAM) { // unexpected char after escape
                    tag.content.append((char) ch).append((char) lastChar);
                } else {
                    tag.content.append((char) unescaped);
                }
                ch = reader.read(); // continue
            } else {
                tag.content.append((char) ch);
                ch = reader.read(); // continue
            }
        }
        return tag;
    }

    //判断是否为行尾
    boolean readEndOfLine(int ch) {
        return ch == LF || ch == CR;
    }

    //判断是否为分割符
    boolean isDelimiter(final int ch) {
        return ch == COMMA;
    }

    //判断是否为转译字符
    boolean isEscape(final int ch) {
        return ch == DISABLED;
    }

    //判断是否为元字符
    private boolean isMetaChar(final int ch) {
        return ch == COMMA ||
                ch == DISABLED ||
                ch == DOUBLE_QUOTE_CHAR;
    }

    //判断是否为文件结尾
    boolean isEndOfFile(final int ch) {
        return ch == END_OF_STREAM;
    }

    /**
     * 读转译字符
     * @param reader
     * @return
     * @throws IOException
     */
    int readEscape(BufferedReader reader) throws IOException {
        // the escape char has just been read (normally a backslash)
        final int ch = reader.read();
        switch (ch) {
            case 'r':
                return CR;
            case 'n':
                return LF;
            case 't':
                return TAB;
            case 'b':
                return BACKSPACE;
            case 'f':
                return FF;
            case CR:
            case LF:
            case FF: // TODO is this correct?
            case TAB: // TODO is this correct? Do tabs need to be escaped?
            case BACKSPACE: // TODO is this correct?
                return ch;
            case END_OF_STREAM:
                throw new IOException("EOF whilst processing escape sequence");
            default:
                // 判断是否为元字符
                if (isMetaChar(ch)) {
                    return ch;
                }
                // indicate unexpected char - available from in.getLastChar()
                return END_OF_STREAM;
        }
    }
}

final class Tag{

    /** 初始化标记内容的长度 */
    private static final int INITIAL_TOKEN_LENGTH = 50;

    enum Type {
        /** 标记没有有效内容，即处于初始化状态 */
        INVALID,

        /** 在行首或行中带有内容的标记 */
        TOKEN,

        /** 标记 (有内容) 当到达文件结尾时. */
        EOF,

        /** 到达行尾时带有内容的标记 */
        EORECORD,

    }

    Tag.Type type = INVALID;

    /** 声明存储读取内容的buffer */
    final StringBuilder content = new StringBuilder(INITIAL_TOKEN_LENGTH);

    boolean isReady;

    void reset() {
        content.setLength(0);
        type = INVALID;
        isReady = false;
    }
}

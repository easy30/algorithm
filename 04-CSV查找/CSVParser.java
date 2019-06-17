package algorithm.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * coolma 2019/5/29
 */
public class CSVParser {


    private List<String[]> records = new ArrayList();
    private List<String> record = new ArrayList<String>();
    private int status = 0;
    private char QUOTE = '\"';
    private boolean inQuote = false;
    private boolean findR = false;
    private StringBuilder value = new StringBuilder();
    private InputStreamReader reader = null;
    private StringBuilder value2 = new StringBuilder();
    private Map<String, Map<String, List<String[]>>> indexMap = new HashMap();
    private String[] fields;

    public CSVParser() {

    }

    public void load(String file, String charset) throws IOException {
        reader = new InputStreamReader(new FileInputStream(file), charset);
        parse();
    }

    private Character nextChar() throws IOException {
        int n = reader.read();

        if (n == -1) throw new EOFException("eof");
        value2.append((char) n);
        return (char) n;
    }

    protected void parse() {


        try {
            while (true) {

                if (status == 0) {
                    start();

                    //status=1;
                } else if (status == 1) {

                    proceed();


                }


            }
        } catch (EOFException e) {
            if (status == 1) addField();
            addRecord();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void start() throws IOException {
        Character c = nextChar();
        if (c == QUOTE) {
            inQuote = true;

        } else if (c == '\r' || c == '\n') {
            if (c == '\r') {
                c = nextChar();
            }
            if (c == '\n') {
                addRecord();

            }

        } else {
            value.append(c);
        }

        status = 1;

    }

    private void proceed() throws IOException {
        Character c = nextChar();
        if (inQuote) {
            if (c == QUOTE) {
                c = nextChar();
                if (c == QUOTE) {
                    value.append(c);
                } else {
                    // end value
                    addField();

                    if (c == ',') {
                        // System.out.println(",");

                    } else {
                        if (c == '\r') {
                            c = nextChar();
                        }
                        if (c == '\n') {
                            addRecord();

                        }
                    }
                }


            } else {
                value.append(c);
            }


        } else {
            if (c == ',') {
                addField();

            } else if (c == '\r') {

            } else if (c == '\n') {

                addField();
                addRecord();

            } else {
                value.append(c);
            }

        }
    }

    private void addField() {
        record.add(value.toString());
        value = new StringBuilder();
        inQuote = false;
        status = 0;// end value


    }


    private void addRecord() {
        String[] row = record.toArray(new String[0]);
        if (fields == null) {
            fields = row;
        } else {
            records.add(row);
            addIndex(row);
        }
        record.clear();
        status = 0;
        // Character c=nextChar();


    }

    private void addIndex(String[] row) {
        for (int i = 0; i < row.length; i++) {
            String fieldName = fields[i];
            String fieldValue = row[i];
            Map<String, List<String[]>> fieldMap = indexMap.get(fieldName);
            if (fieldMap == null) {
                fieldMap = new HashMap();
                indexMap.put(fieldName, fieldMap);
            }
            List<String[]> rows = fieldMap.get(fieldValue);
            if (rows == null) {
                rows = new ArrayList();
                fieldMap.put(fieldValue, rows);
            }
            rows.add(row);

        }

    }


    public List<String[]> getRecords() {
        return records;
    }

    public void setRecords(List<String[]> records) {
        this.records = records;
    }

    public String[][] find(String fieldName, String fieldValue) {
        Map<String, List<String[]>> fieldMap = indexMap.get(fieldName);
        if (fieldMap == null) return new String[0][0];
        List<String[]> rows = fieldMap.getOrDefault(fieldValue, new ArrayList());
        return rows.toArray(new String[0][0]);

    }

    public static void main(String[] args) throws IOException {
        CSVParser parser = new CSVParser();
        parser.load("/sample.csv", "UTF-8");
        for (String[] r : parser.getRecords()) {
            for (String s : r) {
                System.out.print(s + "@----");
            }
            System.out.println("\r\n*****************");
        }
        System.out.println("==================================");
        String[][] rows = parser.find("brand", "小松");
        for (String[] r : rows) {
            for (String s : r) {
                System.out.print(s + "@----");
            }
            System.out.println("\r\n*****************");
        }


    }


}

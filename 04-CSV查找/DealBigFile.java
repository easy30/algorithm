package com.hjq.study;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DealBigFile {
    private static List<String[]> dataList=new ArrayList<String[]>();

    /**
     * 按行分割文件
     * @param sourceFilePath 为源文件路径
     * @param targetDirectoryPath 文件分割后存放的目标目录
     * @param rows 为多少行一个文件
     */
    public static int splitFileByLine(String sourceFilePath, String targetDirectoryPath, int rows) {
        String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf("."));//源文件名
        String splitFileName = targetDirectoryPath + File.separator + sourceFileName + "-%s.csv";//切割后的文件名
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        PrintWriter pw = null;//字符输出流
        String tempLine;
        int lineNum = 0;//本次行数累计 , 达到rows开辟新文件
        int splitFileIndex = 1;//当前文件索引

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)))) {
            pw = new PrintWriter(String.format(splitFileName, splitFileIndex));
            while ((tempLine = br.readLine()) != null) {
                if (lineNum > 0 && lineNum % rows == 0) {//需要换新文件
                    pw.flush();
                    pw.close();
                    pw = new PrintWriter(String.format(splitFileName , ++splitFileIndex));
                }
                pw.write(tempLine + "\n");
                lineNum++;
            }
            return splitFileIndex;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }
    }
    public static String[][] find(String directoryPath,String fileName,String fieldName,String fieldValue,int count) {
        String splitFileName = directoryPath + File.separator + fileName + "-%s.csv";//切割后的文件名
        int headIndex=loadHead(String.format(splitFileName, 1),fieldName);
        for(int i=1;i<=count;i++){
            load(String.format(splitFileName, i),headIndex,fieldValue);
        }
        String[][] arr=convert();
        return arr;
    }
    public static  int loadHead(String path,String fieldName){
        int columnIndex=-1;
        BufferedReader br=null;
        try {
            File file=new File(path);
            br = new BufferedReader(new FileReader(file));
            String head=br.readLine();
            String[] headArr=head.split(",");
            for (int i=0;i<headArr.length;i++){
                if(headArr[i].equals(fieldName)){
                    columnIndex=i;
                    break;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return columnIndex;
    }
    public static  void load(String path,int headIndex,String fieldValue){

        BufferedReader br=null;
        try {
            File file=new File(path);
            br = new BufferedReader(new FileReader(file));

            if(headIndex>-1){
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] column=line.split(",");
                    try {
                        if (column[headIndex].equals(fieldValue)) {
                            dataList.add(column);
                        }
                    }catch (Exception e){

                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
             if(br!=null){
                 try {
                    br.close();
                    br=null;
                 } catch (IOException e) {
                    e.printStackTrace();
                 }
            }
        }
    }

    public static String[][] convert(){

        String[][] arr=new String[dataList.size()][];
        for(int i=0;i<dataList.size();i++){
            arr[i]=dataList.get(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        Table table=new Table();
        String path="E:\\tiejia_space\\study\\src\\main\\resources\\equipment.csv";
        String targetDirectoryPath="E:\\tiejia_space\\study\\src\\main\\resources\\";
//        int count=DealBigFile.splitFileByLine(path,targetDirectoryPath,100000);
        String[][] result=DealBigFile.find(targetDirectoryPath,"equipment","\"price\"","\"380000\"",10);
        System.out.println(result.length);
    }
}

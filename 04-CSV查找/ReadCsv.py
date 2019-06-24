# -*- coding:utf-8 -*-
'''
查询csv文件中某列等于某值的数据
@Author zahi
'''
import csv

def readCsvF(columName,columValue):
    titleIdx = None
    resArray = []
    with open("sample.csv","r") as fileObj:
        csvObj = csv.reader(fileObj)
        for cdata in csvObj:
            if titleIdx:
                if cdata[titleIdx] == columValue:
                    resArray.append(cdata)

            else:
                for i in range(0, len(cdata)):
                    if cdata[i] == columName:
                        titleIdx = i
                        break

    for data in resArray:
        print(data)

if __name__ == '__main__':
    readCsvF("brand","现代")
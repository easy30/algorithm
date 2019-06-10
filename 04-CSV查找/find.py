'''
实现一个csv文件简单操作类，实现两个方法:
load() - 加载数据，请不要用第三方csv解析类
find() - 按照字段名=字段值 条件查询并返回结果集。
内存不限，要求find方法尽量快。
'''
import csv

def load(csvname):
    csvreader=csv.reader(open(csvname))
    return csvreader


def find(csvreader,findcloum,findchar):
    returnArray=[]
    cloumname=None
    for row in csvreader:
        if cloumname:
            if row[cloumname]==findchar:
                returnArray.append(row)
        else:
            for item in range(len(row)):
                if row[item]==findcloum:
                    cloumname=item
                    break
    return returnArray


if __name__ == '__main__':
    csvreader= load('sample.csv')
    print(find(csvreader,'eq_id','645000'))
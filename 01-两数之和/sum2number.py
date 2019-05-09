# -*- coding:utf-8 -*-
#zahi
#给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
def sum(numbers,target):
    if not target:
        return None
    if not numbers:
        return None
    numDict = {}
    for i in range(len(numbers)):
        other = target - numbers[i]
        if(numDict.get(other) != None):
            return [numDict.get(other),i]
        else:
            numDict[numbers[i]] = i

if __name__ == '__main__':
    print(sum([100,23,122,45,1],123))
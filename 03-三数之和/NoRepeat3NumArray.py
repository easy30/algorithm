'''
给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。

例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，

满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]

@Author zahi
'''

def get3NumArray(numbers,target):
    if not numbers:
        return None
    if None == target:
        return None
    #定义字典存储计算好的组合
    numDict = {}
    #给参数数组排序：从小到大
    numbers.sort()
    #定义三个指针
    lIdx = 0
    rIdx = len(numbers) - 1
    mIdx = 0
    for i in range(0,len(numbers)):
        lIdx = i
        mIdx = i + 1
        rIdx = len(numbers) - 1
        if mIdx >= rIdx:
            break
        if numbers[lIdx] >= target:
            break
        #如果是存在的直接进入下次循环
        if numDict.get(numbers[lIdx]):
            continue
        tmpSum = target - numbers[lIdx]
        while True:
            if mIdx >= rIdx:
                break
            if tmpSum == numbers[mIdx] + numbers[rIdx]:
                numDict[numbers[lIdx]] = [numbers[lIdx],numbers[mIdx],numbers[rIdx]]
                break
            elif tmpSum > numbers[mIdx] + numbers[rIdx]:
                mIdx += 1
                continue
            elif tmpSum < numbers[mIdx] + numbers[rIdx]:
                rIdx -= 1
                continue
    return list(numDict.values())


if __name__ == '__main__':
    print(get3NumArray([1,2,-1,-2,0,0,-2,-3,1,2,3,4,5],0))

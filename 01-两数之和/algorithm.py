'''
summary:2019-05-07算法题目
给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
示例:
给定 nums = [2, 7, 11, 15], target = 9
因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
auth:wade
date:2019-05-08
'''
def findNumbers(nums, target):
    # 新建立一个空字典用来保存数值及其在列表中对应的索引
    dictTemp = {}
    for i in range(0, len(nums)):
        subNumber = target - nums[i]
        # 如果另一个数值不在字典中，则将第一个数值及其的索引保存在字典中
        if subNumber not in dictTemp:
            dictTemp[nums[i]] = i
        else:
            return [dictTemp[subNumber],i]

if __name__ == '__main__':
    nums = [2,7,11,15]
    target = 9
    result = findNumbers(nums, target)
    for i in result:
        print(i)

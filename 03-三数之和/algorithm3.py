'''
summary:2019-05-22算法题目
本周题目：
给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
注意：答案中不可以包含重复的三元组。
例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]
auth:wade
date:2019-05-22
'''
def findThreetuple(list):
    if len(list):
        # 存储结果列表
        res_list = []
        # 对nums列表进行排序，无返回值，排序直接改变nums顺序
        list.sort()
        for i in range(len(list)):
            # 如果排序后第一个数都大于0，则跳出循环，不可能有为0的三数之和
            if list[i] > 0:
                break
            # 排序后相邻两数如果相等，则跳出当前循环继续下一次循环，相同的数只需要计算一次
            if i > 0 and list[i] == list[i - 1]:
                continue
            # 记录i的下一个位置
            start = i + 1
            # 最后一个元素的位置
            end = len(list) - 1
            while start < end:
                # 判断三数之和是否为0
                print(list[start])
                print(list[end])
                print(-list[i])
                if list[start] + list[end] == -list[i]:
                    # 把结果加入数组中
                    res_list.append([list[i], list[start], list[end]])
                    # 判断start相邻元素是否相等，有的话跳过这个
                    while start < end and list[start] == list[start + 1]: start += 1
                    # 判断后面end的相邻元素是否相等，是的话跳过
                    while start < end and list[end] == list[end - 1]: end -= 1
                    # 没有相等则start+1，end-1，缩小范围
                    start += 1
                    end -= 1
                # 小于-list[i]的话还能往后取
                elif list[start] + list[end] < -list[i]:
                    start += 1
                else:
                    end -= 1
        return res_list
    else:
        return '数组不能为空'


if __name__ == '__main__':
    list =[1,2,3,4,6,8,-1,0,9,-7]
    # list=[-1, 0, 1, -1 , 2, -1, -4 , 2, 0 , 0 ,0 , 0 ,-1]
    list = [-4, -3, -2, -1, 0, 1, 2, 3, 4]
    print(findThreetuple(list))

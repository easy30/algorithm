import UIKit

let nums = [-1, 0, 1, 2, -1, -4]
// 组合数
let total = nums.count * (nums.count - 1) * (nums.count - 2) / (3 * 2 * 1)

var cache = [String:String]()

var i = 0 , j = 1, k = 2;
for _ in 0 ..< total {
    if (0 == nums[i]+nums[j]+nums[k]){
        cache["\(min(min(nums[i], nums[j]), nums[k])),\(max(max(nums[i], nums[j]), nums[k]))"] = "[\(nums[i]),\(nums[j]),\(nums[k])]"
    }
    k += 1
    if k >= nums.count {
        j += 1
        k = j + 1
    }
    if j >= nums.count - 1 {
        i += 1
        j = i + 1
        k = j + 1
    }
}

for value in cache.values {
    print(value);
}

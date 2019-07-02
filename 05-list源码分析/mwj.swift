import UIKit

//var str = "Hello, playground"
//从第一个数据结构开始：参考java中数据结构源码：ArrayList，Vector， ArrayBlockingQueue实现
//自定义一个循环队列 CycleQueue：
//void insert(long value); //从队列尾部添加数据
//Long remove(); //从队列头部删除数据；
//boolean isFull();
//boolean isEmpty();
//
//将此队列升级为同步队列 SynQueue； 考虑并发情况；
//void insert(long value); //从队列尾部添加数据
//Long remove(); //从队列头部删除数据；
//boolean isFull();
//boolean isEmpty();
//
//在将此循环队列升级为阻塞队列 BlockingQueue；
//void insert(long value); //从队列尾部添加数据
//Long remove(); //从队列头部删除数据-并返回删除元素；
//boolean isFull();
//boolean isEmpty();


// 普通队列
class CycleQueue: NSObject {
    private let max = 100
    
    // 头部
    private var header: Node! = nil
    // 尾部
    private var footer: Node! = nil
    
    
    public func insert(value: Int){
        let node = Node()
        node.data = value
        if footer == nil {
            self.header = node
            self.footer = node
            return
        }
        self.footer.next = node
        self.footer = node
    }
    
    public func remove() -> Int! {
        if header == nil {
            return nil
        }
        let node = header
        header = header.next
        if header == nil {
            self.footer = nil
        }
        return node?.data
    }
    
    public func isFull() -> Bool {
        if header == nil {
            return false
        }
        var next = header.next
        var i = 1
        while next != nil {
            i += 1
            next = next!.next
        }
        return i >= max
    }
    
    public func isEmpty() -> Bool {
        return self.header == nil
    }
    
    // node
    class Node{
        var next: Node! = nil
        var data: Int! = nil
    }
}

// 同步队列
class SynQueue: NSObject {
    
    private var queue: DispatchQueue! = nil
    
    private let max = 100
    
    // 头部
    private var header: Node! = nil
    // 尾部
    private var footer: Node! = nil
    
    override init() {
        super.init()
        // 单线程队列
        queue = DispatchQueue(label: "com.tiebaobei.ershouji")
    }
    
    
    public func insert(value: Int){
        queue.sync {
            let node = Node()
            node.data = value
            if footer == nil {
                self.header = node
                self.footer = node
                return
            }
            self.footer.next = node
            self.footer = node
        }
    }
    
    public func remove() -> Int! {
        var reuslt: Int!
        queue.sync {
            if header == nil {
                reuslt = nil
            }
            let node = header
            header = header.next
            if header == nil {
                self.footer = nil
            }
            reuslt = node?.data
        }
        return reuslt
    }
    
    public func isFull() -> Bool {
        var reuslt: Bool!
        queue.sync {
            if header == nil {
                reuslt =  false
            }
            var next = header.next
            var i = 1
            while next != nil {
                i += 1
                next = next!.next
            }
            reuslt =  i >= max
        }
        return reuslt
    }
    
    public func isEmpty() -> Bool {
        var reuslt: Bool!
        queue.sync {
            reuslt = self.header == nil
        }
        return reuslt
    }
    
    // node
    class Node {
        var next: Node! = nil
        var data: Int! = nil
    }
}



// 阻塞队列
class BlockingQueue : NSObject {
    private var condition: NSCondition = NSCondition()
    private let max = 100
    
    // 头部
    private var header: Node! = nil
    // 尾部
    private var footer: Node! = nil
    
    
    public func insert(value: Int){
        self.condition.lock()
        if self.isFull() { // 如果满了
            //等待的时候，会释放锁
            self.condition.wait()
        }
        let node = Node()
        node.data = value
        if footer == nil {
            self.header = node
            self.footer = node
            return
        }
        self.footer.next = node
        self.footer = node
        
        self.condition.unlock()
    }
    
    public func remove() -> Int! {
        self.condition.lock()
        if header == nil {
            return nil
        }
        let node = header
        header = header.next
        if header == nil {
            self.footer = nil
        }
        self.condition.unlock()
        // 唤起一个等待锁
        self.condition.signal()
        return node?.data
    }
    
    public func isFull() -> Bool {
        if header == nil {
            return false
        }
        var next = header.next
        var i = 1
        while next != nil {
            i += 1
            next = next!.next
        }
        return i >= max
    }
    
    public func isEmpty() -> Bool {
        return self.header == nil
    }
    
    // node
    class Node{
        var next: Node! = nil
        var data: Int! = nil
    }
}



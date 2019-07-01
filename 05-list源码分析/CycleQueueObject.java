package com.algorithm;

/**
 * 
 * @author wuzhuo
 *
 * @param <E>
 */
public class CycleQueueObject<E>{
	private Object[] data = null;
    private int maxSize; //队列容量
    private int front;  //队列头，允许删除
    private int rear;   //队列尾，允许插入
    private int size = 0; //队列当前长度
    
    public CycleQueueObject(){
    		this(10);
    }
    public CycleQueueObject(int initialSize){
    		if (initialSize >= 0) {
            this.maxSize = initialSize;
            data = new Object[initialSize];
            front = rear = 0;
        } else {
            throw new RuntimeException("初始化大小不能小于0：" + initialSize);
        }
    }
    
	//从队列尾部添加数据
	public boolean insert(E e){
		if (size == maxSize) {
            throw new RuntimeException("队列已满，无法插入新的元素！");
        } else {
            data[rear] = e;
            rear = (rear + 1)%maxSize;
            size ++;
            return true;
        }
	}
	
	//从队列头部删除数据-并返回删除元素；
	public E remove(){
		if (isEmpty()) {
            throw new RuntimeException("空队列异常！");
        } else {
            E value = (E) data[front]; // 保留队列的front端的元素的值
            data[front] = null; // 释放队列的front端的元素
            front = (front+1)%maxSize;  //队首指针加1
            size--;
            return value;
        }
	}
	
	public boolean isFull(){
		return size == maxSize;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
}

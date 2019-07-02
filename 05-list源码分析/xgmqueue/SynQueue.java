package com.yunfu.leaf.algorithm.common.xgmqueue;

/**
 * @author xuegm
 */
public class SynQueue {

	private long[] arr; // 基于数组实现
	private int count;
	private int front;
	private int end;

	/**
	 * 无参构造方法
	 */
	public SynQueue() {
		arr = new long[10];
		count = 0;
		front = 0;
		end = -1;
	}

	/**
	 * 有参构造方法
	 */
	public SynQueue(int initialCapacity) {
		arr = new long[initialCapacity];
		count = 0;
		front = 0;
		end = -1;
	}

	/**
	 * 从队列的尾部添加数据
	 */
	public synchronized void insert(long value) {
		if (end == arr.length - 1) {
			end = -1;
		}
		arr[++end] = value;
		count++;
	}

	/**
	 * 从队列的头部删除数据
	 */
	public synchronized long remove() {
		long value = arr[front++];
		if (front == arr.length) {
			front = 0;
		}
		count--;
		return value;
	}

	/**
	 * 是否满了
	 */
	public synchronized boolean isFull() {
		return count == arr.length;
	}

	/**
	 * 是否为空
	 */
	public synchronized boolean isEmpty() {
		return count == 0;
	}

}

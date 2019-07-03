package com.yunfu.leaf.algorithm.common.xgmqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuegm
 */
public class BlockingQueue {

	private ReentrantLock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();

	private Long[] items;
	private int putIndex;
	private int takeIndex;
	private int count;

	/**
	 * 无参构造方法
	 */
	public BlockingQueue() {
		items = new Long[10];
		putIndex = 0;
		takeIndex = 0;
		count = 0;
	}

	/**
	 * 有参构造方法
	 */
	public BlockingQueue(int initialCapacity) {
		items = new Long[initialCapacity];
		putIndex = 0;
		takeIndex = 0;
		count = 0;
	}

	private void enqueue(Long x) {
		final Long[] items = this.items;
		items[putIndex] = x;
		if (++putIndex == items.length)
			putIndex = 0;
		count++;
		notEmpty.signal();
	}

	private Long dequeue() {
		final Long[] items = this.items;
		Long x = (Long) items[takeIndex];
		items[takeIndex] = null;
		if (++takeIndex == items.length)
			takeIndex = 0;
		count--;
		notFull.signal();
		return x;
	}

	/**
	 * 添加数据
	 */
	public void insert(Long value) {
		lock.lock();
		try {
			while (count == items.length)
				notFull.await();
			enqueue(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 删除数据
	 */
	public long remove() {
		lock.lock();
		try {
			while (count == 0)
				notEmpty.await();
			return dequeue();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return count;
	}

	public boolean isFull() {
		lock.lock();
		try {
			return count == items.length;
		} finally {
			lock.unlock();
		}
	}

	public boolean isEmpty() {
		lock.lock();
		try {
			return count == 0;
		} finally {
			lock.unlock();
		}
	}

}

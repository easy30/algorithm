package com.hjq.study;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列
 */
public class BlockingQueue<E> implements Queue<E>{
    transient Object[] elements;
    transient int head;
    transient int tail;
    transient int capacity;

    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;


    public BlockingQueue(int capacity, boolean fair){
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.elements = new Object[capacity];
        this.capacity=capacity;
        
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();

    }
    @Override
    public boolean insert(E value) throws InterruptedException {
        if(value==null){
            throw new NullPointerException();
        }
        lock.lockInterruptibly();
        try {
            while (isFull())
                notFull.await();
            elements[tail]=value;
            System.out.println("入队"+value);
            tail = (tail+1)%capacity;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return false;
    }



    @Override
    public E remove() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (isEmpty())
                notEmpty.await();
            E obj=(E)elements[head];
            elements[head]=null;
            head=(head+1)%capacity;
            notFull.signal();
            System.out.println("出队"+obj);

            return obj;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean isFull() {
        if((tail+1)%capacity==head){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if((tail)%capacity==head){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        final Queue queue=new BlockingQueue(4,true);
        for (int i = 0; i < 10; i++) {
            final int data = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        queue.insert(data);
                    } catch (InterruptedException e) {

                    }
                }
            }).start();

        }
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Integer data = (Integer) queue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

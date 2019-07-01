package com.hjq.study;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步队列
 */
public class SynQueue<E> implements Queue<E>{
    transient E element;
    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;

    public SynQueue(boolean fair){
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }

    @Override
    public  boolean insert(E value) throws InterruptedException {
        if(value==null){
            throw new NullPointerException();
        }
        lock.lockInterruptibly();
        try {
            while (isFull())
                notFull.await();
            element=value;
            System.out.println("入队"+value);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return false;
    }



    @Override
    public  E remove() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (isEmpty())
                notEmpty.await();
            E obj=element;
            element=null;
            notFull.signal();
            System.out.println("出队"+obj);

            return obj;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public  boolean isFull() {
        if(element!=null){
            return true;
        }
        return false;
    }

    @Override
    public  boolean isEmpty() {
        if(element==null){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        final Queue queue=new SynQueue(true);
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

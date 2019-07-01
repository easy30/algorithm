package com.hjq.study;

import java.util.NoSuchElementException;

/**
 * 循环队列
 */
public class CycleQueue<E> implements Queue<E>{
    transient Object[] elements;
    transient int head;
    transient int tail;
    transient int capacity;

    public CycleQueue(int capacity){
        this.elements=new Object[capacity];
        this.head=0;
        this.tail=0;
        this.capacity=capacity;
    }

    @Override
    public boolean insert(E value) {
        if (value == null)
            throw new NullPointerException();
        if(isFull()){
            return false;
        }
        elements[tail]=value;
        tail = (tail+1)%capacity;
        return true;
    }

    @Override
    public E remove() {
        if(isEmpty()){
            return null;
        }
        E obj=(E)elements[head];
        elements[head]=-1;
        head=(head+1)%capacity;
        return obj;

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

    public static void main(String[] args) throws InterruptedException {
        Queue queue=new CycleQueue(4);
        boolean a=queue.isEmpty();
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
//        queue.remove();
//        queue.remove();
        queue.insert(4);
//        boolean d=queue.isFull();
        queue.insert(5);
        queue.insert(6);
//        boolean b=queue.isEmpty();
//        boolean c=queue.isFull();
        queue.insert(7);
        queue.insert(8);
        queue.insert(9);
        boolean e=queue.isFull();
    }

}

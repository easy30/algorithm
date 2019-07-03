package algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * coolma 2019/7/2
 */
interface Queue<T>{
    boolean insert(T value); //从队列尾部添加数据
    T remove(); //从队列头部删除数据；
    boolean isFull();
    boolean isEmpty();
}

public class CycleQueue<T> implements Queue<T> {

    static class Node<T> {
        T value;
        //Node<T> prev;
        Node<T> next;
    }
    private int size;
    private int maxSize;
    private Node<T> head;
    private Node<T> tail;



    public CycleQueue(int maxSize ){
         this.maxSize=maxSize;
    }




    //从队列尾部添加数据
    public boolean insert(T value){
        if(isFull()) return false;
        Node<T> node=new Node();
        node.value=value;
        if(tail==null){
            head=node;
            tail=node;
            //?
        }else{

            tail.next=node;
            tail=node;
        }
        size++;
        tail.next=head;
        return true;


    }



    //从队列头部删除数据；
   public T remove(){

        if(isEmpty()) return null;
        Node<T> node=head;
        head=head.next;
        tail.next=head;
        size--;
        if(size==0){
            head=null;
            tail=null;
        }
        return node.value;

    }
    public boolean isFull(){
       return size==maxSize;
    }
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public String toString() {
        Node<T> node=head;
        StringBuilder sb=new StringBuilder();
        while (node!=null){
            sb.append("-->").append(node.value);
            if(node==tail) break;
            node=node.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CycleQueue<Long> queue=new CycleQueue<>(3);
        queue.insert(10L);
        queue.insert(11L);
        queue.insert(12L);
        queue.insert(13L);
        System.out.println(queue.toString());

        queue.remove();
        System.out.println(queue.toString());

        queue.remove();
        System.out.println(queue.toString());

        queue.remove();
        System.out.println(queue.toString());

        queue.remove();
        System.out.println(queue.toString());

    }

}

class SynQueue<T> implements Queue<T>{
    private CycleQueue<T> q;
    final ReentrantLock lock;

    public SynQueue(int maxSize){
        lock = new ReentrantLock(true);
        q=new CycleQueue(maxSize);
    }

    @Override
    public  boolean insert(T value) {
        try {
            return q.insert(value);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public synchronized T remove() {
        try {
            return q.remove();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public synchronized boolean isFull() {
        try {
            return q.isFull();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public synchronized boolean isEmpty() {
        try {
            return q.isEmpty();
        } finally {
            lock.unlock();
        }

    }
}


class BlockingQueue<T> implements Queue<T>{
    private CycleQueue<T> q;
    final ReentrantLock lock;
    /** Condition for waiting takes */
    private final Condition notEmpty;

    /** Condition for waiting puts */
    private final Condition notFull;

    public BlockingQueue(int maxSize){
        lock = new ReentrantLock(true);
        q=new CycleQueue(maxSize);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }

    @Override
    public  boolean insert(T value) {
        final ReentrantLock lock = this.lock;
        try {
        lock.lockInterruptibly();
        try {
            while (q.isFull())
                notFull.await();
            boolean b=q.insert(value);
            notEmpty.signal();
            return b;
        } finally {
            lock.unlock();
        }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized T remove()   {
        final ReentrantLock lock = this.lock;
        try {
            lock.lockInterruptibly();
            try {
                while (q.isEmpty())
                    notEmpty.await();
                T t= q.remove();
                notFull.signal();
                return t;
            } finally {
                lock.unlock();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized boolean isFull() {
        try {
            return q.isFull();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public synchronized boolean isEmpty() {
        try {
            return q.isEmpty();
        } finally {
            lock.unlock();
        }

    }
}
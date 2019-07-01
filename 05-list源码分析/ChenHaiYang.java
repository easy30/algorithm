import java.util.ArrayList;
import java.util.List;

/**
 * @author haiyang.chen
 */
public class Question20190618 {
    public static void main(String[] args) {
//        CycleQueue cycleQueue = new CycleQueue(2);
//        System.out.println(cycleQueue.isEmpty());
//        System.out.println(cycleQueue.isFull());
//        cycleQueue.insert(1L);
//        cycleQueue.insert(2L);
//        System.out.println(cycleQueue.remove());
//        System.out.println(cycleQueue.isEmpty());
//        System.out.println(cycleQueue.remove());
//        System.out.println(cycleQueue.isEmpty());
//        System.out.println(cycleQueue.isFull());

        BlockingQueue blockingQueue = new BlockingQueue(1);
        new Thread(new Remover(blockingQueue)).start();
        new Thread(new Inserter(blockingQueue)).start();
    }

    static class Remover implements Runnable {

        private Queue queue;

        Remover(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            final Long remove = queue.remove();
            System.out.println(remove);
        }
    }

    static class Inserter implements Runnable {

        private Queue queue;

        Inserter(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            queue.insert(1L);
            System.out.println(1111);
        }
    }
}


interface Queue {

    int DEFAULT_CAPABILITY = 8;

    void insert(Long value);

    Long remove();

    boolean isFull();

    boolean isEmpty();
}

class CycleQueue implements Queue {

    private Long[] array = null;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    public CycleQueue() {
        this(DEFAULT_CAPABILITY);
    }

    public CycleQueue(int initCapability) {
        array = new Long[initCapability + 1];
        size = array.length;
    }

    @Override
    public void insert(Long value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (isFull()) {
            throw new IndexOutOfBoundsException("is full");
        }

        array[rear] = value;
        rear = (rear + 1) % size;
    }

    @Override
    public Long remove() {
        if (isEmpty()) {
            return null;
        }
        return array[front++];
    }

    @Override
    public boolean isFull() {
        return (rear + 1) % size == front;
    }

    @Override
    public boolean isEmpty() {
        return rear == front;
    }
}

class SynQueue implements Queue {

    private Long[] array = null;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    public SynQueue() {
        this(DEFAULT_CAPABILITY);
    }

    public SynQueue(int initCapability) {
        array = new Long[initCapability + 1];
        size = array.length;
    }

    @Override
    public void insert(Long value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (isFull()) {
            throw new IndexOutOfBoundsException("is full");
        }

        synchronized (this) {
            array[rear] = value;
            rear = (rear + 1) % size;
        }
    }

    @Override
    public Long remove() {
        if (isEmpty()) {
            return null;
        }

        synchronized (this) {
            return array[front++];
        }
    }

    @Override
    public boolean isFull() {
        synchronized (this) {
            return (rear + 1) % size == front;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this) {
            return rear == front;
        }
    }
}

class BlockingQueue implements Queue {

    private Long[] array = null;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    public BlockingQueue() {
        this(DEFAULT_CAPABILITY);
    }

    public BlockingQueue(int initCapability) {
        array = new Long[initCapability + 1];
        size = array.length;
    }

    @Override
    public synchronized void insert(Long value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        array[rear] = value;
        rear = (rear + 1) % size;
        notify();
    }

    @Override
    public synchronized Long remove() {
        if (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int currentIndex = front;
        front++;
        notify();
        return array[currentIndex];
    }

    @Override
    public synchronized boolean isFull() {
        return (rear + 1) % size == front;
    }

    @Override
    public synchronized boolean isEmpty() {
        return rear == front;
    }
}
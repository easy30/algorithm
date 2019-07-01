package com.cehome;

import java.util.Arrays;

public class CycleQueueRuidong<E> {


    /**
     * 初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 用于空实例的共享空数组实例。
     */
    transient Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 数组缓冲区，其中存储队列的元素。
     */
    transient Object[] elementData;

    /**
     * 用于默认大小的空实例的共享空数组实例。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 大小
     */
    private int size;

    /**
     *
     * @param initialCapacity
     */
    public CycleQueueRuidong(int initialCapacity){
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }

    }

    /**
     * 无参构造器
     */
    public CycleQueueRuidong() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public int size() {
        return elementData.length;
    }
    /**
     * 向队列末尾添加一个元素
     * @param e
     */
    public void insert(E e) {
        add(e, size);
    }

    private void add(E e, int index) {
        ExtendElement(size + 1);
        if (index != size)
            System.arraycopy(elementData, index, elementData, index + 1, size);
        this.elementData[index] = e;
        size++;
    }

    private void ExtendElement(int i) {
        //容量
        if (this.elementData.length == 0) {
            elementData = new Object[DEFAULT_CAPACITY];
        } else if (this.elementData.length < size) {
            EMPTY_ELEMENTDATA = elementData;
            //获取当前容量
            int oldCapacity = elementData.length;
            //扩展容量 i + i >> 1 就是 i的1.5倍
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            elementData = new Object[newCapacity];
            //1.源数组 2.源数组要复制的起始位置 3.目的数组 4.目的数组放置的起始位置 5.复制的长度
            /**
             * 调用 System的静态方法 arraycopy() 进行数组拷贝
             * 标识为native意味JDK的本地库
             * 如果频繁扩容会降低ArrayList的使用性能
             * 赋值过程
             */
            System.arraycopy(EMPTY_ELEMENTDATA, 0, elementData, 0, size - 1);
        }
    }

    /**
     * 取出队列第一个元素
     */
    public E remove() {
        return remove(0);
    }

    /**
     * 删除一个元素
     */
    private E remove(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            //index + 1 是当前 index 下一个 之 赋给 index 就全部替换了
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null; // 清楚地让GC完成它的工作
        //判断容量是否是当前的1/4 是就 缩容 不要浪费不必要的内存空间
        ShrinkageCapacity();
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * 缩容
     *
     */
    public void ShrinkageCapacity() {
        if (size == elementData.length / 4 && elementData.length / 2 != 0) {
            EMPTY_ELEMENTDATA = elementData;
            //缩二分之一
            int oldCapacity = elementData.length / 2;
            elementData = new Object[oldCapacity];
            System.arraycopy(EMPTY_ELEMENTDATA, 0, elementData, 0, size - 1);
        }
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty() {
        return size() > 0;
    }

    public static void main(String[] args) {
        CycleQueueRuidong<Integer> queue = new CycleQueueRuidong<>();
        for (int i = 0; i < 10; i++) {
            queue.insert(i);
        }
        System.out.println(Arrays.toString(queue.elementData));
        queue.remove();
        System.out.println(Arrays.toString(queue.elementData));
        queue.remove();
        System.out.println(Arrays.toString(queue.elementData));
    }

}

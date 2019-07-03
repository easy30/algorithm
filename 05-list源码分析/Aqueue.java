import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zx 
 */
public abstract class Aqueue {
	public abstract void insert(Long value) throws Exception;
	public abstract Long remove() throws Exception;
	public abstract boolean isFull();
	public abstract boolean isEmpty();

	static class CycleQueue extends Aqueue{
		private Long[] cqueue;
		private final int size;
		private int head;
		private int foot;

		public CycleQueue(int queueSize) {
			cqueue = new Long[queueSize+1];//空闲单元法
			head = 0;
			foot = 0;
			size = queueSize+1;
		}
		@Override
		public void insert(Long value) throws Exception {
		    if(null == value)
		        return;
            if(isFull())
                throw new Exception("queue is full");
			cqueue[foot] = value;
            foot = (foot+1)%size;
		}
		@Override
		public Long remove() throws Exception{
			if(isEmpty())
                throw new Exception("queue is empty");
			Long value = cqueue[head];
			head = (head+1)%size;
			return value;
		}
		@Override
		public boolean isFull() {
			if((foot+1)%size == head)
				return true;
			return false;
		}

		@Override
		public boolean isEmpty() {
			if(head == foot)
				return true;
			return false;
		}
    }
    static class SyncCycleQueue extends Aqueue{
        private Long[] cqueue;
        private final int size;
        private int head;
        private int foot;
        public SyncCycleQueue(int queueSize) {
            cqueue = new Long[queueSize+1];//空闲单元法
            head = 0;
            foot = 0;
            size = queueSize+1;
        }
        @Override
        public synchronized void insert(Long value) throws Exception {
            if(null == value)
                return;
            if(isFull())
                throw new Exception("queue is full");
            cqueue[foot] = value;
            foot = (foot+1)%size;
        }

        @Override
        public synchronized Long remove() throws Exception {
            if(isEmpty())
                throw new Exception("queue is empty");
            Long value = cqueue[head];
            head = (head+1)%size;
            return value;
        }

        @Override
        public boolean isFull() {
            if((foot+1)%size == head)
                return true;
            return false;
        }

        @Override
        public boolean isEmpty() {
            if(head == foot)
                return true;
            return false;
        }
    }

    static class BlockCycleQueue extends Aqueue{
        private Long[] cqueue;
        private final int size;
        private int head;
        private int foot;
        private AtomicInteger handle = new AtomicInteger(0);
        public BlockCycleQueue(int queueSize) {
            cqueue = new Long[queueSize+1];//空闲单元法
            head = 0;
            foot = 0;
            size = queueSize+1;
        }
        @Override
        public void insert(Long value) throws Exception {
            if(null == value)
                return;
            while (handle.get() > 0)
                wait();
            handle.incrementAndGet();
            if(isFull())
                throw new Exception("queue is full");
            cqueue[foot] = value;
            foot = (foot+1)%size;
            handle.decrementAndGet();
            notifyAll();
        }

        @Override
        public Long remove() throws Exception {
            while (handle.get() > 0)
                wait();
            handle.incrementAndGet();
            if(isEmpty())
                throw new Exception("queue is empty");
            Long value = cqueue[head];
            head = (head+1)%size;
            handle.decrementAndGet();
            notifyAll();
            return value;
        }

        @Override
        public boolean isFull() {
            if((foot+1)%size == head)
                return true;
            return false;
        }

        @Override
        public boolean isEmpty() {
            if(head == foot)
                return true;
            return false;
        }
    }
	public static void main(String[] args) {
		Aqueue.BlockCycleQueue q = new BlockCycleQueue(40);
	}
}

package queue;

public class NormalQueue {

	int nMaxQueueSize = 10;
	
	int nFront = 0;
	int nRear = 0;
	
	long a[] = new long[10];
	
	public boolean add(long nNum) {
		if( QueueUtil.isFull(nMaxQueueSize, nFront,  nRear)) {
			return false;
		}
		a[nRear++] = nNum;
		nRear = nRear %nMaxQueueSize;
		return true;
	}
	
	public long pop() {
		if( QueueUtil.isEmpty(nFront, nRear)) {
			return -1;
		}
		long n = a[nFront++];
		nFront = nFront % nMaxQueueSize;
		return n;
	}
}

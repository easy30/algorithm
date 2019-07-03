package queue;

public class SyncQueue extends NormalQueue {

	//??? 
	private volatile int nHandle = 0;
	
	private synchronized int getHandle() {
		 return nHandle+1;
	}
	
	public boolean isFull() {
		int nHandle = getHandle();
		if( nHandle != 1 ) {
			while(true) {
				nHandle = getHandle();
				if( nHandle == 0 ) 
					break;
			}
		}
		boolean bRet = QueueUtil.isFull(nMaxQueueSize, nFront,  nRear);
		--this.nHandle;
		return bRet;
	}
	
	public boolean isEmpty() {
		int nHandle = getHandle();
		if( nHandle != 1 ) {
			while(true) {
				nHandle = getHandle();
				if( nHandle == 0 ) 
					break;
			}
		}
		boolean bRet = QueueUtil.isEmpty(nFront,  nRear);
		--this.nHandle;
		return bRet;
	}
	
	public boolean add(long nNum) {
		int nHandle = getHandle();
		if( nHandle != 1 ) {
			while(true) {
				nHandle = getHandle();
				if( nHandle == 0 ) 
					break;
			}
		}
		if( QueueUtil.isFull(nMaxQueueSize, nFront,  nRear)) {
			--this.nHandle;
			return false;
		}
		a[nRear++] = nNum;
		nRear = nRear %nMaxQueueSize;
		--this.nHandle;
		return true;
	}
	
	public long pop() {
		
		int nHandle = getHandle();
		if( nHandle != 0 ) {
			while(true) {
				nHandle = getHandle();
				if( nHandle == 1 ) 
					break;
			}
		}
		
		if( QueueUtil.isEmpty(nFront, nRear)) {
			--this.nHandle;
			return -1;
		}
		long n = a[nFront++];
		nFront = nFront % nMaxQueueSize;
		--this.nHandle;
		return n;
	}
}


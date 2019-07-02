package queue;

public class QueueUtil {

	public static boolean isFull(int nMax, int nFront, int nRear) {
		if( (nRear+1)%nMax == nFront )
			return true;
		return false;
	}
	
	public static boolean isEmpty(int nFront, int nRear) {
		if( nRear == nFront )
			return true;
		return false;
	}
}

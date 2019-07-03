package queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockQueue extends NormalQueue {
	 class MyAsyncTask  implements Runnable{
		 long nNum;
		 public MyAsyncTask(long num) {
			 nNum = num;
		 }
		@Override
		public void run() {
			add(nNum);
		}
	 }
	 
	 class MyRemoveAsyncTask  implements Runnable{
		 public MyRemoveAsyncTask() {
		 }
		@Override
		public void run() {
			pop();
		}
	 }
	 
     ExecutorService es = Executors.newFixedThreadPool(20);
 
	 public void put(long num) {
		 while(true) {
			 if( !QueueUtil.isFull(nMaxQueueSize, nFront, nRear) ) {
				 MyAsyncTask task = new MyAsyncTask(num);
				 es.execute(task);
			 }
		 }
	 }
	 
	 public void remove(long num) {
		 while(true) {
			 if( !QueueUtil.isFull(nMaxQueueSize, nFront, nRear) ) {
				 MyRemoveAsyncTask task = new MyRemoveAsyncTask();
				 es.execute(task);
			 }
		 }
	 }
}

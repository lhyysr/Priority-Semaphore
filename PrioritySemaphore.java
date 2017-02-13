/*
 * Answer 4a & 4b
 */
import java.util.concurrent.Semaphore;

public class PrioritySemaphore {

	// number of processes
	private static int K;
	// N is an integer counter that tracks the number of processes that are waiting or processing
	private static int N;
	// R is an array of integers representing priority of processes
	private static int[] R;
	// B is an array of normal Semaphores which are initialized with value = 0
	private static Semaphore[] B;
	// mutex is a binary semaphore that protects shared data: N, R, and B
	private static Semaphore mutex;
	
	// constructor
	public PrioritySemaphore(int k) {
		K = k;
		N = 0;
		R = new int[K];
		B = new Semaphore[K];
		for (int i = 0; i < K; i++) {
			B[i] = new Semaphore(0);
		}
		mutex = new Semaphore(1);
	}
	
	// compute the priority of Process i
	public int priority(int i) {
		return i;
	}
	
	// wait function of priority semaphore
	public void newWait(int i) {
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {}
		System.out.println("P" + i + " is requesting CS");
		R[i] = priority(i);
		N++;
		if (N > 1) {
			mutex.release();
			try {
				B[i].acquire();
			} catch (InterruptedException e) {}
		}
		else mutex.release();
		System.out.println("P" + i + " is in the CS");
	}	
	
	// signal function of priority semaphore
	public void newSignal(int i) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {}
		System.out.println("P" + i + " is exiting CS");
		R[i] = 0;
		N--;
		if (N > 0) {
			int j = 0;
			for (int k = 1; k < R.length; k++) {
				if (R[k] > R[j]) j = k;
			}
			B[j].release();
		}
		mutex.release();
	}
}

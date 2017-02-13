/*
 * Answer 4d FIFO
 */
import java.util.concurrent.Semaphore;

public class PrioritySemaphoreD {

	// number of processes
	private static int K;
	// N is an integer counter that tracks the number of processes that are waiting or processing
	private static int N;
	// R is an array of integers representing priority of processes, initialized with 0's
	private static int[] R;
	// B is an array of normal Semaphores which are initialized with value = 0
	private static Semaphore[] B;
	// mutex is a binary semaphore that protects shared data: N, R, T, and B
	private static Semaphore mutex;
	
	// constructor
	public PrioritySemaphoreD(int k) {
		K = k;
		N = 0;
		R = new int[K];
		B = new Semaphore[K];
		for (int i = 0; i < K; i++) {
			B[i] = new Semaphore(0);
			R[i] = 0;
		}
		mutex = new Semaphore(1);
	}
	
	// compute the priority of Process i
	public int priority(int i) {
		int ticket;
		int max = R[0];
		for (int k = 1; k < K; k++) {
			if (R[k] > max) max = R[k];
		}
		ticket = max +1;
//		System.out.println("P" + i + " gets ticket " + ticket);
		return ticket;
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
			while (R[j] == 0) j++;
			for (int k = j; k < K; k++) {
				if (R[k] < R[j] && R[k] != 0) j = k;
			}
//			System.out.println("J is: " + j);
			B[j].release();
		}
		mutex.release();
	}
}

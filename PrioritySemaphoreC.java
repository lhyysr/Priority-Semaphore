/*
 * Answer 4c
 */
import java.util.concurrent.Semaphore;

public class PrioritySemaphoreC {

	// number of processes
	private static int K;
	// N is an integer counter that tracks the number of processes that are waiting or processing
	private static int N;
	// R is an array of integers representing priority of processes
	private static int[] R;
	// B is an array of normal Semaphores which are initialized with value = 0
	private static Semaphore[] B;
	// T is an array of integers representing the number of times a process has used the CS, initialized to 0's
	private static int[] T;
	// mutex is a binary semaphore that protects shared data: N, R, B, and T
	private static Semaphore mutex;	
	
	// constructor
	public PrioritySemaphoreC(int k) {
		K = k;
		N = 0;
		R = new int[K];
		B = new Semaphore[K];
		T = new int[K];
		for (int i = 0; i < K; i++) {
			B[i] = new Semaphore(0);
			T[i] = 0;
		}
		mutex = new Semaphore(1);
	}
	
	// print R
	public void printR() {
		for (int i = 0; i < K; i++) {
			System.out.print(R[i]);
		}
		System.out.println();
	}
	
	// print T
	public void printT() {
		for (int i = 0; i < K; i++) {
			System.out.print(T[i]);
		}
		System.out.println();
	}
	
	// compute the priority of Process i
	public int priority(int i) {
//		System.out.println("computing priority:");
//		printT();
		int p = 0;
		int j = i;
		for (int k = 1; k < K; k++) {
			if (T[k] < T[j]) j = k;
		}
		if (i == j) 
			p = 1;
//		System.out.println("priority is: " + p);
		return p;
	}
	
	// wait function of priority semaphore
	public void newWait(int i) {
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {}
		System.out.println("P" + i + " is requesting CS");
		// dynamic priority assignment
		for (int j = 0; j < K; j++) {
			R[j] = priority(j);
		}
//		printR();
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
		T[i]++;
		R[i] = 0;
		N--;
		if (N > 0) {
			int j = 0;
			for (int k = 1; k < K; k++) {
				if (R[k] > R[j]) j = k;
			}
			B[j].release();
		}
		mutex.release();
	}
}

/*
 * Answer 4
 */
public class Process extends Thread {
	
	private int id;
	// for test purpose, change the PrioritySemaphore ADT accordingly
	private static PrioritySemaphoreD sem = new PrioritySemaphoreD(5);
	
	public Process(int i) {
		id = i;
	}
	
	public void run() {
		for (int i = 0; i < 5; i++) {
			sem.newWait(id);
			
			// cs
			try {
				sleep((int) Math.random() * 100);
			} catch (InterruptedException e1) {}
			
			sem.newSignal(id);
		}
	}
	
	public static void main(String[] args) {
		final int N = 5;
		Process[] p = new Process[N];
		for (int i = 0; i < N; i++) {
			p[i] = new Process(i);
			p[i].start();
		}
	}
}

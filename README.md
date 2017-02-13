# Priority-Semaphore
It is desirable that signal() chooses certain process to signal based on some specific criterion.
# Description
The functionality of the signal() operation on a semaphore does not specify which one of possibly many blocked processes would be awakened in response to the signal operation. As it turns out, this is not acceptable. Rather, it is desirable that when it is time to awaken a blocked process, the blocked processes to be awakened should be the one satisfying some preset criterion (e.g., the process waiting the longest, or the shortest process first, etc.) In short, we need the signal() functionality to "choose" which process to signal based on some specific criterion. For this problem, we will take a simple one: pick the process with the highest priority.  
__Solution sketch__  
__1.__ When a process Pi wishes to wait on the "priority semaphore", it executes the following newWait(i) function:  
Set R[i] = priority(i) = a positive value representing the "priority" of Pi  
Increment a counter N  
If N > 1, then wait on a binary semaphore B[i]  
__2.__ When a process Pi wishes to signal the "priority semaphore", it executes the following newSignal(i) function:  
Set priority[i] = 0  
Decrement the counter N  
If N > 0, then signal semaphore B[j], where R[j] >= R[k] for all k.  
__Answer the following__  
__a.__ Implement the above sketched solution in Java. Specify the role that each data structure plays and its initialization.  
__b.__ Show your Java code in action. Assume that there are five processes P0, P1, P2, ... P5 with P0 being the lowest priority, P1 being the next, ... and P5 being the highest priority. Each process requests the critical section for a total of five times (say in a loop from 1 to 5) and each process spends some random amount of time in the critical section. Process Pi should print "Pi is requesting CS" as soon as it calls newWait(). It should print "Pi is in the CS" whenever it is in the critical section. And, it should print "Pi is exiting the CS" just before it exits newSignal(). Your code needs fixing if you get a printout indicating that two processes Pi and Pj (where i < j) are requesting the CS and Pi gets to the CS before Pj (even if Pi requested it before Pj).  
__c.__ Modify your code to implement a semaphore that allows a set of processes to grab the semaphore, favoring the process that has used the critical section for the least number of times in its lifetime. Show your code in action (e.g., create a scenario where one of the processes, e.g., P0 requests the CS very infrequently compared to the others, but which your code should show as always being signaled (selected) first among any other blocked processes).  
__d.__ Modify your code to implement a semaphore that allows a set of processes to grab the semaphore in strict FIFO order i.e., if Pi calls newWait() before Pj calls newWait(), then Pi will get into the critical section first. Show your code in action.

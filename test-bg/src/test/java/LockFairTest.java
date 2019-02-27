import java.util.concurrent.locks.ReentrantLock;

public class LockFairTest  implements Runnable {

	private static ReentrantLock lock = new ReentrantLock(true);
	
	public void run() {
		while (true) {
			lock.lock();
			try {
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName()+"获得锁");
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				lock.unlock();
			}
			
		}
	}
	public static void main(String[] args) {
		Thread t1 = new Thread(new LockFairTest());
		Thread t2 = new Thread(new LockFairTest());
		Thread t3 = new Thread(new LockFairTest());
		t1.start();
		t2.start();
		t3.start();
	}
}
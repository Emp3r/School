package Class09;

public class Class9 {

	// N
	private static final int N = 45 ;
	
	private volatile int result;
	
	// FIB
	public class Fibonacci implements Runnable {
		private int number;

	    public Fibonacci(int x) {
	        this.number = x;
	    }
	    
	    private int calculate(int x) {
			if (x == 1) 
				return 1;
			else if (x == 0)
				return 0;
			else
				return calculate(x - 1) + calculate(x - 2);
	    }

	    public void run() {
	    	result = calculate(number);
	    }
	}
	public Fibonacci fib(int n) {
		return new Fibonacci(n);
	}
	
	// CAS
	public class Time implements Runnable {
		private double startTime;
		
		public Time() {
			this.startTime = System.currentTimeMillis();
		}
		
		public void run() {
			synchronized(this) {
				for (int i = 1; result == 0; ) {
					if (System.currentTimeMillis() - startTime > (i * 1000)) {
						System.out.print(i + ", ");
						i++;
					}
				}
			}
			System.out.print("\n" + result);
		}
	}
	public Time tim() {
		return new Time();
	}
	
	// TESTS
	public static void main(String[] args) throws InterruptedException {
		Class9 c9 = new Class9();

		Thread[] threads = new Thread[2];
		threads[0] = new Thread(c9.tim());
		threads[1] = new Thread(c9.fib(N));

		for (Thread t: threads)
			t.start();
		for (Thread t: threads)
			t.join();
	}
}

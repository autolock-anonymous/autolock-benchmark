package aeminium.fibonacci.withlock;
import edu.cmu.cs.plural.annot.Perm;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Fibonacci {
	public ReentrantReadWriteLock numberLock = new ReentrantReadWriteLock();
	public Integer number;
	@Perm(requires = "unique(number) in alive", ensures = "unique(number) in alive")
	Fibonacci() {
		for (int i = 0; i < 5; i++) {
			numberLock.writeLock().lock();
			number = 10;
			numberLock.writeLock().unlock();
		}
		int a = 5;
		a++;
	}
	@Perm(requires = "full(number) in alive", ensures = "full(number) in alive")
	public Integer computeFibo(Integer num) {
		try {
			numberLock.writeLock().lock();
			number = 1;
			if (num <= 1) {
				return num;
			} else {
				number = 2;
			}
			return computeFibo(num - 1) + computeFibo(num);
		} finally {
			numberLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void display(Integer num) {
		System.out.println("Number =  " + num);
	}
	@Perm(requires = "pure(number) in alive", ensures = "pure(number) in alive")
	public static void main(String[] args) {
		long start = System.nanoTime();
		Fibonacci obj = new Fibonacci();
		numberLock.readLock().lock();
		obj.computeFibo(obj.number);
		obj.display(obj.number);
		numberLock.readLock().unlock();
		long elapsedTime = System.nanoTime() - start;
		double ms = (double) elapsedTime / 1000000.0;
		System.out.println(" Milli Seconds Time = " + ms);
	}
}

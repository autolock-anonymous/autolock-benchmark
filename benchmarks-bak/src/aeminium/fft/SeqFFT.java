package aeminium.fft.withlock;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqFFT {
	public static Complex[] x;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Complex[] sequentialFFT(Complex[] x) {
		int N = x.length;
		if (N == 1)
			return new Complex[]{x[0]};
		if (N % 2 != 0) {
			throw new RuntimeException("N is not a power of 2");
		}
		Complex[] even = new Complex[N / 2];
		for (int k = 0; k < N / 2; k++) {
			even[k] = x[2 * k];
		}
		Complex[] q = sequentialFFT(even);
		Complex[] odd = even;
		for (int k = 0; k < N / 2; k++) {
			odd[k] = x[2 * k + 1];
		}
		Complex[] r = sequentialFFT(odd);
		Complex[] y = new Complex[N];
		for (int k = 0; k < N / 2; k++) {
			double kth = -2 * k * Math.PI / N;
			Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
			y[k] = q[k].plus(wk.times(r[k]));
			y[k + N / 2] = q[k].minus(wk.times(r[k]));
		}
		return y;
	}
}
class Client {
	public static ReentrantReadWriteLock DEFAULT_SIZELock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock xLock = new ReentrantReadWriteLock();
	@Perm(requires = "pure(x) * immutable(DEFAULT_SIZE) in alive", ensures = "pure(x) * immutable(DEFAULT_SIZE) in alive")
	public static void main(String[] args) {
		long start = System.nanoTime();
		xLock.readLock().lock();
		FFTUtility.createRandomComplexArray(SeqFFT.x, FFTUtility.DEFAULT_SIZE);
		SeqFFT.sequentialFFT(SeqFFT.x);
		FFTUtility.show(SeqFFT.x, "Result");
		xLock.readLock().unlock();
		long end = System.nanoTime();
		long elapsedTime = end - start;
		double seconds = (double) elapsedTime / 1000000.0;
		System.out.println(" Milli Seconds Time = " + seconds);
	}
}

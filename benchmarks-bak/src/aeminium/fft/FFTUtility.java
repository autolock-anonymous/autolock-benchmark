package aeminium.fft.withlock;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class FFTUtility {
	public static int DEFAULT_SIZE = 1024;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Complex[] createRandomComplexArray(Complex[] x, int n) {
		Random r = new Random(1L);
		x = new Complex[n];
		for (int i = 0; i < n; i++) {
			x[i] = new Complex(i, 0);
			x[i] = new Complex(-2 * r.nextDouble() + 1, 0);
		}
		return x;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void show(Complex[] x, String title) {
		System.out.println("-------------------");
		for (int i = 0; i < x.length; i++) {
			System.out.println(x[i]);
		}
		System.out.println();
	}
}

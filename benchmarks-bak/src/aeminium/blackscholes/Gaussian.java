package aeminium.blackscholes.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Gaussian {
	public static ReentrantReadWriteLock mu_sigma_y_zLock = new ReentrantReadWriteLock();
	static double z;
	static double y;
	static double mu;
	static double sigma;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double phi(double x) {
		return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double phiOverload(double x, double mu, double sigma) {
		return phi((x - mu) / sigma) / sigma;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double PhiOverload(double z) {
		if (z < -8.0)
			return 0.0;
		if (z > 8.0)
			return 1.0;
		double sum = 0.0, term = z;
		for (int i = 3; sum + term != sum; i += 2) {
			sum = sum + term;
			term = term * z * z / i;
		}
		return 0.5 + sum * phi(z);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double Phi(double z, double mu, double sigma) {
		return PhiInverse(z, z, mu, sigma);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double PhiInverseOverload(double y) {
		return PhiInverse(y, .00000001, -8, 8);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static double PhiInverse(double y, double delta, double lo, double hi) {
		double mid = lo + (hi - lo) / 2;
		if (hi - lo < delta)
			return mid;
		if (Phi(mid, y, delta) > y)
			return PhiInverse(y, delta, lo, mid);
		else
			return PhiInverse(y, delta, mid, hi);
	}
	@Perm(requires = "unique(z) * full(mu) * full(sigma) * full(y) in alive", ensures = "unique(z) * full(mu) * full(sigma) * full(y) in alive")
	public static void main(String[] args) {
		mu_sigma_y_zLock.writeLock().lock();
		z = Double.parseDouble(args[0]);
		mu = Double.parseDouble(args[1]);
		sigma = Double.parseDouble(args[2]);
		StdOut.println(Phi(z, mu, sigma));
		y = PhiOverload(z);
		StdOut.println(PhiInverse(y, .00000001, -8, 8));
		StdOut.println(PhiInverseOverload(y));
		StdOut.println(phiOverload(y, mu, sigma));
		mu_sigma_y_zLock.writeLock().unlock();
	}
}

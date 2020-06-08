package aeminium.blackscholes.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqBlackScholes {
	public static ReentrantReadWriteLock rLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sigmaLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock TLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock SLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock XLock = new ReentrantReadWriteLock();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double callPrice(double S, double X, double r, double sigma, double T) {
		double d1 = (Math.log(S / X) + (r + sigma * sigma / 2) * T) / (sigma * Math.sqrt(T));
		double d2 = d1 - sigma * Math.sqrt(T);
		return S * Gaussian.PhiOverload(d1) - X * Math.exp(-r * T) * Gaussian.PhiOverload(d2);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double call(double S, double X, double r, double sigma, double T, long N) {
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			double eps = StdRandom.gaussianO1();
			double price = S * Math.exp(r * T - 0.5 * sigma * sigma * T + sigma * eps * Math.sqrt(T));
			double value = Math.max(price - X, 0);
			sum += value;
		}
		double mean = sum / N;
		return Math.exp(-r * T) * mean;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static double call2(double S, double X, double r, double sigma, double T, long N) {
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			double price = S;
			double dt = T / 10000.0;
			for (double t = 0; t <= T; t = t + dt) {
				price += r * price * dt + sigma * price * Math.sqrt(dt) * StdRandom.gaussianO1();
			}
			double value = Math.max(price - X, 0);
			sum += value;
		}
		double mean = sum / N;
		return Math.exp(-r * T) * mean;
	}
	@Perm(requires = "immutable(S) * immutable(X) * immutable(r) * immutable(sigma) * immutable(T) in alive", ensures = "immutable(S) * immutable(X) * immutable(r) * immutable(sigma) * immutable(T) in alive")
	public static void main(String[] args) {
		StdRandom.bernoulliO2();
		long N = 10;
		double cP = callPrice(BlackScholes.S, BlackScholes.X, BlackScholes.r, BlackScholes.sigma, BlackScholes.T);
		double ca = call(BlackScholes.S, BlackScholes.X, BlackScholes.r, BlackScholes.sigma, BlackScholes.T, N);
		double c2 = call2(BlackScholes.S, BlackScholes.X, BlackScholes.r, BlackScholes.sigma, BlackScholes.T, N);
		System.out.println(cP);
		System.out.println(ca);
		System.out.println(c2);
	}
}

package aeminium.blackscholes.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public strictfp class MersenneTwisterFast implements Serializable, Cloneable {
	public ReentrantReadWriteLock haveNextNextGaussian_mag01_mt_mtiLock = new ReentrantReadWriteLock();
	private static final long serialVersionUID = -8219700664442619525L;
	private static final int N = 1000000;
	private static final int M = 397;
	private static final int MATRIXA = 0x9908b0df;
	private static final int UPPERMASK = 0x80000000;
	private static final int LOWERMASK = 0x7fffffff;
	private static final int TEMPERINGMASKB = 0x9d2c5680;
	private static final int TEMPERINGMASKC = 0xefc60000;
	private int mt[];
	private int mti;
	private int mag01[];
	private double nextNextGaussian;
	private boolean haveNextNextGaussian;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public MersenneTwisterFast() {
		this(System.currentTimeMillis());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public MersenneTwisterFast(final long seed) {
		setSeed(seed);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public MersenneTwisterFast(final int[] array) {
		setSeed(array);
	}
	@Perm(requires = "unique(haveNextNextGaussian) * unique(mt) * unique(mag01) * share(mti) in alive", ensures = "unique(haveNextNextGaussian) * unique(mt) * unique(mag01) * share(mti) in alive")
	synchronized public void setSeed(final long seed) {
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		haveNextNextGaussian = false;
		mt = new int[N];
		mag01 = new int[2];
		mag01[0] = 0x0;
		mag01[1] = MATRIXA;
		mt[0] = (int) (seed & 0xffffffff);
		for (mti = 1; mti < N; mti++) {
			mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
			mt[mti] &= 0xffffffff;
		}
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
	}
	@Perm(requires = "unique(mt) in alive", ensures = "unique(mt) in alive")
	synchronized public void setSeed(final int[] array) {
		if (array.length == 0)
			throw new IllegalArgumentException("Array length must be greater than zero");
		int i, j, k;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		setSeed(19650218);
		i = 1;
		j = 0;
		k = (N > array.length ? N : array.length);
		for (; k != 0; k--) {
			mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> 30)) * 1664525)) + array[j] + j;
			mt[i] &= 0xffffffff;
			i++;
			j++;
			if (i >= N) {
				mt[0] = mt[N - 1];
				i = 1;
			}
			if (j >= array.length)
				j = 0;
		}
		for (k = N - 1; k != 0; k--) {
			mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> 30)) * 1566083941)) - i;
			mt[i] &= 0xffffffff;
			i++;
			if (i >= N) {
				mt[0] = mt[N - 1];
				i = 1;
			}
		}
		mt[0] = 0x80000000;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
	}
	@Perm(requires = "share(mti) * share(mt) * share(mag01) in alive", ensures = "share(mti) * share(mt) * share(mag01) in alive")
	public final int nextInt() {
		int y;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERINGMASKB;
		y ^= (y << 15) & TEMPERINGMASKC;
		y ^= (y >>> 18);
		return y;
	}
	@Perm(requires = "share(mti) * share(mt) * pure(mag01) in alive", ensures = "share(mti) * share(mt) * pure(mag01) in alive")
	public final short nextShort() {
		int y;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERINGMASKB;
		y ^= (y << 15) & TEMPERINGMASKC;
		y ^= (y >>> 18);
		return (short) (y >>> 16);
	}
	@Perm(requires = "share(mti) * share(mt) * share(mag01) in alive", ensures = "share(mti) * share(mt) * share(mag01) in alive")
	public final boolean nextBoolean() {
		int y;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERINGMASKB;
		y ^= (y << 15) & TEMPERINGMASKC;
		y ^= (y >>> 18);
		return (boolean) ((y >>> 31) != 0);
	}
	@Perm(requires = "share(mti) * pure(mt) * pure(mag01) in alive", ensures = "share(mti) * pure(mt) * pure(mag01) in alive")
	public final boolean nextBoolean(final float probability) {
		int y;
		if (probability < 0.0f || probability > 1.0f)
			throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		if (probability == 0.0f)
			return false;
		else if (probability == 1.0f)
			return true;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERINGMASKB;
		y ^= (y << 15) & TEMPERINGMASKC;
		y ^= (y >>> 18);
		return (y >>> 8) / ((float) (1 << 24)) < probability;
	}
	@Perm(requires = "share(mti) * pure(mt) * pure(mag01) in alive", ensures = "share(mti) * pure(mt) * pure(mag01) in alive")
	public final boolean nextBoolean(final double probability) {
		int y;
		int z;
		if (probability < 0.0 || probability > 1.0)
			throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		if (probability == 0.0)
			return false;
		else if (probability == 1.0)
			return true;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERINGMASKB;
		y ^= (y << 15) & TEMPERINGMASKC;
		y ^= (y >>> 18);
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				z = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			for (; kk < N - 1; kk++) {
				z = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			z = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];
			mti = 0;
		}
		z = mt[mti++];
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		z ^= z >>> 11;
		z ^= (z << 7) & TEMPERINGMASKB;
		z ^= (z << 15) & TEMPERINGMASKC;
		z ^= (z >>> 18);
		return ((((long) (y >>> 6)) << 27) + (z >>> 5)) / (double) (1L << 53) < probability;
	}
	@Perm(requires = "share(mti) * share(mt) * share(mag01) in alive", ensures = "share(mti) * share(mt) * share(mag01) in alive")
	public final double nextDouble() {
		int y;
		int z;
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERINGMASKB;
		y ^= (y << 15) & TEMPERINGMASKC;
		y ^= (y >>> 18);
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				z = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			for (; kk < N - 1; kk++) {
				z = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
				mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			z = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];
			mti = 0;
		}
		z = mt[mti++];
		haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		z ^= z >>> 11;
		z ^= (z << 7) & TEMPERINGMASKB;
		z ^= (z << 15) & TEMPERINGMASKC;
		z ^= (z >>> 18);
		return ((((long) (y >>> 6)) << 27) + (z >>> 5)) / (double) (1L << 53);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public double nextDouble(boolean includeZero, boolean includeOne) {
		double d = 0.0;
		do {
			haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
			d = nextDouble();
			if (includeOne && nextBoolean())
				d += 1.0;
			haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		} while ((d > 1.0) || (!includeZero && d == 0.0));
		return d;
	}
	@Perm(requires = "share(mti) * pure(mt) * pure(mag01) in alive", ensures = "share(mti) * pure(mt) * pure(mag01) in alive")
	public final int nextInt(final int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive, got: " + n);
		try {
			haveNextNextGaussian_mag01_mt_mtiLock.readLock().lock();
			haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
			if ((n & -n) == n) {
				int y;
				if (mti >= N) {
					int kk;
					final int[] mt = this.mt;
					final int[] mag01 = this.mag01;
					for (kk = 0; kk < N - M; kk++) {
						y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
						mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
					}
					for (; kk < N - 1; kk++) {
						y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
						mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
					}
					y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
					mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
					mti = 0;
				}
				y = mt[mti++];
				y ^= y >>> 11;
				y ^= (y << 7) & TEMPERINGMASKB;
				y ^= (y << 15) & TEMPERINGMASKC;
				y ^= (y >>> 18);
				return (int) ((n * (long) (y >>> 1)) >> 31);
			}
			int bits, val;
			do {
				int y;
				if (mti >= N) {
					int kk;
					final int[] mt = this.mt;
					final int[] mag01 = this.mag01;
					for (kk = 0; kk < N - M; kk++) {
						y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
						mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
					}
					for (; kk < N - 1; kk++) {
						y = (mt[kk] & UPPERMASK) | (mt[kk + 1] & LOWERMASK);
						mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
					}
					y = (mt[N - 1] & UPPERMASK) | (mt[0] & LOWERMASK);
					mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
					mti = 0;
				}
				y = mt[mti++];
				y ^= y >>> 11;
				y ^= (y << 7) & TEMPERINGMASKB;
				y ^= (y << 15) & TEMPERINGMASKC;
				y ^= (y >>> 18);
				bits = (y >>> 1);
				val = bits % n;
			} while (bits - val + (n - 1) < 0);
		} finally {
			haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
			haveNextNextGaussian_mag01_mt_mtiLock.readLock().unlock();
		}
		return val;
	}
}

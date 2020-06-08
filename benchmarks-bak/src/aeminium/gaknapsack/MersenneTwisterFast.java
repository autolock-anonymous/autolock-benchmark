package aeminium.gaknapsack.withlock;
import java.io.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public strictfp class MersenneTwisterFast implements Serializable, Cloneable {
	public ReentrantReadWriteLock __haveNextNextGaussian_mag01_mt_mtiLock = new ReentrantReadWriteLock();
	private static final long serialVersionUID = -8219700664442619525L;
	private static final int N = 1000000;
	private static final int M = 397;
	private static final int MATRIX_A = 0x9908b0df;
	private static final int UPPER_MASK = 0x80000000;
	private static final int LOWER_MASK = 0x7fffffff;
	private static final int TEMPERING_MASK_B = 0x9d2c5680;
	private static final int TEMPERING_MASK_C = 0xefc60000;
	private int mt[];
	private int mti;
	private int mag01[];
	private double __nextNextGaussian;
	private boolean __haveNextNextGaussian;
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
	@Perm(requires = "unique(__haveNextNextGaussian) * unique(mt) * unique(mag01) * share(mti) in alive", ensures = "unique(__haveNextNextGaussian) * unique(mt) * unique(mag01) * share(mti) in alive")
	synchronized public void setSeed(final long seed) {
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		__haveNextNextGaussian = false;
		mt = new int[N];
		mag01 = new int[2];
		mag01[0] = 0x0;
		mag01[1] = MATRIX_A;
		mt[0] = (int) (seed & 0xffffffff);
		for (mti = 1; mti < N; mti++) {
			mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
			mt[mti] &= 0xffffffff;
		}
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
	}
	@Perm(requires = "unique(mt) in alive", ensures = "unique(mt) in alive")
	synchronized public void setSeed(final int[] array) {
		if (array.length == 0)
			throw new IllegalArgumentException("Array length must be greater than zero");
		int i, j, k;
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
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
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
	}
	@Perm(requires = "share(mti) * share(mt) * share(mag01) in alive", ensures = "share(mti) * share(mt) * share(mag01) in alive")
	public final int nextInt() {
		int y;
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERING_MASK_B;
		y ^= (y << 15) & TEMPERING_MASK_C;
		y ^= (y >>> 18);
		return y;
	}
	@Perm(requires = "share(mti) * share(mt) * share(mag01) in alive", ensures = "share(mti) * share(mt) * share(mag01) in alive")
	public final boolean nextBoolean() {
		int y;
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERING_MASK_B;
		y ^= (y << 15) & TEMPERING_MASK_C;
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
		__haveNextNextGaussian_mag01_mt_mtiLock.readLock().lock();
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		__haveNextNextGaussian_mag01_mt_mtiLock.readLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERING_MASK_B;
		y ^= (y << 15) & TEMPERING_MASK_C;
		y ^= (y >>> 18);
		return (y >>> 8) / ((float) (1 << 24)) < probability;
	}
	@Perm(requires = "share(mti) * share(mt) * share(mag01) in alive", ensures = "share(mti) * share(mt) * share(mag01) in alive")
	public final double nextDouble() {
		int y;
		int z;
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERING_MASK_B;
		y ^= (y << 15) & TEMPERING_MASK_C;
		y ^= (y >>> 18);
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			for (; kk < N - 1; kk++) {
				z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];
			mti = 0;
		}
		z = mt[mti++];
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		z ^= z >>> 11;
		z ^= (z << 7) & TEMPERING_MASK_B;
		z ^= (z << 15) & TEMPERING_MASK_C;
		z ^= (z >>> 18);
		return ((((long) (y >>> 6)) << 27) + (z >>> 5)) / (double) (1L << 53);
	}
	@Perm(requires = "share(mti) * share(mt) * pure(mag01) in alive", ensures = "share(mti) * share(mt) * pure(mag01) in alive")
	public final float nextFloat() {
		int y;
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
		if (mti >= N) {
			int kk;
			final int[] mt = this.mt;
			final int[] mag01 = this.mag01;
			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
		y ^= y >>> 11;
		y ^= (y << 7) & TEMPERING_MASK_B;
		y ^= (y << 15) & TEMPERING_MASK_C;
		y ^= (y >>> 18);
		return (y >>> 8) / ((float) (1 << 24));
	}
	@Perm(requires = "share(mti) * pure(mt) * pure(mag01) in alive", ensures = "share(mti) * pure(mt) * pure(mag01) in alive")
	public final int nextInt(final int n) {
		try {
			__haveNextNextGaussian_mag01_mt_mtiLock.readLock().lock();
			__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().lock();
			if (n <= 0)
				if ((n & -n) == n) {
					int y;
					if (mti >= N) {
						int kk;
						final int[] mt = this.mt;
						final int[] mag01 = this.mag01;
						for (kk = 0; kk < N - M; kk++) {
							y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
							mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
						}
						for (; kk < N - 1; kk++) {
							y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
							mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
						}
						y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
						mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
						mti = 0;
					}
					y = mt[mti++];
					y ^= y >>> 11;
					y ^= (y << 7) & TEMPERING_MASK_B;
					y ^= (y << 15) & TEMPERING_MASK_C;
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
						y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
						mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
					}
					for (; kk < N - 1; kk++) {
						y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
						mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
					}
					y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
					mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
					mti = 0;
				}
				y = mt[mti++];
				y ^= y >>> 11;
				y ^= (y << 7) & TEMPERING_MASK_B;
				y ^= (y << 15) & TEMPERING_MASK_C;
				y ^= (y >>> 18);
				bits = (y >>> 1);
				val = bits % n;
			} while (bits - val + (n - 1) < 0);
		} finally {
			__haveNextNextGaussian_mag01_mt_mtiLock.writeLock().unlock();
			__haveNextNextGaussian_mag01_mt_mtiLock.readLock().unlock();
		}
		return val;
	}
}

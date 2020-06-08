package random.entity.withlock;
import java.io.*;
import java.util.concurrent.atomic.AtomicLong;
import sun.misc.Unsafe;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Random implements java.io.Serializable {
	public static ReentrantReadWriteLock seedUniquifierLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock haveNextNextGaussian_nextNextGaussianLock = new ReentrantReadWriteLock();
	static final long serialVersionUID = 3905348978240129619L;
	private final AtomicLong seed;
	private final static long multiplier = 0x5DEECE66DL;
	private final static long addend = 0xBL;
	private final static long mask = (1L << 48) - 1;
	@Perm(requires = "unique(seedUniquifier) in alive", ensures = "unique(seedUniquifier) in alive")
	public Random() {
		seedUniquifierLock.writeLock().lock();
		this(++seedUniquifier + System.nanoTime());
		seedUniquifierLock.writeLock().unlock();
	}
	private static volatile long seedUniquifier = 8682522807148012L;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Random(long seed) {
		this.seed = new AtomicLong(0L);
		setSeed(seed);
	}
	@Perm(requires = "share(haveNextNextGaussian) in alive", ensures = "share(haveNextNextGaussian) in alive")
	synchronized public void setSeed(long seed) {
		seed = (seed ^ multiplier) & mask;
		this.seed.set(seed);
		haveNextNextGaussian_nextNextGaussianLock.writeLock().lock();
		haveNextNextGaussian = false;
		haveNextNextGaussian_nextNextGaussianLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected int next(int bits) {
		long oldseed, nextseed;
		AtomicLong seed = this.seed;
		do {
			oldseed = seed.get();
			nextseed = (oldseed * multiplier + addend) & mask;
		} while (!seed.compareAndSet(oldseed, nextseed));
		return (int) (nextseed >>> (48 - bits));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void nextBytes(byte[] bytes) {
		for (int i = 0, len = bytes.length; i < len;)
			for (int rnd = nextInt(), n = Math.min(len - i, Integer.SIZE / Byte.SIZE); n-- > 0; rnd >>= Byte.SIZE)
				bytes[i++] = (byte) rnd;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int nextInt() {
		return next(32);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int nextInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive");
		if ((n & -n) == n)
			return (int) ((n * (long) next(31)) >> 31);
		int bits, val;
		do {
			bits = next(31);
			val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public long nextLong() {
		return ((long) (next(32)) << 32) + next(32);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean nextBoolean() {
		return next(1) != 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public float nextFloat() {
		return next(24) / ((float) (1 << 24));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public double nextDouble() {
		return (((long) (next(26)) << 27) + next(27)) / (double) (1L << 53);
	}
	private double nextNextGaussian;
	private boolean haveNextNextGaussian = false;
	@Perm(requires = "share(haveNextNextGaussian) * share(nextNextGaussian) in alive", ensures = "share(haveNextNextGaussian) * share(nextNextGaussian) in alive")
	synchronized public double nextGaussian() {
		try {
			haveNextNextGaussian_nextNextGaussianLock.writeLock().lock();
			if (haveNextNextGaussian) {
				haveNextNextGaussian = false;
				return nextNextGaussian;
			} else {
				double v1, v2, s;
				do {
					v1 = 2 * nextDouble() - 1;
					v2 = 2 * nextDouble() - 1;
					s = v1 * v1 + v2 * v2;
				} while (s >= 1 || s == 0);
				double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
				nextNextGaussian = v2 * multiplier;
				haveNextNextGaussian = true;
				return v1 * multiplier;
			}
		} finally {
			haveNextNextGaussian_nextNextGaussianLock.writeLock().unlock();
		}
	}
	private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("seed", Long.TYPE),
			new ObjectStreamField("nextNextGaussian", Double.TYPE),
			new ObjectStreamField("haveNextNextGaussian", Boolean.TYPE)};
	@Perm(requires = "share(nextNextGaussian) * share(haveNextNextGaussian) in alive", ensures = "share(nextNextGaussian) * share(haveNextNextGaussian) in alive")
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		ObjectInputStream.GetField fields = s.readFields();
		long seedVal = (long) fields.get("seed", -1L);
		if (seedVal < 0)
			throw new java.io.StreamCorruptedException("Random: invalid seed");
		resetSeed(seedVal);
		haveNextNextGaussian_nextNextGaussianLock.writeLock().lock();
		nextNextGaussian = fields.get("nextNextGaussian", 0.0);
		haveNextNextGaussian = fields.get("haveNextNextGaussian", false);
		haveNextNextGaussian_nextNextGaussianLock.writeLock().unlock();
	}
	@Perm(requires = "pure(nextNextGaussian) * pure(haveNextNextGaussian) in alive", ensures = "pure(nextNextGaussian) * pure(haveNextNextGaussian) in alive")
	synchronized private void writeObject(ObjectOutputStream s) throws IOException {
		ObjectOutputStream.PutField fields = s.putFields();
		fields.put("seed", seed.get());
		haveNextNextGaussian_nextNextGaussianLock.readLock().lock();
		fields.put("nextNextGaussian", nextNextGaussian);
		fields.put("haveNextNextGaussian", haveNextNextGaussian);
		haveNextNextGaussian_nextNextGaussianLock.readLock().unlock();
		s.writeFields();
	}
	private static final Unsafe unsafe = Unsafe.getUnsafe();
	private static final long seedOffset;
	static {
		try {
			seedOffset = unsafe.objectFieldOffset(Random.class.getDeclaredField("seed"));
		} catch (Exception ex) {
			throw new Error(ex);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private void resetSeed(long seedVal) {
		unsafe.putObjectVolatile(this, seedOffset, new AtomicLong(seedVal));
	}
}

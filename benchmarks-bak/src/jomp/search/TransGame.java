package jomp.search.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class TransGame extends Game implements ConnectFourConstants {
	public ReentrantReadWriteLock columns_he_hits_ht_htindex_lock_posed_strideLock = new ReentrantReadWriteLock();
	static final int NSAMELOCK = 0x20000;
	static final int STRIDERANGE = (TRANSIZE / PROBES - NSAMELOCK);
	static final int INTMODSTRIDERANGE = (int) ((1L << 32) % STRIDERANGE);
	static final int ABSENT = -128;
	int ht[];
	byte he[];
	private int stride;
	private int htindex, lock;
	protected long posed, hits;
	@Perm(requires = "unique(ht) * unique(he) in alive", ensures = "unique(ht) * unique(he) in alive")
	public TransGame() {
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
		ht = new int[TRANSIZE];
		he = new byte[TRANSIZE];
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
	}
	@Perm(requires = "share(he) * share(posed) * share(hits) in alive", ensures = "share(he) * share(posed) * share(hits) in alive")
	void emptyTT() {
		int i, h, work;
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
		for (i = 0; i < TRANSIZE; i++)
			if ((work = (h = he[i]) & 31) < 31)
				he[i] = (byte) (h - (work < 16 ? work : 4));
		posed = hits = 0;
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
	}
	@Perm(requires = "pure(posed) * pure(hits) in alive", ensures = "pure(posed) * pure(hits) in alive")
	double hitRate() {
		try {
			columns_he_hits_ht_htindex_lock_posed_strideLock.readLock().lock();
			return posed != 0 ? (double) hits / (double) posed : 0.0;
		} finally {
			columns_he_hits_ht_htindex_lock_posed_strideLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(columns) * share(lock) * share(htindex) * share(stride) in alive", ensures = "pure(columns) * share(lock) * share(htindex) * share(stride) in alive")
	void hash() {
		int t1, t2;
		long htemp;
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
		t1 = (columns[1] << 7 | columns[2]) << 7 | columns[3];
		t2 = (columns[7] << 7 | columns[6]) << 7 | columns[5];
		htemp = t1 > t2 ? (long) (t1 << 7 | columns[4]) << 21 | t2 : (long) (t2 << 7 | columns[4]) << 21 | t1;
		lock = (int) (htemp >> 17);
		htindex = (int) (htemp % TRANSIZE);
		stride = NSAMELOCK + lock % STRIDERANGE;
		if (lock < 0) {
			if ((stride += INTMODSTRIDERANGE) < NSAMELOCK)
				stride += STRIDERANGE;
		}
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
	}
	@Perm(requires = "share(htindex) * pure(ht) * share(lock) * pure(he) * share(stride) in alive", ensures = "share(htindex) * pure(ht) * share(lock) * pure(he) * share(stride) in alive")
	int transpose() {
		try {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
			hash();
			for (int x = htindex, i = 0; i < PROBES; i++) {
				if (ht[x] == lock)
					return he[x];
				if ((x += stride) >= TRANSIZE)
					x -= TRANSIZE;
			}
		} finally {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
		}
		return ABSENT;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	String result() {
		int x;
		try {
			columns_he_hits_ht_htindex_lock_posed_strideLock.readLock().lock();
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
			return (x = transpose()) == ABSENT ? "n/a" : result(x);
		} finally {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
			columns_he_hits_ht_htindex_lock_posed_strideLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	String result(int x) {
		return "" + "##-<=>+#".charAt(4 + (x >> 5)) + "(" + (x & 31) + ")";
	}
	@Perm(requires = "share(posed) * share(htindex) * pure(ht) * share(lock) * share(hits) * share(he) * share(stride) in alive", ensures = "share(posed) * share(htindex) * pure(ht) * share(lock) * share(hits) * share(he) * share(stride) in alive")
	void transrestore(int score, int work) {
		int i, x;
		if (work > 31)
			work = 31;
		try {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
			posed++;
			hash();
			for (x = htindex, i = 0; i < PROBES; i++) {
				if (ht[x] == lock) {
					hits++;
					he[x] = (byte) (score << 5 | work);
					return;
				}
				if ((x += stride) >= TRANSIZE)
					x -= TRANSIZE;
			}
			transput(score, work);
		} finally {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(posed) in alive", ensures = "share(posed) in alive")
	void transtore(int score, int work) {
		if (work > 31)
			work = 31;
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
		posed++;
		hash();
		transput(score, work);
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
	}
	@Perm(requires = "pure(htindex) * share(he) * share(hits) * share(ht) * pure(lock) * pure(stride) in alive", ensures = "pure(htindex) * share(he) * share(hits) * share(ht) * pure(lock) * pure(stride) in alive")
	void transput(int score, int work) {
		try {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
			for (int x = htindex, i = 0; i < PROBES; i++) {
				if (work > (he[x] & 31)) {
					hits++;
					ht[x] = lock;
					he[x] = (byte) (score << 5 | work);
					return;
				}
				if ((x += stride) >= TRANSIZE)
					x -= TRANSIZE;
			}
		} finally {
			columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(he) in alive", ensures = "share(he) in alive")
	String htstat() {
		int total, i;
		StringBuffer buf = new StringBuffer();
		int works[];
		int typecnt[];
		works = new int[32];
		typecnt = new int[8];
		for (i = 0; i < 32; i++)
			works[i] = 0;
		for (i = 0; i < 8; i++)
			typecnt[i] = 0;
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().lock();
		for (i = 0; i < TRANSIZE; i++) {
			works[he[i] & 31]++;
			if ((he[i] & 31) != 0)
				typecnt[4 + (he[i] >> 5)]++;
		}
		for (total = i = 0; i < 8; i++)
			total += typecnt[i];
		if (total > 0)
			buf.append("store rate = " + hitRate() + "\n- " + typecnt[4 + LOSE] / (double) total + " < "
					+ typecnt[4 + DRAWLOSE] / (double) total + " = " + typecnt[4 + DRAW] / (double) total + " > "
					+ typecnt[4 + DRAWWIN] / (double) total + " + " + typecnt[4 + WIN] / (double) total + "\n");
		columns_he_hits_ht_htindex_lock_posed_strideLock.writeLock().unlock();
		for (i = 0; i < 32; i++) {
			buf.append(works[i]);
			buf.append((i & 7) == 7 ? '\n' : '\t');
		}
		return buf.toString();
	}
}

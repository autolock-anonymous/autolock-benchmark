package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ToTask implements java.io.Serializable {
	public ReentrantReadWriteLock header_randomSeedLock = new ReentrantReadWriteLock();
	private String header;
	private long randomSeed;
	@Perm(requires = "unique(header) * unique(randomSeed) in alive", ensures = "unique(header) * unique(randomSeed) in alive")
	public ToTask(String header, long randomSeed) {
		header_randomSeedLock.writeLock().lock();
		this.header = header;
		this.randomSeed = randomSeed;
		header_randomSeedLock.writeLock().unlock();
	}
	@Perm(requires = "pure(header) in alive", ensures = "pure(header) in alive")
	public String getheader() {
		try {
			header_randomSeedLock.readLock().lock();
			return (this.header);
		} finally {
			header_randomSeedLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(header) in alive", ensures = "full(header) in alive")
	public void setheader(String header) {
		header_randomSeedLock.writeLock().lock();
		this.header = header;
		header_randomSeedLock.writeLock().unlock();
	}
	@Perm(requires = "pure(randomSeed) in alive", ensures = "pure(randomSeed) in alive")
	public long getrandomSeed() {
		try {
			header_randomSeedLock.readLock().lock();
			return (this.randomSeed);
		} finally {
			header_randomSeedLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(randomSeed) in alive", ensures = "full(randomSeed) in alive")
	public void setrandomSeed(long randomSeed) {
		header_randomSeedLock.writeLock().lock();
		this.randomSeed = randomSeed;
		header_randomSeedLock.writeLock().unlock();
	}
}

package aeminium.gaknapsack.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Indiv implements Comparable<Indiv> {
	public ReentrantReadWriteLock has_sizeLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock fitnessLock = new ReentrantReadWriteLock();
	public boolean[] has;
	public double fitness = 0;
	public int size;
	boolean flag;
	@Perm(requires = "unique(size) * unique(has) in alive", ensures = "unique(size) * unique(has) in alive")
	public Indiv(int size) {
		has_sizeLock.writeLock().lock();
		this.size = size;
		this.has = new boolean[size];
		has_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "share(has) in alive", ensures = "share(has) in alive")
	public void set(int w, boolean h) {
		has_sizeLock.writeLock().lock();
		has[w] = h;
		has_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(fitness) in alive", ensures = "pure(fitness) in alive")
	public int compareTo(Indiv other) {
		try {
			fitnessLock.readLock().lock();
			if (this.fitness == other.fitness) {
				return 0;
			} else if (this.fitness > other.fitness) {
				return 1;
			} else {
				return -1;
			}
		} finally {
			fitnessLock.readLock().unlock();
		}
	}
}

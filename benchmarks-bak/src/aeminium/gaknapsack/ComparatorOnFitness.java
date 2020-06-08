package aeminium.gaknapsack.withlock;
import java.util.Comparator;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ComparatorOnFitness implements Comparator<Integer> {
	public ReentrantReadWriteLock popFitLock = new ReentrantReadWriteLock();
	double[] popFit;
	@Perm(requires = "unique(popFit) in alive", ensures = "unique(popFit) in alive")
	public ComparatorOnFitness(double[] ft) {
		popFitLock.writeLock().lock();
		popFit = ft;
		popFitLock.writeLock().unlock();
	}
	@Perm(requires = "none(popFit) in alive", ensures = "none(popFit) in alive")
	public int compare(Integer a, Integer b) {
		if (popFit[b] == popFit[a])
			return 0;
		if (popFit[b] - popFit[a] > 0)
			return 1;
		return -1;
	}
}

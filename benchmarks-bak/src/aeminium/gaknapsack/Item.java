package aeminium.gaknapsack.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Item {
	public ReentrantReadWriteLock name_value_weightLock = new ReentrantReadWriteLock();
	@Perm(requires = "unique(name) * unique(weight) * unique(value) in alive", ensures = "unique(name) * unique(weight) * unique(value) in alive")
	public Item(String n, int w, int v) {
		name_value_weightLock.writeLock().lock();
		this.name = n;
		this.weight = w;
		this.value = v;
		name_value_weightLock.writeLock().unlock();
	}
	public String name;
	public int weight;
	public int value;
}

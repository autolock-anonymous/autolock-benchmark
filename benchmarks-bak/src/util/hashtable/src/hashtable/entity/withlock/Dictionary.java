package hashtable.entity.withlock;
import java.util.Enumeration;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public abstract class Dictionary<K, V> {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Dictionary() {
	}
	abstract public int size();
	abstract public boolean isEmpty();
	abstract public Enumeration<K> keys();
	abstract public Enumeration<V> elements();
	abstract public V get(Object key);
	abstract public V put(K key, V value);
	abstract public V remove(Object key);
}

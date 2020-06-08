package synchronizedmapjava16.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface SortedMap<K, V> extends Map<K, V> {
	Comparator<? super K> comparator();
	SortedMap<K, V> subMap(K fromKey, K toKey);
	SortedMap<K, V> headMap(K toKey);
	SortedMap<K, V> tailMap(K fromKey);
	K firstKey();
	K lastKey();
	Set<K> keySet();
	Collection<V> values();
	Set<Entry<K, V>> entrySet();
}

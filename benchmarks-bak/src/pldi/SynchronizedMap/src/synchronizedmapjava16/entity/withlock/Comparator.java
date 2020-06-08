package synchronizedmapjava16.entity.withlock;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface Comparator<T> {
	int compare(T o1, T o2);
	boolean equals(Object obj);
}
